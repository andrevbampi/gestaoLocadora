package controller.dao;

import java.sql.SQLException;

import model.Endereco;

public interface EnderecoDao {
	public Endereco salvar(Endereco endereco) throws SQLException;
	public int buscarIdEnderecoCompleto(Endereco endereco) throws SQLException;
	public Endereco buscarPorId(int id) throws SQLException;
	public void excluir(int id) throws SQLException;
}
