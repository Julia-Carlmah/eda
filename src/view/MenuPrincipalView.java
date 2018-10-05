/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author saide
 */
public class MenuPrincipalView extends JFrame {
    
    private JButton btnExtracto, btnPagamento, btnAdicNovaMensalidade, btnLista;
    private JLabel lbUsername;
    public static String username;
    private Panel panel;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("/imagens/bGMenuPrincipal.png"));
    private ImageIcon usernameIcon = new ImageIcon(getClass().getResource("/imagens/icUsuario.png"));
    private ImageIcon iiExtracto = new ImageIcon(getClass().getResource("/imagens/btnImgExtracto.png"));
    private ImageIcon iiPagamento = new ImageIcon(getClass().getResource("/imagens/btnImgPagamento.png"));
    private ImageIcon iiAdicNovaMensalidade = new ImageIcon(getClass().getResource("/imagens/btnImgAdicionarMensalidade.png"));
    private ImageIcon iiLista = new ImageIcon(getClass().getResource("/imagens/btnImgLista.png"));

    // Constructor
    public MenuPrincipalView(String username) {
        this.username = username;
        this.lbUsername = new JLabel();
        this.lbUsername.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        this.lbUsername.setText(this.username);
        
        desenharPagina();
        
        ButtonHandler bh = new ButtonHandler();
        btnExtracto.addActionListener(bh);
        btnPagamento.addActionListener(bh);
        btnAdicNovaMensalidade.addActionListener(bh);
        btnLista.addActionListener(bh);
        
        definicoePadrao();

    }

    // definicoes padrao da JFrame 
    private void definicoePadrao() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 531);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }
    
    // metodo que faz o desenho da pagina principal
    private void desenharPagina() {
        
        Box vBox = Box.createVerticalBox();
        Box vUsername = Box.createVerticalBox();
        Box hBox = Box.createHorizontalBox();
        Box hUsername = Box.createHorizontalBox();
        
        // set up the username 
        vUsername.add(Box.createVerticalStrut(67));
        hUsername.add(Box.createHorizontalGlue());
        hUsername.add(new JLabel(usernameIcon));
        hUsername.add(Box.createHorizontalStrut(6));
        hUsername.add(lbUsername);
        hUsername.add(Box.createHorizontalStrut(30));
        vUsername.add(hUsername);
        
        // set components (Buttons)
        panel = new Panel();
        panel.setLayout(new BorderLayout());
                
        btnPagamento = new JButton(iiPagamento);
        btnPagamento.setPreferredSize(new Dimension(9, 30));
        
        btnAdicNovaMensalidade = new JButton(iiAdicNovaMensalidade);
        btnAdicNovaMensalidade.setPreferredSize(new Dimension(100, 30));
        
        btnLista = new JButton(iiLista);
        btnLista.setPreferredSize(new Dimension(9, 30));
        
        btnExtracto = new JButton(iiExtracto);
        btnExtracto.setPreferredSize(new Dimension(9, 30));
        
        hBox.add(Box.createHorizontalStrut(80));
        hBox.add(btnPagamento);
        hBox.add(Box.createHorizontalStrut(7));
        hBox.add(btnLista);
        hBox.add(Box.createHorizontalStrut(7));
        hBox.add(btnExtracto);
        hBox.add(Box.createHorizontalStrut(7)); 
        hBox.add(btnAdicNovaMensalidade);
        hBox.add(Box.createHorizontalStrut(80));
        
        vUsername.add(Box.createVerticalStrut(45));       
        vUsername.add(hBox);
        panel.add(BorderLayout.NORTH, vUsername);
        
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
    
    
    // classe que trata o evento
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
                
            if(ae.getSource() == btnExtracto) {
                setVisible(false);
                ExtractoView ev = new ExtractoView();
            } else if(ae.getSource() == btnPagamento) {
                setVisible(false);
                PagamentoView pv = new PagamentoView();
            } else if(ae.getSource() == btnAdicNovaMensalidade) {
                NovaMensalidadeView pv = new NovaMensalidadeView();
            } else if(ae.getSource() == btnLista) {
                setVisible(false);
                MensalidadesPagasView mpv = new MensalidadesPagasView();
            }   
        }
    }

}
