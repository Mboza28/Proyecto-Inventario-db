package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import modelos.Producto;
import configDatabase.ConexionDB;

public class ProductoDAO {

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

    public void eliminarProducto(int productoABorrar){

        String sqlEliminar = "DELETE FROM productos WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlEliminar)){

            ps.setInt(1, productoABorrar);

            int registrosBorrados = ps.executeUpdate();  //porque executeUpdate aparte de ejecutar la sentencia guardada en ps, retorna un INT con el numero de filas que se han visto afectadas.
            if(registrosBorrados == 0){
                System.out.println("No se ha encontrado el producto con ID " + productoABorrar + ". Inténtelo de nuevo");
            }else {
                System.out.println("Producto eliminado correctamente del almacén.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    public ArrayList<Producto> listarProductos(){

        ArrayList<Producto> inventario = new ArrayList<>();
        String sqlBuscar = "SELECT * FROM productos";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlBuscar);
            ResultSet resultado = ps.executeQuery()){

            while(resultado.next()){
                int idEncontrado = resultado.getInt("id");
                String nombreEncontrado = resultado.getString("nombre");
                double precioEncontrado = resultado.getDouble("precio");
                int cantidadEncontrada = resultado.getInt("cantidad");

                Producto producto = new Producto(nombreEncontrado, precioEncontrado, cantidadEncontrada);
                producto.setId(idEncontrado);

                inventario.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el listado de productos del almacén: " + e.getMessage());
        }
        return inventario;
    }

    public ArrayList<Producto> buscarProductoDAO(String productoBuscado){

        ArrayList<Producto> inventario = new ArrayList<>();
        String sqlBuscar = "SELECT * FROM productos WHERE nombre LIKE ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlBuscar);
            ){

            ps.setString(1,"%" + productoBuscado + "%");
            try(ResultSet busqueda = ps.executeQuery()){
                while(busqueda.next()) {
                    int idEncontrado = busqueda.getInt("id");
                    String nombreEncontrado = busqueda.getString("nombre");
                    double precioEncontrado = busqueda.getDouble("precio");
                    int cantidadEncontrada = busqueda.getInt("cantidad");

                    Producto producto = new Producto(nombreEncontrado, precioEncontrado, cantidadEncontrada);
                    producto.setId(idEncontrado);

                    inventario.add(producto);
                }
            }
        }  catch (SQLException e) {
            System.out.println("Error al buscar el producto en el almacén: " + e.getMessage());
        }
        return inventario;
    }

    public void modificarStockDAO(int idModificacion, int nuevoStock){

        String sqlModStock = "UPDATE productos SET cantidad = ? WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlModStock)){

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idModificacion);

            int registrosModificados = ps.executeUpdate();
            if(registrosModificados == 0){
                System.out.println("No se ha encontrado el producto con ID " + idModificacion + ". Inténtelo de nuevo");
            } else{
                System.out.println("Se ha modificado con éxito el Stock del producto");
            }
        }
        catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    public void modificarPrecioDAO(int idModificacion, double nuevoPrecio){

        String sqlModPrecio = "UPDATE productos SET precio = ? WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlModPrecio)){

            ps.setDouble(1, nuevoPrecio);
            ps.setInt(2, idModificacion);

            int registrosModificados = ps.executeUpdate();
            if(registrosModificados == 0){
                System.out.println("No se ha encontrado el producto con ID " + idModificacion + ". Inténtelo de nuevo");
            } else{
                System.out.println("Se ha modificado con éxito el precio del producto");
            }
        }
        catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }
}
