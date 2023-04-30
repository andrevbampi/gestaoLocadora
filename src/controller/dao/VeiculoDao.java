package controller.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.StatusVeiculo;
import model.Veiculo;

public interface VeiculoDao {
	public Veiculo salvar(Veiculo veiculo) throws SQLException;
	public void editar(Veiculo veiculo) throws SQLException;
	public Veiculo buscarPorId(int id, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException;
	public void excluir(int id) throws SQLException;
	public List<Veiculo> listarVeiculos(Veiculo veiculo, boolean checarAtivo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException;
	public StatusVeiculo buscarStatusVeiculo(int idVeiculo, Date dataInicial, Date dataFinal, int idReserva, int idManutencao) throws SQLException;
}
