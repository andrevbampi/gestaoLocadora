package controller.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.dao.EnderecoDao;
import controller.dao.util.ConnectionMariaDB;
import model.Endereco;

public class EnderecoDaoImpl implements EnderecoDao {

	@Override
	public Endereco salvar(Endereco endereco) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("insert into " + Endereco.TABELA_ENDERECO);
	        sb.append("( ");
	        sb.append(Endereco.CAMPO_ID + ", ");
	        sb.append(Endereco.CAMPO_CEP + ", ");
	        sb.append(Endereco.CAMPO_LOGRADOURO + ", ");
	        sb.append(Endereco.CAMPO_NUMERO + ", ");
	        sb.append(Endereco.CAMPO_COMPLEMENTO + ", ");
	        sb.append(Endereco.CAMPO_BAIRRO + ", ");
	        sb.append(Endereco.CAMPO_CIDADE + ", ");
	        sb.append(Endereco.CAMPO_UF + ", ");
	        sb.append(Endereco.CAMPO_PAIS + ")");
	        sb.append(" values(?,?,?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        endereco.setId(ConnectionMariaDB.nextId(Endereco.TABELA_ENDERECO, Endereco.CAMPO_ID));
	        ps.setInt(1, endereco.getId());
	        ps.setInt(2, endereco.getCep());
	        ps.setString(3, endereco.getLogradouro().trim());
	        ps.setInt(4, endereco.getNumero());
	        ps.setString(5, endereco.getComplemento().trim());
	        ps.setString(6, endereco.getBairro().trim());
	        ps.setString(7, endereco.getCidade().trim());
	        ps.setString(8, endereco.getUf().trim());
	        ps.setString(9, endereco.getPais().trim());
	        
	        if (!ps.execute()) {
	        	return endereco;
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	@Override
	public int buscarIdEnderecoCompleto(Endereco endereco) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Endereco.CAMPO_ID + ", ");
	        sb.append(Endereco.CAMPO_CEP + ", ");
	        sb.append(Endereco.CAMPO_LOGRADOURO + ", ");
	        sb.append(Endereco.CAMPO_NUMERO + ", ");
	        sb.append(Endereco.CAMPO_COMPLEMENTO + ", ");
	        sb.append(Endereco.CAMPO_BAIRRO + ", ");
	        sb.append(Endereco.CAMPO_CIDADE + ", ");
	        sb.append(Endereco.CAMPO_UF + ", ");
	        sb.append(Endereco.CAMPO_PAIS);
	        sb.append(" from " + Endereco.TABELA_ENDERECO);
	        sb.append(" where " + Endereco.CAMPO_CEP + " = ?");
	        sb.append(" and upper(" + Endereco.CAMPO_LOGRADOURO + ") = UPPER(?)");
	        sb.append(" and " + Endereco.CAMPO_NUMERO + " = ?");
	        sb.append(" and upper(" + Endereco.CAMPO_COMPLEMENTO + ") = UPPER(?)");
	        sb.append(" and upper(" + Endereco.CAMPO_BAIRRO + ") = UPPER(?)");
	        sb.append(" and upper(" + Endereco.CAMPO_CIDADE + ") = UPPER(?)");
	        sb.append(" and upper(" + Endereco.CAMPO_UF + ") = UPPER(?)");
	        sb.append(" and upper(" + Endereco.CAMPO_PAIS + ") = UPPER(?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, endereco.getCep());
	        ps.setString(2, endereco.getLogradouro().trim());
	        ps.setInt(3, endereco.getNumero());
	        ps.setString(4, endereco.getComplemento().trim());
	        ps.setString(5, endereco.getBairro().trim());
	        ps.setString(6, endereco.getCidade().trim());
	        ps.setString(7, endereco.getUf().trim());
	        ps.setString(8, endereco.getPais().trim());
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return rs.getInt(Endereco.CAMPO_ID);
	        }
	        return 0;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	@Override
	public Endereco buscarPorId(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Endereco.CAMPO_ID + ", ");
	        sb.append(Endereco.CAMPO_CEP + ", ");
	        sb.append(Endereco.CAMPO_LOGRADOURO + ", ");
	        sb.append(Endereco.CAMPO_NUMERO + ", ");
	        sb.append(Endereco.CAMPO_COMPLEMENTO + ", ");
	        sb.append(Endereco.CAMPO_BAIRRO + ", ");
	        sb.append(Endereco.CAMPO_CIDADE + ", ");
	        sb.append(Endereco.CAMPO_UF + ", ");
	        sb.append(Endereco.CAMPO_PAIS);
	        sb.append(" from " + Endereco.TABELA_ENDERECO);
	        sb.append(" where ");
	        sb.append(Endereco.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarEnderecoPorResultSet(rs);
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Endereco criarEnderecoPorResultSet(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco(rs.getInt(Endereco.CAMPO_ID));
		endereco.setCep(rs.getInt(Endereco.CAMPO_CEP));
		endereco.setLogradouro(rs.getString(Endereco.CAMPO_LOGRADOURO));
		endereco.setNumero(rs.getInt(Endereco.CAMPO_NUMERO));
		endereco.setComplemento(rs.getString(Endereco.CAMPO_COMPLEMENTO));
		endereco.setBairro(rs.getString(Endereco.CAMPO_BAIRRO));
		endereco.setCidade(rs.getString(Endereco.CAMPO_CIDADE));
		endereco.setUf(rs.getString(Endereco.CAMPO_UF));
		endereco.setPais(rs.getString(Endereco.CAMPO_PAIS));
    	return endereco;
	}
	
	public void excluir(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("delete from " + Endereco.TABELA_ENDERECO);
	        sb.append(" where " + Endereco.CAMPO_ID + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, id);
	        
	        ps.execute();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

}
