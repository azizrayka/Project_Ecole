package UI;

import dao.PersonDAO;
import dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class SignUpUI extends JFrame {
    public void styleButton(JButton btn, boolean filled) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(!filled);
        btn.setBackground(filled ? new Color(52, 152, 219) : Color.WHITE);
        btn.setForeground(filled ? Color.WHITE : new Color(52, 152, 219));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (!filled) {
            btn.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        }
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(filled ? new Color(41, 128, 185) : new Color(235, 245, 251));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(filled ? new Color(52, 152, 219) : Color.WHITE);
            }
        });
    }
    private JTextField styleField(String placeholder) {
        JTextField field = new JTextField();
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
        return field;
    }
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(44, 62, 80));
        return lbl;
    }
    private void initLeftPanel() {
        JPanel left = new JPanel(null);
        left.setBackground(new Color(44, 62, 80));
        left.setBounds(0, 0, 350, 650);

        JLabel logo = new JLabel("SMS", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setBackground(new Color(52, 152, 219));
        logo.setOpaque(true);
        logo.setBounds(125, 130, 100, 100);

        JLabel title = new JLabel("Créer un compte", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(25, 250, 300, 35);

        JLabel subtitle = new JLabel("Rejoignez notre système", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(149, 165, 166));
        subtitle.setBounds(25, 290, 300, 25);

        left.add(logo);
        left.add(title);
        left.add(subtitle);
        add(left);
    }
    private void initRightPanel() {
        JPanel right = new JPanel(null);
        right.setBackground(Color.WHITE);
        right.setBounds(350, 0, 550, 650);

        JLabel lblTitle = new JLabel("Inscription");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBounds(50, 30, 300, 35);

        JTextField txtNom    = styleField("Nom");
        JTextField txtPrenom = styleField("Prénom");
        JTextField txtDn     = styleField("Date de naissance (YYYY-MM-DD)");
        JTextField txtEmail  = styleField("Email");
        JPasswordField txtMtp = new JPasswordField();
        JPasswordField txtConfirm = new JPasswordField();

        txtNom.setBounds(50,    80,  200, 35);
        txtPrenom.setBounds(270, 80,  200, 35);
        txtDn.setBounds(50,    155, 420, 35);
        txtEmail.setBounds(50,  230, 420, 35);
        txtMtp.setBounds(50,   305, 200, 35);
        txtConfirm.setBounds(270, 305, 200, 35);

        txtMtp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMtp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtConfirm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel lblNom     = makeLabel("Nom");
        JLabel lblPrenom  = makeLabel("Prénom");
        JLabel lblDn      = makeLabel("Date de naissance");
        JLabel lblEmail   = makeLabel("Email");
        JLabel lblMtp     = makeLabel("Mot de passe");
        JLabel lblConfirm = makeLabel("Confirmer mot de passe");
        JLabel lblRole    = makeLabel("Rôle");

        lblNom.setBounds(50,    63,  200, 18);
        lblPrenom.setBounds(270, 63,  200, 18);
        lblDn.setBounds(50,    138, 300, 18);
        lblEmail.setBounds(50,  213, 200, 18);
        lblMtp.setBounds(50,   288, 200, 18);
        lblConfirm.setBounds(270, 288, 220, 18);
        lblRole.setBounds(50,   363, 200, 18);

        String[] roles = {"etudiant", "enseignant"};
        JComboBox<String> cmbRole = new JComboBox<>(roles);
        cmbRole.setBounds(50, 380, 200, 35);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbRole.setBackground(Color.WHITE);

        JLabel lblExtra = makeLabel("Niveau (ex: L1, L2, L3)");
        lblExtra.setBounds(270, 363, 250, 18);
        JTextField txtExtra = styleField("L1");
        txtExtra.setBounds(270, 380, 200, 35);

        cmbRole.addActionListener(e -> {
            String selected = (String) cmbRole.getSelectedItem();
            if ("etudiant".equals(selected)) {
                lblExtra.setText("Niveau (ex: L1, L2, L3)");
                txtExtra.setText("L1");
            } else {
                lblExtra.setText("Spécialité");
                txtExtra.setText("Spécialité");
            }
        });

        JButton btnSignUp = new JButton("Créer le compte");
        btnSignUp.setBounds(50, 440, 200, 40);
        styleButton(btnSignUp, true);

        JButton btnBack = new JButton("Retour");
        btnBack.setBounds(270, 440, 200, 40);
        styleButton(btnBack, false);

        btnBack.addActionListener(e -> {
            dispose();
            new HomeUI();
        });

        btnSignUp.addActionListener(e -> {
            String nom      = txtNom.getText().trim();
            String prenom   = txtPrenom.getText().trim();
            String dn       = txtDn.getText().trim();
            String email    = txtEmail.getText().trim();
            String mtp      = new String(txtMtp.getPassword()).trim();
            String confirm  = new String(txtConfirm.getPassword()).trim();
            String role     = (String) cmbRole.getSelectedItem();
            String extra    = txtExtra.getText().trim();

            if (nom.isEmpty() || nom.equals("Nom") ||
                    prenom.isEmpty() || prenom.equals("Prénom") ||
                    dn.isEmpty() || dn.equals("Date de naissance (YYYY-MM-DD)") ||
                    email.isEmpty() || email.equals("Email") ||
                    mtp.isEmpty() || extra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }

            if (!mtp.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Email invalide.");
                return;
            }

            try {
                PersonDAO personDAO = new PersonDAO();

                if (personDAO.emailExists(email)) {
                    JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé.");
                    return;
                }
                int newId = personDAO.add(nom, prenom, dn, email, role, mtp);

                if (newId == -1) {
                    JOptionPane.showMessageDialog(this, "Échec de la création du compte.");
                    return;
                }
                if ("etudiant".equals(role)) {
                    AdminDAO dao = new AdminDAO();
                    dao.addEtudiant(newId, 0.0, extra);
                } else if ("enseignant".equals(role)) {
                    AdminDAO dao = new AdminDAO();
                    dao.addEnseignant(newId, extra);
                }

                JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                dispose();
                new HomeUI();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });

        right.add(lblTitle);
        right.add(lblNom);     right.add(txtNom);
        right.add(lblPrenom);  right.add(txtPrenom);
        right.add(lblDn);      right.add(txtDn);
        right.add(lblEmail);   right.add(txtEmail);
        right.add(lblMtp);     right.add(txtMtp);
        right.add(lblConfirm); right.add(txtConfirm);
        right.add(lblRole);    right.add(cmbRole);
        right.add(lblExtra);   right.add(txtExtra);
        right.add(btnSignUp);
        right.add(btnBack);
        add(right);
    }

    public SignUpUI() {
        setTitle("Inscription");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setResizable(false);
        setLayout(null);
        initLeftPanel();
        initRightPanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    static void main(){}
}