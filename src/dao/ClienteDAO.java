package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import modelos.Cliente;
import configDatabase.ConexionDB;

public class ClienteDAO {

    public void añadirClienteDB(Cliente clienteNuevo){

        String sqlAñadirCliente = "INSERT INTO clientes(nombre, edad) VALUES ( ?, ?)";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlAñadirCliente)){

            ps.setString(1, clienteNuevo.getNombre());
            ps.setInt(2,clienteNuevo.getEdad());

            ps.executeUpdate();
            System.out.println("Cliente guardado correctamente en el sistema.");

        }catch(SQLException e){
            System.out.println("Error al añadir al cliente: " + e.getMessage());
        }
    }

    public ArrayList<Cliente> listarClienteDB(){

        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
        String sqlListarCliente = "SELECT * FROM Clientes";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlListarCliente);
            ResultSet resultado = ps.executeQuery()){

            while(resultado.next()){
                int idEncontrado = resultado.getInt("id");
                String nombreEncontrado = resultado.getString("nombre");
                int edadEncontrada = resultado.getInt("edad");

                Cliente cliente = new Cliente(nombreEncontrado,edadEncontrada);
                cliente.setId(idEncontrado);

                clientesEncontrados.add(cliente);
            }

        } catch(SQLException e){
            System.out.println("Error al añadir al cliente: " + e.getMessage());
        }
        return clientesEncontrados;
    }

    public ArrayList<Cliente> buscarClienteDB(String nombreBuscado){

        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
        String sqlBusqueda = "SELECT * FROM clientes WHERE nombre LIKE ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlBusqueda)){

            ps.setString(1, "%" + nombreBuscado + "%");
            try(ResultSet resultado = ps.executeQuery()){
                while (resultado.next()){
                    int idEncontrado = resultado.getInt("id");
                    String nombreEncontrado = resultado.getString("nombre");
                    int edadEncontrada = resultado.getInt("edad");

                    Cliente cliente = new Cliente(nombreEncontrado, edadEncontrada);
                    cliente.setId(idEncontrado);

                    clientesEncontrados.add(cliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar al cliente: " + e.getMessage());
        }
        return clientesEncontrados;
    }

    public void eliminarCliente(int idCliente){

        String sqlEliminarCliente = "DELETE FROM clientes WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
        PreparedStatement ps = con.prepareStatement(sqlEliminarCliente)){

            ps.setInt(1, idCliente);

            int registroBorrado = ps.executeUpdate();
            if(registroBorrado == 0){
                System.out.println("No se ha podido eliminar al cliente con ID: " + idCliente);
            }
            else{
                System.out.println("Cliente eliminado del sistema.");
            }

        } catch (SQLException e){
            System.out.println("Error al eliminar al cliente: " + e.getMessage());
        }
    }

    public void modificarNombreCliente(int idCliente, String nuevoNombre){

        String sqlModificarNombreCliente = "UPDATE clientes SET nombre = ? WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlModificarNombreCliente)){

            ps.setInt(2, idCliente);
            ps.setString(1, nuevoNombre);

            int registrosModificados = ps.executeUpdate();
            if(registrosModificados == 0){
                System.out.println("No se encontró al cliente con ID: " + idCliente);
            }
            else{
                System.out.println("Nombre modificado con éxito");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el nombre del cliente: " + e.getMessage());
        }
    }

    public void modificarEdadCliente(int idCliente, int nuevaEdad){

        String sqlModificarEdadCliente = "UPDATE clientes SET edad = ? WHERE id = ?";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sqlModificarEdadCliente)){

            ps.setInt(2, idCliente);
            ps.setInt(1, nuevaEdad);

            int registrosModificados = ps.executeUpdate();
            if(registrosModificados == 0){
                System.out.println("No se encontró al cliente con ID: " + idCliente);
            }
            else{
                System.out.println("Edad modificada con éxito");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la edad del cliente: " + e.getMessage());
        }
    }
}
