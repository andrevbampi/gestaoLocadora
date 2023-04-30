package gestaoLocadora;

import java.sql.SQLException;

import controller.dao.util.ConnectionMariaDB;

public class Main {

	public static void main(String[] args) {
		try {
			ConnectionMariaDB.getConnection();
			System.out.println("Conectado com sucesso.");
		} catch (SQLException e) {
			System.out.println("Erro ao conectar: " + e.getMessage());
		}
	}

}
