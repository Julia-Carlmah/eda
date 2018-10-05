/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MensalidadeController;
import controller.MensalidadesPagasController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.business.Validacao;

/**
 *
 * @author saide
 */
public class PagamentoView extends JFrame {

    private JTextField tfPesquisar, tfNome, tfValor, tfReferencia, tfDataEmissao, tfDataPagamento;
    private JButton btnPesquisar, btnHome, btnSalvar;
    private ButtonGroup group;
    private JComboBox cbSituacao;
    private JLabel lbNome, lbValor, lbReferencia, lbDataEmissao, lbDataPagamento, lbSituaca;
    private JRadioButton rbPendente, rbPago;
    private JTable tblPagamento;
    private Panel panel;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("/imagens/bGPagamento.png"));
    private ImageIcon iiHome = new ImageIcon(getClass().getResource("/imagens/btnImgHome.png"));
    private ImageIcon iiPesquisar = new ImageIcon(getClass().getResource("/imagens/btnImgPesquisar.png"));
    private ImageIcon iiSalvar = new ImageIcon(getClass().getResource("/imagens/btnImgSalvar.png"));
    private DefaultTableModel modelo = new DefaultTableModel();
    private String estado = "Pendente";
    private Date dataEmissao, dataPagamento;
    private int referencia;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    // Constructor
    public PagamentoView() {

        desenharPagina();

        btnHome.addActionListener(new ButtonHandler());
        btnPesquisar.addActionListener(new ButtonHandler());
        btnSalvar.addActionListener(new ButtonHandler());

        rbPago.addItemListener(new RadioButtonHandler());
        rbPendente.addItemListener(new RadioButtonHandler());
        tfPesquisar.addKeyListener(new TextFildHandler());

        tblPagamento.getSelectionModel().addListSelectionListener(new TableHandler());

        definicoePadrao();

    }

    public PagamentoView(String nome, int ref, Date emissao, Date pagamento, String situacao, double valor) {
        this();
        tfNome.setText(nome);
        tfReferencia.setText("" + ref);
        tfDataEmissao.setText("" + emissao);
        tfDataPagamento.setText("" + pagamento);
        if (situacao.equalsIgnoreCase("Pago")) {
            rbPago.setSelected(true);
        } else {
            rbPendente.setSelected(true);
        }
        tfValor.setText("" + valor);
    }

    // definicoes padrao da JFrame 
    private void definicoePadrao() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(970, 681);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    // metodo que faz o desenho da pagina principal
    private void desenharPagina() {

        Box vBox = Box.createVerticalBox();
        Box hBoxBotao = Box.createHorizontalBox();
        Box hPesquisar = Box.createHorizontalBox();
        vBox.add(Box.createVerticalStrut(90));

        btnHome = new JButton(iiHome);
        btnHome.setPreferredSize(new Dimension(111, 30));
        hBoxBotao.add(Box.createHorizontalStrut(40));
        hBoxBotao.add(btnHome);
        hBoxBotao.add(Box.createHorizontalGlue());
        vBox.add(hBoxBotao);

        tfPesquisar = new JTextField();
        btnPesquisar = new JButton(iiPesquisar);
        btnPesquisar.setPreferredSize(new Dimension(135, 30));
        hPesquisar.add(Box.createHorizontalStrut(560));
        hPesquisar.add(tfPesquisar);
        hPesquisar.add(Box.createHorizontalStrut(10));
        hPesquisar.add(btnPesquisar);
        hPesquisar.add(Box.createHorizontalStrut(35));

        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hPesquisar);
        vBox.add(Box.createVerticalStrut(6));

        panel = new Panel();
        panel.setLayout(new BorderLayout());

        panel.add(BorderLayout.NORTH, vBox);
        panel.add(BorderLayout.CENTER, criarPainelTabelFormulario());
        panel.add(BorderLayout.WEST, criarInvArea(40, 11));
        panel.add(BorderLayout.EAST, criarInvArea(33, 11));
        panel.add(BorderLayout.SOUTH, criarBotao());

        add(panel);
    }

    private Box criarPainelTabelFormulario() {

        Box vBox = Box.createVerticalBox();
        Box hNome = Box.createHorizontalBox();
        Box hReferencia = Box.createHorizontalBox();
        Box hDataEmissao = Box.createHorizontalBox();
        Box hDataPagamento = Box.createHorizontalBox();
        Box hSituacao = Box.createHorizontalBox();
        Box hValor = Box.createHorizontalBox();

        lbNome = new JLabel("Nome");
        lbNome.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfNome = new JTextField();
        tfNome.setEnabled(false);
        adicionarHorizontal(hNome, lbNome, tfNome, 124, 300);

        lbReferencia = new JLabel("Referencia");
        lbReferencia.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfReferencia = new JTextField();
        adicionarHorizontal(hReferencia, lbReferencia, tfReferencia, 86, 300);

        lbDataEmissao = new JLabel("Data de Emissao");
        lbDataEmissao.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfDataEmissao = new JTextField();
        tfDataEmissao.setEnabled(false);
        adicionarHorizontal(hDataEmissao, lbDataEmissao, tfDataEmissao, 36, 300);

        lbDataPagamento = new JLabel("Data de Pagamento");
        lbDataPagamento.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfDataPagamento = new JTextField();
        adicionarHorizontal(hDataPagamento, lbDataPagamento, tfDataPagamento, 10, 300);

        lbSituaca = new JLabel("Situacao");
        lbSituaca.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        hSituacao.add(lbSituaca);
        hSituacao.add(Box.createHorizontalStrut(103));
        rbPendente = new JRadioButton("Pendente", true);
        rbPago = new JRadioButton("Pago", false);
        hSituacao.add(rbPendente);
        hSituacao.add(rbPago);
        group = new ButtonGroup();
        group.add(rbPendente);
        group.add(rbPago);
        hSituacao.add(Box.createHorizontalStrut(600));

        lbValor = new JLabel("Valor");
        lbValor.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfValor = new JTextField();
        tfValor.setEnabled(false);
        adicionarHorizontal(hValor, lbValor, tfValor, 129, 300);

        vBox.add(criarTabela());
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hNome);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hReferencia);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hDataEmissao);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hDataPagamento);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hSituacao);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hValor);
        vBox.add(Box.createVerticalStrut(10));

        return vBox;
    }

    private JScrollPane criarTabela() {

        MensalidadesPagasController mpc = new MensalidadesPagasController();

        tblPagamento = new JTable(modelo);
        JScrollPane pane = new JScrollPane(tblPagamento);
        tblPagamento.setFillsViewportHeight(true);
        modelo.addColumn("Referencia");
        modelo.addColumn("Valor");
        modelo.addColumn("Responsavel");
        modelo.addColumn("Servico");
        modelo.addColumn("Ano Lectivo");
        modelo.addColumn("Data de Emissao");
        modelo.addColumn("Data de Pagamento");
        modelo.addColumn("Estado");
        modelo.addColumn("Curso");
        mpc.listarMensalidade(modelo);
        tblPagamento.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblPagamento.getColumnModel().getColumn(1).setPreferredWidth(20);
        tblPagamento.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblPagamento.getColumnModel().getColumn(3).setPreferredWidth(40);
        tblPagamento.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblPagamento.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblPagamento.getColumnModel().getColumn(6).setPreferredWidth(60);
        tblPagamento.getColumnModel().getColumn(7).setPreferredWidth(20);

        return pane;
    }

    // metodo que cria um botao que estara no canto inferior direito do conteiner geral
    private Box criarBotao() {

        Box vBox = Box.createVerticalBox();
        Box hBox = Box.createHorizontalBox();
        btnSalvar = new JButton(iiSalvar);
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        hBox.add(Box.createHorizontalGlue());
        hBox.add(btnSalvar);
        hBox.add(Box.createHorizontalStrut(333));

        vBox.add(Box.createVerticalStrut(12));
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(25));

        return vBox;
    }

    // sobrescrita do metodo paintComponent para setar 
    // um background
    private class Panel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = fundo.getImage();
            g.drawImage(img, 0, 0, this);
        }
    }

    // metodo que adiciona na horizontal componentes
    private void adicionarHorizontal(Box h, Component c1, Component c2, int strut1, int strut2) {
        h.add(c1);
        h.add(Box.createHorizontalStrut(strut1));
        h.add(c2);
        h.add(Box.createHorizontalStrut(strut2));
    }

    // metodo que cria uma area invisivel horizoltalmente
    private Box criarHorlInvisComp(int altura) {
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(altura));
        return box;
    }

    // metodo que cria uma area inivisivel horizontal x vertical
    private Box criarInvArea(int largura, int altura) {
        Box box = Box.createVerticalBox();
        box.add(Box.createRigidArea(new Dimension(largura, altura)));
        return box;
    }

    // classe que trata o evento
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            MensalidadesPagasController mpc = new MensalidadesPagasController();
            MensalidadeController mc = new MensalidadeController();
            boolean flag = false;
            if (ae.getSource() == btnHome) {
                setVisible(false);
                MenuPrincipalView mpv = new MenuPrincipalView(MenuPrincipalView.username);
            } else if (ae.getSource() == btnPesquisar) {
                if (Validacao.isInteger(tfPesquisar.getText())) {
                    flag = mpc.pesquisar(modelo, Integer.parseInt(tfPesquisar.getText()));
                    if (flag == false) {
                        JOptionPane.showMessageDialog(null, "O Registo nao foi encontrado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Dado de entrada invalido");
                }
            } else if (ae.getSource() == btnSalvar) {
                if (Validacao.isInteger(tfReferencia.getText()) && Date.valueOf(tfDataEmissao.getText()) instanceof Date && Date.valueOf(tfDataPagamento.getText()) instanceof Date) {
                    dataEmissao = Date.valueOf(tfDataEmissao.getText());
                    dataPagamento = Date.valueOf(tfDataPagamento.getText());
                    referencia = Integer.parseInt(tfReferencia.getText());
                    if (mc.efectuarPagamento(referencia, dataEmissao, dataPagamento, estado)) {
                        mpc.listarMensalidade(modelo);
                        tfDataEmissao.setText("");
                        tfDataPagamento.setText("");
                        tfNome.setText("");
                        tfPesquisar.setText("");
                        tfReferencia.setText("");
                        tfValor.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao extrair dados nos componentes");
                }
            }

        }
    }

    private class RadioButtonHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getSource() == rbPago) {
                estado = "Pago";
            } else if (ie.getSource() == rbPendente) {
                estado = "Pendente";
            }
        }
    }

    private class TextFildHandler extends KeyAdapter {

        MensalidadesPagasController mpc = new MensalidadesPagasController();
        boolean flag = false;

        @Override
        public void keyTyped(KeyEvent ke) {

            if (Validacao.isInteger(tfPesquisar.getText())) {
                flag = mpc.pesquisar(modelo, Integer.parseInt(tfPesquisar.getText()));
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

            if (Validacao.isInteger(tfPesquisar.getText())) {
                flag = mpc.pesquisar(modelo, Integer.parseInt(tfPesquisar.getText()));
            }
        }

    }

    private class TableHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {

            if (!tblPagamento.getSelectionModel().isSelectionEmpty()) {
                tfNome.setText((String) modelo.getValueAt(tblPagamento.getSelectedRow(), 2));
                tfReferencia.setText("" + (int) modelo.getValueAt(tblPagamento.getSelectedRow(), 0));
                tfDataEmissao.setText("" + (Date) modelo.getValueAt(tblPagamento.getSelectedRow(), 5));
                tfDataPagamento.setText("" + (Date) modelo.getValueAt(tblPagamento.getSelectedRow(), 6));
                if (((String) modelo.getValueAt(tblPagamento.getSelectedRow(), 7)).equalsIgnoreCase("Pago")) {
                    rbPago.setSelected(true);
                } else {
                    rbPendente.setSelected(true);
                }
                tfValor.setText("" + (double) modelo.getValueAt(tblPagamento.getSelectedRow(), 1));
            }
        }

    }

}
