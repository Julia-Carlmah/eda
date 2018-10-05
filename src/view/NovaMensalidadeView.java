
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MensalidadeController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author saide
 */
public class NovaMensalidadeView extends JFrame {

    private JTextField tfDataEmissao, tfDataLimite;
    private JLabel lbDataEmissao, lbDataLimite;
    private JButton btnAdicionar;
    private JPanel panel;
    private ImageIcon iiSalvar = new ImageIcon(getClass().getResource("/imagens/btnImgSalvar.png"));

    public NovaMensalidadeView() {

        desenharPagina();

        btnAdicionar.addActionListener(new ButtonHandler());

        definicoePadrao();

    }

    // definicoes padrao da JFrame 
    private void definicoePadrao() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    // metodo que faz o desenho da pagina principal
    private void desenharPagina() {

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Box vBox = Box.createVerticalBox();
        Box hDataEmissao = Box.createHorizontalBox();
        Box hDataLimite = Box.createHorizontalBox();

        vBox.add(Box.createVerticalStrut(10));

        lbDataEmissao = new JLabel("Data de Emissao");
        lbDataEmissao.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfDataEmissao = new JTextField();
        tfDataEmissao.setText("1111/11/11");
        adicionarHorizontal(hDataEmissao, lbDataEmissao, tfDataEmissao, 10, 10);

        lbDataLimite = new JLabel("Data Limite");
        lbDataLimite.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        tfDataLimite = new JTextField();
        tfDataLimite.setText("1111/11/11");
        adicionarHorizontal(hDataLimite, lbDataLimite, tfDataLimite, 53, 10);

        vBox.add(hDataEmissao);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(hDataLimite);
        vBox.add(Box.createVerticalStrut(5));

        panel.add(BorderLayout.WEST, criarInvArea(5, 5));
        panel.add(BorderLayout.CENTER, vBox);
        panel.add(BorderLayout.EAST, criarInvArea(3, 3));
        panel.add(BorderLayout.SOUTH, criarBotao());

        add(panel);
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

    // metodo que cria um botao que estara no canto inferior direito do conteiner geral
    private Box criarBotao() {

        Box vBox = Box.createVerticalBox();
        Box hBox = Box.createHorizontalBox();
        btnAdicionar = new JButton(iiSalvar);
        btnAdicionar.setPreferredSize(new Dimension(100, 30));
        hBox.add(Box.createHorizontalGlue());
        hBox.add(btnAdicionar);
        hBox.add(Box.createHorizontalStrut(13));

        vBox.add(Box.createVerticalStrut(7));
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(7));

        return vBox;
    }

    // metodo que adiciona na horizontal componentes
    private void adicionarHorizontal(Box h, Component c1, Component c2, int strut1, int strut2) {
        h.add(c1);
        h.add(Box.createHorizontalStrut(strut1));
        h.add(c2);
        h.add(Box.createHorizontalStrut(strut2));
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            MensalidadeController mc = new MensalidadeController();

            if (mc.adicionarNovaMensalidade(tfDataEmissao.getText(), tfDataLimite.getText())) {
                tfDataEmissao.setText("");
                tfDataLimite.setText("");
                setVisible(false);
            } 

        }

    }
}
