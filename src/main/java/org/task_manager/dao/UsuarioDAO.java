package org.task_manager.dao;

import org.task_manager.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends BaseDAO{

    public Usuario verificarEmailSenha(String email, String senha){

        String query = "SELECT id, nome from usuarios WHERE email = ? and senha = ?";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn.prepareStatement(query);
                ){

            pre.setString(1, email);
            pre.setString(2, senha);

            ResultSet resultado = pre.executeQuery();

            if (resultado.next()) {

                Long id = resultado.getLong("id");
                String nome = resultado.getString("nome");

                Usuario usuario = new Usuario(id, nome, email, senha);
                return usuario;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar email e senha: " + e.getMessage());
            e.printStackTrace();
        }

        return new Usuario();

    }

    public void cadastrarUsuario(Usuario usuario){

        String query = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (
                Connection conn = conn();
                PreparedStatement pre = conn().prepareStatement(query);
                ){

            pre.setString(1, usuario.getNome());
            pre.setString(2, usuario.getEmail());
            pre.setString(3, usuario.getSenha());

            pre.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }


    }

}
