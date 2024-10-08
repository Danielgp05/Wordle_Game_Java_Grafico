package controlador;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import Util.Util;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Juego;


public class WordleController implements Initializable{

	@FXML private AnchorPane panelJuego;

	@FXML private Button boton_A;
	@FXML private Button boton_B;
	@FXML private Button boton_Borrar;
	@FXML private Button boton_C;
	@FXML private Button boton_D;
	@FXML private Button boton_E;
	@FXML private Button boton_Enviar;
	@FXML private Button boton_F;
	@FXML private Button boton_G;
	@FXML private Button boton_H;
	@FXML private Button boton_I;
	@FXML private Button boton_J;
	@FXML private Button boton_K;
	@FXML private Button boton_L;
	@FXML private Button boton_M;
	@FXML private Button boton_N;
	@FXML private Button boton_O;
	@FXML private Button boton_P;
	@FXML private Button boton_Q;
	@FXML private Button boton_R;
	@FXML private Button boton_S;
	@FXML private Button boton_T;
	@FXML private Button boton_U;
	@FXML private Button boton_V;
	@FXML private Button boton_W;
	@FXML private Button boton_X;
	@FXML private Button boton_Y;
	@FXML private Button boton_Z;

	@FXML private TextField casilla1_palabra1;
	@FXML private TextField casilla1_palabra2;
	@FXML private TextField casilla1_palabra3;
	@FXML private TextField casilla1_palabra4;
	@FXML private TextField casilla1_palabra5;
	@FXML private TextField casilla1_palabra6;
	@FXML private TextField casilla2_palabra1;
	@FXML private TextField casilla2_palabra2;
	@FXML private TextField casilla2_palabra3;
	@FXML private TextField casilla2_palabra4;
	@FXML private TextField casilla2_palabra5;
	@FXML private TextField casilla2_palabra6;
	@FXML private TextField casilla3_palabra1;
	@FXML private TextField casilla3_palabra2;
	@FXML private TextField casilla3_palabra3;
	@FXML private TextField casilla3_palabra4;
	@FXML private TextField casilla3_palabra5;
	@FXML private TextField casilla3_palabra6;
	@FXML private TextField casilla4_palabra1;
	@FXML private TextField casilla4_palabra2;
	@FXML private TextField casilla4_palabra3;
	@FXML private TextField casilla4_palabra4;
	@FXML private TextField casilla4_palabra5;
	@FXML private TextField casilla4_palabra6;
	@FXML private TextField casilla5_palabra1;
	@FXML private TextField casilla5_palabra2;
	@FXML private TextField casilla5_palabra3;
	@FXML private TextField casilla5_palabra4;
	@FXML private TextField casilla5_palabra5;
	@FXML private TextField casilla5_palabra6;

	@FXML private ImageView perfil;
	@FXML private ImageView ranking;
	@FXML private ImageView salir;
	@FXML private ImageView ayuda;
	@FXML private ImageView cerrarSesion;
	@FXML private ImageView reiniciarPartida;

	@FXML private Button claro_Oscuro;
	@FXML private Button boton_rendirse;


	ArrayList<TextField> casillas = new ArrayList<>();
	private Map<String, String> letrasIntroducidas = new HashMap<>();

	boolean oscuro = true;
	Util u = new Util();
	Juego j = new Juego();

	int numIntentos = 0;
	double puntosObtenidos = 0;

	@FXML
	void pulsarTecla(KeyEvent event) {

		switch (event.getCode()) {
		case A: asignarLetra("A"); break;
		case B: asignarLetra("B"); break;
		case C: asignarLetra("C"); break;
		case D: asignarLetra("D"); break;
		case E: asignarLetra("E"); break;
		case F: asignarLetra("F"); break;
		case G: asignarLetra("G"); break;
		case H: asignarLetra("H"); break;
		case I: asignarLetra("I"); break;
		case J: asignarLetra("J"); break;
		case K: asignarLetra("K"); break;
		case L: asignarLetra("L"); break;
		case M: asignarLetra("M"); break;
		case N: asignarLetra("N"); break;
		case O: asignarLetra("O"); break;
		case P: asignarLetra("P"); break;
		case Q: asignarLetra("Q"); break;
		case R: asignarLetra("R"); break;
		case S: asignarLetra("S"); break;
		case T: asignarLetra("T"); break;
		case U: asignarLetra("U"); break;
		case V: asignarLetra("V"); break;
		case W: asignarLetra("W"); break;
		case X: asignarLetra("X"); break;
		case Y: asignarLetra("Y"); break;
		case Z: asignarLetra("Z"); break;

		case BACK_SPACE: borrarLetra(); break;

		case ENTER: case FINAL: 
			try {
				obtenerPalabraEscritaComprobar();

			}catch (IndexOutOfBoundsException e) {

			}
			break;

		default: break;
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Rellenar la lista con las casillas
		casillas.add(casilla1_palabra1);
		casillas.add(casilla2_palabra1);
		casillas.add(casilla3_palabra1);
		casillas.add(casilla4_palabra1);
		casillas.add(casilla5_palabra1);

		casillas.add(casilla1_palabra2);
		casillas.add(casilla2_palabra2);
		casillas.add(casilla3_palabra2);
		casillas.add(casilla4_palabra2);
		casillas.add(casilla5_palabra2);

		casillas.add(casilla1_palabra3);
		casillas.add(casilla2_palabra3);
		casillas.add(casilla3_palabra3);
		casillas.add(casilla4_palabra3);
		casillas.add(casilla5_palabra3);

		casillas.add(casilla1_palabra4);
		casillas.add(casilla2_palabra4);
		casillas.add(casilla3_palabra4);
		casillas.add(casilla4_palabra4);
		casillas.add(casilla5_palabra4);

		casillas.add(casilla1_palabra5);
		casillas.add(casilla2_palabra5);
		casillas.add(casilla3_palabra5);
		casillas.add(casilla4_palabra5);
		casillas.add(casilla5_palabra5);

		casillas.add(casilla1_palabra6);
		casillas.add(casilla2_palabra6);
		casillas.add(casilla3_palabra6);
		casillas.add(casilla4_palabra6);
		casillas.add(casilla5_palabra6);

		boton_A.setOnMouseClicked((event) -> asignarLetra("A"));
		boton_B.setOnMouseClicked((event) -> asignarLetra("B"));
		boton_C.setOnMouseClicked((event) -> asignarLetra("C"));
		boton_D.setOnMouseClicked((event) -> asignarLetra("D"));
		boton_E.setOnMouseClicked((event) -> asignarLetra("E"));
		boton_F.setOnMouseClicked((event) -> asignarLetra("F"));
		boton_G.setOnMouseClicked((event) -> asignarLetra("G"));
		boton_H.setOnMouseClicked((event) -> asignarLetra("H"));
		boton_I.setOnMouseClicked((event) -> asignarLetra("I"));
		boton_J.setOnMouseClicked((event) -> asignarLetra("J"));
		boton_K.setOnMouseClicked((event) -> asignarLetra("K"));
		boton_L.setOnMouseClicked((event) -> asignarLetra("L"));
		boton_M.setOnMouseClicked((event) -> asignarLetra("M"));
		boton_N.setOnMouseClicked((event) -> asignarLetra("N"));
		boton_O.setOnMouseClicked((event) -> asignarLetra("O"));
		boton_P.setOnMouseClicked((event) -> asignarLetra("P"));
		boton_Q.setOnMouseClicked((event) -> asignarLetra("Q"));
		boton_R.setOnMouseClicked((event) -> asignarLetra("R"));
		boton_S.setOnMouseClicked((event) -> asignarLetra("S"));
		boton_T.setOnMouseClicked((event) -> asignarLetra("T"));
		boton_U.setOnMouseClicked((event) -> asignarLetra("U"));
		boton_V.setOnMouseClicked((event) -> asignarLetra("V"));
		boton_W.setOnMouseClicked((event) -> asignarLetra("W"));
		boton_X.setOnMouseClicked((event) -> asignarLetra("X"));
		boton_Y.setOnMouseClicked((event) -> asignarLetra("Y"));
		boton_Z.setOnMouseClicked((event) -> asignarLetra("Z"));

		boton_Enviar.setOnMouseClicked((event) -> obtenerPalabraEscritaComprobar());

		boton_Borrar.setOnMouseClicked((event) -> borrarLetra());

		ayuda.setOnMouseClicked((event) -> u.mostrarVentana("/vista/Ayuda.fxml", "Ayuda", false, oscuro));
		salir.setOnMouseClicked((event) -> mostrarDialogoConfirmar("Salir del juego", "¿Estás seguro que quieres cerrar el juego?", "salir",event));
		cerrarSesion.setOnMouseClicked((event) ->{
			if(u.getUsernameUsuarioActual().equals("invitado")) {
				mostrarDialogoConfirmar("Cerrar Sesión", "¿Estás seguro que quieres cerrar sesión?", "cerrarSesion",event);

			}else {
				mostrarDialogoConfirmar("Cerrar Sesión", "¿Estás seguro que quieres cerrar sesión? Tu progreso no se guardará", "cerrarSesion",event);

			}

		});

		perfil.setOnMouseClicked((event) ->{

			if(u.getUsernameUsuarioActual().toLowerCase().equals("invitado")) {
				u.mostrarDialogo("Acción no permitida", "Como invitado no puede acceder a perfil, entre con su usuario para tener acceso", oscuro);

			}else {
				u.mostrarVentana("/vista/Perfil.fxml", "Perfil", false, oscuro);

			}
		});

		ranking.setOnMouseClicked((event) ->{
			if(u.getUsernameUsuarioActual().toLowerCase().equals("invitado")) {
				u.mostrarDialogo("Acción no permitida", "Como invitado no puede acceder a ranking, entre con su usuario para tener acceso", oscuro);

			}else {
				u.mostrarVentana("/vista/Ranking.fxml", "Ranking", false, oscuro);
			}

		});

		reiniciarPartida.setOnMouseClicked((event) -> mostrarDialogoConfirmar("Reiniciar Partida", "Estás apunto de reiniciar la partida, si estás en tu cuenta la puntuación no se guardará, ¿Quieres reiniciar la partída?", "reiniciarPartida", event));

		claro_Oscuro.setOnMouseClicked(event ->{
			claro_Oscuro.setText(oscuro ? "Modo Oscuro" : "Modo Claro");
			cambiarCSS();

		});

		boton_rendirse.setOnMouseClicked((event) ->	mostrarDialogoConfirmar("Rendirse", "¿Estás seguro que quieres rendirte? Esta partida contará como perdida", "rendirse", event));

	}

	public void asignarLetra(String letra) {

		try {
			for (int i = 0; i <= casillas.size(); i++) {
				if(casillas.get(i).getText().equals("")) {
					casillas.get(i).setText(letra);
					break;

				}

				//Si esta la palabra completa, no continuar
				if(i == 4) {
					boton_Enviar.setDisable(false);
					break;
				}
			}
		}catch (IndexOutOfBoundsException e) {

		}

	}

	public void borrarLetra() {
		for (int i = 4; i >= 0; i--) {
			if (!casillas.get(i).getText().equals("")) {
				casillas.get(i).setText("");
				letrasIntroducidas.remove(casillas.get(i).getText());
				break;

			}
		}
	}

	public void salir() {
		Stage stage = (Stage)panelJuego.getScene().getWindow();
		stage.close();

	}

	private void obtenerPalabraEscritaComprobar() {

		String palabraEscrita = "";
		LocalTime horaTerminada;

		ArrayList<TextField> introducido = new ArrayList<>();

		//Guardar todas las letras y formar la palabra
		for (int i = 0; i < 5; i++) {
			introducido.add(casillas.get(i));
			palabraEscrita += introducido.get(i).getText();

		}

		//Si es menor de tamaño 5 no hace nada
		if(palabraEscrita.length() == 5) {

			//Si la palabra es valida hacer cosas
			if(j.validarPalabra(palabraEscrita)) {

				//Recorrer la palabra
				for (int i = 0; i < 5; i++) {

					//Comprobar Casillas
					if(palabraEscrita.charAt(i) == j.getPalabraRandom().charAt(i)) {
						cambiarCasillas("posicionCorrecta", introducido.get(i));
						letrasIntroducidas.put(palabraEscrita.charAt(i)+"", "posicionCorrecta");
						cambiarTeclas("posicionCorrecta", obtenerBotonPorLetra(palabraEscrita.charAt(i)+""));

					}else if(j.getPalabraRandom().contains(palabraEscrita.charAt(i)+"")) {
						cambiarCasillas("letraCorrecta", introducido.get(i));
						letrasIntroducidas.put(palabraEscrita.charAt(i)+"", "letraCorrecta");
						cambiarTeclas("letraCorrecta", obtenerBotonPorLetra(palabraEscrita.charAt(i)+""));

					}else {
						cambiarCasillas("incorrecto", introducido.get(i));
						letrasIntroducidas.put(palabraEscrita.charAt(i)+"", "incorrecto");
						cambiarTeclas("incorrecto", obtenerBotonPorLetra(palabraEscrita.charAt(i)+""));

					}
					//Cambiar teclas
					animarGiro(casillas.get(i));
				}

				// Borrar las casillas de esta palabra
				numIntentos++;
				borrarCasillas(numIntentos);

				horaTerminada = LocalTime.now();
				j.setHoraTerminada(horaTerminada);

				// Comprobar si se ha acertado la palabra
				if (palabraEscrita.equals(j.getPalabraRandom())) {
					casillas.clear();
					añadirPuntosIntento(numIntentos);
					j.setPuntosObtenidos(puntosObtenidos + 25);

					if(!u.getUsernameUsuarioActual().equals("invitado")) {
						j.setNumIntentos(++numIntentos);
						j.guardarEnBD(true, j.getPalabraRandom());
						j.guardarMaxPuntos();			

					}else {
						u.guardarDatosInvitado(numIntentos, true, puntosObtenidos + 25, j.getPalabraRandom());

					}

					abrirEstadisticas();
					puntosObtenidos = 0;

				} else if (numIntentos == 6) {
					// Si ha alcanzado el máximo de intentos sin acertar
					j.setPuntosObtenidos(0);

					if(!u.getUsernameUsuarioActual().equals("invitado")) {
						j.guardarEnBD(false, j.getPalabraRandom());

					}else {
						u.guardarDatosInvitado(numIntentos, false, 0, j.getPalabraRandom());

					}

					abrirEstadisticas();
					puntosObtenidos = 0;

				}

			}else {
				u.mostrarDialogo("Palabra No Válida", "Esa palabra no está en el diccionario, introduce otra", oscuro);

			}

		}

	}

	public void abrirEstadisticas() {

		PauseTransition delay = new PauseTransition(Duration.seconds(2));

		delay.setOnFinished(event -> {

			mostrarVentanaEstadisticas("/vista/Estadisticas.fxml", "Estadísticas", true, oscuro);

		});

		delay.play();

	}

	public void cambiarCSS() {

		String ruta = "";
		Scene scene = panelJuego.getScene();


		if(oscuro) {
			ruta = "/vista/css/modoClaro.css";
			oscuro = false;

		}else {
			ruta = "/vista/css/modoOscuro.css";
			oscuro = true;

		}

		scene.getStylesheets().clear();
		scene.getStylesheets().add(ruta);

	}

	public void cambiarCasillas(String estado, TextField casilla) {

		switch (estado) {
		case "posicionCorrecta": casilla.getStyleClass().add("correct-position"); break;
		case "letraCorrecta": casilla.getStyleClass().add("correct-letter"); break;
		case "incorrecto": casilla.getStyleClass().add("incorrect-letter"); break;
		default: break;

		}

	}

	public void cambiarTeclas(String estado, Button boton) {

		boton.getStyleClass().clear();

		switch (estado) {
		case "posicionCorrecta": boton.getStyleClass().add("correct-position"); break;
		case "letraCorrecta": boton.getStyleClass().add("correct-letter"); break;
		case "incorrecto": boton.getStyleClass().add("incorrect-letter"); break;
		default: break;

		}

	}

	public void borrarCasillas(int num) {

		switch (num) {
		case 1:
			casillas.remove(casilla1_palabra1);
			casillas.remove(casilla2_palabra1);
			casillas.remove(casilla3_palabra1);
			casillas.remove(casilla4_palabra1);
			casillas.remove(casilla5_palabra1);

			break;

		case 2:
			casillas.remove(casilla1_palabra2);
			casillas.remove(casilla2_palabra2);
			casillas.remove(casilla3_palabra2);
			casillas.remove(casilla4_palabra2);
			casillas.remove(casilla5_palabra2);

			break;

		case 3:
			casillas.remove(casilla1_palabra3);
			casillas.remove(casilla2_palabra3);
			casillas.remove(casilla3_palabra3);
			casillas.remove(casilla4_palabra3);
			casillas.remove(casilla5_palabra3);

			break;

		case 4:
			casillas.remove(casilla1_palabra4);
			casillas.remove(casilla2_palabra4);
			casillas.remove(casilla3_palabra4);
			casillas.remove(casilla4_palabra4);
			casillas.remove(casilla5_palabra4);

			break;

		case 5:
			casillas.remove(casilla1_palabra5);
			casillas.remove(casilla2_palabra5);
			casillas.remove(casilla3_palabra5);
			casillas.remove(casilla4_palabra5);
			casillas.remove(casilla5_palabra5);

			break;
		default:
			break;
		}

	}

	private Button obtenerBotonPorLetra(String letra) {
		switch (letra) {
		case "A": return boton_A;
		case "B": return boton_B;
		case "C": return boton_C;
		case "D": return boton_D;
		case "E": return boton_E;
		case "F": return boton_F;
		case "G": return boton_G;
		case "H": return boton_H;
		case "I": return boton_I;
		case "J": return boton_J;
		case "K": return boton_K;
		case "L": return boton_L;
		case "M": return boton_M;
		case "N": return boton_N;
		case "O": return boton_O;
		case "P": return boton_P;
		case "Q": return boton_Q;
		case "R": return boton_R;
		case "S": return boton_S;
		case "T": return boton_T;
		case "U": return boton_U;
		case "V": return boton_V;
		case "W": return boton_W;
		case "X": return boton_X;
		case "Y": return boton_Y;
		case "Z": return boton_Z;
		default: return null;
		}
	}

	private void animarGiro(TextField casilla) {

		Rotate rotate = new Rotate(0, 50 / 2, 50 / 2, 0, Rotate.X_AXIS);
		casilla.getTransforms().add(rotate);

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(rotate.angleProperty(), 0)),
				new KeyFrame(Duration.seconds(1.5), new KeyValue(rotate.angleProperty(), 360))
				);

		timeline.setOnFinished(event -> casilla.getTransforms().remove(rotate));

		timeline.play();
	}

	public void mostrarDialogoConfirmar(String error, String mensajeError, String accion, Event event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(error);
		alert.setHeaderText(null);
		alert.setContentText(mensajeError);

		// Get the Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		stage.getIcons().add(new Image(this.getClass().getResource("/vista/img/logo.png").toString()));

		// Obtener la escena del Alert
		Scene scene = alert.getDialogPane().getScene();

		if(oscuro) {
			scene.getStylesheets().add(getClass().getResource("/vista/css/modoOscuro.css").toExternalForm());

		}else {
			scene.getStylesheets().add(getClass().getResource("/vista/css/modoClaro.css").toExternalForm());

		}

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){

			switch (accion) {
			case "salir": salir(); break;
			case "cerrarSesion": u.sustituirVentana("/vista/Login.fxml", "Iniciar Sesión", event, oscuro); break;
			case "reiniciarPartida": u.sustituirVentana("/vista/Wordle.fxml", "Wordle", event, oscuro); break;
			case "rendirse": 
				if(u.getUsernameUsuarioActual().equals("invitado")) {
					u.guardarDatosInvitado(0, false, 0, j.getPalabraRandom());

				}else {
					j.setPuntosObtenidos(0);
					j.guardarEnBD(false, j.getPalabraRandom());

				};

				abrirEstadisticas(); break;

			default: break;
			}

		} 

	}

	public void mostrarVentanaEstadisticas(String rutaFXML, String titulo, boolean cancelarCerrar, boolean oscuro) {
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

			//Pasar datos a la otra ventana
			EstadisticasController controller = fxmlLoader.getController();
			controller.setDatos(this);

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

	public void añadirPuntosIntento(int numIntentos) {

		switch (numIntentos) {
		case 1:	puntosObtenidos = 60; break;
		case 2:	puntosObtenidos = 50; break;
		case 3:	puntosObtenidos = 40; break;
		case 4:	puntosObtenidos = 30; break;
		case 5:	puntosObtenidos = 20; break;
		case 6:	puntosObtenidos = 10; break;

		default: break;
		}

	}

	public void sustituirVentanaAlReiniciar(String accion, Event event) {

		switch (accion) {
		case "Juego": u.sustituirVentana("/vista/Wordle.fxml", "Wordle", event, oscuro); break;
		case "iniciarSesion": u.sustituirVentana("/vista/Login.fxml", "Iniciar Sesión", event, oscuro); break;

		default:
			break;
		}

	}

}
