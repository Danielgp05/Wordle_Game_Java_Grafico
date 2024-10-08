package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Util.Util;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modelo.Usuario;

public class RegistroController implements Initializable{

	@FXML private Button boton_Registrarse;
	@FXML private TextField constraseñaVisible;
	@FXML private DatePicker fechaNacimiento;
	@FXML private PasswordField field_Constraseña;
	@FXML private Hyperlink iniciarSesion;
	@FXML private TextField text_Usuario;
	@FXML private CheckBox mostrarConstraseña;
	@FXML private TextField text_Apellidos;
	@FXML private TextField text_fullName;

	Util u = new Util();

	@FXML
	void pulsarTecla(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER: case FINAL: registrarse(event); break;

		default: break;

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Enlazar el texto de ambos campos de contraseña
		field_Constraseña.textProperty().bindBidirectional(constraseñaVisible.textProperty());

		// Mostrar contraseña
		mostrarConstraseña.setOnAction(event -> {
			if (mostrarConstraseña.isSelected()) {
				constraseñaVisible.setVisible(true);

			} else {
				constraseñaVisible.setVisible(false);
				field_Constraseña.setVisible(true);

			}

		}); 

		boton_Registrarse.setOnMouseClicked((event) -> registrarse(event));

		//Iniciar Sesion
		iniciarSesion.setOnMouseClicked((event) -> u.sustituirVentana("/vista/Login.fxml", "Iniciar Sesión", event, true));

	}

	public void registrarse(Event event) {
		Usuario user; 
		
		try {
			user = new Usuario(text_Usuario.getText(), field_Constraseña.getText(), text_fullName.getText(), text_Apellidos.getText(), fechaNacimiento.getValue().toString());
			
			//Si la fecha es anterior o igual que hoy que haga cosas
			if(LocalDate.parse(fechaNacimiento.getValue().toString()).isBefore(LocalDate.now()) 
					|| LocalDate.parse(fechaNacimiento.getValue().toString()).equals(LocalDate.now())) {
				
				//Comprobar que todos los campos tengan informacion
				if(!text_Apellidos.getText().equals("") && !text_fullName.getText().equals("") && !text_Usuario.getText().equals("") 
						&& !field_Constraseña.getText().equals("")) {
				
					if(user.registrarUsuario()) {
						//Cambiar de ventana
						u.guardarUsuarioActual(text_Usuario.getText());
						u.sustituirVentana("/vista/Wordle.fxml", "Wordle", event, true);

					}else {
						text_Usuario.setText("");

					}
					
				}else {
					u.mostrarDialogo("Error de registro", "Faltan campos por rellenar, introduzca todos los datos y registrate", true);

				}
						
			}else {
				u.mostrarDialogo("Error de registro", "No puedes poner una fecha que pase del dia actual o superior, indica una fecha correcta", true);

			}

		}catch (NullPointerException e) {
			u.mostrarDialogo("Error de registro", "Faltan campos por rellenar, introduzca todos los datos y registrate", true);

		}

	}


}
