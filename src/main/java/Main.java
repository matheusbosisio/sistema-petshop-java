

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IGerenciadorPetshop sistema = new GerenciadorPetshop();
        Scanner scanner = new Scanner(System.in);

        // Carrega os dados ao iniciar (Req. 4)
        try {
            sistema.carregarDados();
            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Nenhum dado anterior encontrado ou erro ao carregar.");
        }

        int opcao = 0;
        while (opcao != 6) {
            System.out.println("\n=== SISTEMA PETSHOP ===");
            System.out.println("1. Agendar Serviço (Cadastrar)");
            System.out.println("2. Pesquisar Agendamento");
            System.out.println("3. Remover Agendamento");
            System.out.println("4. Listar Todos");
            System.out.println("5. Salvar Dados");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("ID do Agendamento: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome do Cliente: ");
                        String nome = scanner.nextLine();
                        System.out.print("Tipo do Animal (1 - Cachorro | 2 - Gato): ");
                        String tipoAnimal = scanner.nextLine().equals("1") ? "Cachorro" : "Gato";
                        System.out.print("Serviço (1 - Banho | 2 - Banho e Tosa): ");
                        String tipoServico = scanner.nextLine().equals("1") ? "Banho" : "Banho e Tosa";
                        System.out.print("Data (ex: 20/03/2026): ");
                        String data = scanner.nextLine();

                        // Regra de precificação baseada na sua ideia
                        double valor = 0;
                        if (tipoAnimal.equals("Cachorro") && tipoServico.equals("Banho")) valor = 50.0;
                        else if (tipoAnimal.equals("Cachorro") && tipoServico.equals("Banho e Tosa")) valor = 80.0;
                        else if (tipoAnimal.equals("Gato") && tipoServico.equals("Banho")) valor = 40.0;
                        else if (tipoAnimal.equals("Gato") && tipoServico.equals("Banho e Tosa")) valor = 70.0;

                        Agendamento novo = new Agendamento(id, nome, tipoAnimal, tipoServico, data, valor);

                        // O método cadastrar pode lançar exceção, que é tratada no catch abaixo (Req. 3)
                        sistema.cadastrar(novo);
                        System.out.printf("Agendamento criado! Valor total a pagar: R$ %.2f\n", valor);
                        break;

                    case 2:
                        System.out.print("Digite o ID para pesquisar: ");
                        int idPesquisa = Integer.parseInt(scanner.nextLine());
                        Agendamento encontrado = sistema.pesquisar(idPesquisa);
                        System.out.println("Encontrado: " + encontrado.getNomeCliente() + " | " + encontrado.getTipoServico() + " | R$ " + encontrado.getValor());
                        break;

                    case 3:
                        System.out.print("Digite o ID para remover: ");
                        int idRemover = Integer.parseInt(scanner.nextLine());
                        sistema.remover(idRemover);
                        System.out.println("Agendamento removido com sucesso!");
                        break;

                    case 4:
                        System.out.println("\n--- Lista de Agendamentos ---");
                        for (Agendamento a : sistema.listarTodos()) {
                            System.out.println("ID: " + a.getId() + " | Cliente: " + a.getNomeCliente() + " | " + a.getTipoAnimal() + " | " + a.getTipoServico() + " | Data: " + a.getData() + " | R$ " + a.getValor());
                        }
                        break;

                    case 5:
                        sistema.salvarDados();
                        System.out.println("Dados salvos no arquivo TXT com sucesso!");
                        break;

                    case 6:
                        sistema.salvarDados(); // Salva automaticamente ao sair
                        System.out.println("Saindo do sistema... Dados salvos.");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (PetshopException e) {
                // Requisito 3: Tratamento da exceção customizada no menu principal
                System.out.println("\n[ATENÇÃO] " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("\n[ERRO] Por favor, digite um número válido.");
            } catch (Exception e) {
                System.out.println("\n[ERRO INESPERADO] " + e.getMessage());
            }
        }
        scanner.close();
    }
}