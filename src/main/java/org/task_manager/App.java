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

        System.out.println("\nSeja bem-vindo ao TaskManager!\n");

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
                    System.out.println("6 - Listar tarefas (Filtro de Situacao)");
                    System.out.println("7 - Encerrar\n");

                    int opcaoDesejada = leitor.nextInt();
                    leitor.nextLine();

                    if (opcaoDesejada == 1){

                        TarefaDAO tarefaDAO = new TarefaDAO();
                        List<Tarefa> listaTarefas = tarefaDAO.obterTodosPorUsuario(usuario);

                        if(listaTarefas.isEmpty()){
                            System.out.println("Nenhuma tarefa criada por este usuário!");
                        }else{
                            System.out.println("-".repeat(5) + "LISTA DE TAREFAS" + "-".repeat(5));
                            for (Tarefa tarefa : listaTarefas){
                                System.out.println("| ID: " + tarefa.getId());
                                System.out.println("| Título: " + tarefa.getTitulo());
                                System.out.println("| Descrição: " + tarefa.getDescricao());
                                System.out.println("| Situação: " + tarefa.getSituacao() + "\n");
                            }
                        }

                    } else if (opcaoDesejada == 2) {

                        System.out.println("-".repeat(5) + "ATUALIZAR TAREFA" + "-".repeat(5));
                        System.out.println("Digite o id da tarefa: ");
                        Long id = leitor.nextLong();
                        leitor.nextLine();
                        System.out.println("Qual situação deseja alterar? \n");
                        System.out.println("1 - PENDENTE");
                        System.out.println("2 - ANDAMENTO");
                        System.out.println("3 - CONCLUIDO\n");

                        int opcao = leitor.nextInt();
                        leitor.nextLine();

                        TarefaDAO tarefaDAO = new TarefaDAO();
                        Tarefa tarefa = tarefaDAO.findById(id, usuario);
                        Situacao novaSituacao = tarefa.getSituacao();

                        switch (opcao){
                            case 1:
                                novaSituacao = Situacao.PENDENTE;
                                break;
                            case 2:
                                novaSituacao = Situacao.ANDAMENTO;
                                break;
                            case 3:
                                novaSituacao = Situacao.CONCLUIDO;
                                break;
                        }

                        if (tarefa.getTitulo() == null){
                            System.out.println("\nNão encontrado essa tarefa para o seu usuário!");
                        }else{

                            if(opcao >= 1 && opcao <= 3){
                                tarefa.setSituacao(novaSituacao);
                                tarefaDAO.atualizar(tarefa);
                                System.out.println("\nTarefa atualizada com sucesso!");
                            }else{
                                System.out.println("\nDigite uma opcao valida!");
                            }

                        }

                    } else if (opcaoDesejada == 3) {

                        System.out.println("-".repeat(5) + "CRIAR TAREFA" + "-".repeat(5));
                        System.out.println("Digite o titulo: ");
                        String titulo = leitor.nextLine();
                        System.out.println("Digite uma descrição para a tarefa: ");
                        String descricao = leitor.nextLine();

                        TarefaDAO tarefaDAO = new TarefaDAO();

                        Tarefa tarefa = new Tarefa(titulo, descricao, Situacao.PENDENTE, usuario);
                        tarefaDAO.salvar(tarefa);

                        System.out.println("\nTarefa cadastrada com sucesso!");

                    } else if (opcaoDesejada == 4) {

                        System.out.println("-".repeat(5) + "DELETAR TAREFA" + "-".repeat(5));
                        System.out.println("Digite o id da tarefa: ");
                        Long id = leitor.nextLong();
                        leitor.nextLine();

                        TarefaDAO tarefaDAO = new TarefaDAO();
                        Tarefa tarefaParaDeletar = tarefaDAO.findById(id, usuario);

                        if (tarefaParaDeletar.getTitulo() == null){
                            System.out.println("\nNão encontrado essa tarefa para o seu usuário!");
                        }else{
                            tarefaDAO.deletar(tarefaParaDeletar);
                            System.out.println("\nDeletado com sucesso!");
                        }





                    } else if (opcaoDesejada == 5) {

                    } else if (opcaoDesejada == 6) {
                        System.out.println("-".repeat(5) + "FILTRO SITUAÇÃO" + "-".repeat(5));
                        System.out.println("Qual situação deseja filtrar? \n");
                        System.out.println("1 - PENDENTE");
                        System.out.println("2 - ANDAMENTO");
                        System.out.println("3 - CONCLUIDO\n");

                        int opcao = leitor.nextInt();
                        leitor.nextLine();



                        if(opcao < 1 || opcao > 3){
                            System.out.println("\nDigite uma opcao valida!");
                        }else{

                            Situacao situacaoFiltro;
                            switch (opcao){
                                case 1:
                                    situacaoFiltro = Situacao.PENDENTE;
                                    break;
                                case 2:
                                    situacaoFiltro = Situacao.ANDAMENTO;
                                    break;
                                case 3:
                                    situacaoFiltro = Situacao.CONCLUIDO;
                                    break;
                                default:
                                    situacaoFiltro = Situacao.PENDENTE;
                            }

                            TarefaDAO tarefaDAO = new TarefaDAO();
                            List<Tarefa> listaTarefas = tarefaDAO.obterTarefasFiltradasPorUsuario(usuario, situacaoFiltro);

                            if(listaTarefas.isEmpty()){
                                System.out.println("Nenhuma tarefa criada por este usuário!");
                            }else{
                                System.out.println("-".repeat(5) + "LISTA DE TAREFAS (FILTRADO)" + "-".repeat(5));
                                for (Tarefa tarefa : listaTarefas){
                                    System.out.println("| ID: " + tarefa.getId());
                                    System.out.println("| Título: " + tarefa.getTitulo());
                                    System.out.println("| Descrição: " + tarefa.getDescricao());
                                    System.out.println("| Situação: " + tarefa.getSituacao() + "\n");
                                }
                            }
                        }



                    } else if (opcaoDesejada == 7) {
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
