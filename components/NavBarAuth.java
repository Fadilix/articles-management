package components;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import views.Login;
import views.Register;

public class NavBarAuth extends JMenuBar {

    private JFrame parentFrame;

    public NavBarAuth(JFrame parentFrame) {

        this.parentFrame = parentFrame;
        this.setPreferredSize(new Dimension(getPreferredSize().width, 50));

        // Load the custom font
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "C:\\Users\\MSI Stealth\\Documents\\Coding\\Java\\vente_articles\\fonts\\Manrope-Regular.ttf"));

            // Register the custom font with the UIManager
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Apply the custom font to all Swing components
            setUIFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 16)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // the background
        this.setBackground(new Color(36, 41, 46)); // A dark bluish-gray

        JMenu menuAuth = new JMenu("Authentification");
        JMenuItem login = new JMenuItem("Login");
        JMenuItem inscription = new JMenuItem("Inscription");
        // JMenuItem deconnection = new JMenuItem("Deconnection");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                parentFrame.dispose();
            }
        });

        inscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                parentFrame.dispose();
            }
        });

        // deconnection.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         new Login();
        //         parentFrame.dispose();
        //     }
        // });

        menuAuth.add(login);
        menuAuth.add(inscription);
        // menuAuth.add(deconnection);
        menuAuth.setForeground(Color.WHITE);
        this.add(menuAuth);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Your Application Name");
            new TopNavBar(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Method to set the font for all Swing components
    private static void setUIFont(FontUIResource fontUIResource) {
        UIManager.put("Button.font", fontUIResource);
        UIManager.put("ToggleButton.font", fontUIResource);
        UIManager.put("RadioButton.font", fontUIResource);
        UIManager.put("CheckBox.font", fontUIResource);
        UIManager.put("Menu.font", fontUIResource);
        UIManager.put("MenuItem.font", fontUIResource);
        UIManager.put("MenuBar.font", fontUIResource);
        UIManager.put("PopupMenu.font", fontUIResource);
        UIManager.put("OptionPane.messageFont", fontUIResource);
        // ... (add more components as needed)
    }
}
