package uap.geocolportaje.geocoportaje.Entidades;

/**
 * Created by Lucho on 16/12/2019.
 */

public class Punto {
    int id;
    double latitud;
    double longitud;
    String descripcion;
    String titulo;
    int id_cliente;
    int id_usuario;

    public Punto(int id, double latitud, double longitud, String descripcion, String titulo, int id_cliente, int id_usuario) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.id_cliente = id_cliente;
        this.id_usuario = id_usuario;
    }

    public Punto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
