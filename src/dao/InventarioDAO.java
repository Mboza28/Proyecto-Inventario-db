package dao;

import configDatabase.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventarioDAO {

    public void añadirProductoCarrito(int cliente, int producto){

        String sqlAñadirProductoCarrito = "INSERT INTO inventario (clientes_id, productos_id) VALUES (?, ?)";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlAñadirProductoCarrito)){

            ps.setInt(1, cliente);
            ps.setInt(2, producto);

            ps.executeUpdate();
            System.out.println("Producto añadido correctamente al carrito.");

        } catch(SQLException e){
            System.out.println("Error al añadir producto al carrito del cliente: " + e.getMessage());
        }
    }
}
