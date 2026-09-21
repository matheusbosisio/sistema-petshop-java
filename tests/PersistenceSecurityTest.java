package br.gerenciamento.petshop;

import java.nio.file.*;
import java.io.IOException;

public class PersistenceSecurityTest {
    public static void main(String[] args) throws Exception {
        Path directory = Files.createTempDirectory("petshop-security-");
        Path file = directory.resolve("data.txt");
        try {
            for (String invalid : new String[]{"Maria;outro", "Maria\nregistro", "Maria\rregistro"}) {
                try { new Agendamento(1, invalid, "Gato", "Banho", "21/09/2026", 40); throw new AssertionError("Campo inválido aceito"); }
                catch (IllegalArgumentException expected) { }
            }
            var manager = new GerenciadorPetshop(file);
            manager.cadastrar(new Agendamento(1, "João", "Gato", "Banho", "21/09/2026", 40));
            manager.salvarDados();
            manager.carregarDados();
            manager.carregarDados();
            if (manager.listarTodos().size() != 1 || !manager.pesquisar(1).getNomeCliente().equals("João")) throw new AssertionError("Round trip");
            try { manager.listarTodos().clear(); throw new AssertionError("Coleção mutável"); }
            catch (UnsupportedOperationException expected) { }
            String valid = Files.readString(file);
            for (String invalid : new String[]{valid + "inválido", valid + valid, valid + "abc;Maria;Gato;Banho;hoje;40\n"}) {
                Files.writeString(file, invalid);
                try { manager.carregarDados(); throw new AssertionError("Arquivo inválido aceito"); }
                catch (IOException expected) { }
                if (manager.listarTodos().size() != 1) throw new AssertionError("Estado alterado após falha");
                if (!Files.readString(file).equals(invalid)) throw new AssertionError("Arquivo alterado após falha");
            }
            Files.writeString(file, valid);
            manager.salvarDados();
            System.out.println("Persistence security checks passed.");
        } finally { Files.deleteIfExists(file); Files.deleteIfExists(directory); }
    }
}
