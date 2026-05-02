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

    private void initLeftPanel() {
        JPanel left = new JPanel(null);
        left.setBackground(new Color(44, 62, 80));
        left.setBounds(0, 0, 400, 600);

        JLabel logo = new JLabel("SMS", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setBackground(new Color(52, 152, 219));
        logo.setOpaque(true);
        logo.setBounds(150, 120, 100, 100);

        JLabel title = new JLabel("Bienvenue", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 240, 300, 40);

        JLabel subtitle = new JLabel("Système de gestion scolaire", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(149, 165, 166));
        subtitle.setBounds(50, 285, 300, 25);

        left.add(logo);
        left.add(title);
        left.add(subtitle);
        add(left);
    }

    private void initRightPanel() {
        JPanel right = new JPanel(null);
        right.setBackground(Color.WHITE);
        right.setBounds(400, 0, 500, 600);

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

        JLabel lblSignUp = new JLabel("Pas encore de compte ?", SwingConstants.CENTER);
        lblSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSignUp.setForeground(new Color(149, 165, 166));
        lblSignUp.setBounds(100, 343, 300, 20);

        JButton btnSignUp = new JButton("Créer un compte");
        btnSignUp.setBounds(100, 373, 300, 40);
        styleButton(btnSignUp, false);

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

        btnSignUp.addActionListener(e -> {
            dispose();
            new SignUpUI();
        });

        right.add(lblSignIn);
        right.add(txtEmail);
        right.add(txtPassword);
        right.add(btnSignIn);
        right.add(lblSignUp);
        right.add(btnSignUp);
        add(right);
    }

    public HomeUI() {
        setTitle("Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLayout(null);
        initLeftPanel();
        initRightPanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}