package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.impl.ReservaDaoImpl;
import controller.exception.BusinessRuleException;
import controller.util.ControllerUtils;
import model.Reserva;
import model.StatusReserva;
import model.StatusVeiculo;
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
    
    private void validarReserva(Reserva reserva) throws BusinessRuleException {
    	if (reserva.getCliente() == null || reserva.getCliente().getId() == 0) {
    		throw new BusinessRuleException("Cliente não informado.");
    	}
    	
    	if (!usuarioSistema.isAdministrador() && reserva.getCliente().getId() != usuarioSistema.getId()) {
    		throw new BusinessRuleException("Usuário não administrador não pode fazer reservas para outro cliente.");
    	}
    	
    	reserva.setCliente(usuarioController.buscarPorId(reserva.getCliente().getId()));
    	
    	if (!reserva.getCliente().isAtivo()) {
    		throw new BusinessRuleException("Cliente inativo no sistema.");
    	}
    	
    	if (reserva.getVeiculo() == null || reserva.getVeiculo().getId() == 0) {
    		throw new BusinessRuleException("Veiculo não informado.");
    	}
    	
    	reserva.setVeiculo(veiculoController.buscarPorId(reserva.getVeiculo().getId(), reserva.getDtContratacao(),
    			reserva.getDtDevolucao() == null ? reserva.getDtPrevDevolucao() : reserva.getDtDevolucao(),
    					reserva.getId(), 0));
    	
    	if (!reserva.getVeiculo().isAtivo()) {
    		throw new BusinessRuleException("Veículo inativo no sistema.");
    	}
    	
    	if (reserva.getStatus() == null) {
    		throw new BusinessRuleException("Status não informado.");
    	}
    	
    	if (reserva.getStatus() == StatusReserva.EM_ANDAMENTO && reserva.getVeiculo().getStatus() != StatusVeiculo.DISPONIVEL) {
    		throw new BusinessRuleException("Veículo indisponível para o período da reserva.");
    	}

    	if (reserva.getDtContratacao() == null) {
    		throw new BusinessRuleException("Data de contratação não informada.");
    	}
    	
    	if (reserva.getDtPrevDevolucao() == null) {
    		throw new BusinessRuleException("Data prevista de devolução não informada.");
    	}
    	
    	if (reserva.getDtContratacao().compareTo(reserva.getDtPrevDevolucao()) >= 0) {
    		throw new BusinessRuleException("Data prevista de devolução deve ser maior que a data de contratação.");
    	}
    	
    	if (reserva.getDtDevolucao() != null && reserva.getDtContratacao().compareTo(reserva.getDtDevolucao()) >= 0) {
    		throw new BusinessRuleException("Data de devolução deve ser maior que a data de contratação.");
    	}
    	
    	Date dataDeAgora = new Date(Calendar.getInstance().getTimeInMillis());
    	if (ControllerUtils.contarDias(reserva.getDtContratacao(), reserva.getDtPrevDevolucao()) > 7) {
    		throw new BusinessRuleException("Não é possível fazer uma reserva com duração maior que uma semana.");
    	}
    	
    	if (ControllerUtils.contarDias(dataDeAgora, reserva.getDtContratacao()) > 7) {
    		throw new BusinessRuleException("Não é possível fazer uma reserva com mais de uma semana de antecedência.");
    	}
    	
    	if (reserva.getStatus() == StatusReserva.PAGO && reserva.getDtDevolucao() == null) {
    		throw new BusinessRuleException("Data devolução não informada. É obrigatório informar ao pagar.");
    	}
    	
    	if (reserva.getStatus() != StatusReserva.PAGO && reserva.getDtDevolucao() != null) {
    		throw new BusinessRuleException("Só é possível informar a data de devolução no momento do pagamento da reserva.");
    	}
    	
    	if (!usuarioSistema.isAdministrador() 
    			&& reserva.getStatus() == StatusReserva.CANCELADO
    			&& dataDeAgora.compareTo(reserva.getDtContratacao()) >= 0) {
    		throw new BusinessRuleException("Apenas administradores podem cancelar após passar da data de contratação.");
    	}
    }
    
    public Reserva salvar(Reserva reserva) throws BusinessRuleException {
        try {
            validarReserva(reserva);
            return reservaDao.salvar(reserva);
        } catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao cadastrar reserva no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public void editar(Reserva reserva) throws BusinessRuleException {
    	try {
    		validarReserva(reserva);
	        reservaDao.editar(reserva);
    	} catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao editar reserva no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Reserva buscarPorId(int id) throws BusinessRuleException {
    	try {
			Reserva reserva = reservaDao.buscarPorId(id);
			if (reserva == null) {
				throw new BusinessRuleException("Não existe reserva cadastrada com esse id.");
			}
			if (!usuarioSistema.isAdministrador() && reserva.getCliente().getId() != usuarioSistema.getId()) {
	    		throw new BusinessRuleException("Usuário não administrador não pode visualizar reserva de outro cliente.");
	    	}
			reserva.setCliente(usuarioController.buscarPorId(reserva.getCliente().getId()));
			reserva.setVeiculo(veiculoController.buscarPorId(reserva.getVeiculo().getId(), null, null, 0, 0));
			return reserva;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao buscar reserva por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Reserva> listarReservas(Reserva reserva, Date dtContFinal, Date dtPrevDevFinal, Date dtDevFinal) throws BusinessRuleException {
    	try {
    		List<Reserva> listaReservas = reservaDao.listarReservas(reserva, dtContFinal, dtPrevDevFinal, dtDevFinal);
			for (Reserva reservaLista : listaReservas) {
				if (!usuarioSistema.isAdministrador() && reservaLista.getCliente().getId() != usuarioSistema.getId()) {
					throw new BusinessRuleException("Usuário não administrador não pode visualizar reserva de outro cliente.");
				}
				reservaLista.setCliente(usuarioController.buscarPorId(reservaLista.getCliente().getId()));
				reservaLista.setVeiculo(veiculoController.buscarPorId(reservaLista.getVeiculo().getId(), null, null, 0, 0));
			}
			return listaReservas;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao listar reservas no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public int getQtdeReservasPorCliente(int idCliente) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador() && idCliente != usuarioSistema.getId()) {
    		throw new BusinessRuleException("Usuário não administrador não pode visualizar reserva de outro cliente.");
    	}
    	try {
    		return reservaDao.getQtdeReservasPorCliente(idCliente);
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao contar reservas por cliente no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public int getQtdeReservasPorVeiculo(int idVeiculo) throws BusinessRuleException {
    	try {
    		return reservaDao.getQtdeReservasPorVeiculo(idVeiculo);
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao contar reservas por veículo no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public Usuario buscarClientePorId(int idCliente) throws BusinessRuleException {
    	return usuarioController.buscarPorId(idCliente);
    }
    
    public Veiculo buscarVeiculoPorId(int idVeiculo) throws BusinessRuleException {
    	return veiculoController.buscarPorId(idVeiculo, null, null, 0, 0);
    }
    
    public Reserva calcularValoresReserva(Reserva reserva, boolean usarDadosVeiculo){
		if (reserva != null
				&& (!usarDadosVeiculo || reserva.getVeiculo() != null)) {
			if (usarDadosVeiculo) {
				reserva.setValorDiaria(reserva.getVeiculo().getCategoria().getValorDiaria());
				reserva.setPercMultaDiaria(reserva.getVeiculo().getCategoria().getPercMultaDiaria());
			}
			if (reserva.getDtContratacao() != null && reserva.getDtPrevDevolucao() != null) {
				reserva.setValorTotalPrev(reserva.getDiasPrev() * reserva.getValorDiaria());
				reserva.setMultaTotal(reserva.getDiasAtraso() * (reserva.getValorDiaria() / 100 * reserva.getPercMultaDiaria()));
				reserva.setValorTotal(reserva.getTotalDias() * reserva.getValorDiaria() + reserva.getMultaTotal());
			}
		}
		return reserva;
	}
    
}
