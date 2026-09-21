package br.gerenciamento.petshop;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class GerenciadorPetshop implements IGerenciadorPetshop {
    private List<Agendamento> agendamentos;
    private final Path arquivo;

    public GerenciadorPetshop() {
        this(Path.of("agendamentos_petshop.txt"));
    }

    GerenciadorPetshop(Path arquivo) {
        this.arquivo = arquivo.toAbsolutePath();
        this.agendamentos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Agendamento agendamento) throws PetshopException {
        Objects.requireNonNull(agendamento, "Agendamento obrigatório.");
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
        return List.copyOf(agendamentos);
    }

    // Requisito 4: Persistência com BufferedWriter
    @Override
    public void salvarDados() throws IOException {
        Path temporario = Files.createTempFile(arquivo.getParent(), ".petshop-", ".tmp");
        try {
            try (BufferedWriter bw = Files.newBufferedWriter(temporario, StandardCharsets.UTF_8)) {
                for (Agendamento a : agendamentos) {
                    bw.write(a.toString());
                    bw.newLine();
                }
            }
            Files.move(temporario, arquivo, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(temporario);
        }
    }

    // Requisito 4: Recuperação com BufferedReader
    @Override
    public void carregarDados() throws IOException {
        if (!Files.exists(arquivo)) return;
        List<Agendamento> carregados = new ArrayList<>();
        var ids = new HashSet<Integer>();
        try (BufferedReader br = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;
            int numeroLinha = 0;
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                String[] dados = linha.split(";", -1);
                try {
                    if (dados.length != 6) throw new IllegalArgumentException("Quantidade de campos inválida.");
                    Agendamento a = new Agendamento(
                            Integer.parseInt(dados[0]), dados[1], dados[2], dados[3], dados[4], Double.parseDouble(dados[5])
                    );
                    if (!ids.add(a.getId())) throw new IllegalArgumentException("ID duplicado.");
                    carregados.add(a);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Arquivo inválido na linha " + numeroLinha + ". Os dados não foram carregados.", e);
                }
            }
        }
        agendamentos = carregados;
    }
}
