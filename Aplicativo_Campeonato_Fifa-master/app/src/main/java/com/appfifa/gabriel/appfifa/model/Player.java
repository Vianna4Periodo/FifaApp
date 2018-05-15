package com.appfifa.gabriel.appfifa.model;

import java.io.Serializable;

public class Player implements Serializable {
    private Long id;
    private String nome;
    private String time;
    private Campeonato campeonato;
    private int pontuacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    @Override
    public String toString() {
        return getId()+" -> "+getNome()+" - Time: "+getTime()+" / Pontos: "+getPontuacao();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
