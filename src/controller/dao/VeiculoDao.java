package controller.dao;

import java.sql.SQLException;
import java.util.List;
import model.Veiculo;

public interface VeiculoDao {
	public Veiculo salvar(Veiculo veiculo) throws SQLException;
	public void editar(Veiculo veiculo) throws SQLException;
	public Veiculo buscarPorId(int id) throws SQLException;
	public void excluir(int id) throws SQLException;
	public List<Veiculo> listarVeiculos(Veiculo veiculo) throws SQLException;
}
