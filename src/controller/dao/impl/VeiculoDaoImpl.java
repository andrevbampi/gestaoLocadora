package controller.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.dao.VeiculoDao;
import controller.dao.util.ConnectionMariaDB;
import model.CategoriaVeiculo;
import model.StatusVeiculo;
import model.Veiculo;

public class VeiculoDaoImpl implements VeiculoDao {

	@Override
	public Veiculo salvar(Veiculo veiculo) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("insert into " + Veiculo.TABELA_VEICULO);
	        sb.append("( ");
	        sb.append(Veiculo.CAMPO_ID + ", ");
	        sb.append(Veiculo.CAMPO_MARCA + ", ");
	        sb.append(Veiculo.CAMPO_MODELO + ", ");
	        sb.append(Veiculo.CAMPO_ANO + ", ");
	        sb.append(Veiculo.CAMPO_COR + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + ", ");
	        sb.append(Veiculo.CAMPO_PLACA + ", ");
	        sb.append(Veiculo.CAMPO_ATIVO + ")");
	        sb.append(" values(?,?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        veiculo.setId(ConnectionMariaDB.nextId(Veiculo.TABELA_VEICULO, Veiculo.CAMPO_ID));
	        ps.setInt(1, veiculo.getId());
	        ps.setString(2, veiculo.getMarca());
	        ps.setString(3, veiculo.getModelo());
	        ps.setInt(4, veiculo.getAno());
	        ps.setString(5, veiculo.getCor());
	        ps.setInt(6, veiculo.getCategoria().ordinal());
	        ps.setString(7, veiculo.getPlaca());
	        ps.setBoolean(8, veiculo.isAtivo());
	        
	        if (!ps.execute()) {
	        	return veiculo;
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public void editar(Veiculo veiculo) throws SQLException {
        ConnectionMariaDB.getConnection();
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("update " + Veiculo.TABELA_VEICULO);
	        sb.append(" set ");
	        sb.append(Veiculo.CAMPO_MARCA + " = ?, ");
	        sb.append(Veiculo.CAMPO_MODELO + " = ?, ");
	        sb.append(Veiculo.CAMPO_ANO + " = ?, ");
	        sb.append(Veiculo.CAMPO_COR + " = ?, ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + " = ?, ");
	        sb.append(Veiculo.CAMPO_PLACA + " = ?, ");
	        sb.append(Veiculo.CAMPO_ATIVO + " = ?");
	        sb.append(" where ");
	        sb.append(Veiculo.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, veiculo.getMarca());
	        ps.setString(2, veiculo.getModelo());
	        ps.setInt(3, veiculo.getAno());
	        ps.setString(4, veiculo.getCor());
	        ps.setInt(5, veiculo.getCategoria().ordinal());
	        ps.setString(6, veiculo.getPlaca());
	        ps.setBoolean(7, veiculo.isAtivo());
	        ps.setInt(8, veiculo.getId());
	        
	        ps.execute();
        } finally {
        	ConnectionMariaDB.closeConnection();	
        }
	}

	@Override
	public Veiculo buscarPorId(int id, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Veiculo.CAMPO_ID + ", ");
	        sb.append(Veiculo.CAMPO_MARCA + ", ");
	        sb.append(Veiculo.CAMPO_MODELO + ", ");
	        sb.append(Veiculo.CAMPO_ANO + ", ");
	        sb.append(Veiculo.CAMPO_COR + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + ", ");
	        sb.append(Veiculo.CAMPO_PLACA + ", ");
	        sb.append(Veiculo.CAMPO_ATIVO);
	        sb.append(" from " + Veiculo.TABELA_VEICULO);
	        sb.append(" where ");
	        sb.append(Veiculo.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarVeiculoPorResultSet(rs, dataInicial, dataFinal, idReserva, idManutencao);
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Veiculo criarVeiculoPorResultSet(ResultSet rs, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException {
		Veiculo veiculo = new Veiculo(rs.getInt(Veiculo.CAMPO_ID));
		veiculo.setMarca(rs.getString(Veiculo.CAMPO_MARCA));
		veiculo.setModelo(rs.getString(Veiculo.CAMPO_MODELO));
		veiculo.setAno(rs.getInt(Veiculo.CAMPO_ANO));
		veiculo.setCor(rs.getString(Veiculo.CAMPO_COR));
		veiculo.setStatus(buscarStatusVeiculo(veiculo.getId(), dataInicial, dataFinal, idReserva, idManutencao));
		veiculo.setCategoria(CategoriaVeiculo.getCategoriaVeiculo(rs.getInt(Veiculo.CAMPO_CATEGORIA)));
		veiculo.setPlaca(rs.getString(Veiculo.CAMPO_PLACA));
		veiculo.setAtivo(rs.getBoolean(Veiculo.CAMPO_ATIVO));
    	return veiculo;
	}

	@Override
	public void excluir(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("delete from " + Veiculo.TABELA_VEICULO);
	        sb.append(" where " + Veiculo.CAMPO_ID + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, id);
	        
	        ps.execute();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public List<Veiculo> listarVeiculos(Veiculo veiculo, boolean checarAtivo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException {
		List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
		
		if (veiculo.getId() > 0) {
			Veiculo veiculoPorId = buscarPorId(veiculo.getId(), dataInicial, dataFinal, idReserva, idManutencao);
			if (veiculoPorId != null) {
				listaVeiculos.add(veiculoPorId);
			}
			return listaVeiculos;
		}

		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
	        sb.append(Veiculo.CAMPO_ID + ", ");
	        sb.append(Veiculo.CAMPO_MARCA+ ", ");
	        sb.append(Veiculo.CAMPO_MODELO + ", ");
	        sb.append(Veiculo.CAMPO_ANO + ", ");
	        sb.append(Veiculo.CAMPO_COR + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + ", ");
	        sb.append(Veiculo.CAMPO_ATIVO + ", ");
	        sb.append(Veiculo.CAMPO_PLACA);
	        sb.append(" from " + Veiculo.TABELA_VEICULO);
	        sb.append(" where (upper(" + Veiculo.CAMPO_MARCA + ") like upper(?) or ? = '')");
	        sb.append(" and (upper(" + Veiculo.CAMPO_MODELO + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Veiculo.CAMPO_ANO + " = ? or ? = 0)");
	        sb.append(" and (upper(" + Veiculo.CAMPO_COR + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Veiculo.CAMPO_CATEGORIA + " = ? or ? = -1)");
	        sb.append(" and (upper(" + Veiculo.CAMPO_PLACA + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Veiculo.CAMPO_ATIVO + " = ? or ? = -1)");
	        sb.append(" and (? = -1 or (select " + Veiculo.FUNCAO_GETSTATUS + "(" + Veiculo.CAMPO_ID);
	        sb.append(", ?, ?, ?, ?) from dual) = ?)");
	        sb.append(" order by " + Veiculo.CAMPO_ID);
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, "%" + veiculo.getMarca()+ "%");
	        ps.setString(2, veiculo.getMarca().trim());
	        ps.setString(3, "%" + veiculo.getModelo()+ "%");
	        ps.setString(4, veiculo.getModelo().trim());
	        ps.setInt(5, veiculo.getAno());
	        ps.setInt(6, veiculo.getAno());
	        ps.setString(7, "%" + veiculo.getCor()+ "%");
	        ps.setString(8, veiculo.getCor().trim());
	        if (veiculo.getCategoria() == null) {
	        	ps.setInt(9, -1);
		        ps.setInt(10, -1);
	        } else {
		        ps.setInt(9, veiculo.getCategoria().ordinal());
		        ps.setInt(10, veiculo.getCategoria().ordinal());
	        }
	        ps.setString(11, "%" + veiculo.getPlaca()+ "%");
	        ps.setString(12, veiculo.getPlaca().trim());
	        if (checarAtivo) {
		        ps.setInt(13, veiculo.isAtivo() ? 1 : 0);
		        ps.setInt(14, veiculo.isAtivo() ? 1 : 0);
	        } else {
	        	ps.setInt(13, -1);
		        ps.setInt(14, -1);
	        }
	        ps.setInt(18, 0);
	        ps.setInt(19, 0);
	        if (veiculo.getStatus() == null) {
	        	ps.setInt(15, -1);
		        ps.setInt(20, -1);
	        } else {
		        ps.setInt(15, veiculo.getStatus().ordinal());
		        ps.setInt(20, veiculo.getStatus().ordinal());
	        }
	        setDatasSelectListarVeiculos(ps, dataInicial, 16);
	        setDatasSelectListarVeiculos(ps, dataFinal, 17);
	        
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()){
	        	listaVeiculos.add(criarVeiculoPorResultSet(rs, dataInicial, dataFinal, idReserva, idManutencao));
	        }
			return listaVeiculos;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public StatusVeiculo buscarStatusVeiculo(int idVeiculo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select " + Veiculo.FUNCAO_GETSTATUS + "(?, ?, ?, ?, ?) status from dual");
			
			PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
			
			ps.setInt(1, idVeiculo);
			if (dataInicial == null) {
				ps.setTimestamp(2, null);
			} else {
				ps.setTimestamp(2, new Timestamp(dataInicial.getTime()));
			}
			if (dataFinal == null) {
				ps.setTimestamp(3, null);
			} else {
				ps.setTimestamp(3, new Timestamp(dataFinal.getTime()));
			}
			ps.setInt(4, idReserva);
			ps.setInt(5, idManutencao);
			
			ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return StatusVeiculo.getStatusVeiculo(rs.getInt("status"));
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private void setDatasSelectListarVeiculos(PreparedStatement ps, Date dataDate, int posicao) throws SQLException {
		if (dataDate == null) {
        	ps.setInt(posicao, 0);
        } else {
	        ps.setTimestamp(posicao, new Timestamp(dataDate.getTime()));
        }
	}

}
