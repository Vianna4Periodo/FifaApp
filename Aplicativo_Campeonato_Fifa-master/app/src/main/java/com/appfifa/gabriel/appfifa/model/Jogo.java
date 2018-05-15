package com.appfifa.gabriel.appfifa.model;

import java.io.Serializable;

public class Jogo implements Serializable{
    private Long id;
    private Player player1;
    private Player player2;
    private int placarPlayer1;
    private int placarPlayer2;
    private  Campeonato campeonato;
    private  String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getPlacarPlayer1() {
        return placarPlayer1;
    }

    public void setPlacarPlayer1(int placarPlayer1) {
        this.placarPlayer1 = placarPlayer1;
    }

    public int getPlacarPlayer2() {
        return placarPlayer2;
    }

    public void setPlacarPlayer2(int placarPlayer2) {
        this.placarPlayer2 = placarPlayer2;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getPlacarPlayer1()+" __ "+getPlayer1().getNome()+" X "+getPlayer2().getNome()+" __ "+getPlacarPlayer2()+" __-__"+getStatus();
    }
}
