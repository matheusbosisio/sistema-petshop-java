# 🐾 Sistema de Gerenciamento de PetShop (Java)

## 📌 Descrição

Este projeto é um sistema de gerenciamento de PetShop desenvolvido em Java, com foco em organização de código e aplicação de conceitos de Programação Orientada a Objetos.

O sistema permite gerenciar informações relacionadas a agendamentos e operações básicas de um petshop.

---

## 🚀 Funcionalidades

* Gerenciamento de agendamentos
* Estrutura baseada em interface (`IGerenciadorPetshop`)
* Implementação de regras de negócio (`GerenciadorPetshop`)
* Execução via interface gráfica (GUI)

---

## 🛠️ Tecnologias Utilizadas

* Java
* Maven
* Programação Orientada a Objetos (POO)

---

## 📂 Estrutura do Projeto

```
src/main/java/
├── Agendamento.java
├── GerenciadorPetshop.java
├── IGerenciadorPetshop.java
├── Main.java
└── MainGUI.java
```

* `Agendamento`: representa os dados de um agendamento
* `IGerenciadorPetshop`: define as regras do sistema
* `GerenciadorPetshop`: implementação da lógica
* `Main`: execução via terminal
* `MainGUI`: execução com interface gráfica

---

## ▶️ Como Executar

### Pré-requisitos

* Java instalado
* Maven instalado

### Passos

1. Clone o repositório:

```bash
git clone (link do seu repo)
```

2. Acesse a pasta:

```bash
cd ProjetoLpPetSHop
```

3. Execute o projeto:

```bash
mvn compile
mvn exec:java
```

Ou execute diretamente pela IDE (IntelliJ / Eclipse)

---

## 📷 Demonstração

*(adicione prints da aplicação aqui — isso aumenta MUITO o impacto do projeto)*

---

## 📌 Melhorias Futuras

* Transformar em API REST com Spring Boot
* Integração com banco de dados
* Melhorar interface gráfica
* Adicionar autenticação de usuários

---

## 👨‍💻 Autor

Carlos Daniel, Matheus Bosisio, Carlos Eduardo, Pedro 
