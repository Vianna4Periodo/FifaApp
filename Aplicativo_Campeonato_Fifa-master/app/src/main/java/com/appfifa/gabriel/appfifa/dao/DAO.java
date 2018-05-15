package com.appfifa.gabriel.appfifa.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {
        super(context, "campeonatoDB", null, 18);
    }

    public static final String CREATE_TABLE_CAMPEONATO = "CREATE TABLE Campeonato (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, status TEXT NOT NULL)";
    public static final String CREATE_TABLE_PLAYER = "CREATE TABLE Player (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, time_nome TEXT NOT NULL, campeonato_Fk_Id INTEGER NOT NULL, pontuacao INTEGER)";
    public static final String CREATE_TABLE_JOGO = "CREATE TABLE Jogo (id INTEGER PRIMARY KEY, player1_Fk_Id INTEGER NOT NULL,  player2_Fk_Id INTEGER NOT NULL, campeonato_Fk_Id INTEGER NOT NULL, placar_player1 INTEGER, placar_player2 INTEGER, status TEXT NOT NULL)";
    public static final String CREATE_TABLE_GOL = "CREATE TABLE Gol (id INTEGER PRIMARY KEY, jogador_nome TEXT NOT NULL, player_Fk_Id INTEGER NOT NULL, campeonato_Fk_Id INTEGER NOT NULL, jogo_Fk_Id INTEGER NOT NULL)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CAMPEONATO);
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_JOGO);
        db.execSQL(CREATE_TABLE_GOL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Campeonato;");
        db.execSQL("drop table Player;");
        db.execSQL("drop table Jogo;");
        db.execSQL("drop table Gol;");

        onCreate(db);
    }



}
