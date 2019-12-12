package uap.geocolportaje.geocoportaje;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import  uap.geocolportaje.geocoportaje.Utilidades;

/**
 * Created by Lucho on 13/11/2019.
 */

public class Conexion extends SQLiteOpenHelper {

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_CATEGORIAS);
        db.execSQL(Utilidades.CREAR_TABLA_LIBROS);
        db.execSQL(Utilidades.CREAR_TABLA_PLANILLAS);
        db.execSQL(Utilidades.CREAR_TABLA_VENTA);
        db.execSQL(Utilidades.CREAR_TABLA_VENTA_LIBROS);
        db.execSQL(Utilidades.CREAR_TABLA_PUNTO);
        db.execSQL(Utilidades.CREAR_TABLA_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST usuario");
        db.execSQL("DROP TABLE IF EXIST categoria");
        db.execSQL("DROP TABLE IF EXIST libro");
        db.execSQL("DROP TABLE IF EXIST planilla");
        db.execSQL("DROP TABLE IF EXIST venta");
        db.execSQL("DROP TABLE IF EXIST venta_libro");
        db.execSQL("DROP TABLE IF EXIST punto");
        db.execSQL("DROP TABLE IF EXIST cliente");

        onCreate(db);
    }
}
