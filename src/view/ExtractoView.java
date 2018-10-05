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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.business.Validacao;

/**
 *
 * @author saide
 */
public class ExtractoView extends JFrame {

    private JButton btnHome, btnPesquisar;
    private JTextField tfPesquisar;
    private JTable tblPagamento;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("/imagens/bGExtracto.png"));
    private ImageIcon iiHome = new ImageIcon(getClass().getResource("/imagens/btnImgHome.png"));
    private ImageIcon iiPesquisar = new ImageIcon(getClass().getResource("/imagens/btnImgPesquisar.png"));
    private Panel panel;
    private DefaultTableModel modelo = new DefaultTableModel();

    public ExtractoView() {

        desenharPagina();
        btnHome.addActionListener(new ButtonHandler());
        btnPesquisar.addActionListener(new ButtonHandler());
        tfPesquisar.addKeyListener(new TextFildHandler());
        definicoePadrao();

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

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(hPesquisar);
        vBox.add(Box.createVerticalStrut(20));

        panel = new Panel();
        panel.setLayout(new BorderLayout());

        panel.add(BorderLayout.NORTH, vBox);
        panel.add(BorderLayout.CENTER, criarTabela());
        panel.add(BorderLayout.WEST, criarInvArea(40, 11));
        panel.add(BorderLayout.EAST, criarInvArea(33, 11));
        panel.add(BorderLayout.SOUTH, criarHorlInvisComp(60));

        add(panel);
    }

    private JScrollPane criarTabela() {

        MensalidadesPagasController mpc = new MensalidadesPagasController();

        tblPagamento = new JTable(modelo);
        JScrollPane pane = new JScrollPane(tblPagamento);
        tblPagamento.setFillsViewportHeight(false);
        modelo.addColumn("Referencia");
        modelo.addColumn("Valor");
        modelo.addColumn("Responsavel");
        modelo.addColumn("Servico");
        modelo.addColumn("Ano Lectivo");
        modelo.addColumn("Data de Emissao");
        modelo.addColumn("Data de Pagamento");
        modelo.addColumn("Estado");
        modelo.addColumn("Curso");
        mpc.pesquisar(modelo, -1);
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

    // sobrescrita do metodo paintComponent para setar 
    // um background
    private class Panel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = fundo.getImage();
            g.drawImage(img, 0, 0, this);
        }
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
                if (flag == false) {
                    modelo.setNumRows(0);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

            if (Validacao.isInteger(tfPesquisar.getText())) {
                flag = mpc.pesquisar(modelo, Integer.parseInt(tfPesquisar.getText()));
                if (flag == false) {
                    modelo.setNumRows(0);
                }
            }
        }

    }
}
