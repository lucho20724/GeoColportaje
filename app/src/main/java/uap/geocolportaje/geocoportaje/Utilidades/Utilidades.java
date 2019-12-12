package uap.geocolportaje.geocoportaje.Utilidades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import uap.geocolportaje.geocoportaje.Conexion;

/**
 * Created by Lucho on 13/11/2019.
 */

public class Utilidades {

    Conexion conn;

    public static final String CREAR_TABLA_USUARIOS="CREATE TABLE usuario (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, "+
            "apellido TEXT, " +
            "mail TEXT, " +
            "telefono, TEXT" +
            ")";

    public static final String CREAR_TABLA_CATEGORIAS="CREATE TABLE categoria (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT" +
            ")";

    public static final String CREAR_TABLA_LIBROS="CREATE TABLE libro (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "autor TEXT, " +
            "editorial TEXT" +
            ")";

    public static final String CREAR_TABLA_PLANILLAS= "CREATE TABLE planilla ( "+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "horas_presenta INTEGER, "+
            "horas_entrega INTEGER, "+
            "presentaciones INTEGER, "+
            "suscripciones INTEGER, "+
            "folletos_misioneros INTEGER, "+
            "oraciones INTEGER, "+
            "cantidad_ventas INTEGER, "+
            "fecha DATE, "+
            "id_usuario INTEGER, "+
            "FOREIGN KEY (id_usuario) REFERENCES usuario (id) "+
            ")";

    public static final String CREAR_TABLA_VENTA="CREATE TABLE venta (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "preciototal INTEGER, " +
            "id_usuario INTEGER, " +
            "FOREIGN KEY (id_usuario) REFERENCES usuario (id)" +
            ")";

    public static final String CREAR_TABLA_VENTA_LIBROS="CREATE TABLE venta_libro (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_venta INTEGER, " +
            "id_libro INTEGER, " +
            "FOREIGN KEY (id_venta) REFERENCES venta (id), " +
            "FOREIGN KEY (id_libro) REFERENCES libro (id)" +
            ")";

    public static final String CREAR_TABLA_PUNTO="CREATE TABLE punto ("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "lat DOUBLE, "+
            "long DOUBLE, "+
            "descripcion TEXT "+
            ")";
            /*"id_categoria INTEGER, "+
            "FOREING KEY (id_categoria) REFERENCES categoria (id)"+
            ")";*/

    public static final String CREAR_TABLA_CLIENTES= "CREATE TABLE cliente (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "nombre TEXT, "+
            "apellido TEXT, "+
            "telefono TEXT, "+
            "mail TEXT "+
            /*"id_punto INTEGER, "+
            "FOREING KEY (id_punto) REFERENCES punto (id)"+*/
            ")";

    public Integer ObtenerIdUsuario(Context context){
        Integer idUser=null;
        conn = new Conexion(context,"BD",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT id "+
                " FROM usuario ",null);

        try {
            if(cursor !=null) {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    idUser=cursor.getInt(0);
                }
            }
        }catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return idUser;
    }
}

