package controller.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.dao.UsuarioDao;
import controller.dao.util.ConnectionMariaDB;
import model.Endereco;
import model.GeneroUsuario;
import model.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	@Override
	public Usuario salvar(Usuario usuario) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("insert into " + Usuario.TABELA_USUARIO);
	        sb.append("( ");
	        sb.append(Usuario.CAMPO_LOGIN + ", ");
	        sb.append(Usuario.CAMPO_SENHA + ", ");
	        sb.append(Usuario.CAMPO_NOME + ", ");
	        sb.append(Usuario.CAMPO_GENERO + ", ");
	        sb.append(Usuario.CAMPO_TELEFONE + ", ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + ", ");
	        sb.append(Usuario.CAMPO_ATIVO + ", ");
	        sb.append(Usuario.CAMPO_IDENDERECO + ")");
	        sb.append(" values(?,?,?,?,?,?,?,?)");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setString(1, usuario.getLogin().trim());
	        ps.setString(2, usuario.getSenha());
	        ps.setString(3, usuario.getNome());
	        ps.setInt(4, usuario.getGenero().ordinal());
	        ps.setString(5, usuario.getTelefone());
	        ps.setBoolean(6, usuario.isAdministrador());
	        ps.setBoolean(7, usuario.isAtivo());
	        ps.setInt(8, usuario.getEndereco().getId());
	        
	        if (!ps.execute()) {
	        	return buscarPorLogin(usuario.getLogin());
	        }
	        return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public Usuario verificarUsuario(Usuario usuario) throws SQLException {
        ConnectionMariaDB.getConnection();
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Usuario.CAMPO_ID + ", ");
	        sb.append(Usuario.CAMPO_LOGIN + ", ");
	        sb.append(Usuario.CAMPO_SENHA + ", ");
	        sb.append(Usuario.CAMPO_NOME + ", ");
	        sb.append(Usuario.CAMPO_GENERO + ", ");
	        sb.append(Usuario.CAMPO_TELEFONE + ", ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + ", ");
	        sb.append(Usuario.CAMPO_ATIVO + ", ");
	        sb.append(Usuario.CAMPO_IDENDERECO);
	        sb.append(" from " + Usuario.TABELA_USUARIO);
	        sb.append(" where ");
	        sb.append(Usuario.CAMPO_LOGIN + " = ? and ");
	        sb.append(Usuario.CAMPO_SENHA + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setString(1, usuario.getLogin().trim());
	        ps.setString(2, usuario.getSenha());
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarUsuarioPorResultSet(rs);
	        }
	        return null;
        } finally {
        	ConnectionMariaDB.closeConnection();
        }
	}

	@Override
	public void editar(Usuario usuario) throws SQLException {
        ConnectionMariaDB.getConnection();
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("update " + Usuario.TABELA_USUARIO);
	        sb.append(" set ");
	        sb.append(Usuario.CAMPO_LOGIN + " = ?, ");
	        sb.append(Usuario.CAMPO_NOME + " = ?, ");
	        sb.append(Usuario.CAMPO_GENERO + " = ?, ");
	        sb.append(Usuario.CAMPO_TELEFONE + " = ?, ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + " = ?, ");
	        sb.append(Usuario.CAMPO_ATIVO + " = ?, ");
	        sb.append(Usuario.CAMPO_IDENDERECO + " = ?");
	        if (!usuario.getSenha().trim().equals("")) {
	        	sb.append("," + Usuario.CAMPO_SENHA + " = ?");
	        }
	        sb.append(" where ");
	        sb.append(Usuario.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, usuario.getLogin().trim());
	        ps.setString(2, usuario.getNome());
	        ps.setInt(3, usuario.getGenero().ordinal());
	        ps.setString(4, usuario.getTelefone());
	        ps.setBoolean(5, usuario.isAdministrador());
	        ps.setBoolean(6, usuario.isAtivo());
	        ps.setInt(7, usuario.getEndereco().getId());
	        if (!usuario.getSenha().trim().equals("")) {
	        	ps.setString(8, usuario.getSenha());
	        	ps.setInt(9, usuario.getId());
	        } else {
	        	ps.setInt(8, usuario.getId());
	        }
	        
	        ps.execute();
        } finally {
        	ConnectionMariaDB.closeConnection();	
        }
	}

	@Override
	public boolean verificarLogin(String login, int idUsuario) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Usuario.CAMPO_LOGIN);
	        sb.append(" from " + Usuario.TABELA_USUARIO);
	        sb.append(" where ");
	        sb.append(Usuario.CAMPO_LOGIN + " = ?");
	        if (idUsuario > 0) {
	        	sb.append(" and " + Usuario.CAMPO_ID + " <> ?");
	        }
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setString(1, login.trim());
	        if (idUsuario > 0) {
	        	ps.setInt(2, idUsuario);
	        }
	        
	        ResultSet rs = ps.executeQuery();
	        return rs.next();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

	@Override
	public Usuario buscarPorId(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Usuario.CAMPO_ID + ", ");
	        sb.append(Usuario.CAMPO_LOGIN + ", ");
	        sb.append(Usuario.CAMPO_SENHA + ", ");
	        sb.append(Usuario.CAMPO_NOME + ", ");
	        sb.append(Usuario.CAMPO_GENERO + ", ");
	        sb.append(Usuario.CAMPO_TELEFONE + ", ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + ", ");
	        sb.append(Usuario.CAMPO_ATIVO + ", ");
	        sb.append(Usuario.CAMPO_IDENDERECO);
	        sb.append(" from " + Usuario.TABELA_USUARIO);
	        sb.append(" where ");
	        sb.append(Usuario.CAMPO_ID + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setInt(1, id);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarUsuarioPorResultSet(rs);
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	@Override
	public Usuario buscarPorLogin(String login) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("select ");
	        sb.append(Usuario.CAMPO_ID + ", ");
	        sb.append(Usuario.CAMPO_LOGIN + ", ");
	        sb.append(Usuario.CAMPO_SENHA + ", ");
	        sb.append(Usuario.CAMPO_NOME + ", ");
	        sb.append(Usuario.CAMPO_GENERO + ", ");
	        sb.append(Usuario.CAMPO_TELEFONE + ", ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + ", ");
	        sb.append(Usuario.CAMPO_ATIVO + ", ");
	        sb.append(Usuario.CAMPO_IDENDERECO);
	        sb.append(" from " + Usuario.TABELA_USUARIO);
	        sb.append(" where ");
	        sb.append(Usuario.CAMPO_LOGIN + " = ?");
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, login.trim());
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()){
	        	return criarUsuarioPorResultSet(rs);
	        }
			return null;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	@Override
	public void excluir(int id) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
	        StringBuilder sb = new StringBuilder();
	        sb.append("delete from " + Usuario.TABELA_USUARIO);
	        sb.append(" where " + Usuario.CAMPO_ID + " = ?");
	
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        ps.setInt(1, id);
	        
	        ps.execute();
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}
	
	private Usuario criarUsuarioPorResultSet(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario(rs.getInt(Usuario.CAMPO_ID));
    	usuario.setLogin(rs.getString(Usuario.CAMPO_LOGIN));
    	usuario.setSenha(rs.getString(Usuario.CAMPO_SENHA));
    	usuario.setNome(rs.getString(Usuario.CAMPO_NOME));
    	usuario.setGenero(GeneroUsuario.getGeneroUsuario(rs.getInt(Usuario.CAMPO_GENERO)));
    	usuario.setTelefone(rs.getString(Usuario.CAMPO_TELEFONE));
    	usuario.setAdministrador(rs.getBoolean(Usuario.CAMPO_ADMINISTRADOR));
    	usuario.setAtivo(rs.getBoolean(Usuario.CAMPO_ATIVO));
    	usuario.setEndereco(new Endereco(rs.getInt(Usuario.CAMPO_IDENDERECO)));
    	return usuario;
	}
	
	@Override
	public int getQtdUsuariosMesmoIdEndereco(int idEndereco) throws SQLException {
		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(" + Usuario.CAMPO_ID + ") qtde");
			sb.append(" from " + Usuario.TABELA_USUARIO);
			sb.append(" where " + Usuario.CAMPO_IDENDERECO + " = ?");
			
			PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
			ps.setInt(1, idEndereco);
			
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
	public List<Usuario> listarUsuarios(Usuario usuario, boolean checarAdministrador, boolean checarAtivo) throws SQLException {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		if (usuario.getId() > 0) {
			Usuario usuarioPorId = buscarPorId(usuario.getId());
			if (usuarioPorId != null) {
				listaUsuarios.add(usuarioPorId);
			}
			return listaUsuarios;
		}

		ConnectionMariaDB.getConnection();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
	        sb.append(Usuario.CAMPO_ID + ", ");
	        sb.append(Usuario.CAMPO_LOGIN + ", ");
	        sb.append(Usuario.CAMPO_SENHA + ", ");
	        sb.append(Usuario.CAMPO_NOME + ", ");
	        sb.append(Usuario.CAMPO_GENERO + ", ");
	        sb.append(Usuario.CAMPO_TELEFONE + ", ");
	        sb.append(Usuario.CAMPO_ADMINISTRADOR + ", ");
	        sb.append(Usuario.CAMPO_ATIVO + ", ");
	        sb.append(Usuario.CAMPO_IDENDERECO);
	        sb.append(" from " + Usuario.TABELA_USUARIO);
	        sb.append(" where (upper(" + Usuario.CAMPO_LOGIN + ") like upper(?) or ? = '')");
	        sb.append(" and (upper(" + Usuario.CAMPO_NOME + ") like upper(?) or ? = '')");
	        sb.append(" and (" + Usuario.CAMPO_GENERO + " = ? or ? = -1)");
	        sb.append(" and (" + Usuario.CAMPO_ADMINISTRADOR + " = ? or ? = -1)");
	        sb.append(" and (" + Usuario.CAMPO_ATIVO + " = ? or ? = -1)");
	        sb.append(" order by " + Usuario.CAMPO_ID + ", ");
	        sb.append(Usuario.CAMPO_ID);
	        
	        PreparedStatement ps = ConnectionMariaDB.connection.prepareStatement(sb.toString());
	        
	        ps.setString(1, "%" + usuario.getLogin()+ "%");
	        ps.setString(2, usuario.getLogin().trim());
	        ps.setString(3, "%" + usuario.getNome()+ "%");
	        ps.setString(4, usuario.getNome().trim());
	        if (usuario.getGenero() == null) {
	        	ps.setInt(5, -1);
		        ps.setInt(6, -1);
	        } else {
		        ps.setInt(5, usuario.getGenero().ordinal());
		        ps.setInt(6, usuario.getGenero().ordinal());
	        }
	        if (checarAdministrador) {
		        ps.setInt(7, usuario.isAdministrador() ? 1 : 0);
		        ps.setInt(8, usuario.isAdministrador() ? 1 : 0);
	        } else {
	        	ps.setInt(7, -1);
		        ps.setInt(8, -1);
	        }
	        if (checarAtivo) {
		        ps.setInt(9, usuario.isAtivo() ? 1 : 0);
		        ps.setInt(10, usuario.isAtivo() ? 1 : 0);
	        } else {
	        	ps.setInt(9, -1);
		        ps.setInt(10, -1);
	        }
	        
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()){
	        	listaUsuarios.add(criarUsuarioPorResultSet(rs));
	        }
			return listaUsuarios;
		} finally {
			ConnectionMariaDB.closeConnection();
		}
	}

}
