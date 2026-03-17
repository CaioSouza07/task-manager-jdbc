package org.task_manager;

import org.task_manager.dao.TarefaDAO;
import org.task_manager.dao.UsuarioDAO;
import org.task_manager.entity.Situacao;
import org.task_manager.entity.Tarefa;
import org.task_manager.entity.Usuario;
import org.task_manager.service.Auth;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {

        Scanner leitor = new Scanner(System.in);

        System.out.println("Seja bem-vindo ao TaskManager!\n");

        Usuario usuario = new Usuario();
        boolean acaoEscolhida = false;
        boolean acesso = false;

        try{

            while (!acaoEscolhida) {

                System.out.println("Informe a ação desejada:");
                System.out.println("1 - Login");
                System.out.println("2 - Cadastro");
                System.out.println("3 - Encerrar\n");
                int acao = leitor.nextInt();
                leitor.nextLine();


                if (acao == 1) {
                    acaoEscolhida = true;
                    while (!acesso) {
                        System.out.println("-".repeat(5) + "LOGIN" + "-".repeat(5));
                        System.out.println("Digite seu e-mail: ");
                        String email = leitor.nextLine();
                        System.out.println("Digite sua senha: ");
                        String senha = leitor.nextLine();

                        Auth auth = new Auth(new UsuarioDAO());
                        Usuario autenticado = auth.login(email, senha);

                        if (autenticado.getEmail() != null) {
                            System.out.println("\nLogin realizado com sucesso!");
                            usuario = autenticado;
                            acesso = true;
                        } else {
                            System.out.println("\nE-mail ou senha incorretos, tente novamente!\n");
                        }

                    }

                } else if (acao == 2) {
                    acaoEscolhida = true;
                    while (!acesso) {
                        System.out.println("-".repeat(5) + "CADASTRO" + "-".repeat(5));
                        System.out.println("Digite seu nome: ");
                        String nome = leitor.nextLine();
                        System.out.println("Digite seu e-mail: ");
                        String email = leitor.nextLine();
                        System.out.println("Digite sua senha: ");
                        String senha = leitor.nextLine();

                        Auth auth = new Auth(new UsuarioDAO());
                        boolean autenticado = auth.cadastro(nome, email, senha);

                        if (autenticado) {
                            System.out.println("\nCadastro realizado com sucesso!");
                            usuario = auth.login(email, senha);
                            acesso = true;
                        } else {
                            System.out.println("\nE-mail escrito incorretamente ou já cadastrado, tente novamente!\n");
                        }

                    }

                } else if (acao == 3) {
                    acaoEscolhida = true;
                    System.out.println("Encerrando programa...");
                } else {
                    System.out.println("Não entendi a ação desejada, informe com um número correspondente!");
                }

            }

            if (acesso){
                boolean encerrarPrograma = false;

                while (!encerrarPrograma){
                    System.out.println( "\n" + "-".repeat(5) + "MENU" + "-".repeat(5));
                    System.out.println("Escolha uma opção do menu de opções:");
                    System.out.println("1 - Listar tarefas");
                    System.out.println("2 - Mudar situação da tarefa");
                    System.out.println("3 - Adicionar tarefa");
                    System.out.println("4 - Deletar tarefa");
                    System.out.println("5 - Editar tarefa");
                    System.out.println("6 - Encerrar\n");

                    int opcaoDesejada = leitor.nextInt();
                    leitor.nextLine();

                    if (opcaoDesejada == 1){

                        TarefaDAO tarefaDAO = new TarefaDAO();
                        List<Tarefa> listaTarefas = tarefaDAO.obterTodosPorUsuario(usuario);

                        if(listaTarefas.isEmpty()){
                            System.out.println("Nenhuma tarefa criada por este usuário!");
                        }else{
                            System.out.println("-".repeat(5));
                            System.out.println("Tarefas");
                            for (Tarefa tarefa : listaTarefas){
                                System.out.println("| " + tarefa.getId() + " | " + tarefa.getTitulo());
                                System.out.println("| Descrição: " + tarefa.getDescricao() + "\n");
                            }
                        }

                    } else if (opcaoDesejada == 2) {

                    } else if (opcaoDesejada == 3) {

                        System.out.println("-".repeat(5) + "CRIAR TAREFA" + "-".repeat(5));
                        System.out.println("Digite o titulo: ");
                        String titulo = leitor.nextLine();
                        System.out.println("Digite uma descrição para a tarefa: ");
                        String descricao = leitor.nextLine();

                        TarefaDAO tarefaDAO = new TarefaDAO();

                        Tarefa tarefa = new Tarefa(titulo, descricao, Situacao.PENDENTE, usuario);
                        tarefaDAO.cadastrarTarefa(tarefa);

                        System.out.println("\nTarefa cadastrada com sucesso!");

                    } else if (opcaoDesejada == 4) {

                    } else if (opcaoDesejada == 5) {

                    } else if (opcaoDesejada == 6) {
                        System.out.println("Encerrando programa...");
                        encerrarPrograma = true;
                    } else {
                        System.out.println("\nNão entendi a opção desejada, tente novamente!");
                    }


                }



            }
        }catch (InputMismatchException e ){
            System.out.println("Digite uma opção correta!");
        }







    }
}
