package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Util.DataBaseUtil;
import Util.Util;


public class Usuario {

	//Atributos
	private String username;
	private String password;
	private String nombre;
	private String surname;
	private LocalDate birth_date;

	DataBaseUtil db = new DataBaseUtil();
	Util u = new Util();

	//Constructor para registrar el usuario
	public Usuario(String username, String password, String nombre, String surname, String birth_date) {
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.surname = surname;
		this.birth_date = LocalDate.parse(birth_date);

	}

	public Usuario() {

	}

	public boolean registrarUsuario(){

		ResultSet resultSet = db.realizarQuery("SELECT username FROM WordleGame.USERS WHERE username = '" + this.username + "'");
		boolean registrado = false;

		try {
			if (resultSet != null && resultSet.next()) {
				u.mostrarDialogo("Error de registro", "Nombre de usuario ya utilizado, por favor, introduzca otro", true);

			}else {
				db.realizarInsertYUpdate("INSERT INTO WordleGame.USERS(username, password, name, surname, birth_date, max_score) VALUES ('" 
						+ this.username + "', '" + this.password + "', '" + this.nombre + "', '" + this.surname + "', '" 
						+ this.birth_date + "', '" + 0 + "')");

				registrado = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return registrado;
	}

	public boolean validarUsuario(String username, String password) {

		boolean valido = false;

		try {
			ResultSet resultSet = db.realizarQuery("SELECT username, password FROM WordleGame.USERS WHERE username = '" + username + "' AND password= '" + password + "'");

			if(username.toLowerCase().equals("invitado") && password.equals("") || resultSet != null && resultSet.next()) {
				valido = true;

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return valido;
	}

}
