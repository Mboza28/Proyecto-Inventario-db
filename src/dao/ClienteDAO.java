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

        } catch(SQLException e){
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
}
