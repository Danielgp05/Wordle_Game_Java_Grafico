package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Util {

	DataBaseUtil db = new DataBaseUtil();

	public void sustituirVentana(String rutaFXML, String titulo, Event event, boolean oscuro) {

		try {
			// Cargar la nueva vista
			Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));

			// Obtener el escenario actual
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			//stage.initStyle(StageStyle.UTILITY);

			// Crear una nueva escena con la nueva vista
			Scene scene = new Scene(root);

			if(oscuro) {
				scene.getStylesheets().add(getClass().getResource("/vista/css/modoOscuro.css").toExternalForm());

			}else {
				scene.getStylesheets().add(getClass().getResource("/vista/css/modoClaro.css").toExternalForm());

			}

			// Configurar la escena en el escenario y mostrarla
			stage.setScene(scene);
			stage.setTitle(titulo);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void mostrarVentana(String rutaFXML, String titulo, boolean cancelarCerrar, boolean oscuro) {
		try {
			// Cargar el FXML
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(rutaFXML));
			Parent root = fxmlLoader.load();

			// Crear una nueva escena con el nodo raíz del FXML
			Scene scene = new Scene(root);

			// Cargar el CSS para aplicar al estilo de la escena
			if(oscuro) {
				scene.getStylesheets().add(getClass().getResource("/vista/css/modoOscuro.css").toExternalForm());

			}else {
				scene.getStylesheets().add(getClass().getResource("/vista/css/modoClaro.css").toExternalForm());

			}

			// Crear un nuevo Stage (ventana)
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);

			//stage.initStyle(StageStyle.UTILITY);
			//stage.initStyle(StageStyle.DECORATED);
			//stage.initStyle(StageStyle.UNDECORATED);
			//stage.initStyle(StageStyle.UNIFIED);
			//stage.initStyle(StageStyle.TRANSPARENT);                

			// Configurar el icono de la ventana
			stage.getIcons().add(new Image(getClass().getResource("/vista/img/logo.png").toExternalForm()));

			if (cancelarCerrar) {
				// Evitar el cierre de la ventana si es necesario
				stage.setOnCloseRequest(event -> event.consume());
			}

			// Mostrar la ventana
			stage.show();

		} catch (Exception e) {
			// Manejar cualquier excepción
			e.printStackTrace();
		}
	}

	public void mostrarDialogo(String cabezera, String mensajeError, boolean oscuro) {
		Alert alert = new Alert(AlertType.ERROR);

		// Get the Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(this.getClass().getResource("/vista/img/logo.png").toString()));

		alert.setTitle(cabezera);
		alert.setHeaderText(null);

		// Obtener la escena del Alert
		Scene scene = alert.getDialogPane().getScene();

		if(oscuro) {
			scene.getStylesheets().add(getClass().getResource("/vista/css/modoOscuro.css").toExternalForm());

		}else {
			scene.getStylesheets().add(getClass().getResource("/vista/css/modoClaro.css").toExternalForm());

		}

		alert.setContentText(mensajeError);
		alert.showAndWait();

	}

	public void guardarUsuarioActual(String nombre) {
		try {
			PrintWriter pw = new PrintWriter(new File("src/ficheros/usuarioActual.txt")); //Habilitamos la escritura

			pw.print(nombre.toLowerCase()); //Añadimos contenido
			pw.close(); //Cerramos la conexion con el fichero

		} catch (FileNotFoundException e) {
			System.err.println("Problemas al abrir el fichero");

		}
	}

	public String getUsernameUsuarioActual() {

		String user = "";
		try {
			Scanner scanner = new Scanner(new File("src/ficheros/usuarioActual.txt"));
			user = scanner.nextLine();
			scanner.close();

		}
		catch (FileNotFoundException ex) {
			System.err.println("El fichero no existe. " + ex);

		}

		return user;
	}

	public String obtenerCodigoUsuario(String user) {
		String textId = "";

		try {
			ResultSet idUsuario = db.realizarQuery("SELECT * FROM WordleGame.USERS WHERE LOWER(username)='" + user + "'");

			while(idUsuario.next()) {
				textId =  idUsuario.getString("user_id");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return textId;
	}

	public int obtenerCodigoPalabra(String palabra) {
		int wordId = 0;

		try {
			ResultSet idUsuario = db.realizarQuery("SELECT word_id FROM WordleGame.WORD WHERE description='" + palabra + "'");

			while(idUsuario.next()) {
				wordId =  idUsuario.getInt("word_id");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wordId;


	}

	public void guardarDatosInvitado(int intentos, boolean ganado, double puntos, String palabra) {
		try {
			PrintWriter pw = new PrintWriter(new File("src/ficheros/usuarioActualDatos.txt"));

			pw.print(intentos + "," + ganado + "," + puntos + "," + palabra);
			pw.close();

		} catch (FileNotFoundException e) {
			System.err.println("Problemas al abrir el fichero");

		}

	}

	public String[] getDatosInvitado() {

		String[] invitado = new String[3];
		String linea = "";

		try {
			Scanner scanner = new Scanner(new File("src/ficheros/usuarioActualDatos.txt"));

			linea = scanner.nextLine();
			invitado = linea.split(",");

			scanner.close();

		}
		catch (FileNotFoundException ex) {
			System.err.println("El fichero no existe. " + ex);

		}

		return invitado;
	}

	public String formatearPuntos(String puntos) {
		
		double puntuacion = Double.parseDouble(puntos);
		
		int puntosInt = (int) puntuacion;
		
		if(puntosInt == puntuacion) {
			return puntosInt + "";
			
		}else {
			return puntos +"";
	
		}
				
	}

}



