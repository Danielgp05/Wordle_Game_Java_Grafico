package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class AyudaController implements Initializable{

    @FXML private TextField textField_A;
    @FXML private TextField textField_A2;
    @FXML private TextField textField_I;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		animarGiro(textField_A);
		animarGiro(textField_I);
		animarGiro(textField_A2);

	}
    
	private void animarGiro(TextField casilla) {
		// Rotar desde el centro del textfield
		Rotate rotate = new Rotate(0, 50 / 2, 50 / 2, 0, Rotate.X_AXIS);
		casilla.getTransforms().add(rotate);

		// Creamos una animación para el backflip
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(rotate.angleProperty(), 0)),
				new KeyFrame(Duration.seconds(1.5), new KeyValue(rotate.angleProperty(), 360))
				);

		//Restaurar la posición inicial
		timeline.setOnFinished(event -> casilla.getTransforms().remove(rotate));

		timeline.play();
	}

    
}
