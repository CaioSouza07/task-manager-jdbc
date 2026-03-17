package org.task_manager.dao;

import org.task_manager.entity.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TarefaDAO extends BaseDAO{

    public void cadastrarTarefa(Tarefa tarefa){
        String query = "INSERT INTO tarefas (titulo, descricao, situacao, usuario_id) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query);
        ){

            pre.setString(1, tarefa.getTitulo());
            pre.setString(2, tarefa.getDescricao());
            pre.setString(3, tarefa.getSituacao().name());
            pre.setLong(4, tarefa.getUsuario().getId());

            pre.execute();

            System.out.println("Tarefa cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar Tarefa: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
