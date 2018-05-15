package com.appfifa.gabriel.appfifa.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Jogo;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.ArrayList;
import java.util.List;

public class JogoDao {

    private DAO banco;

    public JogoDao(DAO banco) {
        this.banco = banco;
    }

    public void insere (Jogo jogo){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("campeonato_Fk_Id", jogo.getCampeonato().getId());
        dados.put("player1_Fk_Id", jogo.getPlayer1().getId());
        dados.put("player2_Fk_Id", jogo.getPlayer2().getId());
        dados.put("status", jogo.getStatus());

        db.insert("Jogo",null,dados);
    }

    public void atualiza (Jogo jogo){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("campeonato_Fk_Id", jogo.getCampeonato().getId());
        dados.put("player1_Fk_Id", jogo.getPlayer1().getId());
        dados.put("player2_Fk_Id", jogo.getPlayer2().getId());
        dados.put("placar_player1", jogo.getPlacarPlayer1());
        dados.put("placar_player2", jogo.getPlacarPlayer2());
        dados.put("status", jogo.getStatus());

        String[] params = {jogo.getId().toString()};

        db.update("Jogo",dados,"id = ?", params);
    }

    public List<Jogo> buscaJogoPorCampeonato(Campeonato campeonato){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.query("Jogo", null, "campeonato_Fk_Id ='"+campeonato.getId()+"'",null,null,null,null);

        List<Jogo> jogos = new ArrayList<>();
        PlayerDao pDao = new PlayerDao(banco);
        CampeonatoDao cDao = new CampeonatoDao(banco);
        while (c.moveToNext()){
            Jogo jogo = new Jogo();
            jogo.setId(c.getLong(c.getColumnIndex("id")));
            jogo.setPlayer1(pDao.buscaPlayerPorId(campeonato, c.getLong(c.getColumnIndex("player1_Fk_Id"))));
            jogo.setPlayer2(pDao.buscaPlayerPorId(campeonato, c.getLong(c.getColumnIndex("player2_Fk_Id"))));
            jogo.setCampeonato(campeonato);
            jogo.setPlacarPlayer1(c.getInt(c.getColumnIndex("placar_player1")));
            jogo.setPlacarPlayer2(c.getInt(c.getColumnIndex("placar_player2")));
            jogo.setStatus(c.getString(c.getColumnIndex("status")));

            jogos.add(jogo);
        }
        c.close();
        return jogos;
    }
}
