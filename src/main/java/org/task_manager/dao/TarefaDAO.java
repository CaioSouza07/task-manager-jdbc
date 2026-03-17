package org.task_manager.dao;

import org.task_manager.entity.Situacao;
import org.task_manager.entity.Tarefa;
import org.task_manager.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar Tarefa: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<Tarefa> obterTodosPorUsuario(Usuario usuario){

        String query = "SELECT * FROM tarefas WHERE usuario_id = ?";
        List<Tarefa> listaTarefas = new ArrayList<>();

        System.out.println();

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query);
                ){

            pre.setLong(1, usuario.getId());
            ResultSet resultado = pre.executeQuery();

            while (resultado.next()){

                Long id = resultado.getLong("id");
                String titulo = resultado.getString("titulo");
                String descricao = resultado.getString("descricao");
                Situacao situacao = Situacao.valueOf(resultado.getString("situacao"));

                Tarefa tarefa = new Tarefa(id, titulo, descricao, situacao, usuario);

                listaTarefas.add(tarefa);

            }

        } catch (SQLException e) {
            System.out.println("Erro ao obter as tarefas: " + e.getMessage());
            e.printStackTrace();
        }

        return listaTarefas;
    }

    public void deletarTarefa(Tarefa tarefa){

        String query = "DELETE FROM tarefas WHERE id = ?";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query);
                ){

            pre.setLong(1, tarefa.getId());

            pre.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao obter as tarefas: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
