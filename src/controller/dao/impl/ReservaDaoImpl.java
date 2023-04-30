package controller.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.util.ConnectionMariaDB;
import model.Reserva;
import model.StatusReserva;
import model.Usuario;
import model.Veiculo;

public class ReservaDaoImpl implements ReservaDao {

	@Override
	public Reserva salvar(Reserva reserva) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("insert into " + Reserva.TABELA_RESERVA);
	        sb.append("( ");
	        sb.append(Reserva.CAMPO_ID + ", ");
	        sb.append(Reserva.CAMPO_IDCLIENTE + ", ");
	        sb.append(Reserva.CAMPO_IDVEICULO + ", ");
	        sb.append(Reserva.CAMPO_DTCONTRATACAO + ", ");
	        sb.append(Reserva.CAMPO_DTPREVDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_DTDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_STATUS + ", ");
	        sb.append(Reserva.CAMPO_VALORDIARIA + ", ");
	        sb.append(Reserva.CAMPO_PERCMULTADIARIA + ", ");
	        sb.append(Reserva.CAMPO_MULTATOTAL + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTALPREV + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTAL + ")");
	        sb.append(" values(?,?,?,?,?,?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        reserva.setId(ConnectionMariaDB.nextId(Reserva.TABELA_RESERVA, Reserva.CAMPO_ID));
	        ps.setInt(1, reserva.getId());
	        ps.setInt(2, reserva.getCliente().getId());
	        ps.setInt(3, reserva.getVeiculo().getId());
	        if (reserva.getDtContratacao() == null) {
	        	ps.setTimestamp(4, null);
	        } else {
	        	ps.setTimestamp(4, new Timestamp(reserva.getDtContratacao().getTime()));
	        }
	        if (reserva.getDtPrevDevolucao() == null) {
	        	ps.setTimestamp(5, null);
	        } else {
	        	ps.setTimestamp(5, new Timestamp(reserva.getDtPrevDevolucao().getTime()));
	        }
	        if (reserva.getDtDevolucao() == null) {
	        	ps.setTimestamp(6, null);
	        } else {
	        	ps.setTimestamp(6, new Timestamp(reserva.getDtDevolucao().getTime()));
	        }
	        ps.setInt(7, reserva.getStatus().ordinal());
	        ps.setDouble(8, reserva.getValorDiaria());
	        ps.setDouble(9, reserva.getPercMultaDiaria());
	        ps.setDouble(10, reserva.getMultaTotal());
	        ps.setDouble(11, reserva.getValorTotalPrev());
	        ps.setDouble(12, reserva.getValorTotal());
	        
	        if (!ps.execute()) {
	        	return reserva;
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public void editar(Reserva reserva) throws SQLException {
        ConnectionMariaDB.getConnection();
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("update " + Reserva.TABELA_RESERVA);
	        sb.append(" set ");
	        sb.append(Reserva.CAMPO_IDCLIENTE + " = ?, ");
	        sb.append(Reserva.CAMPO_IDVEICULO + " = ?, ");
	        sb.append(Reserva.CAMPO_DTCONTRATACAO + " = ?, ");
	        sb.append(Reserva.CAMPO_DTPREVDEVOLUCAO + " = ?, ");
	        sb.append(Reserva.CAMPO_DTDEVOLUCAO + " = ?, ");
	        sb.append(Reserva.CAMPO_STATUS + " = ?, ");
	        sb.append(Reserva.CAMPO_VALORDIARIA + " = ?, ");
	        sb.append(Reserva.CAMPO_PERCMULTADIARIA + " = ?, ");
	        sb.append(Reserva.CAMPO_MULTATOTAL  + " = ?, ");
	        sb.append(Reserva.CAMPO_VALORTOTALPREV + " = ?, ");
	        sb.append(Reserva.CAMPO_VALORTOTAL + " = ?");
	        sb.append(" where ");
	        sb.append(Reserva.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, reserva.getCliente().getId());
	        ps.setInt(2, reserva.getVeiculo().getId());
	        if (reserva.getDtContratacao() == null) {
	        	ps.setTimestamp(3, null);
	        } else {
	        	ps.setTimestamp(3, new Timestamp(reserva.getDtContratacao().getTime()));
	        }
	        if (reserva.getDtPrevDevolucao() == null) {
	        	ps.setTimestamp(4, null);
	        } else {
	        	ps.setTimestamp(4, new Timestamp(reserva.getDtPrevDevolucao().getTime()));
	        }
	        if (reserva.getDtDevolucao() == null) {
	        	ps.setTimestamp(5, null);
	        } else {
	        	ps.setTimestamp(5, new Timestamp(reserva.getDtDevolucao().getTime()));
	        }
	        ps.setInt(6, reserva.getStatus().ordinal());
	        ps.setDouble(7, reserva.getValorDiaria());
	        ps.setDouble(8, reserva.getPercMultaDiaria());
	        ps.setDouble(9, reserva.getMultaTotal());
	        ps.setDouble(10, reserva.getValorTotalPrev());
	        ps.setDouble(11, reserva.getValorTotal());
	        ps.setInt(12, reserva.getId());
	        
	        ps.execute();
        } finally {
        	ConnectionMariaDB.closeConnection();	
        }
	}

	@Override
	public Reserva buscarPorId(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Reserva.CAMPO_ID + ", ");
	        sb.append(Reserva.CAMPO_IDCLIENTE + ", ");
	        sb.append(Reserva.CAMPO_IDVEICULO + ", ");
	        sb.append(Reserva.CAMPO_DTCONTRATACAO + ", ");
	        sb.append(Reserva.CAMPO_DTPREVDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_DTDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_STATUS + ", ");
	        sb.append(Reserva.CAMPO_VALORDIARIA + ", ");
	        sb.append(Reserva.CAMPO_PERCMULTADIARIA + ", ");
	        sb.append(Reserva.CAMPO_MULTATOTAL + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTALPREV + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTAL);
	        sb.append(" from " + Reserva.TABELA_RESERVA);
	        sb.append(" where ");
	        sb.append(Reserva.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarReservaPorResultSet(rs);
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Reserva criarReservaPorResultSet(ResultSet rs) throws SQLException {
		Reserva reserva = new Reserva(rs.getInt(Reserva.CAMPO_ID));
		reserva.setCliente(new Usuario(rs.getInt(Reserva.CAMPO_IDCLIENTE)));
		reserva.setVeiculo(new Veiculo(rs.getInt(Reserva.CAMPO_IDVEICULO)));
		if (rs.getTimestamp(Reserva.CAMPO_DTCONTRATACAO) != null) {
			reserva.setDtContratacao(new Date(rs.getTimestamp(Reserva.CAMPO_DTCONTRATACAO).getTime()));
		}
		if (rs.getTimestamp(Reserva.CAMPO_DTPREVDEVOLUCAO) != null) {
			reserva.setDtPrevDevolucao(new Date(rs.getTimestamp(Reserva.CAMPO_DTPREVDEVOLUCAO).getTime()));
		}
		if (rs.getTimestamp(Reserva.CAMPO_DTDEVOLUCAO) != null) {
			reserva.setDtDevolucao(new Date(rs.getTimestamp(Reserva.CAMPO_DTDEVOLUCAO).getTime()));
		}
		reserva.setStatus(StatusReserva.getStatusReserva(rs.getInt(Reserva.CAMPO_STATUS)));
		reserva.setValorDiaria(rs.getDouble(Reserva.CAMPO_VALORDIARIA));
		reserva.setPercMultaDiaria(rs.getDouble(Reserva.CAMPO_PERCMULTADIARIA));
		reserva.setMultaTotal(rs.getDouble(Reserva.CAMPO_MULTATOTAL));
		reserva.setValorTotalPrev(rs.getDouble(Reserva.CAMPO_VALORTOTALPREV));
		reserva.setValorTotal(rs.getDouble(Reserva.CAMPO_VALORTOTAL));
		//reserva.setDiasPrev(ControllerUtils.contarDiasPrevistosReserva(reserva));
		//reserva.setTotalDias(ControllerUtils.contarTotalDiasReserva(reserva));
		//reserva.setDiasAtraso(ControllerUtils.contarDiasAtrasoReserva(reserva));
    	return reserva;
	}

	@Override
	public void excluir(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("delete from " + Reserva.TABELA_RESERVA);
	        sb.append(" where " + Reserva.CAMPO_ID + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, id);
	        
	        ps.execute();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public List<Reserva> listarReservas(Reserva reserva, Date dtContFinal, Date dtPrevDevFinal, Date dtDevFinal) throws SQLException {
		List<Reserva> listaReservas = new ArrayList<Reserva>();
		
		if (reserva.getId() > 0) {
			Reserva reservaPorId = buscarPorId(reserva.getId());
			if (reservaPorId != null) {
				listaReservas.add(reservaPorId);
			}
			return listaReservas;
		}
		
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
	        sb.append(Reserva.CAMPO_ID + ", ");
	        sb.append(Reserva.CAMPO_IDCLIENTE + ", ");
	        sb.append(Reserva.CAMPO_IDVEICULO + ", ");
	        sb.append(Reserva.CAMPO_DTCONTRATACAO + ", ");
	        sb.append(Reserva.CAMPO_DTPREVDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_DTDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_STATUS + ", ");
	        sb.append(Reserva.CAMPO_VALORDIARIA + ", ");
	        sb.append(Reserva.CAMPO_PERCMULTADIARIA + ", ");
	        sb.append(Reserva.CAMPO_MULTATOTAL + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTALPREV + ", ");
	        sb.append(Reserva.CAMPO_VALORTOTAL);
	        sb.append(" from " + Reserva.TABELA_RESERVA);
	        sb.append(" where (" + Reserva.CAMPO_IDCLIENTE + " = ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_IDVEICULO + " = ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTCONTRATACAO + " >= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTCONTRATACAO + " <= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTPREVDEVOLUCAO + " >= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTPREVDEVOLUCAO + " <= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTDEVOLUCAO + " >= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_DTDEVOLUCAO + " <= ? or ? = 0)");
	        sb.append(" and (" + Reserva.CAMPO_STATUS + " = ? or ? = -1)");
	        sb.append(" order by " + Reserva.CAMPO_DTCONTRATACAO + ", ");
	        sb.append(Reserva.CAMPO_DTPREVDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_DTDEVOLUCAO + ", ");
	        sb.append(Reserva.CAMPO_ID);
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, reserva.getCliente().getId());
	        ps.setInt(2, reserva.getCliente().getId());	
	        ps.setInt(3, reserva.getVeiculo().getId());
	        ps.setInt(4, reserva.getVeiculo().getId());
	        
	        setDatasSelectListarReservas(ps, reserva.getDtContratacao(), 5);
	        setDatasSelectListarReservas(ps, dtContFinal, 7);
	        setDatasSelectListarReservas(ps, reserva.getDtPrevDevolucao(), 9);
	        setDatasSelectListarReservas(ps, dtPrevDevFinal, 11);
	        setDatasSelectListarReservas(ps, reserva.getDtDevolucao(), 13);
	        setDatasSelectListarReservas(ps, dtDevFinal, 15);

	        if (reserva.getStatus() == null) {
	        	ps.setInt(17, -1);
		        ps.setInt(18, -1);
	        } else {
		        ps.setInt(17, reserva.getStatus().ordinal());
		        ps.setInt(18, reserva.getStatus().ordinal());
	        }
	        
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()){
	        	listaReservas.add(criarReservaPorResultSet(rs));
	        }
			return listaReservas;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private void setDatasSelectListarReservas(PreparedStatement ps, Date dataDate, int posicao) throws SQLException {
		if (dataDate == null) {
        	ps.setInt(posicao, 0);
	        ps.setInt(posicao + 1, 0);
        } else {
	        ps.setTimestamp(posicao, new Timestamp(dataDate.getTime()));
	        ps.setTimestamp(posicao + 1, new Timestamp(dataDate.getTime()));
        }
	}

	@Override
	public int getQtdeReservasPorCliente(int idCliente) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(" + Reserva.CAMPO_ID + ") qtde");
			sb.append(" from " + Reserva.TABELA_RESERVA);
			sb.append(" where " + Reserva.CAMPO_IDCLIENTE + " = ?");
			
			PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
			ps.setInt(1, idCliente);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("qtde");
			}
			return 0;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public int getQtdeReservasPorVeiculo(int idVeiculo) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(" + Reserva.CAMPO_ID + ") qtde");
			sb.append(" from " + Reserva.TABELA_RESERVA);
			sb.append(" where " + Reserva.CAMPO_IDVEICULO + " = ?");
			
			PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
			ps.setInt(1, idVeiculo);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("qtde");
			}
			return 0;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

}
