package br.gerenciamento.petshop;

import java.io.IOException;
import java.util.List;

public interface IGerenciadorPetshop {
    public void cadastrar(Agendamento agendamento) throws PetshopException;
    public Agendamento pesquisar(int id) throws PetshopException;
    public void remover(int id) throws PetshopException;
    public List<Agendamento> listarTodos();
    public void salvarDados() throws IOException;
    public void carregarDados() throws IOException;
}
