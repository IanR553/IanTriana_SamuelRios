package controller;

import java.sql.Connection;

import application.Main;
import data.UsuarioDAO;
import data.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtUsuario;
    
    private Connection connection = DBConnection.getInstance().getConnection();
    private UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
    

    @FXML
    void iniciarSesion(ActionEvent event) {
    	 String nickname1 = txtUsuario.getText().trim();
         String contraseña1 = txtContraseña.getText().trim();
    	
    	if(UsuarioDAO.authenticate(nickname1, contraseña1)) {
    		Main.loadView("/view/RegistroProductos.fxml");
    	}else {
    	 Main.showAlert("ERROR", "Credenciales incorrectas", "ingrese un usuario y contraseña validas", Alert.AlertType.ERROR);
    	}
    }
    

}

