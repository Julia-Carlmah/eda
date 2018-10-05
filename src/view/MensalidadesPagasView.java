/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MensalidadeController;
import controller.MensalidadesPagasController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saide
 */
public class MensalidadesPagasView extends JFrame {

    private JButton btnHome, btnEditar, btnEliminar;
    private JComboBox cbListarPor;
    private JTable tblPagamento;
    private Panel panel;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("/imagens/bGListaPagamento.png"));
    private ImageIcon iiHome = new ImageIcon(getClass().getResource("/imagens/btnImgHome.png"));
    private ImageIcon iiEditar = new ImageIcon(getClass().getResource("/imagens/btnImgEditar.png"));
    private ImageIcon iiEliminar = new ImageIcon(getClass().getResource("/imagens/btnImgEliminar.png"));
    private DefaultTableModel modelo = new DefaultTableModel();

    // Constructor
    public MensalidadesPagasView() {

        desenharPagina();

        ButtonHandler bh = new ButtonHandler();
        ComboBoxHandler cbh = new ComboBoxHandler();
        btnHome.addActionListener(bh);
        btnEditar.addActionListener(bh);
        btnEliminar.addActionListener(bh);
        cbListarPor.addItemListener(cbh);

        definicoePadrao();

    }

    // definicoes padrao da JFrame 
    private void definicoePadrao() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(970, 681);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    // metodo que faz o desenho da pagina principal
    private void desenharPagina() {

        Box vBox = Box.createVerticalBox();
        Box hBoxBotao = Box.createHorizontalBox();
        Box hBoxComboBox = Box.createHorizontalBox();
        vBox.add(Box.createVerticalStrut(90));

        panel = new Panel();
        panel.setLayout(new BorderLayout());

        btnHome = new JButton(iiHome);
        String[] nomes = {"Toda Lista", "Estudantes de Informatica", "Estudantes de Electronica", "Estudantes de Electrica"};
        cbListarPor = criarComboBox(nomes, 4);
        btnHome.setPreferredSize(new Dimension(111, 30));
        hBoxBotao.add(Box.createHorizontalStrut(40));
        hBoxBotao.add(btnHome);
        hBoxBotao.add(Box.createHorizontalGlue());
        hBoxComboBox.add(Box.createHorizontalStrut(40));
        hBoxComboBox.add(cbListarPor);
        hBoxComboBox.add(Box.createHorizontalStrut(900));
        vBox.add(hBoxBotao);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBoxComboBox);
        vBox.add(Box.createVerticalStrut(20));

        panel.add(BorderLayout.NORTH, vBox);
        panel.add(BorderLayout.CENTER, criarTabela());
        panel.add(BorderLayout.WEST, criarInvArea(40, 11));
        panel.add(BorderLayout.EAST, criarInvArea(35, 11));
        panel.add(BorderLayout.SOUTH, criarBotoes());

        add(panel);
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

    // metodo que cria combox
    private JComboBox criarComboBox(String[] nomes, int i) {
        JComboBox jcb = new JComboBox(nomes);
        jcb.setMaximumRowCount(i);
        return jcb;
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
    
    private Box criarBotoes() {
        
        Box hBox = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();
        btnEditar = new JButton(iiEditar);
        btnEliminar = new JButton(iiEliminar);
        btnEditar.setPreferredSize(new Dimension(110, 30));       
        btnEliminar.setPreferredSize(new Dimension(120, 30));
        hBox.add(Box.createHorizontalGlue());
        hBox.add(btnEditar);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(btnEliminar);
        hBox.add(Box.createHorizontalStrut(35));
        
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(20));
        
        return vBox;
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

    // abaixo estao as classes que tratam eventos 
    private class ButtonHandler implements ActionListener {

        private MensalidadeController mc = new MensalidadeController();
        private MensalidadesPagasController mpc = new MensalidadesPagasController();
        private String nome, situacao;
        private int ref;
        private Date dataEmissao, dataPagamento;
        private double valor;
        
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnHome) {
                setVisible(false);
                MenuPrincipalView mpv = new MenuPrincipalView(MenuPrincipalView.username);
            } else if(ae.getSource() == btnEditar) {
                if(!tblPagamento.getSelectionModel().isSelectionEmpty()) {
                    setVisible(false);
                    nome = (String) modelo.getValueAt(tblPagamento.getSelectedRow(), 2);
                    ref = (int) modelo.getValueAt(tblPagamento.getSelectedRow(), 0);
                    dataEmissao = (Date) modelo.getValueAt(tblPagamento.getSelectedRow(), 5);
                    dataPagamento = (Date) modelo.getValueAt(tblPagamento.getSelectedRow(), 6);
                    situacao = (String) modelo.getValueAt(tblPagamento.getSelectedRow(), 7);
                    valor = (double) modelo.getValueAt(tblPagamento.getSelectedRow(), 1);
                    PagamentoView pv = new PagamentoView(nome, ref, dataEmissao, dataPagamento, situacao, valor);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Selecione a linha que deseja editar");
                }            
            } else if(ae.getSource() == btnEliminar) {
                if(!tblPagamento.getSelectionModel().isSelectionEmpty()) {
                    mc.removerRegPagamento(modelo, tblPagamento.getSelectedRow());
                    mpc.listarMensalidade(modelo);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Selecione a linha que deseja eliminar");
                }
            }
        }
    }
    
    private class ComboBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ie) {
            
            MensalidadesPagasController mpc = new MensalidadesPagasController();
            
            if(ie.getStateChange() == ItemEvent.SELECTED) {
                
                switch(cbListarPor.getSelectedIndex()) {
                    
                    case 0: mpc.listarMensalidade(modelo); break;
                    case 1: mpc.listaPorCurso(modelo, "Eng. Informatica"); break;
                    case 2: mpc.listaPorCurso(modelo, "Eng. Electronica"); break;
                    case 3: mpc.listaPorCurso(modelo, "Eng. Electrica"); break;
                    default: javax.swing.JOptionPane.showMessageDialog(null, "Selecione a lista que deseja!");
                }
            }
            
        }
    
    }
    
    
}
