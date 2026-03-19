package br.gerenciamento.petshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorPetshop implements IGerenciadorPetshop {
    private List<Agendamento> agendamentos;
    private static final String ARQUIVO = "agendamentos_petshop.txt";

    public GerenciadorPetshop() {
        this.agendamentos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Agendamento agendamento) throws PetshopException {
        // Verifica se o ID já existe
        for (Agendamento a : agendamentos) {
            if (a.getId() == agendamento.getId()) {
                throw new PetshopException("Erro: Já existe um agendamento com o ID " + agendamento.getId());
            }
        }
        agendamentos.add(agendamento);
    }

    @Override
    public Agendamento pesquisar(int id) throws PetshopException {
        for (Agendamento a : agendamentos) {
            if (a.getId() == id) {
                return a;
            }
        }
        throw new PetshopException("Erro: Agendamento não encontrado para o ID " + id);
    }

    @Override
    public void remover(int id) throws PetshopException {
        Agendamento a = pesquisar(id); // Reutiliza o método pesquisar que já lança a exceção
        agendamentos.remove(a);
    }

    @Override
    public List<Agendamento> listarTodos() {
        return agendamentos;
    }

    // Requisito 4: Persistência com BufferedWriter
    @Override
    public void salvarDados() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Agendamento a : agendamentos) {
                bw.write(a.toString());
                bw.newLine();
            }
        }
    }

    // Requisito 4: Recuperação com BufferedReader
    @Override
    public void carregarDados() throws IOException {
        File file = new File(ARQUIVO);
        if (!file.exists()) return; // Se não existe, ignora na primeira execução

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    Agendamento a = new Agendamento(
                            Integer.parseInt(dados[0]), dados[1], dados[2], dados[3], dados[4], Double.parseDouble(dados[5])
                    );
                    agendamentos.add(a);
                }
            }
        }
    }
}