package com.appfifa.gabriel.appfifa.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDao {
    private DAO banco;

    public PlayerDao(DAO banco) {
        this.banco = banco;
    }

    public void insere (Player player){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("nome", player.getNome());
        dados.put("time_nome", player.getTime());
        dados.put("campeonato_Fk_Id", player.getCampeonato().getId());
        dados.put("pontuacao", player.getPontuacao());

        db.insert("Player",null,dados);
    }

    public List<Player> buscaPlayerPorCampeonato(Campeonato campeonato){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.query("Player", null, "campeonato_Fk_Id ='"+campeonato.getId()+"'",null,null,null,"pontuacao"+" DESC");

        List<Player> players = new ArrayList<>();
        while (c.moveToNext()){
            Player player = new Player();
            player.setId(c.getLong(c.getColumnIndex("id")));
            player.setNome(c.getString(c.getColumnIndex("nome")));
            player.setTime(c.getString(c.getColumnIndex("time_nome")));
            long campeonatoId = c.getLong(c.getColumnIndex("campeonato_Fk_Id"));
            player.setPontuacao(c.getInt(c.getColumnIndex("pontuacao")));

            player.setCampeonato(campeonato);

            players.add(player);
        }
        c.close();
        return players;
    }

    public Player buscaPlayerPorId(Campeonato campeonato,Long id){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.query("Player", null, "id ='"+id+"'",null,null,null,null);

        Player player = new Player();
        while (c.moveToNext()){
            player.setId(c.getLong(c.getColumnIndex("id")));
            player.setNome(c.getString(c.getColumnIndex("nome")));
            player.setTime(c.getString(c.getColumnIndex("time_nome")));
            long campeonatoId = c.getLong(c.getColumnIndex("campeonato_Fk_Id"));
            player.setPontuacao(c.getInt(c.getColumnIndex("pontuacao")));

            player.setCampeonato(campeonato);
        }
        c.close();
        return player;
    }

    public void atualiza(Player player) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("nome", player.getNome());
        dados.put("time_nome", player.getTime());
        dados.put("campeonato_Fk_Id", player.getCampeonato().getId());
        dados.put("pontuacao", player.getPontuacao());

        String[] params = {player.getId().toString()};
        db.update("Player",dados,"id = ?",params);
    }
}
