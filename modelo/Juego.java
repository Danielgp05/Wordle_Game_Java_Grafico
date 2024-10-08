package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import Util.DataBaseUtil;
import Util.Util;

public class Juego {

	//Atributos
	private String palabraRandom;
	private LocalTime horaEmpezada;
	private LocalTime horaTerminada;
	private int numIntentos;
	private LocalDate diaActual;
	private double puntosObtenidos;

	DataBaseUtil db = new DataBaseUtil();
	Util u = new Util();

	//Constructor
	public Juego() {
		this.palabraRandom = conseguirPalabra(); 
		this.horaEmpezada = LocalTime.now();
		this.diaActual = LocalDate.now();
		
	}

	public void setNumIntentos(int numIntentos) {
		this.numIntentos = numIntentos - 1;
	}

	public String getPalabraRandom() {
		return palabraRandom;
	}

	public void setHoraTerminada(LocalTime horaTerminada) {
		this.horaTerminada = horaTerminada;

	}

	public void setPuntosObtenidos(double puntosObtenidos) {
		this.puntosObtenidos = puntosObtenidos;
	}

	public String conseguirPalabra() {

		String columnValue = "";
		int random;

		try {

			while(true) {
				random = (int) (Math.random() * (685 - 1 + 1) + 1);

				ResultSet resultSet = db.realizarQuery("SELECT description FROM WordleGame.WORD WHERE comun=true AND word_id=" + random);
				while (resultSet.next()) {
					columnValue = resultSet.getString("description");

				}

				if(!columnValue.equals("")) {
					//System.out.println("Respuesta: " + columnValue);
					break;
					
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return columnValue;
	}

	public void guardarEnBD(boolean ganado, String palabra) {

		String tiempo = "";
		
		if(!u.getUsernameUsuarioActual().equals("invitado")) {
		
			try {
				Duration duracion = Duration.between(this.horaEmpezada, this.horaTerminada);
				
				// Obtener los minutos y segundos de la duración
				long minutos = duracion.toMinutes();
				long segundos = duracion.getSeconds() % 60; // El resto de segundos después de restar los minutos

				tiempo = String.format("%d:%02d%n", minutos, segundos);

			}catch (NullPointerException e) {
				tiempo = "RENDIDA";
				
			}

			String id = u.obtenerCodigoUsuario(u.getUsernameUsuarioActual());
			

			int idWord = u.obtenerCodigoPalabra(palabra);

			if(ganado) {
				//Guardar el game en la base de datos
				db.realizarInsertYUpdate("INSERT INTO WordleGame.GAME (game_time, game_date, number_attemps, score, user_id, victory, correctWord) VALUES ('" 
						+ tiempo + "', '" + this.diaActual + "', '" + this.numIntentos + "', '" + this.puntosObtenidos + "', "+ id + ", " + true + ", " + idWord + ")");

			}else {
				//Guardar el game en la base de datos
				db.realizarInsertYUpdate("INSERT INTO WordleGame.GAME (game_time, game_date, number_attemps, score, user_id, correctWord) VALUES ('" 
						+ tiempo + "', '" + this.diaActual + "', '" + this.numIntentos + "', '" + this.puntosObtenidos + "', "+ id + ", " + idWord + ")");
				
			}

		}

	}

	public boolean validarPalabra(String palabra) {
		boolean valido = false;

		try  {
			// Realizar operaciones con la base de datos
			ResultSet resultSet = db.realizarQuery("SELECT * FROM WordleGame.WORD WHERE description='" + palabra + "'");

			// Verificar si el ResultSet no es nulo y si contiene algún resultado
			if (resultSet != null && resultSet.next()) {
				// Si hay al menos un resultado en el ResultSet
				valido = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return valido;
	}

	public void guardarMaxPuntos() {
		double maxPunt = 0;
		
		try  {
			// Realizar operaciones con la base de datos
			ResultSet resultSet = db.realizarQuery("SELECT max_score FROM WordleGame.USERS WHERE user_id='" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()) + "'");

			while(resultSet.next()) {
				maxPunt =  resultSet.getDouble("max_score");
			}

			if(this.puntosObtenidos > maxPunt) {
				db.realizarInsertYUpdate("UPDATE WordleGame.USERS SET max_score=" + this.puntosObtenidos + " WHERE user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		
	}

}
