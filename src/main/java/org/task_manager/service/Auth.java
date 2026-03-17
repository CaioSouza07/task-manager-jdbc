package org.task_manager.service;

import org.task_manager.dao.UsuarioDAO;
import org.task_manager.entity.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Auth {

    private UsuarioDAO usuarioDAO;

    public Auth(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario login(String email, String senha) {

        String senhaCrypt = "";
        MessageDigest algoritmoCrypt = null;

        try {
            algoritmoCrypt = MessageDigest.getInstance("MD5");
            byte[] bytesSenha = algoritmoCrypt.digest(senha.getBytes(StandardCharsets.UTF_8));

            StringBuffer sb = new StringBuffer();
            for(byte b : bytesSenha){
                sb.append(String.format("%02x", b & 0xff));
            }

            senhaCrypt = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao criptografar senha: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarioDAO.verificarEmailSenha(email, senhaCrypt);
    }

    public boolean cadastro(String nome, String email, String senha) {

        String senhaCrypt = "";
        MessageDigest algoritmoCrypt = null;

        if (!email.contains("@") || !email.contains(".com")){
            return false;
        }

        try {
            algoritmoCrypt = MessageDigest.getInstance("MD5");
            byte[] bytesSenha = algoritmoCrypt.digest(senha.getBytes(StandardCharsets.UTF_8));

            StringBuffer sb = new StringBuffer();
            for(byte b : bytesSenha){
                sb.append(String.format("%02x", b & 0xff));
            }

            senhaCrypt = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao criptografar senha: " + e.getMessage());
            e.printStackTrace();
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senhaCrypt);

        usuarioDAO.cadastrarUsuario(usuario);

        return true;

    }
}
