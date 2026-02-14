package dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import configDatabase.ConexionDB;
import modelos.Cliente;
import modelos.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public ArrayList<String> listarInventarioCliente(int cliente){

        ArrayList<String> productosEncontrados = new ArrayList<>();
        String sqlListar = "SELECT p.nombre FROM productos AS p INNER JOIN inventario AS i ON i.productos_id = p.id WHERE clientes_id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlListar)){

            ps.setInt(1, cliente);

            try(ResultSet resultado = ps.executeQuery()){
                while (resultado.next()){
                    String nombreEncontrado = resultado.getString("nombre");

                    productosEncontrados.add(nombreEncontrado);
                }
            }
        } catch(SQLException e){
            System.out.println("Error al listar el inventario del cliente: " + e.getMessage());
        }
        return productosEncontrados;
    }

    public void eliminarProductos(int idCliente, int idProducto){

        String sqlEliminarProducto = "DELETE FROM inventario WHERE clientes_id = ? AND productos_id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlEliminarProducto)){

            ps.setInt(1, idCliente);
            ps.setInt(2, idProducto);

            int registrosBorrados = ps.executeUpdate();

            if(registrosBorrados > 0){
                System.out.println("Se eliminó con éxito el producto del inventario del cliente");
            }
            else{
                System.out.println("El cliente no tiene ese producto en su inventario");
            }
        } catch(SQLException e){
            System.out.println("Error al eliminar producto del inventario del cliente: " + e.getMessage());
        }
    }

    public void eliminarInventario (int idCliente) {

        String sqlEliminarInventario = "DELETE FROM inventario WHERE clientes_id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sqlEliminarInventario)) {

            ps.setInt(1, idCliente);
            int registrosBorrados = ps.executeUpdate();

            if(registrosBorrados > 0){
                System.out.println("El inventario del cliente ha sido eliminado con éxito");
            }
            else{
                System.out.println("El cliente no tenía productos en su inventario");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el inventario del cliente: " + e.getMessage());
        }
    }
}
