package controlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Util.DataBaseUtil;
import Util.Util;

public class RankingController implements Initializable {

    @FXML private TableColumn<String[], String> columna_Nombre;
    @FXML private TableColumn<String[], String> columna_NombreUsuario;
    @FXML private TableColumn<String[], String> columna_Puesto;
    @FXML private TableColumn<String[], String> columna_Puntos;
    @FXML private TableView<String[]> tabla;

    DataBaseUtil db = new DataBaseUtil();
    Util u = new Util();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Configurar las celdas de cada columna para mostrar los valores
        columna_Puesto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0] + "º"));
        columna_Nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        columna_NombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        columna_Puntos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));

        // Insertar los datos en la tabla
        insertarDatos();
        
        // Deshabilitar la capacidad de reordenar columnas
        tabla.getColumns().forEach(columna -> columna.setReorderable(false));
        
    }

    public void insertarDatos() {
        try {

            ResultSet resultSet = db.realizarQuery("SELECT * FROM WordleGame.USERS ORDER BY max_score DESC");

            int puesto = 1;

            while (resultSet.next()) {
                String nombre = resultSet.getString("name");
                String nombreUsuario = resultSet.getString("username");
                String puntos = resultSet.getString("max_score");

                String[] fila = {Integer.toString(puesto), nombre, nombreUsuario, u.formatearPuntos(puntos)};

                // Agregar la fila a la tabla
                tabla.getItems().add(fila);

                puesto++;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}