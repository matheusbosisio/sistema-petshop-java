package br.gerenciamento.petshop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class MainGUI extends JFrame {
    private IGerenciadorPetshop sistema;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JTextField txtId, txtNome;
    private JFormattedTextField txtData; // Alterado para JFormattedTextField
    private JComboBox<String> cbAnimal, cbServico;

    public MainGUI() {
        sistema = new GerenciadorPetshop();
        configurarTema();
        try { sistema.carregarDados(); } catch (IOException e) { /* Arquivo novo */ }

        configurarJanela();
        inicializarComponentes();
        atualizarTabela();
    }

    private void configurarTema() {
        try {

            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void configurarJanela() {
        setTitle("PetShop Pro - Gestão de Agendamentos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
    }

    private void inicializarComponentes() {

        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("Sistema de Gerenciamento PetShop");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelTopo.add(lblTitulo);
        add(painelTopo, BorderLayout.NORTH);


        JPanel painelLateral = new JPanel(new GridBagLayout());
        painelLateral.setBorder(BorderFactory.createTitledBorder("Novo Agendamento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        txtId = new JTextField(15);
        txtNome = new JTextField(15);


        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            txtData = new JFormattedTextField(mascaraData);
            txtData.setColumns(15);
        } catch (ParseException e) {
            txtData = new JFormattedTextField(); // Fallback
        }

        cbAnimal = new JComboBox<>(new String[]{"Cachorro", "Gato"});
        cbServico = new JComboBox<>(new String[]{"Banho", "Banho e Tosa"});


        adicionarCampo(painelLateral, "ID do Registro:", txtId, gbc, 0);
        adicionarCampo(painelLateral, "Nome do Cliente:", txtNome, gbc, 1);
        adicionarCampo(painelLateral, "Tipo de Animal:", cbAnimal, gbc, 2);
        adicionarCampo(painelLateral, "Tipo de Serviço:", cbServico, gbc, 3);
        adicionarCampo(painelLateral, "Data (DD/MM/AAAA):", txtData, gbc, 4);


        JButton btnCadastrar = new JButton("Confirmar Agendamento");
        btnCadastrar.setBackground(new Color(46, 204, 113));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCadastrar.addActionListener(e -> cadastrar());

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        painelLateral.add(btnCadastrar, gbc);

        add(painelLateral, BorderLayout.WEST);


        JPanel painelCentral = new JPanel(new BorderLayout());
        String[] colunas = {"ID", "Cliente", "Animal", "Serviço", "Data", "Valor (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setFillsViewportHeight(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabela);
        painelCentral.add(scroll, BorderLayout.CENTER);


        JPanel painelAcoesTabela = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRemover = new JButton("Remover Selecionado");
        JButton btnSalvar = new JButton("Salvar Base de Dados");

        btnRemover.addActionListener(e -> remover());
        btnSalvar.addActionListener(e -> salvar());

        painelAcoesTabela.add(btnRemover);
        painelAcoesTabela.add(btnSalvar);
        painelCentral.add(painelAcoesTabela, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);
    }

    private void adicionarCampo(JPanel p, String rotulo, JComponent campo, GridBagConstraints gbc, int linha) {
        gbc.gridwidth = 1;
        gbc.gridy = linha;
        gbc.gridx = 0;
        p.add(new JLabel(rotulo), gbc);
        gbc.gridx = 1;
        p.add(campo, gbc);
    }

    private void cadastrar() {
        try {

            if (txtId.getText().trim().isEmpty()) {
                throw new Exception("O campo ID é obrigatório.");
            }
            int id = Integer.parseInt(txtId.getText().trim());


            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) throw new Exception("O nome do cliente é obrigatório.");


            String data = txtData.getText().trim();
            if (data.contains("_")) {
                throw new Exception("Data incompleta! Preencha como DD/MM/AAAA.");
            }

            double valor = calcularPreco((String)cbAnimal.getSelectedItem(), (String)cbServico.getSelectedItem());

            Agendamento a = new Agendamento(id, nome, (String)cbAnimal.getSelectedItem(),
                    (String)cbServico.getSelectedItem(), data, valor);

            sistema.cadastrar(a);
            atualizarTabela();
            limpar();
            JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!\nValor Total: R$ " + String.format("%.2f", valor), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID deve ser apenas números!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private double calcularPreco(String animal, String servico) {
        if (animal.equals("Cachorro")) return servico.equals("Banho") ? 50.0 : 80.0;
        return servico.equals("Gato") ? 40.0 : 70.0;
    }

    private void remover() {
        int fila = tabela.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para remover.");
            return;
        }
        try {
            int id = (int)modeloTabela.getValueAt(fila, 0);
            sistema.remover(id);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Agendamento removido.");
        } catch (PetshopException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void salvar() {
        try {
            sistema.salvarDados();
            JOptionPane.showMessageDialog(this, "Dados salvos no arquivo com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Agendamento a : sistema.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                    a.getId(), a.getNomeCliente(), a.getTipoAnimal(), a.getTipoServico(), a.getData(), a.getValor()
            });
        }
    }

    private void limpar() {
        txtId.setText("");
        txtNome.setText("");
        txtData.setValue(null); // Limpa o campo formatado corretamente
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}