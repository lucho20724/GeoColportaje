package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Usuario;

/**
 * Created by Lucho on 31/3/2020.
 */

public class pUsuario {

    public void guardarUsuarioBD(Usuario usuario, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql= "INSERT INTO usuario (nombre, apellido, mail, telefono)" +
                "VALUES ('"+usuario.getNombre()+"', '"+usuario.getApellido()+"', '"+usuario.getMail()+"', '"+usuario.getTelefono()+"')";

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public void modificarUsuarioBD(Usuario usuario, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql= "UPDATE usuario "+
                "SET nombre = '"+usuario.getNombre()+"', apellido= '"+usuario.getApellido()+
                "', mail= '"+usuario.getMail()+"', telefono= '"+usuario.getTelefono()+"'";

        try{
            db.execSQL(sql);
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public Usuario buscarUsuarioDB(Context context){
        Usuario usuario = null;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        try{
            Cursor cursor = db.rawQuery("SELECT nombre, apellido, mail, telefono " +
                    "FROM usuario",null);

            cursor.moveToFirst();

            usuario.setNombre(cursor.getString(0));
            usuario.setApellido(cursor.getString(1));
            usuario.setMail(cursor.getString(2));
            usuario.setTelefono(cursor.getString(3));
        }catch(Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

        db.close();

        return usuario;
    }




}
