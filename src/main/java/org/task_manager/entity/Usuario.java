package org.task_manager.entity;

import java.util.List;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Tarefa> listaTarefas;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, List<Tarefa> listaTarefas) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.listaTarefas = listaTarefas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tarefa> getListaTarefas() {
        return listaTarefas;
    }

    public void setListaTarefas(List<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }

    public String getSenha() {
        return senha;
    }
}
