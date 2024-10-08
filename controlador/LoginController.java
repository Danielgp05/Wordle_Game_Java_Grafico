package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Util.Util;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modelo.Usuario;

public class LoginController implements Initializable{

	@FXML private PasswordField field_Constraseña;
	@FXML private TextField contraseñaVisible;
	@FXML private TextField text_Usuario;
	@FXML private Hyperlink registrarse;
	@FXML private Button boton_iniciarSesion;
	@FXML private CheckBox mostrarConstraseña;

	Util u = new Util();
	Usuario user = new Usuario();

	@FXML
	void pulsarTecla(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER: case FINAL: iniciarSesion(event); break;

		default: break;

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Enlazar el texto de ambos campos de contraseña
		field_Constraseña.textProperty().bindBidirectional(contraseñaVisible.textProperty());

		// Mostrar contraseña
		mostrarConstraseña.setOnAction(event -> {
			if (mostrarConstraseña.isSelected()) {
				contraseñaVisible.setVisible(true);

			} else {
				contraseñaVisible.setVisible(false);
				field_Constraseña.setVisible(true);

			}

		}); 

		boton_iniciarSesion.setOnMouseClicked((event) -> iniciarSesion(event));

		//Registrarse
		registrarse.setOnMouseClicked((event) -> u.sustituirVentana("/vista/Registro.fxml", "Registrarse", event, true));

	}

	public void iniciarSesion(Event event) {
		if(user.validarUsuario(text_Usuario.getText(), field_Constraseña.getText())){
			u.guardarUsuarioActual(text_Usuario.getText());
			u.sustituirVentana("/vista/Wordle.fxml", "Wordle", event, true);

		}else {
			u.mostrarDialogo("Usuario No Válido", "Usuario o contraseña incorrectos", true);

		}

	}

}
