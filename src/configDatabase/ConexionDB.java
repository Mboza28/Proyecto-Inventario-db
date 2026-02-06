package configDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    //Datos de configuración para encontrar la base de datos y poder acceder a ella
    private static final String URL = "jdbc:mysql://localhost:3306/supermercado_db";
    private static final String USER = "root";
    private static final String PASS = "";

    //Esta funcion se encarga de conectar con la base de datos
    public static Connection conectar(){

        Connection conexion = null;
        try{
            conexion = DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException e){
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}
