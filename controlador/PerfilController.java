package controlador;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Util.DataBaseUtil;
import Util.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PerfilController implements Initializable{

	@FXML private TextField textField_nombre;
	@FXML private TextField textField_Apellidos;
	@FXML private TextField textField_fecha;
	@FXML private TextField textField_Puntos;
	@FXML private TextField textField_nombreUsuario;
	@FXML private TextField textField_textoContraseña;

	@FXML private PasswordField field_Contraseña;

	@FXML private Button boton_Editar;
	@FXML private Button boton_cancelarEditar;
	@FXML private Button boton_guardarCambios;


	@FXML private CheckBox mostrarContraseña;

	Util u = new Util();
	DataBaseUtil db = new DataBaseUtil();

	String nombreUsuario = u.getUsernameUsuarioActual();
	String idUsuario = u.obtenerCodigoUsuario(nombreUsuario);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Enlazar los dos textField
		field_Contraseña.textProperty().bindBidirectional(textField_textoContraseña.textProperty());

		//Obtener datos de la BD
		textField_nombre.setText(obtenerNombre());
		textField_Apellidos.setText(obtenerApellidos());
		textField_fecha.setText(formatearFecha());
		textField_Puntos.setText(u.formatearPuntos(obtenerMaxPuntos()));
		field_Contraseña.setText(obtenerContraseña());
		textField_nombreUsuario.setText(obtenerNombreUsuario());

		//Permitir edición
		boton_Editar.setOnMouseClicked((event) -> {
			textField_nombre.setEditable(true);
			textField_fecha.setEditable(true);
			textField_Apellidos.setEditable(true);
			mostrarContraseña.setVisible(true);
			textField_nombreUsuario.setEditable(true);
			field_Contraseña.setEditable(true);
			
			boton_Editar.setVisible(false);
			boton_cancelarEditar.setVisible(true);
			boton_guardarCambios.setVisible(true);

		});

		boton_cancelarEditar.setOnMouseClicked((event) -> salir());
		boton_guardarCambios.setOnMouseClicked((event) ->{

			if(validarFecha()) {
				actualizarDatosBD();
				salir();
				
			}else {
				u.mostrarDialogo("Error en la fecha", "Introduzca un formato de fecha válido", true);

			}

		});

		//Mostrar contreseña
		mostrarContraseña.setOnAction(event -> {
			if (mostrarContraseña.isSelected()) {
				textField_textoContraseña.setVisible(true);

			} else {
				textField_textoContraseña.setVisible(false);
				field_Contraseña.setVisible(true);

			}

		}); 

	}

	public void salir() {
		Stage stage = (Stage)boton_cancelarEditar.getScene().getWindow();
		stage.close();

	}

	public boolean validarFecha() {

		boolean valida = false;

		try {
			LocalDate.parse(textField_fecha.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			valida = true;

		}catch(Exception e) {

		}

		return valida;

	}

	public String obtenerNombreUsuario() {

		String nombreUsuario = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT username FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				nombreUsuario = resultSet.getString("username");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombreUsuario;
	}

	public String obtenerNombre() {

		String nombre = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT name FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				nombre = resultSet.getString("name");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombre;

	}

	public String obtenerApellidos() {

		String apellidos = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT surname FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				apellidos = resultSet.getString("surname");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return apellidos;

	}

	public String obtenerFecha() {

		String fecha = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT birth_date FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				fecha = resultSet.getString("birth_date");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fecha;
	}

	public String obtenerMaxPuntos() {

		String puntos = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT max_score FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				puntos = resultSet.getString("max_score");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return puntos;

	}

	public String obtenerContraseña() {

		String contraseña = "";

		try {
			ResultSet resultSet = db.realizarQuery("SELECT password FROM WordleGame.USERS WHERE user_id=" + idUsuario);	
			while (resultSet.next()) {
				contraseña = resultSet.getString("password");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contraseña;

	}

	public String formatearFecha() {
		String fecha = "";
		String[] partesFecha = obtenerFecha().split("-");

		fecha = partesFecha[2] + "/" +  partesFecha[1] + "/" + partesFecha[0];

		return fecha;
	}

	public void actualizarDatosBD() {

		String nombre = textField_nombre.getText();
		String apellidos = textField_Apellidos.getText();
		String fecha = "";
		String contraseña = textField_textoContraseña.getText();
		String nombreUsuario = textField_nombreUsuario.getText();

		String[] partesFecha = textField_fecha.getText().split("\\/");
		fecha = partesFecha[2] + "-" +  partesFecha[1] + "-" + partesFecha[0];
		
		u.guardarUsuarioActual(nombreUsuario);
		db.realizarInsertYUpdate("UPDATE WordleGame.USERS SET username= '" + nombreUsuario + "', surname='" + apellidos +  "', name='" + nombre + "', birth_date='" + fecha 
				+ "', password='" + contraseña + "' WHERE user_id=" + u.obtenerCodigoUsuario(u.getUsernameUsuarioActual()));

	}

}
