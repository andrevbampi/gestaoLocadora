package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;

import controller.dao.EnderecoDao;
import controller.dao.impl.EnderecoDaoImpl;
import controller.exception.BusinessException;
import model.Endereco;

public class EnderecoController {

    public EnderecoDao enderecoDao;

    public EnderecoController() {
    	enderecoDao = new EnderecoDaoImpl();
    }

    public Endereco salvar(Endereco endereco) throws BusinessException {
        try {
            validarEndereco(endereco);
            int idEnderecoCompleto = enderecoDao.buscarIdEnderecoCompleto(endereco); 
            if (idEnderecoCompleto == 0) {
            	return enderecoDao.salvar(endereco);
            } else {
            	endereco.setId(idEnderecoCompleto);
            	return endereco;
            }
        } catch (SQLException ex) {
        	throw new BusinessException("Falha técnica ao cadastrar endereço no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }

    private void validarEndereco(Endereco endereco) throws BusinessException {
    	if (endereco.getCep() == 0) {
    		throw new BusinessException("CEP não informado.");
    	}
    	if (endereco.getLogradouro().trim().equals("")) {
    		throw new BusinessException("Logradouro não informado.");
    	}
    	if (endereco.getNumero() == 0) {
    		throw new BusinessException("Número não informado.");
    	}
    	if (endereco.getBairro().trim().equals("")) {
    		throw new BusinessException("Bairro não informado.");
    	}
    	if (endereco.getCidade().trim().equals("")) {
    		throw new BusinessException("Cidade não informada.");
    	}
    	if (endereco.getUf().trim().equals("")) {
    		throw new BusinessException("Estado não informado.");
    	}
        if (endereco.getPais().trim().equals("")) {
    		throw new BusinessException("País não informado.");
    	}
    }

    public Endereco buscarPorId(int id) throws BusinessException {
    	try {
			Endereco endereco = enderecoDao.buscarPorId(id);
			if (endereco == null) {
				throw new BusinessException("Não existe endereço cadastrado com esse id.");
			}
	    	return endereco;
		} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao buscar endereço por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }

    public void excluir(int id) throws BusinessException {
    	try {
			enderecoDao.excluir(id);
    	} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao excluir endereço no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }

    public Endereco buscarDadosViaCep(int cep) {
    	try {
			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String strCep = "";
			StringBuilder jsonCep = new StringBuilder();
			while ((strCep = br.readLine()) != null) {
				jsonCep.append(strCep);
			}
			Map<?, ?> dadosCep = new Gson().fromJson(jsonCep.toString(), Map.class);
			if (dadosCep.get("erro") == null) {
				Endereco endereco = new Endereco(0);
				endereco.setCep(cep);
				endereco.setLogradouro(dadosCep.get("logradouro").toString());
				endereco.setComplemento(dadosCep.get("complemento").toString());
				endereco.setBairro(dadosCep.get("bairro").toString());
				endereco.setCidade(dadosCep.get("localidade").toString());
				endereco.setUf(dadosCep.get("uf").toString());
				endereco.setPais("Brasil");
				return endereco;
			}
		} catch (IOException e) {}
    	return null;
    }
    
}
