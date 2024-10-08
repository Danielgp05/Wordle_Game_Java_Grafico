package aplicacion;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			String fxml = "vista/Login.fxml";

			// Cargar la ventana
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));

			// Cargar la Scene
			Scene scene = new Scene(root);

			// Asignar propiedades al Stage
			primaryStage.setTitle("Iniciar Sesión");
			primaryStage.setResizable(false);

			//Asignar icono
			primaryStage.getIcons().add(new Image(getClass().getResource("/vista/img/logo.png").toExternalForm()));
			//primaryStage.initStyle(StageStyle.UNDECORATED);

			// Asignar la scene y mostrar
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
