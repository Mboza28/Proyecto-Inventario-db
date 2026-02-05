package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import modelos.Producto;
import configDatabase.ConexionDB;

public class ProductoDAO {

    private Producto miProducto;

    public void insertarProducto(Producto productoNuevo){

        String sql = "INSERT INTO productos(nombre, precio, cantidad) VALUES (?, ?, ?)";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, productoNuevo.getNombre());
            ps.setDouble(2, productoNuevo.getPrecio());
            ps.setInt(3, productoNuevo.getCantidad());

            ps.executeUpdate();
            System.out.println("Producto guardado correctamente en el almacén.");
        } catch (SQLException e) {
            System.out.println("Error al guardar el producto: " + e.getMessage());
        }
    }
}
