package com.appfifa.gabriel.appfifa.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Gol;
import com.appfifa.gabriel.appfifa.model.Jogo;

import java.util.ArrayList;
import java.util.List;

public class GolDao {

    private DAO banco;

    public GolDao(DAO banco) {
        this.banco = banco;
    }

    public void insere (Gol gol){
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues dados =  new ContentValues();

        dados.put("jogador_nome", gol.getJogador());
        dados.put("player_Fk_Id", gol.getPlayer().getId());
        dados.put("campeonato_Fk_Id", gol.getCampeonato().getId());
        dados.put("jogo_Fk_Id", gol.getJogo().getId());

        db.insert("Gol",null,dados);
    }

    public List<Gol> buscaPlayerPorJogo(Jogo jogo) {
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.query("Gol", null, "jogo_Fk_Id ='"+jogo.getId()+"'",null,null,null,null);

        List<Gol> gols = new ArrayList<>();
        PlayerDao pDao = new PlayerDao(banco);
        CampeonatoDao cDao = new CampeonatoDao(banco);
        while (c.moveToNext()){
            Gol gol = new Gol();
            gol.setPlayer(pDao.buscaPlayerPorId(jogo.getCampeonato(), c.getLong(c.getColumnIndex("player_Fk_Id"))));
            gol.setJogador(c.getString(c.getColumnIndex("jogador_nome")));
            gol.setId(c.getLong(c.getColumnIndex("id")));
            gol.setCampeonato(jogo.getCampeonato());
            gol.setJogo(jogo);

            gols.add(gol);
        }
        c.close();
        return gols;
    }

    public List<Gol> buscaGoleador(Campeonato campeonato) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String sql = "SELECT jogador_nome, count(jogador_nome) FROM Gol WHERE campeonato_FK_Id ="+campeonato.getId()+" GROUP BY jogador_nome ORDER BY count(jogador_nome) DESC;";
        Cursor c = db.rawQuery(sql, null);

        List<Gol> gols = new ArrayList<>();
        PlayerDao pDao = new PlayerDao(banco);
        CampeonatoDao cDao = new CampeonatoDao(banco);
        while (c.moveToNext()){
            Gol gol = new Gol();
            gol.setJogador(c.getString(c.getColumnIndex("jogador_nome")));

            gols.add(gol);
        }
        c.close();
        return gols;
    }

    public List<Gol> buscaPlayerArtilheiro(Campeonato campeonato) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String sql = "SELECT player_Fk_Id, count(player_Fk_Id) FROM Gol WHERE campeonato_FK_Id ="+campeonato.getId()+" GROUP BY player_Fk_Id ORDER BY count(player_Fk_Id) DESC;";
        Cursor c = db.rawQuery(sql, null);

        List<Gol> gols = new ArrayList<>();
        PlayerDao pDao = new PlayerDao(banco);
        CampeonatoDao cDao = new CampeonatoDao(banco);
        while (c.moveToNext()){
            Gol gol = new Gol();
            gol.setPlayer(pDao.buscaPlayerPorId(campeonato, c.getLong(c.getColumnIndex("player_Fk_Id"))));
            gols.add(gol);
        }
        c.close();
        return gols;
    }
}
