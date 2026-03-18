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
import java.util.Objects;

public class TarefaDAO extends BaseDAO{

    public void salvar(Tarefa tarefa){

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

    public Tarefa findById(Long id, Usuario usuario){

        String query = "SELECT * FROM tarefas WHERE id = ? AND usuario_id = ?";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query)
                ){

            pre.setLong(1, id);
            pre.setLong(2, usuario.getId());

            ResultSet resultado = pre.executeQuery();


            if(resultado.next()){
                do{

                    Long idTarefa = resultado.getLong("id");
                    String titulo = resultado.getString("titulo");
                    String descricao = resultado.getString("descricao");
                    Situacao situacao = Situacao.valueOf(resultado.getString("situacao"));

                    Tarefa tarefa = new Tarefa(idTarefa, titulo, descricao, situacao, usuario);

                    return tarefa;
                }while(resultado.next());
            }else{
                return new Tarefa();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao achar a tarefa: " + e.getMessage());
            e.printStackTrace();
            return new Tarefa();
        }


    }

    public void deletar(Tarefa tarefa){

        String query = "DELETE FROM tarefas WHERE id = ?";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query);
                ){

            pre.setLong(1, tarefa.getId());

            pre.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar a tarefa: " + e.getMessage());
            e.printStackTrace();
        }

    }


}
