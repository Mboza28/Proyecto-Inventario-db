package modelos;

public class Producto {

    private String nombre;
    private double precio;     //Se utiliza Double porque float los calculos no los hace exactos y a veces trunca numeros en calculos.
    private int cantidad;

    public Producto() {
        this.nombre = "";
    }

    public Producto(String nombre, double precio, int cantidad){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            System.out.println("Error: El precio no puede ser negativo");
        }
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            System.out.println("Error: La cantidad no puede ser negativa");
        }
    }

    @Override
    public String toString() {
        return "Nombre del producto: " + nombre + ", Precio del producto: " + precio + "€, Stock del producto: " + cantidad;
    }
}
