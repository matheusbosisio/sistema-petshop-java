

import java.util.Objects;

public class Agendamento {
    private int id;
    private String nomeCliente;
    private String tipoAnimal;
    private String tipoServico;
    private String data;
    private double valor;

    public Agendamento(int id, String nomeCliente, String tipoAnimal, String tipoServico, String data, double valor) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.tipoAnimal = tipoAnimal;
        this.tipoServico = tipoServico;
        this.data = data;
        this.valor = valor;
    }

    public int getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public String getTipoAnimal() { return tipoAnimal; }
    public String getTipoServico() { return tipoServico; }
    public String getData() { return data; }
    public double getValor() { return valor; }

    // Requisito 1: hashCode e equals usando o ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return id + ";" + nomeCliente + ";" + tipoAnimal + ";" + tipoServico + ";" + data + ";" + valor;
    }
}
