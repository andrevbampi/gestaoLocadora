package controller.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Manutencao;

public interface ManutencaoDao {
	public Manutencao salvar(Manutencao manutencao) throws SQLException;
	public void editar(Manutencao manutencao) throws SQLException;
	public Manutencao buscarPorId(int id) throws SQLException;
	public void excluir(int id) throws SQLException;
	public List<Manutencao> listarManutencoes(Manutencao manutencao, Date dtInicioFinal, Date dtPrevFimFinal, Date dtFimFinal) throws SQLException;
	public int getQtdeManutencoesPorVeiculo(int idVeiculo) throws SQLException;
}
