package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;
import uap.geocolportaje.geocoportaje.Entidades.Punto;

/**
 * Created by Lucho on 8/3/2020.
 */

public class pPunto {

    public void guardarPuntoBD(Punto punto, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        if(punto.getId_cliente() == 0){
            sql= "INSERT INTO punto " +
                    "(latitud, longitud, descripcion, titulo, id_cliente) " +
                    "VALUES ("+punto.getLatitud()+", "+punto.getLongitud()+", '"+punto.getDescripcion()+"', " +
                    "'"+punto.getTitulo()+", "+punto.getId_cliente()+")";
        }else {
            sql= "INSERT INTO punto " +
                    "(latitud, longitud, descripcion, titulo) " +
                    "VALUES ("+punto.getLatitud()+", "+punto.getLongitud()+", '"+punto.getDescripcion()+"', " +
                    "'"+punto.getTitulo()+")";
        }

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public ArrayList<Cliente> listarClientesSeleccionBD(Context context){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;

        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        try {
            Cursor cursor=db.rawQuery("SELECT id, nombre, apellido FROM cliente", null);

            while (cursor.moveToNext()){
                cliente=new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setApellido(cursor.getString(2));
                clientes.add(cliente);
            }
        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();

        return clientes;
    }





}
