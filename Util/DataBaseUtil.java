package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtil {

	//SELECT setval('wordlegame.game_game_id_seq', 1, false);

	//BD: https://console.clever-cloud.com/users/me/addons/addon_92adc669-4f75-4020-bd2d-22f9303b679c

	private static final String JDBC_URL = "jdbc:postgresql://b4eu9tgzem21rldfhlyw-postgresql.services.clever-cloud.com:50013/b4eu9tgzem21rldfhlyw";

	private static final String USERNAME = "um1vwwwy8b2ww7xkgaaw";
	private static final String PASSWORD = "rH4VwamK48Pq4rpOadpQlcncB4EU9N";


	//Se hace al acceder al juego solo una vez
	public static void conectarBD() {
		try {
			// Register the PostgreSQL driver
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}

	}

	public ResultSet realizarQuery(String query) {

		ResultSet resultSet = null;

		try {
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// Realizar operaciones con la base de datos
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			connection.close();

			// La conexión se cierra automáticamente al final del bloque try-with-resources
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return resultSet;

	}

	public void realizarInsertYUpdate(String query){

		try {
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// Realizar operaciones con la base de datos
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.close();

			// La conexión se cierra automáticamente al final del bloque try-with-resources
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}