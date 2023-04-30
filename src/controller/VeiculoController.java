package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.ManutencaoDao;
import controller.dao.VeiculoDao;
import controller.dao.impl.ReservaDaoImpl;
import controller.dao.impl.ManutencaoDaoImpl;
import controller.dao.impl.VeiculoDaoImpl;
import controller.exception.BusinessRuleException;
import model.StatusVeiculo;
import model.Usuario;
import model.Veiculo;

public class VeiculoController {

	private VeiculoDao veiculoDao;
    private ReservaDao reservaDao;
    private ManutencaoDao manutencaoDao;
    private Usuario usuarioSistema;

    public VeiculoController(Usuario usuarioSistema) {
    	veiculoDao = new VeiculoDaoImpl();
        reservaDao = new ReservaDaoImpl();
        manutencaoDao = new ManutencaoDaoImpl();
        this.usuarioSistema = usuarioSistema;
    }
    
    private void validarVeiculo(Veiculo veiculo) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessRuleException("Usuário não administrador não pode cadastrar ou editar veículos.");
    	}
    	
    	if (veiculo.getMarca().trim().equals("")) {
    		throw new BusinessRuleException("Marca não informada.");
    	}
    	
    	if (veiculo.getModelo().trim().equals("")) {
    		throw new BusinessRuleException("Modelo não informado.");
    	}
    	
    	if (veiculo.getAno() == 0) {
    		throw new BusinessRuleException("Ano não informado.");
    	}
    	
    	if (veiculo.getCor().trim().equals("")) {
    		throw new BusinessRuleException("Cor não informada.");
    	}
    	
    	if (veiculo.getCategoria() == null) {
    		throw new BusinessRuleException("Categoria não informada.");
    	}
    	
    	if (veiculo.getPlaca().trim().equals("")) {
    		throw new BusinessRuleException("Placa não informada.");
    	}
    }
    
    public Veiculo salvar(Veiculo veiculo) throws BusinessRuleException {
        try {
            validarVeiculo(veiculo);
            return veiculoDao.salvar(veiculo);
        } catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao cadastrar veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public void editar(Veiculo veiculo) throws BusinessRuleException {
    	try {
    		validarVeiculo(veiculo);
	        veiculoDao.editar(veiculo);
    	} catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao editar veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Veiculo buscarPorId(int id, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws BusinessRuleException {
    	try {
			Veiculo veiculo = veiculoDao.buscarPorId(id, dataInicial, dataFinal, idReserva, idManutencao);
			if (veiculo == null) {
				throw new BusinessRuleException("Não existe veículo cadastrado com esse id.");
			}
	    	return veiculo;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao buscar veículo por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public void excluir(int id) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessRuleException("Usuário não administrador não pode excluir veículos.");
    	}
    	try {
    		if (reservaDao.getQtdeReservasPorVeiculo(id) > 0) {
    			throw new BusinessRuleException("Não é possível excluir esse veículo porque ele possui reserva cadastrada no sistema.");
    		}
    		if (manutencaoDao.getQtdeManutencoesPorVeiculo(id) > 0) {
    			throw new BusinessRuleException("Não é possível excluir esse veículo porque ele possui manutenção cadastrada no sistema.");
    		}
			veiculoDao.excluir(id);
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao excluir veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Veiculo> listarVeiculos(Veiculo veiculo, boolean checarAtivo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws BusinessRuleException {
    	try {
			return veiculoDao.listarVeiculos(veiculo, checarAtivo, dataInicial, dataFinal, idReserva, idManutencao);
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao listar veículos no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public StatusVeiculo buscarStatusVeiculo(int idVeiculo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws BusinessRuleException {
    	try {
			return veiculoDao.buscarStatusVeiculo(idVeiculo, dataInicial, dataFinal, idReserva, idManutencao);
		} catch (SQLException e) {
			throw new BusinessRuleException("Falha técnica ao buscar status de um veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + e.getMessage());
		}
    }
}
