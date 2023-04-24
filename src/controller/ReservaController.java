package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.impl.ReservaDaoImpl;
import controller.exception.BusinessException;
import model.Reserva;
import model.Usuario;
import model.Veiculo;

public class ReservaController {
	
	private ReservaDao reservaDao;
	private UsuarioController usuarioController;
	private VeiculoController veiculoController;
	private Usuario usuarioSistema;

    public ReservaController(Usuario usuarioSistema) {
    	reservaDao = new ReservaDaoImpl();
    	this.usuarioSistema = usuarioSistema;
    	usuarioController = new UsuarioController(usuarioSistema);
    	veiculoController = new VeiculoController(usuarioSistema);
    }
    
    private void validarReserva(Reserva reserva) throws BusinessException {
    	if (reserva.getCliente() == null || reserva.getCliente().getId() == 0) {
    		throw new BusinessException("Cliente não informado.");
    	}
    	if (!usuarioSistema.isAdministrador() && reserva.getCliente().getId() != usuarioSistema.getId()) {
    		throw new BusinessException("Usuário não administrador não pode fazer reservas para outro cliente.");
    	}
    	usuarioController.buscarPorId(reserva.getCliente().getId());
    	if (reserva.getVeiculo() == null || reserva.getVeiculo().getId() == 0) {
    		throw new BusinessException("Veiculo não informado.");
    	}
    	veiculoController.buscarPorId(reserva.getVeiculo().getId());
    	if (reserva.getDtContratacao() == null) {
    		throw new BusinessException("Data de contratação não informada.");
    	}
    	if (reserva.getDtPrevDevolucao() == null) {
    		throw new BusinessException("Data prevista de devolução não informada.");
    	}
    	if (reserva.getStatus() == null) {
    		throw new BusinessException("Status não informado.");
    	}
    }
    
    public Reserva salvar(Reserva reserva) throws BusinessException {
        try {
            validarReserva(reserva);
            return reservaDao.salvar(reserva);
        } catch (SQLException ex) {
        	throw new BusinessException("Falha técnica ao cadastrar reserva no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public void editar(Reserva reserva) throws BusinessException {
    	try {
    		validarReserva(reserva);
	        reservaDao.editar(reserva);
    	} catch (SQLException ex) {
        	throw new BusinessException("Falha técnica ao editar reserva no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Reserva buscarPorId(int id) throws BusinessException {
    	try {
			Reserva reserva = reservaDao.buscarPorId(id);
			if (reserva == null) {
				throw new BusinessException("Não existe reserva cadastrada com esse id.");
			}
			if (!usuarioSistema.isAdministrador() && reserva.getCliente().getId() != usuarioSistema.getId()) {
	    		throw new BusinessException("Usuário não administrador não pode visualizar reserva de outro cliente.");
	    	}
			reserva.setCliente(usuarioController.buscarPorId(reserva.getCliente().getId()));
			reserva.setVeiculo(veiculoController.buscarPorId(reserva.getVeiculo().getId()));
			return reserva;
		} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao buscar reserva por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public void excluir(int id) throws BusinessException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessException("Usuário não administrador não pode excluir reservas.");
    	}
    	try {
			reservaDao.excluir(id);
    	} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao excluir reserva no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Reserva> listarReservas(Reserva reserva, Date dtContFinal, Date dtPrevDevFinal, Date dtDevFinal) throws BusinessException {
    	try {
    		List<Reserva> listaReservas = reservaDao.listarReservas(reserva, dtContFinal, dtPrevDevFinal, dtDevFinal);
			for (Reserva reservaLista : listaReservas) {
				if (!usuarioSistema.isAdministrador() && reservaLista.getCliente().getId() != usuarioSistema.getId()) {
					throw new BusinessException("Usuário não administrador não pode visualizar reserva de outro cliente.");
				}
				reservaLista.setCliente(usuarioController.buscarPorId(reservaLista.getCliente().getId()));
				reservaLista.setVeiculo(veiculoController.buscarPorId(reservaLista.getVeiculo().getId()));
			}
			return listaReservas;
		} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao listar reservas no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public int getQtdeReservasPorCliente(int idCliente) throws BusinessException {
    	if (!usuarioSistema.isAdministrador() && idCliente != usuarioSistema.getId()) {
    		throw new BusinessException("Usuário não administrador não pode visualizar reserva de outro cliente.");
    	}
    	try {
    		return reservaDao.getQtdeReservasPorCliente(idCliente);
    	} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao contar reservas por cliente no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public int getQtdeReservasPorVeiculo(int idVeiculo) throws BusinessException {
    	try {
    		return reservaDao.getQtdeReservasPorVeiculo(idVeiculo);
    	} catch (SQLException ex) {
			throw new BusinessException("Falha técnica ao contar reservas por veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public Usuario buscarClientePorId(int idCliente) throws BusinessException {
    	return usuarioController.buscarPorId(idCliente);
    }
    
    public Veiculo buscarVeiculoPorId(int idVeiculo) throws BusinessException {
    	return veiculoController.buscarPorId(idVeiculo);
    }
    
}
