# 🐾 Sistema de Gerenciamento de Petshop

Um sistema interativo via linha de comando (CLI) desenvolvido em Java para gerenciar os agendamentos de serviços de um Petshop. O projeto foi construído com foco em aplicar boas práticas de Programação Orientada a Objetos (POO), tratamento de exceções e persistência de dados.

## 🚀 Funcionalidades

O sistema apresenta um menu interativo que permite ao usuário:
- **Cadastrar Agendamentos:** Registro de clientes, selecionando o tipo de animal (Cachorro/Gato) e o serviço desejado (Banho / Banho e Tosa). O sistema calcula o valor automaticamente com base nessas escolhas.
- **Pesquisar e Remover:** Busca e exclusão de agendamentos utilizando um ID único.
- **Listar Todos:** Exibição formatada de todos os serviços agendados no momento.
- **Persistência de Dados:** Salvamento e carregamento automático dos agendamentos em um arquivo de texto (`agendamentos_petshop.txt`), garantindo que os dados não sejam perdidos ao fechar o programa.

## 🛠️ Tecnologias e Conceitos Aplicados

Este projeto serve como laboratório prático para os seguintes conceitos:

*   **Java (JDK 25):** Linguagem principal do projeto, gerenciado via Maven (`pom.xml`).
*   **Orientação a Objetos (POO):** Uso de classes de modelo (`Agendamento`) com encapsulamento.
*   **Interfaces (Contratos):** Implementação da interface `IGerenciadorPetshop` para padronizar as ações de negócio do sistema e favorecer o baixo acoplamento.
*   **Tratamento de Exceções Customizadas:** Criação e lançamento da classe `PetshopException` para gerenciar erros de regra de negócio (ex: tentar cadastrar IDs duplicados ou buscar IDs inexistentes) sem quebrar a execução do menu principal.
*   **Manipulação de Arquivos (I/O):** Uso de `BufferedReader`, `BufferedWriter`, `FileReader` e `FileWriter` para ler e escrever dados no disco local.

## ⚙️ Como Executar

1. Certifique-se de ter o **Java/JDK** e o **Maven** instalados na sua máquina.
2. Clone este repositório:
   ```bash
   git clone [https://github.com/SEU_USUARIO/sistema-petshop-java.git](https://github.com/SEU_USUARIO/sistema-petshop-java.git)
