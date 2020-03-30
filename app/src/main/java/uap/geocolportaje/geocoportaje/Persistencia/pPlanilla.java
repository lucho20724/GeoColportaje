package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Planilla;

/**
 * Created by Lucho on 8/3/2020.
 */

public class pPlanilla {

    public void guardarPlanillaBD(Planilla planilla, int idUsuario, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        sql= "INSERT INTO planilla " +
                "(horas_presenta, horas_entrega, presentaciones, suscripciones, folletos_misioneros, oraciones, cantidad_ventas, fecha, id_usuario ) " +
                "VALUES ("+planilla.getHoras_presenta()+", "+planilla.getHoras_entrega()+", "+planilla.getPresentaciones()+", "+planilla.getSuscripciones()+
                ", "+planilla.getFolletos_misioneros()+", "+planilla.getOraciones()+", " +
                ""+planilla.getCantidad_ventas()+", "+planilla.getFecha()+", "+String.valueOf(idUsuario)+")";

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public void modificarPlanillaBD(Planilla planilla, Context context,int idUsuario, int idPlanilla){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql = "UPDATE planilla " +
                "SET horas_presenta ="+planilla.getHoras_presenta()+", horas_entrega="+planilla.getHoras_entrega()+", presentaciones="+planilla.getPresentaciones()+" " +
                ", suscripciones ="+planilla.getSuscripciones()+", folletos_misioneros="+planilla.getFolletos_misioneros()+"," +
                " oraciones = "+planilla.getOraciones()+", cantidad_ventas = "+planilla.getCantidad_ventas()+", fecha = '"+planilla.getFecha()+"'," +
                " id_usuario ="+String.valueOf(idUsuario)+"" +
                "WHERE id= "+String.valueOf(idPlanilla);
        try{
            db.execSQL(sql);

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public void eliminarPlanillaBD(int idPlanilla, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql="DELETE FROM planilla " +
                "WHERE id="+String.valueOf(idPlanilla);

        try{
            db.execSQL(sql);

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public ArrayList<Planilla> listarPlanillasBD(ArrayList<Planilla> planillas, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        Planilla planilla= null;
        planillas= new ArrayList<Planilla>();

        try {
            Cursor cursor=db.rawQuery("SELECT id, horas_presenta, horas_entrega, presentaciones," +
                    "suscripciones, folletos_misioneros, oraciones, cantidad_ventas" +
                    "FROM planilla", null);

            while (cursor.moveToNext()){
                planilla=new Planilla();
                planilla.setId(cursor.getInt(0));
                planilla.setHoras_presenta(cursor.getInt(1));
                planilla.setHoras_entrega(cursor.getInt(2));
                planilla.setPresentaciones(cursor.getInt(3));
                planilla.setSuscripciones(cursor.getInt(4));
                planilla.setFolletos_misioneros(cursor.getInt(5));
                planilla.setOraciones(cursor.getInt(6));
                planilla.setCantidad_ventas(cursor.getInt(7));

                planillas.add(planilla);
            }

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
        return planillas;
    }





}
