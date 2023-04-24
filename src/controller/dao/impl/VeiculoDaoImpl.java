package controller.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	        sb.append(Veiculo.CAMPO_STATUS + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + ")");
	        sb.append(" values(?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        veiculo.setId(ConnectionMariaDB.nextId(Veiculo.TABELA_VEICULO, Veiculo.CAMPO_ID));
	        ps.setInt(1, veiculo.getId());
	        ps.setString(2, veiculo.getMarca());
	        ps.setString(3, veiculo.getModelo());
	        ps.setInt(4, veiculo.getAno());
	        ps.setString(5, veiculo.getCor());
	        ps.setInt(6, veiculo.getStatus().ordinal());
	        ps.setInt(7, veiculo.getCategoria().ordinal());
	        
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
	        sb.append(Veiculo.CAMPO_STATUS + " = ?, ");
	        sb.append(Veiculo.CAMPO_CATEGORIA + " = ?");
	        sb.append(" where ");
	        sb.append(Veiculo.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, veiculo.getMarca());
	        ps.setString(2, veiculo.getModelo());
	        ps.setInt(3, veiculo.getAno());
	        ps.setString(4, veiculo.getCor());
	        ps.setInt(5, veiculo.getStatus().ordinal());
	        ps.setInt(6, veiculo.getCategoria().ordinal());
	        ps.setInt(7, veiculo.getId());
	        
	        ps.execute();
        } finally {
        	ConnectionMariaDB.closeConnection();	
        }
	}

	@Override
	public Veiculo buscarPorId(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Veiculo.CAMPO_ID + ", ");
	        sb.append(Veiculo.CAMPO_MARCA + ", ");
	        sb.append(Veiculo.CAMPO_MODELO + ", ");
	        sb.append(Veiculo.CAMPO_ANO + ", ");
	        sb.append(Veiculo.CAMPO_COR + ", ");
	        sb.append(Veiculo.CAMPO_STATUS + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA);
	        sb.append(" from " + Veiculo.TABELA_VEICULO);
	        sb.append(" where ");
	        sb.append(Veiculo.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarVeiculoPorResultSet(rs);
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Veiculo criarVeiculoPorResultSet(ResultSet rs) throws SQLException {
		Veiculo veiculo = new Veiculo(rs.getInt(Veiculo.CAMPO_ID));
		veiculo.setMarca(rs.getString(Veiculo.CAMPO_MARCA));
		veiculo.setModelo(rs.getString(Veiculo.CAMPO_MODELO));
		veiculo.setAno(rs.getInt(Veiculo.CAMPO_ANO));
		veiculo.setCor(rs.getString(Veiculo.CAMPO_COR));
		veiculo.setStatus(StatusVeiculo.getStatusVeiculo(rs.getInt(Veiculo.CAMPO_STATUS)));
		veiculo.setCategoria(CategoriaVeiculo.getCategoriaVeiculo(rs.getInt(Veiculo.CAMPO_CATEGORIA)));
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
	public List<Veiculo> listarVeiculos(Veiculo veiculo) throws SQLException {
		List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
		
		if (veiculo.getId() > 0) {
			Veiculo veiculoPorId = buscarPorId(veiculo.getId());
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
	        sb.append(Veiculo.CAMPO_STATUS + ", ");
	        sb.append(Veiculo.CAMPO_CATEGORIA);
	        sb.append(" from " + Veiculo.TABELA_VEICULO);
	        sb.append(" where (upper(" + Veiculo.CAMPO_MARCA + ") like upper(?) or ? = '')");
	        sb.append(" and (upper(" + Veiculo.CAMPO_MODELO + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Veiculo.CAMPO_ANO + " = ? or ? = 0)");
	        sb.append(" and (upper(" + Veiculo.CAMPO_COR + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Veiculo.CAMPO_STATUS + " = ? or ? = -1)");
	        sb.append(" and (" + Veiculo.CAMPO_CATEGORIA + " = ? or ? = -1)");
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
	        if (veiculo.getStatus() == null) {
	        	ps.setInt(9, -1);
		        ps.setInt(10, -1);
	        } else {
		        ps.setInt(9, veiculo.getStatus().ordinal());
		        ps.setInt(10, veiculo.getStatus().ordinal());
	        }
	        if (veiculo.getCategoria() == null) {
	        	ps.setInt(11, -1);
		        ps.setInt(12, -1);
	        } else {
		        ps.setInt(11, veiculo.getCategoria().ordinal());
		        ps.setInt(12, veiculo.getCategoria().ordinal());
	        }
	        
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()){
	        	listaVeiculos.add(criarVeiculoPorResultSet(rs));
	        }
			return listaVeiculos;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

}
