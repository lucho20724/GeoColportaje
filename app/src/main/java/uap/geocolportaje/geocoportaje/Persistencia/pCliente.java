package uap.geocolportaje.geocoportaje.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Cliente;

/**
 * Created by Lucho on 8/3/2020.
 */

public class pCliente {

    public void guardarClienteDB(Cliente cliente, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        sql= "INSERT INTO cliente (nombre, apellido, mail, telefono)" +
                "VALUES ('"+cliente.getNombre()+"','"+cliente.getApellido()+"', '"+cliente.getMail()+"', '"+cliente.getTelefono()+"')";

        try{
            db.execSQL(sql);
        }catch(Exception e) {
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void modificarClienteBD(Cliente cliente, Context context, int idCliente){
        String sql;
        Conexion con= new Conexion(context,"BD",null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        sql= "UPDATE cliente " +
                "SET nombre= '"+cliente.getNombre()+"', apellido= '"+cliente.getApellido()+
                "', mail= '"+cliente.getMail()+
                "', telefono='"+cliente.getTelefono()+"'" +
                "WHERE id ="+String.valueOf(idCliente);

        try{
            db.execSQL(sql);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void eliminarClienteBD(int idCliente, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getWritableDatabase();

        sql="DELETE FROM cliente " +
                "WHERE id="+String.valueOf(idCliente);

        try{
            db.execSQL(sql);

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public ArrayList<Cliente> listarClientesBD(ArrayList<Cliente> clientes, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        Cliente cliente=null;
        clientes=new ArrayList<Cliente>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, nombre, apellido, mail, telefonp FROM cliente",null);

            while (cursor.moveToNext()){
                cliente = new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setApellido(cursor.getString(2));
                cliente.setMail(cursor.getString(3));
                cliente.setTelefono(cursor.getString(4));

                clientes.add(cliente);
            }

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        return clientes;
    }

    public Cliente buscarClienteIdBD(int id, Context context){
        String sql;
        Conexion con = new Conexion(context,"BD",null,1);
        SQLiteDatabase db= con.getReadableDatabase();

        Cliente cliente = new Cliente();


        try{
            Cursor cursor=db.rawQuery("SELECT nombre, apellido, mail, telefono "+
                    " FROM cliente WHERE id="+String.valueOf(id),null);

            cursor.moveToFirst();

            cliente.setNombre(cursor.getString(0));
            cliente.setApellido(cursor.getString(1));
            cliente.setMail(cursor.getString(2));
            cliente.setTelefono(cursor.getString(3));

        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

        db.close();
        return cliente;
    }


}
