import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> productosCliente;

    public Inventario(){
        this.productosCliente = new ArrayList<Producto>();
    }

    @Override
    public String toString() {
        return "" + productosCliente;
    }

    public ArrayList<Producto> getProductos() {
        return productosCliente;
    }

    //AÑADIR AL INVENTARIO
    public void añadirProducto(Producto producto) {
        productosCliente.add(producto);
    }

    //BORRAR DEL INVENTARIO
    public void borrarProducto(Producto producto) {
        productosCliente.remove(producto);
    }
    //LIMPIAR EL INVENTARIO
    public void borrarInventario() {
        productosCliente.clear();
    }
}
