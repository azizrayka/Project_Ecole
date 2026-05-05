package UI;

import dao.PersonDAO;
import javax.swing.*;
import java.awt.*;

public class HomeUI extends JFrame {
    public void styleButton(JButton btn, boolean filled) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBackground(filled ? new Color(52, 152, 219) : Color.WHITE);
        btn.setForeground(filled ? Color.WHITE : new Color(52, 152, 219));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
    }
    public void styleField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
    private void initRightPanel() {
        JPanel right = new JPanel(null);
        right.setBackground(Color.WHITE);
        right.setBounds(200, 0, 500, 600);

        JLabel lblSignIn = new JLabel("Se connecter");
        lblSignIn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSignIn.setForeground(new Color(44, 62, 80));
        lblSignIn.setBounds(100, 80, 300, 35);

        JTextField txtEmail = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        styleField(txtEmail, "Email");
        txtEmail.setBounds(100, 140, 300, 38);

        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtPassword.setBounds(100, 195, 300, 38);

        JButton btnSignIn = new JButton("Se connecter");
        btnSignIn.setBounds(100, 255, 300, 40);
        styleButton(btnSignIn, true);

        btnSignIn.addActionListener(e -> {
            String email    = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (email.isEmpty() || email.equals("Email") || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }

            try {
                PersonDAO dao = new PersonDAO();
                int[] result  = dao.login(email, password);
                if (result == null) {
                    JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
                    return;
                }
                int id   = result[0];
                int role = result[1];

                dispose();
                switch (role) {
                    case 0 -> new AdminUI(id);
                    case 1 -> new EnseignantUI(id);
                    case 2 -> new EtudiantUI(id);
                    default -> JOptionPane.showMessageDialog(this, "Rôle inconnu.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });
        right.add(lblSignIn);
        right.add(txtEmail);
        right.add(txtPassword);
        right.add(btnSignIn);
        add(right);
    }

    public HomeUI() {
        setTitle("Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLayout(null);
        initRightPanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}