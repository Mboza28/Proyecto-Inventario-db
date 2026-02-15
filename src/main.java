import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

import java.sql.Connection;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.InventarioDAO;
import configDatabase.ConexionDB;
import modelos.Cliente;
import modelos.Producto;

public static void main(String[] args) {

    // Probamos si la conexion a la base de datos funciona
    try(Connection prueba = ConexionDB.conectar()){
        System.out.println("¡Conexion realizada con éxito a la base de datos!");
    }catch(SQLException e){
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }

    ProductoDAO productoDao = new ProductoDAO();
    ClienteDAO clienteDao = new ClienteDAO();
    InventarioDAO inventarioDao = new InventarioDAO();

    boolean salir = false;

    Scanner lectura = new Scanner(System.in);
    String opcion = "0";

    do{
        System.out.println();
        System.out.println("**** MENÚ PRINCIPAL ****");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Productos");
        System.out.println("3. Gestión de Inventarios");
        System.out.println("4. Salir");
        System.out.println();
        System.out.print("Elige una opción: ");
        System.out.println();

        opcion = lectura.nextLine();

        switch(opcion){
            case "1":
                menuClientes(lectura, clienteDao);
                break;
            case "2":
                menuProductos(lectura, productoDao);
                break;
            case "3":
                menuInventarios(lectura, clienteDao, productoDao, inventarioDao);
                break;
            case "4":
                System.out.println("Cerrando programa");
                salir = true;
                break;
            default:
                System.out.println("Por favor, introduce un número de la lista.");
        }
    }while(!salir);
}

public static void menuClientes(Scanner lectura, ClienteDAO clienteDao){

    boolean salirSubmenuCliente = false;
    String opcionCliente;

    do{
        System.out.println();
        System.out.println("**** GESTIÓN DE CLIENTES ****");
        System.out.println("1. Añadir cliente");
        System.out.println("2. Borrar cliente");
        System.out.println("3. Modificar nombre de un cliente");
        System.out.println("4. Modificar edad de un cliente");
        System.out.println("5. Mostrar todos los clientes");
        System.out.println("6. Mostrar informacion de un cliente");
        System.out.println("7. Volver al menú principal");
        System.out.println();
        System.out.print("Elige una opción: ");
        System.out.println();

        opcionCliente = lectura.nextLine();

        switch(opcionCliente){
            case "1":{
                clienteDao.añadirClienteDB(añadirCliente(lectura));
                break;
            }
            case "2":{

                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if(listado.isEmpty()){
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
                    for(int i = 0; i < listado.size(); i++){
                        System.out.println(listado.get(i).toString());
                    }

                    int idClienteBorrar = pedirIdCliente(lectura);

                    if(idClienteBorrar == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    }else{
                        boolean esValido = false;
                        for(int i = 0; i < listado.size(); i++){
                            if(listado.get(i).getId() == idClienteBorrar){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            clienteDao.eliminarCliente(idClienteBorrar);
                        }else{
                            System.out.println("Por seguridad no puedes borrar el cliente con ID " + idClienteBorrar);
                        }
                    }
                }
                break;
            }
            case "3":{

                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if(listado.isEmpty()){
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
                    for(int i = 0; i < listado.size(); i++){
                        System.out.println(listado.get(i).toString());
                    }

                    int idClienteNombre = pedirIdCliente(lectura);

                    if(idClienteNombre == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    }else{
                        boolean esValido = false;
                        for(int i = 0; i < listado.size(); i++){
                            if(listado.get(i).getId() == idClienteNombre){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            System.out.println("Qué nombre nuevo quieres asignarle al cliente?");
                            String nuevoNombre = lectura.nextLine();
                            clienteDao.modificarNombreCliente(idClienteNombre, nuevoNombre);
                        }else{
                            System.out.println("Por seguridad no puedes modificar el nombre del cliente con ID " + idClienteNombre);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "4":{

                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if(listado.isEmpty()){
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
                    for(int i = 0; i < listado.size(); i++){
                        System.out.println(listado.get(i).toString());
                    }

                    int idCliente = pedirIdCliente(lectura);

                    if(idCliente == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                        System.out.println();
                    }else{
                        boolean esValido = false;
                        for(int i = 0; i < listado.size(); i++){
                            if(listado.get(i).getId() == idCliente){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){

                            int nuevaEdad = -1;
                            esValido = false;

                            while(!esValido){
                                System.out.print("Escribe la edad del cliente: ");
                                try{
                                    if(lectura.hasNextInt()){
                                        nuevaEdad = lectura.nextInt();
                                        lectura.nextLine();

                                        if(nuevaEdad >= 0 && nuevaEdad <= 113){
                                            esValido = true;
                                        }else{
                                            System.out.println("La edad introducida no es válida. Por favor, introduce un valor entre 0 y 113 años");
                                        }
                                    }else{
                                        System.out.println("Por favor, introduce un número");
                                        lectura.next();
                                    }
                                }catch(Exception e){
                                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                                    lectura.nextLine();
                                }
                            }
                            clienteDao.modificarEdadCliente(idCliente, nuevaEdad);
                        }else{
                            System.out.println("Por seguridad no puedes modificar la edad del cliente con ID " + idCliente);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "5":{
                ArrayList<Cliente> listado = clienteDao.listarClienteDB();

                if(listado.isEmpty()){
                    System.out.println("No hay clientes registrados");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES --- ");
                    for(int i = 0; i < listado.size(); i++){
                        System.out.println(listado.get(i).toString());
                    }
                }
                break;
            }
            case "6":{
                ArrayList<Cliente> listadoBusqueda = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if(listadoBusqueda.isEmpty()){
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
                    for(int i = 0; i < listadoBusqueda.size(); i++){
                        System.out.println(listadoBusqueda.get(i).toString());
                    }
                }
                break;
            }
            case "7":{
                salirSubmenuCliente = true;
                break;
            }
            default:
                System.out.println("Opción no válida.");
                System.out.println();
        }
    } while(!salirSubmenuCliente);
}

public static void menuProductos(Scanner lectura, ProductoDAO productoDao){

    boolean salirSubmenuProducto = false;
    String opcionProducto;

    do{
        System.out.println();
        System.out.println("**** GESTIÓN DE PRODUCTOS ****");
        System.out.println("1. Añadir producto");
        System.out.println("2. Borrar producto");
        System.out.println("3. Modificar stock producto");
        System.out.println("4. Modificar precio producto");
        System.out.println("5. Mostrar todos los productos");
        System.out.println("6. Mostrar informacion de un producto");
        System.out.println("7. Volver al menú principal");
        System.out.println();
        System.out.print("Elige una opción: ");

        opcionProducto = lectura.nextLine();

        switch(opcionProducto){
            case "1":{
                productoDao.insertarProducto(añadirProducto(lectura));
                break;
            }
            case "2":{

                ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

                if(productosCoincidentes.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS ---");
                    for(int i = 0; i < productosCoincidentes.size(); i++){
                        System.out.println(productosCoincidentes.get(i).toString());
                    }

                    int idSeleccionado = borrarProducto(lectura);

                    if(idSeleccionado == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                        System.out.println();
                    }else{
                        boolean esValido = false;

                        for(int i = 0; i < productosCoincidentes.size(); i++){
                            if(productosCoincidentes.get(i).getId() == idSeleccionado){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            productoDao.eliminarProducto(idSeleccionado);
                        }else{
                            System.out.println("Por seguridad no puedes borrar el producto con ID " + idSeleccionado);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "3":{

                ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

                if(productosCoincidentes.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                }else{
                    System.out.println();
                    System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS ---");
                    for(int i = 0; i < productosCoincidentes.size(); i++){
                        System.out.println(productosCoincidentes.get(i).toString());
                    }
                    System.out.println();
                    System.out.println("De qué producto quieres modificar el stock? Introducte el ID por favor, si no quieres moficicar ninguno introduce el 0(cero)");
                    int idProductoModificado = lectura.nextInt();
                    lectura.nextLine();
                    if(idProductoModificado == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                        System.out.println();
                    }else{
                        boolean esValido = false;

                        for(int i = 0; i < productosCoincidentes.size(); i++){
                            if(productosCoincidentes.get(i).getId() == idProductoModificado){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            System.out.println();
                            System.out.println("Cuál es el nuevo stock del producto?");
                            int stockNuevo = lectura.nextInt();
                            lectura.nextLine();

                            productoDao.modificarStockDAO(idProductoModificado, stockNuevo);
                        }else{
                            System.out.println("Por seguridad no puedes modificar el stock del producto con ID " + idProductoModificado);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "4":{

                ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

                if(productosCoincidentes.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS ---");
                    for(int i = 0; i < productosCoincidentes.size(); i++){
                        System.out.println(productosCoincidentes.get(i).toString());
                    }
                    System.out.println();
                    System.out.println("De qué producto quieres modificar el precio? Introducte el ID por favor, si no quieres moficicar ninguno introduce el 0(cero)");
                    int idProductoModificado = lectura.nextInt();
                    lectura.nextLine();
                    if(idProductoModificado == 0) {
                        System.out.println("Operación cancelada. Volviendo al menú...");
                        System.out.println();
                    }else{
                        boolean esValido = false;

                        for(int i = 0; i < productosCoincidentes.size(); i++){
                            if (productosCoincidentes.get(i).getId() == idProductoModificado) {
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            System.out.println("Cuál es el nuevo precio del producto? Utiliza , para separar parte entera y parte decimal");
                            double precioNuevo = lectura.nextDouble();
                            lectura.nextLine();
                            if(precioNuevo < 0){
                                System.out.println("El producto no puede tener un precio negativo");
                                System.out.println();
                            }else{
                                productoDao.modificarPrecioDAO(idProductoModificado, precioNuevo);
                            }
                        }else{
                            System.out.println("Por seguridad no puedes modificar el precio del producto con ID " + idProductoModificado);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "5": {

                ArrayList<Producto> listado = productoDao.listarProductos();

                if(listado.isEmpty()){
                    System.out.println("El almacén está vacio");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("--- LISTADO DE PRODUCTOS ---");
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }
                }
                break;
            }
            case "6":{

                ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

                if(productosCoincidentes.isEmpty()){
                    System.out.println("No se han encontrado productos con ese nombre");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("--- LISTADO DE PRODUCTOS ---");
                    for (int i = 0; i < productosCoincidentes.size(); i++) {
                        System.out.println(productosCoincidentes.get(i).toString());
                    }
                }
                break;
            }
            case "7":{
                salirSubmenuProducto = true;
                break;
            }
            default:
                System.out.println("Opción no válida.");
                System.out.println();
        }
    }while(!salirSubmenuProducto);
}

public static void menuInventarios(Scanner lectura, ClienteDAO clienteDao, ProductoDAO productoDao, InventarioDAO inventarioDao){

    boolean salirSubmenuInventario = false;
    String opcionInventario;

    do{
        System.out.println();
        System.out.println("**** GESTIÓN DE INVENTARIOS ****");
        System.out.println("1. Añadir producto al inventario de un cliente");
        System.out.println("2. Borrar un producto del inventario de un cliente");
        System.out.println("3. Mostrar inventario de un cliente");
        System.out.println("4. Borrar un inventario de cliente completo");
        System.out.println("5. Volver al menú principal");
        System.out.println();
        System.out.print("Elige una opción: ");

        opcionInventario = lectura.nextLine();

        switch(opcionInventario){
            case "1":{

                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if(listado.isEmpty()){
                    System.out.println("No existen clientes con ese nombre.");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
                    for(int i = 0; i < listado.size(); i++){
                        System.out.println(listado.get(i).toString());
                    }

                    int idCliente = pedirIdCliente(lectura);

                    if(idCliente == 0){
                        System.out.println("Cancelando operación. Volviendo al menú...");
                        System.out.println();
                    }else{

                        boolean clienteValido = false;

                        for(int i = 0; i < listado.size(); i++){
                            if(listado.get(i).getId() == idCliente){
                                clienteValido = true;
                                break;
                            }
                        }
                        if(clienteValido){

                            ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

                            if(productosCoincidentes.isEmpty()){
                                System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                                System.out.println();
                            }else{
                                System.out.println();
                                System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS CON ESE NOMBRE ---");
                                for(int i = 0; i < productosCoincidentes.size(); i++){
                                    System.out.println(productosCoincidentes.get(i).toString());
                                }

                                System.out.println();
                                System.out.println("Que producto quieres añadir al carrito? Introducte el ID por favor, si no quieres añadir ninguno introduce el 0(cero)");

                                int idProducto = lectura.nextInt();
                                lectura.nextLine();

                                if(idProducto == 0){
                                    System.out.println("Operación cancelada. Volviendo al menú...");
                                    System.out.println();
                                }else{
                                    boolean productoValido = false;

                                    for(int i = 0; i < productosCoincidentes.size(); i++){
                                        if (productosCoincidentes.get(i).getId() == idProducto) {
                                            productoValido = true;
                                            break;
                                        }
                                    }
                                    if(productoValido){
                                        inventarioDao.añadirProductoCarrito(idCliente,idProducto);
                                    }else{
                                        System.out.println("Por seguridad no puedes modificar el stock del producto con ID " + idProducto);
                                        System.out.println();
                                    }
                                }
                            }
                        }else{
                            System.out.println("Por seguridad no puedes realizar ninguna accion sobre el cliente con ID: " + idCliente);
                            System.out.println();
                        }
                    }
                }
                break;
            }
            case "2":{

                int idCliente = seleccionarCliente(lectura, clienteDao);

                if(idCliente == 0){
                    break;
                }else{

                    ArrayList<String> productosEncontrados = new ArrayList<>();
                    productosEncontrados = (inventarioDao.listarInventarioCliente(idCliente));

                    if(productosEncontrados.isEmpty()){
                        System.out.println("No se han encontrado productos en el inventario de este cliente.");
                        System.out.println();
                    }else{
                        System.out.println();
                        System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS ---");
                        for(int i = 0; i < productosEncontrados.size(); i++){
                            System.out.println(productosEncontrados.get(i));
                        }
                        int idProducto = seleccionarProducto(lectura, productoDao);

                        if(idProducto == 0){
                            break;
                        }else{
                            inventarioDao.eliminarProductos(idCliente, idProducto);
                        }
                    }
                }
                break;
            }
            case "3":{

                int eleccionCliente = seleccionarCliente(lectura, clienteDao);

                if(eleccionCliente == 0){
                    break;
                }else{

                    ArrayList<String> productosEncontrados = new ArrayList<>();
                    productosEncontrados = (inventarioDao.listarInventarioCliente(eleccionCliente));

                    if(productosEncontrados.isEmpty()){
                        System.out.println("No se han encontrado productos en el inventario de este cliente.");
                        System.out.println();
                    }else{
                        System.out.println();
                        System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS ---");
                        for(int i = 0; i < productosEncontrados.size(); i++){
                            System.out.println(productosEncontrados.get(i));
                        }
                    }
                }
                break;
            }
            case "4":{

                int eleccionCliente = seleccionarCliente(lectura, clienteDao);

                if(eleccionCliente == 0){
                    break;
                }
                else{
                    inventarioDao.eliminarInventario(eleccionCliente);
                }
                break;
            }
            case "5":{
                salirSubmenuInventario = true;
                break;
            }
            default:
                System.out.println("Opción no válida.");
                System.out.println();
        }
    } while (!salirSubmenuInventario);
}

public static Cliente añadirCliente(Scanner lectura){

    System.out.println();
    System.out.println("****** ALTA DE NUEVO CLIENTE ******");
    System.out.print("Nombre del nuevo cliente: ");
    String nuevoCliente = lectura.nextLine();

    int nuevaEdad = -1;
    boolean esValido = false;

    while(!esValido){
        System.out.print("Edad del cliente: ");
        try{
            if(lectura.hasNextInt()){
                nuevaEdad = lectura.nextInt();
                lectura.nextLine();

                if(nuevaEdad >= 0 && nuevaEdad <= 113){
                    esValido = true;
                }else{
                    System.out.println("La edad introducida no es válida. Por favor, introduce un valor entre 0 y 113 años");
                }
            }else{
                System.out.println("Por favor, introduce un número");
                lectura.next();
            }
        }catch(Exception e){
            System.out.println("Ha ocurrido un error: " + e.getMessage());
            lectura.nextLine();
        }
    }
    return new Cliente(nuevoCliente, nuevaEdad);
}

public static String buscarCliente(Scanner lectura){

    System.out.println();
    System.out.println("Escribe el nombre del cliente sobre el que quieres realizar la acción:");
    String clienteBuscado = lectura.nextLine();
    return clienteBuscado;
}

public static Producto añadirProducto(Scanner lectura) {

    System.out.println();
    System.out.println("****** ALTA DE NUEVO PRODUCTO ******");
    System.out.print("Nombre del producto: ");
    String nombre = lectura.nextLine();

    System.out.print("Precio (usa coma ',' para decimales): ");
    double precio = lectura.nextDouble();

    System.out.print("Cantidad en stock: ");
    int cantidad = lectura.nextInt();
    lectura.nextLine();

    return new Producto(nombre, precio, cantidad);
}

public static int borrarProducto(Scanner lectura){

    System.out.println();
    System.out.println("Qué producto quieres eliminar? Introducte el ID por favor, si no quieres eliminar ninguno introduce el 0 (cero)");
    int productoEliminado = lectura.nextInt();
    lectura.nextLine();
    return productoEliminado;
}

public static String buscarIdProducto(Scanner lectura){

    System.out.println();
    System.out.println("Qué producto quieres encontrar?");
    String productoBuscado = lectura.nextLine();
    return productoBuscado;
}

public static int pedirIdCliente(Scanner lectura){

    System.out.println();
    System.out.println("Qué cliente quieres seleccionar? Introducte el ID por favor, si no quieres seleccionar ninguno introduce el 0 (cero)");
    int idCliente = lectura.nextInt();
    lectura.nextLine();
    return idCliente;
}

public static int seleccionarCliente(Scanner lectura, ClienteDAO clienteDao){

    System.out.println();
    ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

    if(listado.isEmpty()){
        System.out.println("No existen clientes con ese nombre.");
        return 0;
    }

    System.out.println();
    System.out.println(" --- LISTADO DE CLIENTES ENCONTRADOS --- ");
    for(int i = 0; i < listado.size(); i++){
        System.out.println(listado.get(i).toString());
    }

    int idCliente = pedirIdCliente(lectura);

    if(idCliente == 0){
        System.out.println("Operación cancelada. Volviendo al menú...");
        System.out.println();
        return 0;
    }
    for(int i = 0; i < listado.size(); i++){
        if(listado.get(i).getId() == idCliente){
            return idCliente;
        }
    }
    System.out.println("Por seguridad no puedes seleccionar el cliente con ID: " + idCliente);
    System.out.println();
    return 0;
}

public static int seleccionarProducto(Scanner lectura, ProductoDAO productoDao){

    System.out.println();
    ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarIdProducto(lectura));

    if(productosCoincidentes.isEmpty()){
        System.out.println("No se ha encontrado el producto solicitado. Inténtelo de nuevo");
        return 0;
    }

    System.out.println();
    System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS CON ESE NOMBRE ---");
    for(int i = 0; i < productosCoincidentes.size(); i++){
        System.out.println(productosCoincidentes.get(i).toString());
    }

    System.out.println();
    System.out.println("Sobre que producto quieres realizar la accion? Introducte el ID por favor, si no quieres añadir ninguno introduce el 0(cero)");

    int idProducto = lectura.nextInt();
    lectura.nextLine();

    if(idProducto == 0){
        System.out.println("Operación cancelada. Volviendo al menú...");
        return 0;
    }

    for(int i = 0; i < productosCoincidentes.size(); i++){
        if(productosCoincidentes.get(i).getId() == idProducto){
            if(productosCoincidentes.get(i).getCantidad() > 0){
                return idProducto;
            }else{
                System.out.println("No tenemos stock de ese producto");
                System.out.println();
                return 0;
            }
        }
    }

    System.out.println("Por seguridad no puedes seleccionar el producto con ID " + idProducto);
    System.out.println();
    return 0;
}