package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Libro;

/**
 * Created by Lucho on 8/3/2020.
 */

public class pLibro {

    public void guardarLibroBD(Libro libro, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        sql= "INSERT INTO libro (nombre, autor, editorial) " +
                "VALUES('"+libro.getNombre()+"', '"+libro.getAutor()+"', '"+libro.getEditorial()+"')";

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();

    }

    public void modificarLibroBD(Libro libro, Context context){
        String  sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql= "UPDATE libro "+
                "SET nombre= '"+libro.getNombre()+"', autor= '"+libro.getAutor()+
                "', editorial= '"+libro.getEditorial()+"'"+
                "WHERE id= "+String.valueOf(libro.getId());

        try{
            db.execSQL(sql);

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
          db.close();
    }


    public void eliminarLibroBD(int idLibro, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql="DELETE FROM libro " +
                "WHERE id="+String.valueOf(idLibro);

        try{
            db.execSQL(sql);

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public ArrayList<Libro> listarLibrosBD(ArrayList<Libro> libros, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        Libro libro=null;
        libros=new ArrayList<Libro>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, nombre, autor FROM libro",null);

            while (cursor.moveToNext()){
                libro = new Libro();
                libro.setId(cursor.getInt(0));
                libro.setNombre(cursor.getString(1));
                libro.setAutor(cursor.getString(2));

                libros.add(libro);
            }

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
        return libros;
    }

    public Libro buscarLibroIdBD(int id, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        Libro libro = new Libro();


        try{
            Cursor cursor=db.rawQuery("SELECT nombre, autor, editorial "+
                    " FROM libro WHERE id="+String.valueOf(id),null);

            cursor.moveToFirst();

            libro.setNombre(cursor.getString(0));
            libro.setAutor(cursor.getString(1));
            libro.setEditorial(cursor.getString(2));

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

        db.close();
        return libro;
    }
}
