/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MensalidadesPagasController;
import controller.UsuarioController;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author saide
 */
public class PaginaPrincipalView extends JFrame {

    private JTextField tfUserName;
    private JPasswordField pfPassword;
    private JButton btnEntrar;
    private Panel panel;
    private ImageIcon fundo = new ImageIcon(getClass().getResource("/imagens/bGPaginaPrincipal.png"));
    private ImageIcon ImageButton = new ImageIcon(getClass().getResource("/imagens/btnImgEntrar.png"));
    
    // Construtor
    public PaginaPrincipalView() {
        
        desenharPagina();
        
        ButtonHandler bh = new ButtonHandler();
        btnEntrar.addActionListener(bh);

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
        Box hBox = Box.createHorizontalBox();
        vBox.add(Box.createVerticalStrut(200));
        
        panel = new Panel();
        
        tfUserName = new JTextField(15);
        
        pfPassword = new JPasswordField(10);
        pfPassword.setEchoChar('#');
        btnEntrar = new JButton(ImageButton);
        btnEntrar.setPreferredSize(new Dimension(110, 30));
        hBox.add(Box.createHorizontalStrut(190));
        hBox.add(tfUserName);
        hBox.add(Box.createHorizontalStrut(7));
        hBox.add(pfPassword);
        hBox.add(Box.createHorizontalStrut(7));
        hBox.add(btnEntrar);
        hBox.add(Box.createHorizontalStrut(10));
        
        vBox.add(hBox);
        panel.add(vBox);
        
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
            boolean encontrou;
            UsuarioController uc = new UsuarioController();
            encontrou = uc.autenticar(tfUserName.getText(), new String(pfPassword.getPassword()));
            if(encontrou) {
                setVisible(false);
                MenuPrincipalView mp = new MenuPrincipalView(tfUserName.getText());
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Username ou password incorrecto. Tente de novo!");
                tfUserName.setText("");
                pfPassword.setText("");
            }     
        }
    }
    
   
}
