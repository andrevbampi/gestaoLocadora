package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import controller.dao.ManutencaoDao;
import controller.dao.impl.ManutencaoDaoImpl;
import controller.exception.BusinessRuleException;
import model.Manutencao;
import model.Usuario;
import model.Veiculo;

public class ManutencaoController {

	private ManutencaoDao manutencaoDao;
	private VeiculoController veiculoController;
	private Usuario usuarioSistema;

    public ManutencaoController(Usuario usuarioSistema) {
    	manutencaoDao = new ManutencaoDaoImpl();
    	this.usuarioSistema = usuarioSistema;
    	veiculoController = new VeiculoController(usuarioSistema);
    }
    
    private void validarManutencao(Manutencao manutencao) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessRuleException("Usuário não administrador não pode cadastrar ou editar manutenções.");
    	}
    	if (manutencao.getDescricao().trim().equals("")) {
    		throw new BusinessRuleException("Descrição não informada.");
    	}
    	if (manutencao.getDtInicio() == null) {
    		throw new BusinessRuleException("Data de início não informada.");
    	}
    	if (manutencao.getDtPrevFim() == null) {
    		throw new BusinessRuleException("Data prevista de término não informada.");
    	}
    	veiculoController.buscarPorId(manutencao.getVeiculo().getId(), manutencao.getDtInicio(),
    			manutencao.getDtFim() == null ? manutencao.getDtPrevFim() : manutencao.getDtFim(),
    					0, manutencao.getId());
    	if (manutencao.getStatus() == null) {
    		throw new BusinessRuleException("Status não informado.");
    	}
    }
    
    public Manutencao salvar(Manutencao manutencao) throws BusinessRuleException {
        try {
            validarManutencao(manutencao);
            return manutencaoDao.salvar(manutencao);
        } catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao cadastrar manutenção no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public void editar(Manutencao manutencao) throws BusinessRuleException {
    	try {
    		validarManutencao(manutencao);
	        manutencaoDao.editar(manutencao);
    	} catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao editar manutenção no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Manutencao buscarPorId(int id) throws BusinessRuleException {
    	try {
    		if (!usuarioSistema.isAdministrador()) {
	    		throw new BusinessRuleException("Usuário não administrador não pode visualizar manutenções.");
	    	}
			Manutencao manutencao = manutencaoDao.buscarPorId(id);
			if (manutencao == null) {
				throw new BusinessRuleException("Não existe manutenção cadastrada com esse id.");
			}
			manutencao.setVeiculo(veiculoController.buscarPorId(manutencao.getVeiculo().getId(), null, null, 0, 0));
			return manutencao;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao buscar manutenção por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public void excluir(int id) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessRuleException("Usuário não administrador não pode excluir manutenções.");
    	}
    	try {
			manutencaoDao.excluir(id);
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao excluir manutenção no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Manutencao> listarManutencoes(Manutencao manutencao, Date dtInicioFinal, Date dtPrevFimFinal, Date dtFimFinal) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
			throw new BusinessRuleException("Usuário não administrador não pode visualizar manutenções.");
		}
    	try {
    		List<Manutencao> listaManutencoes = manutencaoDao.listarManutencoes(manutencao, dtInicioFinal, dtPrevFimFinal, dtFimFinal);
			for (Manutencao manutencaoLista : listaManutencoes) {
				manutencaoLista.setVeiculo(veiculoController.buscarPorId(manutencaoLista.getVeiculo().getId(), null, null, 0, 0));
			}
			return listaManutencoes;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao listar manutenções no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public int getQtdeManutencoesPorVeiculo(int idVeiculo) throws BusinessRuleException {
    	try {
    		return manutencaoDao.getQtdeManutencoesPorVeiculo(idVeiculo);
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao contar manutenções por veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public Veiculo buscarVeiculoPorId(int idVeiculo) throws BusinessRuleException {
    	return veiculoController.buscarPorId(idVeiculo, null, null, 0, 0);
    }
}
