package com.appfifa.gabriel.appfifa.model;

import java.io.Serializable;

public class Gol implements Serializable {
    private Long id;
    private String jogador;
    private Player player;
    private Jogo jogo;
    private Campeonato campeonato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    @Override
    public String toString() {
        return getPlayer().getNome()+" - "+getJogador();
    }
}
