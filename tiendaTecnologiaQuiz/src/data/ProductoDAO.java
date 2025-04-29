package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Main;
import javafx.scene.control.Alert;
import model.Producto;



public class ProductoDAO {
    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    
	
	public void save(Producto producto) {
		String query = "INSERT INTO Student (referencia, nombre, precio, cantidad) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        	

            pstmt.setInt(1, producto.getReferencia());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getCantidad());


            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
            	Main.showAlert("satisfactorio", "Producto ingresado", "Se ingreso el producto de forma correcta", Alert.AlertType.CONFIRMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	
	public ArrayList<Producto> fetch() {

        ArrayList<Producto> productos = new ArrayList<>();
        String query = "SELECT referencia, nombre, precio, cantidad FROM Producto";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int referencia = rs.getInt("Referencia");
                String nombre = rs.getString("Nombre");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("Cantidad");
                
                Producto producto = new Producto(referencia, nombre, precio,cantidad);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
	}

	
	public void delete(int referencia) {
		String sql = "DELETE FROM Producto WHERE referencia=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, referencia);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public boolean authenticate(int referencia) {
String sql = "SELECT referencia FROM Producto WHERE referencia=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, referencia);
			ResultSet rs = stmt.executeQuery();
		
			if (rs.next()) {
			return rs.getInt("referencia")==referencia;
			}
		} catch (SQLException e) {
		e.printStackTrace();}
		
		return false;
	
	}


}
