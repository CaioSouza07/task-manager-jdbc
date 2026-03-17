package org.task_manager.entity;

public class Tarefa {

    private Long id;
    private String titulo;
    private String descricao;
    private Situacao situacao;
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, Situacao situacao, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.situacao = situacao;
        this.usuario = usuario;
    }

    public Tarefa(Long id, String titulo, String descricao, Situacao situacao, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.situacao = situacao;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
