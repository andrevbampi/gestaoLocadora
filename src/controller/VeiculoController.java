package controller;

import java.sql.SQLException;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.VeiculoDao;
import controller.dao.impl.ReservaDaoImpl;
import controller.dao.impl.VeiculoDaoImpl;
import controller.exception.BusinessException;
import model.Usuario;
import model.Veiculo;

public class VeiculoController {

	private VeiculoDao veiculoDao;
    private ReservaDao reservaDao;
    private Usuario usuarioSistema;

    public VeiculoController(Usuario usuarioSistema) {
    	veiculoDao = new VeiculoDaoImpl();
        reservaDao = new ReservaDaoImpl();
        this.usuarioSistema = usuarioSistema;
    }
    
    private void validarVeiculo(Veiculo veiculo) throws BusinessException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessException("Usuário não administrador não pode cadastrar ou editar veículos.");
    	}
    	if (veiculo.getMarca().trim().equals("")) {
    		throw new BusinessException("Marca não informada.");
    	}
    	if (veiculo.getModelo().trim().equals("")) {
    		throw new BusinessException("Modelo não informado.");
    	}
    	if (veiculo.getAno() == 0) {
    		throw new BusinessException("Ano não informado.");
    	}
    	if (veiculo.getCor().trim().equals("")) {
    		throw new BusinessException("Cor não informada.");
    	}
    	if (veiculo.getStatus() == null) {
    		throw new BusinessException("Status não informado.");
    	}
    	if (veiculo.getCategoria() == null) {
    		throw new BusinessException("Categoria não informada.");
    	}
    }
    
    public Veiculo salvar(Veiculo veiculo) throws BusinessException {
        try {
            validarVeiculo(veiculo);
            return veiculoDao.salvar(veiculo);
        } catch (SQLException ex) {
        	throw new BusinessException("Falha técnica ao cadastrar veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public void editar(Veiculo veiculo) throws BusinessException {
    	try {
    		validarVeiculo(veiculo);
	        veiculoDao.editar(veiculo);
    	} catch (SQLException ex) {
        	throw new BusinessException("Falha técnica ao editar veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Veiculo buscarPorId(int id) throws BusinessException {
    	try {
			Veiculo veiculo = veiculoDao.buscarPorId(id);
			if (veiculo == null) {
				throw new BusinessException("Não existe veículo cadastrado com esse id.");
			}
	    	return veiculo;
		} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao buscar veículo por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public void excluir(int id) throws BusinessException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessException("Usuário não administrador não pode excluir veículos.");
    	}
    	try {
    		if (reservaDao.getQtdeReservasPorVeiculo(id) > 0) {
    			throw new BusinessException("Não é possível excluir esse veículo porque ele possui reserva no sistema.");
    		}
			veiculoDao.excluir(id);
    	} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao excluir veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Veiculo> listarVeiculos(Veiculo veiculo) throws BusinessException {
    	try {
			return veiculoDao.listarVeiculos(veiculo);
		} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao listar veículos no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
}
