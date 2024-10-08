package controlador;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Util.DataBaseUtil;
import Util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class EstadisticasController implements Initializable{

	private WordleController fxmlLoader;

	@FXML private Label label_intentos;
	@FXML private Label label_numPartidas;
	@FXML private Label label_numVictorias;
	@FXML private Label label_palabra;
	@FXML private Label label_puntosObtenidos;

	@FXML private Button boton_volverAJugar;
	@FXML private Button boton_cerrarSesion;
	@FXML private Button boton_salirDelJuego;

	@FXML private BarChart<Integer, String> grafico;

	String[] datosInvitado;

	int partidasInvitado = 0;
	int partidasGanadas = 0;

	DataBaseUtil db = new DataBaseUtil();
	Util u = new Util();

	@FXML
	void pulsarTecla(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER: case FINAL: 
			fxmlLoader.oscuro = true;
			fxmlLoader.sustituirVentanaAlReiniciar("Juego", event);
			fxmlLoader.salir();

		default: break;

		}
	}

	@FXML
	void cerrar(ActionEvent event) {

		Stage stage = (Stage) boton_salirDelJuego.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setDatos(fxmlLoader);

		// Definir una serie de datos
		XYChart.Series<Integer, String> series1 = new XYChart.Series<>();

		if(!u.getUsernameUsuarioActual().equals("invitado")) {
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(6), "6"));
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(5), "5"));
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(4), "4"));
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(3), "3"));
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(2), "2"));
			series1.getData().add(new XYChart.Data<>(obtenerVictoriasDependeIntentos(1), "1"));

			label_numPartidas.setText(obtenerPartidasDeUsuario()+"");
			label_intentos.setText(obtenerIntentos()+"");
			label_palabra.setText("RESULTADO: " + obtenerPalabra());
			label_numVictorias.setText(obtenerVictorias()+"");
			label_puntosObtenidos.setText(u.formatearPuntos(obtenerPuntuacion()+""));

		}else {

			partidasInvitado++;
			datosInvitado = u.getDatosInvitado();

			agregarInfoGraficoInvitado(datosInvitado[0], series1);

			if(datosInvitado[1].equals("true")) {
				partidasGanadas++;

			}

			label_numPartidas.setText(partidasInvitado+"");

			label_intentos.setText(datosInvitado[0]);
			label_numVictorias.setText(partidasGanadas+"");
			label_puntosObtenidos.setText(u.formatearPuntos(datosInvitado[2]));
			label_palabra.setText("RESULTADO: " + datosInvitado[3]);

		}

		// Agregar las series al gráfico
		grafico.getData().add(series1);

		boton_volverAJugar.setOnMouseClicked((event) ->{
			fxmlLoader.oscuro = true;
			fxmlLoader.sustituirVentanaAlReiniciar("Juego", event);
			fxmlLoader.salir();

		});

		boton_cerrarSesion.setOnMouseClicked((event) ->{
			fxmlLoader.sustituirVentanaAlReiniciar("iniciarSesion", event);
			fxmlLoader.salir();

		});

		boton_salirDelJuego.setOnMouseClicked((event) ->{
			salir();
			fxmlLoader.salir();

		});

	}

	public int obtenerPartidasDeUsuario() {

		int numPartidas = 0;

		try {
			ResultSet resultSet = db.realizarQuery("SELECT COUNT(*) AS total_Partidas FROM WordleGame.GAME WHERE user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()));

			while(resultSet.next()) {
				numPartidas =  resultSet.getInt("total_Partidas");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return numPartidas;
	}

	public int obtenerIntentos() {

		int numIntentos = 0;

		try {
			ResultSet resultSet = db.realizarQuery("SELECT number_attemps AS total_Intentos FROM WordleGame.GAME WHERE user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()) + " ORDER BY game_id DESC LIMIT 1");

			while(resultSet.next()) {
				numIntentos =  resultSet.getInt("total_Intentos");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return numIntentos;

	}

	public String obtenerPalabra() {

		String idWord = "";
		String palabra = "";

		try {

			ResultSet idWordGame = db.realizarQuery("SELECT correctWord FROM WordleGame.GAME ORDER BY game_id DESC LIMIT 1" );

			while(idWordGame.next()) {
				idWord =  idWordGame.getString("correctWord");
			}

			ResultSet obtenerPalabra = db.realizarQuery("SELECT description FROM WordleGame.WORD WHERE word_id =" + idWord);

			while(obtenerPalabra.next()) {
				palabra = obtenerPalabra.getString("description");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}


		return palabra;
	}

	public int obtenerVictorias() {

		int victorias = 0;

		try {

			ResultSet totalVictorias = db.realizarQuery("SELECT COUNT(*) as victoria FROM WordleGame.GAME WHERE victory=true AND user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()));

			while(totalVictorias.next()) {
				victorias =  totalVictorias.getInt("victoria");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return victorias;
	}

	public double obtenerPuntuacion() {

		double puntos = 0;

		try {

			ResultSet totalVictorias = db.realizarQuery("SELECT score FROM WordleGame.GAME WHERE user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()) + " ORDER BY game_id DESC");

			while(totalVictorias.next()) {
				puntos =  totalVictorias.getDouble("score");
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return puntos;

	}

	public int obtenerVictoriasDependeIntentos(int intento) {

		int victorias = 0;

		try {

			ResultSet totalVictorias = db.realizarQuery("SELECT COUNT(*) as numVictorias FROM WordleGame.GAME WHERE victory=true AND user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()) + " AND number_attemps=" + intento);

			while(totalVictorias.next()) {
				victorias =  totalVictorias.getInt("numVictorias");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return victorias;
	}

	private void salir() {
		Stage stage = (Stage) label_intentos.getScene().getWindow();
		stage.close();

	}

	public void setDatos(WordleController loader) {
		this.fxmlLoader = loader;

	}

	public void agregarInfoGraficoInvitado(String intento, XYChart.Series<Integer, String> series1) {

		switch (intento) {
		case "1":
			series1.getData().add(new XYChart.Data<>(0, "6"));
			series1.getData().add(new XYChart.Data<>(0, "5"));
			series1.getData().add(new XYChart.Data<>(0, "4"));
			series1.getData().add(new XYChart.Data<>(0, "3"));
			series1.getData().add(new XYChart.Data<>(0, "2"));
			series1.getData().add(new XYChart.Data<>(1, "1"));
			break;
		case "2":
			series1.getData().add(new XYChart.Data<>(0, "6"));
			series1.getData().add(new XYChart.Data<>(0, "5"));
			series1.getData().add(new XYChart.Data<>(0, "4"));
			series1.getData().add(new XYChart.Data<>(0, "3"));
			series1.getData().add(new XYChart.Data<>(1, "2"));
			series1.getData().add(new XYChart.Data<>(0, "1"));
			break;
		case "3":
			series1.getData().add(new XYChart.Data<>(0, "6"));
			series1.getData().add(new XYChart.Data<>(0, "5"));
			series1.getData().add(new XYChart.Data<>(0, "4"));
			series1.getData().add(new XYChart.Data<>(1, "3"));
			series1.getData().add(new XYChart.Data<>(0, "2"));
			series1.getData().add(new XYChart.Data<>(0, "1"));
			break;
		case "4":
			series1.getData().add(new XYChart.Data<>(0, "6"));
			series1.getData().add(new XYChart.Data<>(0, "5"));
			series1.getData().add(new XYChart.Data<>(1, "4"));
			series1.getData().add(new XYChart.Data<>(0, "3"));
			series1.getData().add(new XYChart.Data<>(0, "2"));
			series1.getData().add(new XYChart.Data<>(0, "1"));
			break;
		case "5":
			series1.getData().add(new XYChart.Data<>(0, "6"));
			series1.getData().add(new XYChart.Data<>(1, "5"));
			series1.getData().add(new XYChart.Data<>(0, "4"));
			series1.getData().add(new XYChart.Data<>(0, "3"));
			series1.getData().add(new XYChart.Data<>(0, "2"));
			series1.getData().add(new XYChart.Data<>(0, "1"));
			break;
		case "6":
			series1.getData().add(new XYChart.Data<>(1, "6"));
			series1.getData().add(new XYChart.Data<>(0, "5"));
			series1.getData().add(new XYChart.Data<>(0, "4"));
			series1.getData().add(new XYChart.Data<>(0, "3"));
			series1.getData().add(new XYChart.Data<>(0, "2"));
			series1.getData().add(new XYChart.Data<>(0, "1"));
			break;	
		default:
			break;
		}

	}

}
