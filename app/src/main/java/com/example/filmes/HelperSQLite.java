package com.example.filmes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperSQLite extends SQLiteOpenHelper {
    private String tabela1 ="CATEGORIA";
    private String idcategoria="IDCATEGORIA";
    private String descricao="DESCRICAO";


    private String tabela2 ="filmes";
    private String idFilme="IDFILMES";//(chave prim´aria-autoincrement)
    // (chave estrangeira)
    private String titulo="TITULO";
    private String ano="ANO";
    private String avaliacao="AVALIACAO"; //(0 a 100)
    private String tempo="TEMPO";


    public HelperSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIA = "CREATE TABLE " + tabela1 +
                "(" +
                idcategoria + " INTEGER PRIMARY KEY," + // Define a primary key
                descricao + " TEXT " +
                ")";
        String CREATE_FILME = "CREATE TABLE " + tabela2 +
                "(" +
                idFilme + " INTEGER PRIMARY KEY," +
                idcategoria+" INTEGER REFERENCES " + tabela1 +","+
                titulo + " TEXT," +
                ano +"INTEGER,"+
                avaliacao + " INTEGER," +
                tempo +"TEXT"+
                ")";
        db.execSQL(CREATE_CATEGORIA);
        db.execSQL(CREATE_FILME);
    }


    public void addFilme(FilmeModel filme) {
// Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
// It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
// consistency of the database.
        db.beginTransaction();
        try {
// The user might already exist in the database (i.e. the same user created multiple posts).
            long categoria = addCategoria(filme.categoria);
            ContentValues values = new ContentValues();
            values.put(this.idcategoria, filme.idcategoria);
            values.put(this.titulo, filme.titulo);
            values.put(this.ano,filme.ano);
            values.put(this.avaliacao,filme.avaliacao);
            values.put(this.tempo,filme.tempo);
            //values.put()
// Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(tabela2, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
          //  Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }


    public long addCategoria(CategoriaModel categoria){
        SQLiteDatabase db = getWritableDatabase();
        long idcategoria=-1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(this.idcategoria,categoria.idcategoria);
            values.put(this.descricao,categoria.descricao);

            int rows = db.update(tabela1, values, descricao + "= ?", new String[]{categoria.descricao});
            idcategoria = db.insertOrThrow(tabela1, null, values);
            db.setTransactionSuccessful();

        }catch (Exception e){

        }

        return idcategoria;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
