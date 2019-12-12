package uap.geocolportaje.geocoportaje.Entidades;

import java.util.Date;

/**
 * Created by Lucho on 12/12/2019.
 */

public class Planilla {
    int id;
    int horas_presenta;
    int horas_entrega;
    int presentaciones;
    int suscripciones;
    int folletos_misioneros;
    int oraciones;
    int cantidad_ventas;
    String fecha;

    public Planilla(int id, int horas_presenta, int horas_entrega, int presentaciones, int suscripciones, int folletos_misioneros, int oraciones, int cantidad_ventas, String fecha) {
        this.id = id;
        this.horas_presenta = horas_presenta;
        this.horas_entrega = horas_entrega;
        this.presentaciones = presentaciones;
        this.suscripciones = suscripciones;
        this.folletos_misioneros = folletos_misioneros;
        this.oraciones = oraciones;
        this.cantidad_ventas = cantidad_ventas;
        this.fecha = fecha;
    }

    public Planilla() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoras_presenta() {
        return horas_presenta;
    }

    public void setHoras_presenta(int horas_presenta) {
        this.horas_presenta = horas_presenta;
    }

    public int getHoras_entrega() {
        return horas_entrega;
    }

    public void setHoras_entrega(int horas_entrega) {
        this.horas_entrega = horas_entrega;
    }

    public int getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(int presentaciones) {
        this.presentaciones = presentaciones;
    }

    public int getSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(int suscripciones) {
        this.suscripciones = suscripciones;
    }

    public int getFolletos_misioneros() {
        return folletos_misioneros;
    }

    public void setFolletos_misioneros(int folletos_misioneros) {
        this.folletos_misioneros = folletos_misioneros;
    }

    public int getOraciones() {
        return oraciones;
    }

    public void setOraciones(int oraciones) {
        this.oraciones = oraciones;
    }

    public int getCantidad_ventas() {
        return cantidad_ventas;
    }

    public void setCantidad_ventas(int cantidad_ventas) {
        this.cantidad_ventas = cantidad_ventas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
