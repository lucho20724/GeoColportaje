package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;
import uap.geocolportaje.geocoportaje.Entidades.Venta;

/**
 * Created by Lucho on 8/3/2020.
 */

public class pVenta {

    public void guardarVentaBD(Venta venta, Context context){
        String sql;
        Conexion con= new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        sql= "INSERT INTO venta (preciototal, cuotas, fecha, id_cliente) " +
                "VALUES('"+venta.getPreciototal()+"',"+venta.getCuotas()+", '"+venta.getFecha()+"',"+venta.getId_cliente()+")";

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public  void guardarVentaLibroDB(int idVenta,int idLibro, Context context){
        String sql;
        Conexion con= new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();


        sql = "INSERT INTO venta_libro (id_venta,id_libro)" +
                "VALUES (" + String.valueOf(idVenta) + "," + String.valueOf(idLibro)+ ")";

        try {
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


    public int ultimaVentaID(Context context){
        int idVenta;
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getReadableDatabase();

        sql="SELECT id FROM venta ORDER BY id DESC LIMIT 1";

        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        idVenta=cursor.getInt(0);
        db.close();

        return idVenta;
    }
}
