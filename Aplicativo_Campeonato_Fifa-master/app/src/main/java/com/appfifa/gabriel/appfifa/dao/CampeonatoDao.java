package com.appfifa.gabriel.appfifa.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appfifa.gabriel.appfifa.model.Campeonato;

import java.util.ArrayList;
import java.util.List;

public class CampeonatoDao{
    private DAO banco;

    public CampeonatoDao(DAO banco) {
        this.banco = banco;
    }

    public void insere (Campeonato campeonato){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("nome", campeonato.getNome());
        dados.put("status", campeonato.getStatus());

        db.insert("Campeonato",null,dados);
    }

    public List<Campeonato> buscaCampeonatos(){
        String sql = "SELECT * from Campeonato;";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Campeonato> campeonatos = new ArrayList<>();
        while (c.moveToNext()){
            Campeonato campeonato = new Campeonato();
            campeonato.setId(c.getLong(c.getColumnIndex("id")));
            campeonato.setNome(c.getString(c.getColumnIndex("nome")));
            campeonato.setStatus(c.getString(c.getColumnIndex("status")));

            campeonatos.add(campeonato);
        }
        c.close();
        return campeonatos;
    }

    public void altera (Campeonato campeonato){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("nome", campeonato.getNome());
        dados.put("status", campeonato.getStatus());

        String[] params = {campeonato.getId().toString()};
        db.update("Campeonato",dados,"id = ?",params);
    }
}
