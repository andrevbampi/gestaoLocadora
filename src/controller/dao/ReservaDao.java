package controller.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Reserva;

public interface ReservaDao {
	public Reserva salvar(Reserva reserva) throws SQLException;
	public void editar(Reserva reserva) throws SQLException;
	public Reserva buscarPorId(int id) throws SQLException;
	public void excluir(int id) throws SQLException;
	public List<Reserva> listarReservas(Reserva reserva, Date dtContFinal, Date dtPrevDevFinal, Date dtDevFinal) throws SQLException;
	public int getQtdeReservasPorCliente(int idCliente) throws SQLException;
	public int getQtdeReservasPorVeiculo(int idVeiculo) throws SQLException;
}
