package modelos;

public class Cliente {

    private int id;
    private String nombre;
    private int edad;

    public Cliente() {
        this.nombre = "";          // Esto se hace porque es una variable no primitiva y si no se inicializa se queda en null con su error NullPointerException si tratamos de acceder a ello a traves del getter
    }

    public Cliente(String nombre, int edad){
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        if(edad >= 0) {
            this.edad = edad;
        }
        else{
            System.out.println("La edad no puede ser negativa.");
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " - " + edad + " años";
    }

    public String toStringInventario() {
        return "ID: " + id + " | " + nombre + " - " + edad + " años |";
    }
}
