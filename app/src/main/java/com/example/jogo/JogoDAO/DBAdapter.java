package com.example.jogo.JogoDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    private static final String TAG = "DBAdapter";

    public static final String KEY_ID = "id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_TEMPO = "tempo";
    public static final String KEY_DIFICULDADE = "dificuldade";
    public static final String KEY_PONTOS = "pontos";

    private static final String DATABASE_NAME = "PrisioneiroDB";
    private static final String DATABASE_TABLE = "ranking";
    private static final int DATABASE_VERSION = 1;

    // tipos existentes no SQLite: INTEGER/REAL/TEXT
    private static final String CRIA_DATABASE = "create table ranking " +
            "(id integer primary key autoincrement, " +
            " nome text not null," +
            " tempo real not null," +
            " dificuldade integer not null," +
            " pontos integer not null);";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context); //classe interna que herda de SQLiteOpenHelper
    }

    //classe interna que manipula o banco
    //SQLiteOpenHelper � uma classe abstrata.
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CRIA_DATABASE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Atualizando a base de dados a partir da versao " + oldVersion
                    + " para " + newVersion + ",isso ira destruir todos os dados antigos");
            //Os logs permitem ao desenvolvedor debugar erros durante o desenvolvimento e também investigar problemas com o software em produção,
            // ou seja, com o usuário final (podem ser vistos no LogCat). Para este fim, android tem um classe específica, a classe Log (android.util.Log).
            // Para criar os log, temos à disposição as funções Log.v(), Log.d(), Log.i(), Log.w(), r Log.e().
            // DEBUG – logs impressos pela função Log.d()
            // ERROR – logs impressos pela função Log.e()
            // INFO – logs impressos pela função Log.i()
            // VERBOSE – logs impressos pela função Log.v()
            // WARN – logs impressos pela função Log.w()

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    // *******************************************************************************
    //--- abre a base de dados ---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //--- fecha a base de dados ---
    public void close() {
        DBHelper.close();
    }

    //--- insere um Jogador no Ranking ---
    public long insereJogador(String nome, Float tempo, Integer dificuldade, Integer pontos) {
        ContentValues dados = new ContentValues();
        dados.put(KEY_NOME, nome);
        dados.put(KEY_TEMPO, tempo);
        dados.put(KEY_DIFICULDADE, dificuldade);
        dados.put(KEY_PONTOS, pontos);
        return db.insert(DATABASE_TABLE, null, dados);
    }

    //--- limpa o Ranking ---
    public boolean limpaRanking(long idLinha) {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    //--- retorna os primeiros do ranking ---
    public Cursor getRanking() {
        String colunas[] = {KEY_NOME, KEY_TEMPO, KEY_DIFICULDADE, KEY_PONTOS};
        return db.query(DATABASE_TABLE, colunas, null, null, null, null, KEY_PONTOS);
    }

}

