package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private JLabel lab;
    private JLabel mess;
    private JButton app;
    private JButton ok;

    public Frame() {
        setTitle("Notification Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI() {
        int nb = 4;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        lab = new JLabel("NOTIFICATIONS");
        mess = new JLabel(nb + " articles ont atteint leur seuil ! Veuillez réapprovisionner.");
        app = new JButton("RÉAPPROVISIONNER");
        ok = new JButton("OK");

        lab.setFont(new Font("SansSerif", Font.BOLD, 24));
        lab.setForeground(new Color(44, 130, 201));

        mess.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mess.setForeground(new Color(76, 86, 106));

        app.setBackground(new Color(52, 152, 219));
        app.setForeground(Color.WHITE);
        app.setFont(new Font("SansSerif", Font.BOLD, 18));
        app.setBorderPainted(false);

        ok.setBackground(new Color(231, 76, 60));
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("SansSerif", Font.BOLD, 18));
        ok.setBorderPainted(false);

        panel.add(lab, gbc);
        gbc.gridy++;
        panel.add(mess, gbc);
        gbc.gridy++;
        panel.add(app, gbc);
        gbc.gridx++;
        panel.add(ok, gbc);

        app.addActionListener(e -> {
            TabPanel.setSelectedIndex(3);
            closePopup();
        });

        ok.addActionListener(e -> closePopup());

        setContentPane(panel);
    }

    private void closePopup() {
        System.out.println("Fermeture de la fenêtre");
        dispose();
    }

    private static class TabPanel {
        public static void setSelectedIndex(int index) {
            System.out.println("Index sélectionné : " + index);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            Frame frame = new Frame();
            frame.setVisible(true);
        });
    }
}
