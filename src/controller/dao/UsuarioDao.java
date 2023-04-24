package controller.dao;

import java.sql.SQLException;
import java.util.List;

import model.Usuario;

public interface UsuarioDao {
    public Usuario salvar(Usuario usuario) throws SQLException;
    public Usuario verificarUsuario(Usuario usuario) throws SQLException;
    public void editar(Usuario usuario) throws SQLException;
    public boolean verificarLogin(String login, int idUsuario) throws SQLException;
    public Usuario buscarPorId(int id) throws SQLException;
    public Usuario buscarPorLogin(String login) throws SQLException;
    public void excluir(int id) throws SQLException;
    public int getQtdUsuariosMesmoIdEndereco(int idEndereco) throws SQLException;
    public List<Usuario> listarUsuarios(Usuario usuario, boolean checarAdministrador) throws SQLException;
}
