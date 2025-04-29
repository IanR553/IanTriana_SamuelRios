package controller;

import java.sql.Connection;

import application.Main;
import data.DBConnection;
import data.ProductoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Producto;

public class RegistroProductosController {

    @FXML
    private TableColumn<Producto, String> columnCantidad;

    @FXML
    private TableColumn<Producto, String> columnNombre;

    @FXML
    private TableColumn<Producto, String> columnPrecio;

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtReferencia;

    private Connection connection = DBConnection.getInstance().getConnection();
    private ProductoDAO productoDAO = new ProductoDAO(connection);

    @FXML
    public void initialize() {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        loadProductos();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Producto selected = tableProductos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productoDAO.delete(selected.getReferencia());
            loadProductos();
        } else {
            showAlert("Error", "Selecciona un producto para eliminar.");
        }
    }

    @FXML
    void registrar(ActionEvent event) {
        try {
            String referenciaTexto = txtReferencia.getText();

            if (!referenciaTexto.matches("\\d{4}")) {
                showAlert("Error", "La referencia debe ser un número de 4 dígitos.");
                return;
            }

            int referencia = Integer.parseInt(referenciaTexto);
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (nombre.isEmpty()) {
                showAlert("Error", "Por favor completa todos los campos.");
                return;
            }

            Producto producto = new Producto(referencia, nombre, precio, cantidad);
            productoDAO.save(producto);
            loadProductos();
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Error", "Revisa los valores numéricos ingresados.");
        } catch (Exception e) {
            showAlert("Error", "No se pudo guardar el producto.");
            e.printStackTrace();
        }
    }


    @FXML
    void cerrarSesion(ActionEvent event) {
        Main.loadView("/view/Login.fxml");
    }

    private void loadProductos() {
        tableProductos.getItems().setAll(productoDAO.fetch());
    }

    private void clearFields() {
        txtReferencia.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

