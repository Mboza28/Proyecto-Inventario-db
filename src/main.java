import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

import java.sql.Connection;

import dao.ClienteDAO;
import dao.ProductoDAO;
import configDatabase.ConexionDB;
import modelos.Cliente;
import modelos.Inventario;
import modelos.Producto;

public static void main(String[] args) {

    // Probamos si la conexion a la base de datos funciona
    try(Connection prueba = ConexionDB.conectar()){
        System.out.println("¡Conexion realizada con éxito a la base de datos!");
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }

    ProductoDAO productoDao = new ProductoDAO();
    ClienteDAO clienteDao = new ClienteDAO();

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Producto> productosGeneral = new ArrayList<>();

    boolean salir = false;

    Scanner lectura = new Scanner(System.in);
    String opcion = "0";

    do {
        System.out.println("**** MENÚ PRINCIPAL ****");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Productos");
        System.out.println("3. Gestión de Inventarios");
        System.out.println("4. Salir");
        System.out.print("¿Qué te gustaría hacer? ");

        opcion = lectura.nextLine();

        switch (opcion) {
            case "1":
                menuClientes(lectura, clienteDao);
                break;
            case "2":
                menuProductos(lectura, productoDao);
                break;
            case "3":
                menuInventarios(lectura, clientes, productosGeneral);
                break;
            case "4":
                System.out.println("Cerrando programa");
                salir = true;
                break;
            default:
                System.out.println("Por favor, introduce un número de la lista.");
        }
    } while (!salir);
}

public static void menuClientes(Scanner lectura, ClienteDAO clienteDao){

    boolean salirSubmenuCliente = false;
    String opcionCliente;

    do{
        System.out.println("**** GESTIÓN DE CLIENTES ****");
        System.out.println("1. Añadir cliente");
        System.out.println("2. Borrar cliente");
        System.out.println("3. Modificar nombre de un cliente");
        System.out.println("4. Modificar edad de un cliente");
        System.out.println("5. Mostrar todos los clientes");
        System.out.println("6. Mostrar informacion de un cliente");
        System.out.println("7. Volver al menú principal");
        System.out.print("Elige una opción: ");

        opcionCliente = lectura.nextLine();

        switch (opcionCliente) {
            case "1":
                clienteDao.añadirClienteDB(añadirCliente(lectura));
                break;
            case "2": {
                System.out.println(" --- LISTADO DE CLIENTES --- ");
                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if (listado.isEmpty()) {
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                } else {
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }

                    int idClienteBorrar = pedirIdCliente(lectura);

                    if (idClienteBorrar == 0) {
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    } else {
                        boolean esValido = false;
                        for (int i = 0; i < listado.size(); i++) {
                            if (listado.get(i).getId() == idClienteBorrar) {
                                esValido = true;
                                break;
                            }
                        }
                        if (esValido) {
                            clienteDao.eliminarCliente(idClienteBorrar);
                        } else {
                            System.out.println("Por seguridad no puedes borrar el cliente con ID " + idClienteBorrar);
                        }
                    }
                }
                break;
            }
            case "3": {
                System.out.println(" --- LISTADO DE CLIENTES --- ");
                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if (listado.isEmpty()) {
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                } else {
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }

                    int idClienteNombre = pedirIdCliente(lectura);

                    if (idClienteNombre == 0) {
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    } else {
                        boolean esValid = false;
                        for (int i = 0; i < listado.size(); i++) {
                            if (listado.get(i).getId() == idClienteNombre) {
                                esValid = true;
                                break;
                            }
                        }
                        if (esValid) {
                            System.out.println("Qué nombre nuevo quieres asignarle al cliente?");
                            String nuevoNombre = lectura.nextLine();
                            clienteDao.modificarNombreCliente(idClienteNombre, nuevoNombre);
                        } else {
                            System.out.println("Por seguridad no puedes modificar el nombre del cliente con ID " + idClienteNombre);
                        }
                    }
                }
                break;
            }
            case "4": {
                System.out.println(" --- LISTADO DE CLIENTES --- ");
                ArrayList<Cliente> listado = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if (listado.isEmpty()) {
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                } else {
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }

                    int idClienteEdad = pedirIdCliente(lectura);

                    if (idClienteEdad == 0) {
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    } else {
                        boolean esValid = false;
                        for (int i = 0; i < listado.size(); i++) {
                            if (listado.get(i).getId() == idClienteEdad) {
                                esValid = true;
                                break;
                            }
                        }
                        if (esValid) {
                            System.out.println("Qué edad nueva quieres asignarle al cliente?");
                            int nuevaEdad = lectura.nextInt();
                            lectura.nextLine();
                            clienteDao.modificarEdadCliente(idClienteEdad, nuevaEdad);
                        } else {
                            System.out.println("Por seguridad no puedes modificar la edad del cliente con ID " + idClienteEdad);
                        }
                    }
                }
                break;
            }
            case "5": {
                System.out.println("--- LISTADO DE CLIENTES ---");
                ArrayList<Cliente> listado = clienteDao.listarClienteDB();

                if (listado.isEmpty()) {
                    System.out.println("No hay clientes registrados");
                } else {
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }
                }
                break;
            }
            case "6": {
                System.out.println("--- LISTADO DE CLIENTES ---");
                ArrayList<Cliente> listadoBusqueda = clienteDao.buscarClienteDB(buscarCliente(lectura));

                if (listadoBusqueda.isEmpty()) {
                    System.out.println("No se ha encontrado ningún cliente con ese nombre");
                } else {
                    for (int i = 0; i < listadoBusqueda.size(); i++) {
                        System.out.println(listadoBusqueda.get(i).toString());
                    }
                }
                break;
            }
            case "7":
                salirSubmenuCliente = true;
                break;
            default:
                System.out.println("Opción no válida.");
        }
    } while (!salirSubmenuCliente);
}

public static void menuProductos(Scanner lectura, ProductoDAO productoDao){

    boolean salirSubmenuProducto = false;
    String opcionProducto;

    do{
        System.out.println("**** GESTIÓN DE PRODUCTOS ****");
        System.out.println("1. Añadir producto");
        System.out.println("2. Borrar producto");
        System.out.println("3. Modificar stock producto");
        System.out.println("4. Modificar precio producto");
        System.out.println("5. Mostrar todos los productos");
        System.out.println("6. Mostrar informacion de un producto");
        System.out.println("7. Volver al menú principal");
        System.out.print("Elige una opción: ");

        opcionProducto = lectura.nextLine();

        switch (opcionProducto) {
            case "1":
                productoDao.insertarProducto(añadirProducto(lectura));
                break;
            case "2":
                System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS CON ESE NOMBRE ---");
                ArrayList<Producto> productosCoincidentes = productoDao.buscarProductoDAO(buscarProducto(lectura));

                if(productosCoincidentes.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                }
                else{
                    for (int i = 0; i < productosCoincidentes.size(); i++) {
                        System.out.println(productosCoincidentes.get(i).toString());
                    }

                    int idSeleccionado = borrarProducto(lectura);

                    if (idSeleccionado == 0) {
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    }
                    else{
                        boolean esValido = false;

                        for (int i = 0; i < productosCoincidentes.size(); i++) {
                            if(productosCoincidentes.get(i).getId() == idSeleccionado){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            productoDao.eliminarProducto(idSeleccionado);
                        }
                        else{
                            System.out.println("Por seguridad no puedes borrar el producto con ID " + idSeleccionado);
                        }
                    }
                }
                break;
            case "3":
                System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS CON ESE NOMBRE ---");
                ArrayList<Producto> productosEncontrados = productoDao.buscarProductoDAO(buscarProducto(lectura));

                if(productosEncontrados.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                }
                else {
                    for (int i = 0; i < productosEncontrados.size(); i++) {
                        System.out.println(productosEncontrados.get(i).toString());
                    }
                    System.out.println("De qué producto quieres modificar el stock? Introducte el ID por favor, si no quieres moficicar ninguno introduce el 0(cero)");
                    int idProductoModificado = lectura.nextInt();
                    lectura.nextLine();
                    if(idProductoModificado == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    }
                    else{
                        boolean esValido = false;

                        for (int i = 0; i < productosEncontrados.size(); i++) {
                            if(productosEncontrados.get(i).getId() == idProductoModificado){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            System.out.println("Cuál es el nuevo stock del producto?");
                            int stockNuevo = lectura.nextInt();
                            lectura.nextLine();

                            productoDao.modificarStockDAO(idProductoModificado, stockNuevo);
                        }
                        else{
                            System.out.println("Por seguridad no puedes modificar el stock del producto con ID " + idProductoModificado);
                        }
                    }
                }
                break;
            case "4":
                System.out.println("--- LISTADO DE PRODUCTOS ENCONTRADOS CON ESE NOMBRE ---");
                ArrayList<Producto> productosModificados = productoDao.buscarProductoDAO(buscarProducto(lectura));

                if(productosModificados.isEmpty()){
                    System.out.println("No se ha encontrado el producto solicitado en el almacén. Inténtelo de nuevo");
                }
                else {
                    for (int i = 0; i < productosModificados.size(); i++) {
                        System.out.println(productosModificados.get(i).toString());
                    }
                    System.out.println("De qué producto quieres modificar el precio? Introducte el ID por favor, si no quieres moficicar ninguno introduce el 0(cero)");
                    int idProductoModificado = lectura.nextInt();
                    lectura.nextLine();
                    if(idProductoModificado == 0){
                        System.out.println("Operación cancelada. Volviendo al menú...");
                    }
                    else{
                        boolean esValido = false;

                        for (int i = 0; i < productosModificados.size(); i++) {
                            if(productosModificados.get(i).getId() == idProductoModificado){
                                esValido = true;
                                break;
                            }
                        }
                        if(esValido){
                            System.out.println("Cuál es el nuevo precio del producto?");
                            double precioNuevo = lectura.nextDouble();
                            lectura.nextLine();
                            if(precioNuevo < 0){
                                System.out.println("El producto no puede tener un precio negativo");
                            }
                            else{
                                productoDao.modificarPrecioDAO(idProductoModificado,precioNuevo);
                            }
                        }
                        else{
                            System.out.println("Por seguridad no puedes modificar el precio del producto con ID " + idProductoModificado);
                        }
                    }
                }
                break;
            case "5":
                System.out.println("--- LISTADO DE PRODUCTOS ---");
                ArrayList<Producto> listado = productoDao.listarProductos();

                if(listado.isEmpty()){
                    System.out.println("El almacén está vacio");
                }
                else{
                    for (int i = 0; i < listado.size(); i++) {
                        System.out.println(listado.get(i).toString());
                    }
                }
                break;
            case "6":
                System.out.println("--- LISTADO DE PRODUCTOS ---");
                ArrayList<Producto> busqueda = productoDao.buscarProductoDAO(buscarProducto(lectura));

                if(busqueda.isEmpty()){
                    System.out.println("El almacén está vacio");
                }
                else{
                    for (int i = 0; i < busqueda.size(); i++) {
                        System.out.println(busqueda.get(i).toString());
                    }
                }
                break;
            case "7":
                salirSubmenuProducto = true;
                break;
            default:
                System.out.println("Opción no válida.");
        }
    } while (!salirSubmenuProducto);
}

public static void menuInventarios(Scanner lectura, ArrayList<Cliente> clientes, ArrayList<Producto> productosGeneral){

    boolean salirSubmenuInventario = false;
    String opcionInventario;

    do{
        System.out.println("**** GESTIÓN DE INVENTARIOS ****");
        System.out.println("1. Añadir producto al inventario de un cliente");
        System.out.println("2. Borrar un producto del inventario de un cliente");
        System.out.println("3. Mostrar inventario de un cliente");
        System.out.println("4. Borrar un inventario de cliente completo");
        System.out.println("5. Volver al menú principal");
        System.out.print("Elige una opción: ");

        opcionInventario = lectura.nextLine();

        switch (opcionInventario) {
            case "1":
                añadirProductoAInventario(lectura, clientes, productosGeneral);
                break;
            case "2":
                borrarProductoDeInventario(lectura, clientes, productosGeneral);
                break;
            case "3":
                mostrarInventario(lectura, clientes);
                break;
            case "4":
                borrarInventario(lectura, clientes);
                break;
            case "5":
                salirSubmenuInventario = true;
                break;
            default:
                System.out.println("Opción no válida.");
        }
    } while (!salirSubmenuInventario);
}

public static Cliente añadirCliente(Scanner lectura){

    System.out.println();
    System.out.println("****** ALTA DE NUEVO CLIENTE ******");
    System.out.print("Nombre del nuevo cliente: ");
    String nuevoCliente = lectura.nextLine();

    System.out.print("Edad del cliente: ");
    int nuevaEdad = lectura.nextInt();
    lectura.nextLine();

    return new Cliente(nuevoCliente,nuevaEdad);
}

public static int pedirIdCliente(Scanner lectura){

    System.out.println("Qué cliente quieres seleccionar? Introducte el ID por favor, si no quieres eliminar ninguno introduce el 0 (cero)");
    int idCliente = lectura.nextInt();
    lectura.nextLine();
    return idCliente;
}

public static String buscarCliente(Scanner lectura){
    System.out.println("De qué cliente quieres ver la información?");
    String clienteBuscado = lectura.nextLine();
    return clienteBuscado;
}

public static Producto añadirProducto(Scanner lectura) {

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

    System.out.println("Qué producto quieres eliminar? Introducte el ID por favor, si no quieres eliminar ninguno introduce el 0 (cero)");
    int productoEliminado = lectura.nextInt();
    lectura.nextLine();
    return productoEliminado;
}

public static String buscarProducto(Scanner lectura){

    System.out.println("Qué producto quieres encontrar?");
    String productoBuscado = lectura.nextLine();
    return productoBuscado;
}

public static void añadirProductoAInventario(Scanner lectura, ArrayList<Cliente> clientes, ArrayList<Producto> productosGeneral){

    System.out.println("Qué cliente ha comprado?");
    String clienteCompra = lectura.nextLine();
    boolean clienteEncontrado = false;

    for (int i = 0; i < clientes.size(); i++) {
        if(clientes.get(i).getNombre().equalsIgnoreCase(clienteCompra)){
            clienteEncontrado = true;

            System.out.println("Qué producto ha comprado?");
            String productoComprado = lectura.nextLine();
            boolean productoEncontrado = false;

            for (int j = 0; j < productosGeneral.size(); j++) {
                if(productosGeneral.get(j).getNombre().equalsIgnoreCase(productoComprado)){
                    productoEncontrado = true;

                    if(productosGeneral.get(j).getCantidad() > 0) {
                        clientes.get(i).getInventario().añadirProducto(productosGeneral.get(j));
                        System.out.println("✅ El producto " + productoComprado + " se ha añadido al inventario de " + clienteCompra);
                        int stockActual = productosGeneral.get(j).getCantidad();
                        productosGeneral.get(j).setCantidad(stockActual - 1);
                        System.out.println("El producto " + productoComprado + " ahora tiene " + productosGeneral.get(j).getCantidad() + " unidades en stock.");
                    }else{
                        System.out.println("No queda stock del producto solicitado, lo sentimos.");
                    }
                    break;
                }
            }
            if(!productoEncontrado){
                System.out.println("No se ha encontrado ningun producto con ese nombre.");
            }
            break;
        }
    }
    if(!clienteEncontrado){
        System.out.println("No se ha encontrado ningun cliente con ese nombre.");
    }
}

public static void borrarProductoDeInventario(Scanner lectura, ArrayList<Cliente> clientes, ArrayList<Producto> productosGeneral){

    System.out.println("Qué cliente ha eliminado un producto?");
    String clienteElimina = lectura.nextLine();
    boolean clienteEncontrado = false;

    for (int i = 0; i < clientes.size(); i++) {
        if(clientes.get(i).getNombre().equalsIgnoreCase(clienteElimina)){

            clienteEncontrado = true;

            System.out.println("Qué producto ha eliminado del inventario?");
            String productoEliminado = lectura.nextLine();
            boolean productoEncontrado = false;

            for (int j = 0; j < clientes.get(i).getInventario().getProductos().size(); j++) {

                Inventario carrito = clientes.get(i).getInventario();
                Producto productoBorradoDelCarrito = carrito.getProductos().get(j);

                if(productoBorradoDelCarrito.getNombre().equalsIgnoreCase(productoEliminado)){

                    productoEncontrado = true;
                    carrito.borrarProducto(productoBorradoDelCarrito);
                    System.out.println("El producto " + productoEliminado + " se ha eliminado del inventario del cliente " + clienteElimina);

                    boolean productoEncontradoEnGeneral = false;

                    for (int k = 0; k < productosGeneral.size(); k++) {
                        if(productosGeneral.get(k).getNombre().equalsIgnoreCase(productoEliminado)){

                            productoEncontradoEnGeneral = true;
                            int stockActual = productosGeneral.get(k).getCantidad();
                            productosGeneral.get(k).setCantidad(stockActual + 1);
                            System.out.println("El producto " + productoEliminado + " ahora tiene " + productosGeneral.get(k).getCantidad() + " unidades en stock.");
                            break;
                        }
                    }
                    if(!productoEncontradoEnGeneral){
                        System.out.println("El producto " + productoEliminado + " ya no existe en el almacen.");
                    }
                    break;
                }
            }
            if(!productoEncontrado){
                System.out.println("No se ha encontrado ningun producto con ese nombre en el inventario de este cliente.");
            }
            break;
        }
    }
    if(!clienteEncontrado){
        System.out.println("No se ha encontrado ningun cliente con ese nombre.");
    }
}

public static void mostrarInventario(Scanner lectura, ArrayList<Cliente> clientes){

    System.out.println("De que cliente quieres ver el inventario?");
    String clienteBuscado = lectura.nextLine();
    boolean clienteEncontrado = false;

    for (int i = 0; i < clientes.size(); i++) {
        if(clientes.get(i).getNombre().equalsIgnoreCase(clienteBuscado)){

            clienteEncontrado = true;
            System.out.println(clientes.get(i).getInventario().toString());
            break;
        }
    }
    if(!clienteEncontrado){
        System.out.println("No se ha encontrado ningun cliente con ese nombre.");
    }
}

public static void borrarInventario(Scanner lectura, ArrayList<Cliente> clientes){

    System.out.println("De que cliente quieres borrar el inventario?");
    String clienteBuscado = lectura.nextLine();
    boolean clienteEncontrado = false;

    for (int i = 0; i < clientes.size(); i++) {
        if(clientes.get(i).getNombre().equalsIgnoreCase(clienteBuscado)){

            clienteEncontrado = true;
            clientes.get(i).getInventario().borrarInventario();
            System.out.println("El inventario del cliente " + clienteBuscado + " se ha eliminado.");
            break;
        }
    }
    if(!clienteEncontrado){
        System.out.println("No se ha encontrado ningun cliente con ese nombre.");
    }
}
