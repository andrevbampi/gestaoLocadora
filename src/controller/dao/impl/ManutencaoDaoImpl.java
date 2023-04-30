package controller.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.dao.ManutencaoDao;
import controller.dao.util.ConnectionMariaDB;
import model.Manutencao;
import model.StatusManutencao;
import model.Veiculo;

public class ManutencaoDaoImpl implements ManutencaoDao {

	@Override
	public Manutencao salvar(Manutencao manutencao) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("insert into " + Manutencao.TABELA_MANUTENCAO);
	        sb.append("( ");
	        sb.append(Manutencao.CAMPO_ID + ", ");
	        sb.append(Manutencao.CAMPO_IDVEICULO + ", ");
	        sb.append(Manutencao.CAMPO_DESCRICAO + ", ");
	        sb.append(Manutencao.CAMPO_STATUS + ", ");
	        sb.append(Manutencao.CAMPO_DTINICIO + ", ");
	        sb.append(Manutencao.CAMPO_DTPREVFIM + ", ");
	        sb.append(Manutencao.CAMPO_DTFIM + ")");
	        sb.append(" values(?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        manutencao.setId(ConnectionMariaDB.nextId(Manutencao.TABELA_MANUTENCAO, Manutencao.CAMPO_ID));
	        ps.setInt(1, manutencao.getId());
	        ps.setInt(2, manutencao.getVeiculo().getId());
	        ps.setString(3, manutencao.getDescricao());
	        ps.setInt(4, manutencao.getStatus().ordinal());
	        if (manutencao.getDtInicio() == null) {
	        	ps.setTimestamp(5, null);
	        } else {
	        	ps.setTimestamp(5, new Timestamp(manutencao.getDtInicio().getTime()));
	        }
	        if (manutencao.getDtPrevFim() == null) {
	        	ps.setTimestamp(6, null);
	        } else {
	        	ps.setTimestamp(6, new Timestamp(manutencao.getDtPrevFim().getTime()));
	        }
	        if (manutencao.getDtFim() == null) {
	        	ps.setTimestamp(7, null);
	        } else {
	        	ps.setTimestamp(7, new Timestamp(manutencao.getDtFim().getTime()));
	        }
	        
	        
	        if (!ps.execute()) {
	        	return manutencao;
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public void editar(Manutencao manutencao) throws SQLException {
		ConnectionMariaDB.getConnection();
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("update " + Manutencao.TABELA_MANUTENCAO);
	        sb.append(" set ");
	        sb.append(Manutencao.CAMPO_IDVEICULO + " = ?, ");
	        sb.append(Manutencao.CAMPO_DESCRICAO + " = ?, ");
	        sb.append(Manutencao.CAMPO_STATUS + " = ?, ");
	        sb.append(Manutencao.CAMPO_DTINICIO + " = ?, ");
	        sb.append(Manutencao.CAMPO_DTPREVFIM + " = ?, ");
	        sb.append(Manutencao.CAMPO_DTFIM + " = ?");
	        sb.append(" where ");
	        sb.append(Manutencao.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, manutencao.getVeiculo().getId());
	        ps.setString(2, manutencao.getDescricao());
	        ps.setInt(3, manutencao.getStatus().ordinal());
	        if (manutencao.getDtInicio() == null) {
	        	ps.setTimestamp(4, null);
	        } else {
	        	ps.setTimestamp(4, new Timestamp(manutencao.getDtInicio().getTime()));
	        }
	        if (manutencao.getDtPrevFim() == null) {
	        	ps.setTimestamp(5, null);
	        } else {
	        	ps.setTimestamp(5, new Timestamp(manutencao.getDtPrevFim().getTime()));
	        }
	        if (manutencao.getDtFim() == null) {
	        	ps.setTimestamp(6, null);
	        } else {
	        	ps.setTimestamp(6, new Timestamp(manutencao.getDtFim().getTime()));
	        }
	        ps.setInt(7, manutencao.getId());
	        
	        ps.execute();
        } finally {
        	ConnectionMariaDB.closeConnection();	
        }
	}

	@Override
	public Manutencao buscarPorId(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Manutencao.CAMPO_ID + ", ");
	        sb.append(Manutencao.CAMPO_IDVEICULO + ", ");
	        sb.append(Manutencao.CAMPO_DESCRICAO + ", ");
	        sb.append(Manutencao.CAMPO_STATUS + ", ");
	        sb.append(Manutencao.CAMPO_DTINICIO + ", ");
	        sb.append(Manutencao.CAMPO_DTPREVFIM + ", ");
	        sb.append(Manutencao.CAMPO_DTFIM);
	        sb.append(" from " + Manutencao.TABELA_MANUTENCAO);
	        sb.append(" where ");
	        sb.append(Manutencao.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarManutencaoPorResultSet(rs);
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Manutencao criarManutencaoPorResultSet(ResultSet rs) throws SQLException {
		Manutencao manutencao = new Manutencao(rs.getInt(Manutencao.CAMPO_ID));
		manutencao.setVeiculo(new Veiculo(rs.getInt(Manutencao.CAMPO_IDVEICULO)));
		manutencao.setDescricao(rs.getString(Manutencao.CAMPO_DESCRICAO));
		manutencao.setStatus(StatusManutencao.getStatusManutencao(rs.getInt(Manutencao.CAMPO_STATUS)));
		if (rs.getTimestamp(Manutencao.CAMPO_DTINICIO) != null) {
			manutencao.setDtInicio(new Date(rs.getTimestamp(Manutencao.CAMPO_DTINICIO).getTime()));
		}
		if (rs.getTimestamp(Manutencao.CAMPO_DTPREVFIM) != null) {
			manutencao.setDtPrevFim(new Date(rs.getTimestamp(Manutencao.CAMPO_DTPREVFIM).getTime()));
		}
		if (rs.getTimestamp(Manutencao.CAMPO_DTFIM) != null) {
			manutencao.setDtFim(new Date(rs.getTimestamp(Manutencao.CAMPO_DTFIM).getTime()));
		}
    	return manutencao;
	}

	@Override
	public void excluir(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("delete from " + Manutencao.TABELA_MANUTENCAO);
	        sb.append(" where " + Manutencao.CAMPO_ID + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, id);
	        
	        ps.execute();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public List<Manutencao> listarManutencoes(Manutencao manutencao, Date dtInicioFinal, Date dtPrevFimFinal, Date dtFimFinal) throws SQLException {
		List<Manutencao> listaManutencoes = new ArrayList<Manutencao>();
		
		if (manutencao.getId() > 0) {
			Manutencao manutencaoPorId = buscarPorId(manutencao.getId());
			if (manutencaoPorId != null) {
				listaManutencoes.add(manutencaoPorId);
			}
			return listaManutencoes;
		}
		
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
			sb.append(Manutencao.CAMPO_ID + ", ");
	        sb.append(Manutencao.CAMPO_IDVEICULO + ", ");
	        sb.append(Manutencao.CAMPO_DESCRICAO + ", ");
	        sb.append(Manutencao.CAMPO_STATUS + ", ");
	        sb.append(Manutencao.CAMPO_DTINICIO + ", ");
	        sb.append(Manutencao.CAMPO_DTPREVFIM + ", ");
	        sb.append(Manutencao.CAMPO_DTFIM);
	        sb.append(" from " + Manutencao.TABELA_MANUTENCAO);
	        sb.append(" where (" + Manutencao.CAMPO_IDVEICULO + " = ? or ? = 0)");
	        sb.append(" and (upper(" + Manutencao.CAMPO_DESCRICAO + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Manutencao.CAMPO_STATUS + " = ? or ? = -1)");
	        sb.append(" and (" + Manutencao.CAMPO_DTINICIO + " >= ? or ? = 0)");
	        sb.append(" and (" + Manutencao.CAMPO_DTINICIO + " <= ? or ? = 0)");
	        sb.append(" and (" + Manutencao.CAMPO_DTPREVFIM + " >= ? or ? = 0)");
	        sb.append(" and (" + Manutencao.CAMPO_DTPREVFIM + " <= ? or ? = 0)");
	        sb.append(" and (" + Manutencao.CAMPO_DTFIM + " >= ? or ? = 0)");
	        sb.append(" and (" + Manutencao.CAMPO_DTFIM + " <= ? or ? = 0)");
	        sb.append(" order by " + Manutencao.CAMPO_DTINICIO + ", ");
	        sb.append(Manutencao.CAMPO_DTPREVFIM + ", ");
	        sb.append(Manutencao.CAMPO_DTFIM + ", ");
	        sb.append(Manutencao.CAMPO_ID);
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, manutencao.getVeiculo().getId());
	        ps.setInt(2, manutencao.getVeiculo().getId());
	        ps.setString(3, "%" + manutencao.getDescricao()+ "%");
	        ps.setString(4, manutencao.getDescricao().trim());
	        if (manutencao.getStatus() == null) {
	        	ps.setInt(5, -1);
		        ps.setInt(6, -1);
	        } else {
		        ps.setInt(5, manutencao.getStatus().ordinal());
		        ps.setInt(6, manutencao.getStatus().ordinal());
	        }
	        
	        setDatasSelectListarManutencoes(ps, manutencao.getDtInicio(), 7);
	        setDatasSelectListarManutencoes(ps, dtInicioFinal, 9);
	        setDatasSelectListarManutencoes(ps, manutencao.getDtPrevFim(), 11);
	        setDatasSelectListarManutencoes(ps, dtPrevFimFinal, 13);
	        setDatasSelectListarManutencoes(ps, manutencao.getDtFim(), 15);
	        setDatasSelectListarManutencoes(ps, dtFimFinal, 17);
	        
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()){
	        	listaManutencoes.add(criarManutencaoPorResultSet(rs));
	        }
			return listaManutencoes;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private void setDatasSelectListarManutencoes(PreparedStatement ps, Date dataDate, int posicao) throws SQLException {
		if (dataDate == null) {
        	ps.setInt(posicao, 0);
	        ps.setInt(posicao + 1, 0);
        } else {
	        ps.setTimestamp(posicao, new Timestamp(dataDate.getTime()));
	        ps.setTimestamp(posicao + 1, new Timestamp(dataDate.getTime()));
        }
	}

	@Override
	public int getQtdeManutencoesPorVeiculo(int idVeiculo) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(" + Manutencao.CAMPO_ID + ") qtde");
			sb.append(" from " + Manutencao.TABELA_MANUTENCAO);
			sb.append(" where " + Manutencao.CAMPO_IDVEICULO + " = ?");
			
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
