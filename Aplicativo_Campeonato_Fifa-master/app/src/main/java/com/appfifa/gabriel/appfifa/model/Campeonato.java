package com.appfifa.gabriel.appfifa.model;

import java.io.Serializable;

public class Campeonato implements Serializable {
    private Long id;
    private String nome;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getNome() +"  ---->  "+ getStatus();
    }
}
