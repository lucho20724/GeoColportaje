package uap.geocolportaje.geocoportaje.Entidades;

/**
 * Created by Lucho on 15/12/2019.
 */

public class Venta {
    int id;
    double preciototal;
    int cuotas;
    String fecha;
    int id_cliente;

    public Venta(int id, double preciototal, int cuotas, String fecha, int id_cliente) {
        this.id = id;
        this.preciototal = preciototal;
        this.cuotas = cuotas;
        this.fecha = fecha;
        this.id_cliente = id_cliente;
    }

    public Venta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(double preciototal) {
        this.preciototal = preciototal;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
}
