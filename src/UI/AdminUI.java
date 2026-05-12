package UI;
import dao.AdminDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class AdminUI extends JFrame {
    private JPanel panelEtudiants, panelEnseignants, panelAffectMatiere, panelAffectEtudiant;
    private JTable tableEtudiants, tableEnseignants;
    private final AdminDAO dao = new AdminDAO();
    private JComboBox<String> cmbProfAffect, cmbMatiereAffectProf;
    private JComboBox<String> cmbEtuAffect, cmbMatiereAffectEtu;
    public void styleTable(JTable table) {
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(235, 245, 251));
        table.setSelectionForeground(Color.BLACK);
    }
    public void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(41, 128, 185)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(52, 152, 219)); }
        });
    }
    private void reloadAffectMatiereComboBoxes() {
        cmbProfAffect.removeAllItems();
        for (Object[] row : dao.getEnseignants())
            cmbProfAffect.addItem(row[0] + " — " + row[1] + " " + row[2]);
        cmbMatiereAffectProf.removeAllItems();
        for (Object[] row : dao.getMatieres())
            cmbMatiereAffectProf.addItem((String) row[0]);
    }
    private void reloadAffectEtudiantComboBoxes() {
        cmbEtuAffect.removeAllItems();
        for (Object[] row : dao.getEtudiants())
            cmbEtuAffect.addItem(row[0] + " — " + row[1] + " " + row[2]);
        cmbMatiereAffectEtu.removeAllItems();
        for (Object[] row : dao.getMatieres())
            cmbMatiereAffectEtu.addItem((String) row[0]);
    }
    public void initializeLayeredPanel() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);
        panelEtudiants = new JPanel();
        panelEtudiants.setBackground(Color.lightGray);
        panelEtudiants.setBounds(0, 0, 800, 600);
        panelEtudiants.setVisible(true);
        initEtudiantsPanel();
        panelEnseignants = new JPanel();
        panelEnseignants.setBackground(Color.lightGray);
        panelEnseignants.setBounds(0, 0, 800, 600);
        panelEnseignants.setVisible(false);
        initEnseignantsPanel();
        panelAffectMatiere = new JPanel();
        panelAffectMatiere.setBackground(Color.lightGray);
        panelAffectMatiere.setBounds(0, 0, 800, 600);
        panelAffectMatiere.setVisible(false);
        initAffectMatierePanel();
        panelAffectEtudiant = new JPanel();
        panelAffectEtudiant.setBackground(Color.lightGray);
        panelAffectEtudiant.setBounds(0, 0, 800, 600);
        panelAffectEtudiant.setVisible(false);
        initAffectEtudiantPanel();
        layeredPane.add(panelEtudiants, Integer.valueOf(0));
        layeredPane.add(panelEnseignants, Integer.valueOf(1));
        layeredPane.add(panelAffectMatiere, Integer.valueOf(2));
        layeredPane.add(panelAffectEtudiant, Integer.valueOf(3));
        this.add(layeredPane);
    }
    private void initEtudiantsPanel() {
        panelEtudiants.setLayout(null);
        String[] columns = {"ID", "Nom", "Prénom", "Date naissance", "Email", "Niveau"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEtudiants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEtudiants);
        scrollPane.setBounds(20, 20, 740, 220);
        styleTable(tableEtudiants);
        panelEtudiants.add(scrollPane);
        int fw = 170, fh = 28, lh = 20;
        int r1Label = 258, r1Field = 278;
        int x1 = 20, x2 = 205, x3 = 390;
        JLabel lblNom = new JLabel("Nom");
        JLabel lblPrenom = new JLabel("Prénom");
        JLabel lblDn = new JLabel("Date naissance (YYYY-MM-DD)");
        JTextField txtNom = new JTextField();
        JTextField txtPrenom = new JTextField();
        JTextField txtDn = new JTextField();
        lblNom.setBounds(x1, r1Label, fw, lh);
        txtNom.setBounds(x1, r1Field, fw, fh);
        lblPrenom.setBounds(x2, r1Label, fw, lh);
        txtPrenom.setBounds(x2, r1Field, fw, fh);
        lblDn.setBounds(x3, r1Label, fw, lh);
        txtDn.setBounds(x3, r1Field, fw, fh);
        int r2Label = 323, r2Field = 343;
        JLabel lblEmail = new JLabel("Email");
        JLabel lblMtp = new JLabel("Mot de passe");
        JLabel lblNiveau = new JLabel("Niveau");
        JTextField txtEmail = new JTextField();
        JTextField txtMtp = new JTextField();
        JTextField txtNiveau = new JTextField();
        lblEmail.setBounds(x1, r2Label, fw, lh);
        txtEmail.setBounds(x1, r2Field, fw, fh);
        lblMtp.setBounds(x2, r2Label, fw, lh);
        txtMtp.setBounds(x2, r2Field, fw, fh);
        lblNiveau.setBounds(x3, r2Label, fw, lh);
        txtNiveau.setBounds(x3, r2Field, fw, fh);
        for (JLabel l : new JLabel[]{lblNom, lblPrenom, lblDn, lblEmail, lblMtp, lblNiveau})
            panelEtudiants.add(l);
        for (JTextField f : new JTextField[]{txtNom, txtPrenom, txtDn, txtEmail, txtMtp, txtNiveau})
            panelEtudiants.add(f);
        JTextField[] allFields = {txtNom, txtPrenom, txtDn, txtEmail, txtMtp, txtNiveau};
        JButton btnAdd = new JButton("Ajouter");
        JButton btnDelete = new JButton("Supprimer");
        btnAdd.setBounds(x1, 388, 170, 35);
        btnDelete.setBounds(x2, 388, 170, 35);
        styleButton(btnAdd);
        styleButton(btnDelete);
        btnAdd.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String dn = txtDn.getText().trim();
            String email = txtEmail.getText().trim();
            String mtp = txtMtp.getText().trim();
            String niveau = txtNiveau.getText().trim();
            if (nom.isEmpty() || prenom.isEmpty() || dn.isEmpty() || email.isEmpty() || mtp.isEmpty() || niveau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
                return;
            }
            int newPersonId = dao.addPerson(nom, prenom, dn, email, "etudiant", mtp);
            if (newPersonId == -1) {
                JOptionPane.showMessageDialog(this, "Échec de l'ajout dans la table person.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dao.addEtudiant(newPersonId, niveau)) {
                JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès !");
                for (JTextField f : allFields) f.setText("");
                updateEtudiantsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Person créé (ID=" + newPersonId + ") mais échec pour etudiant.", "Erreur partielle", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> {
            int selected = tableEtudiants.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant dans le tableau.");
                return;
            }
            int id_etu = (int) tableEtudiants.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet étudiant et son compte person ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            if (dao.deleteEtudiant(id_etu)) {
                JOptionPane.showMessageDialog(this, "Étudiant supprimé !");
                updateEtudiantsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelEtudiants.add(btnAdd);
        panelEtudiants.add(btnDelete);
        updateEtudiantsTable();
    }
    private void updateEtudiantsTable() {
        try {
            List<Object[]> rows = dao.getEtudiants();
            DefaultTableModel model = (DefaultTableModel) tableEtudiants.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) model.addRow(row);
        } catch (Exception e) {
            System.out.println("updateEtudiantsTable error: " + e.getMessage());
        }
    }
    private void initEnseignantsPanel() {
        panelEnseignants.setLayout(null);
        String[] columns = {"ID", "Nom", "Prénom", "email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEnseignants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEnseignants);
        scrollPane.setBounds(20, 20, 740, 220);
        styleTable(tableEnseignants);
        panelEnseignants.add(scrollPane);
        int fw = 170, fh = 28, lh = 20;
        int r1Label = 258, r1Field = 278;
        int x1 = 20, x2 = 205, x3 = 390;
        JLabel lblNom = new JLabel("Nom");
        JLabel lblPrenom = new JLabel("Prénom");
        JLabel lblDn = new JLabel("Date naissance (YYYY-MM-DD)");
        JTextField txtNom = new JTextField();
        JTextField txtPrenom = new JTextField();
        JTextField txtDn = new JTextField();
        lblNom.setBounds(x1, r1Label, fw, lh);
        txtNom.setBounds(x1, r1Field, fw, fh);
        lblPrenom.setBounds(x2, r1Label, fw, lh);
        txtPrenom.setBounds(x2, r1Field, fw, fh);
        lblDn.setBounds(x3, r1Label, fw, lh);
        txtDn.setBounds(x3, r1Field, fw, fh);
        int r2Label = 323, r2Field = 343;
        JLabel lblEmail = new JLabel("Email");
        JLabel lblMtp = new JLabel("Mot de passe");
        JTextField txtEmail = new JTextField();
        JTextField txtMtp = new JTextField();
        lblEmail.setBounds(x1, r2Label, fw, lh);
        txtEmail.setBounds(x1, r2Field, fw, fh);
        lblMtp.setBounds(x2, r2Label, fw, lh);
        txtMtp.setBounds(x2, r2Field, fw, fh);
        for (JLabel l : new JLabel[]{lblNom, lblPrenom, lblDn, lblEmail, lblMtp})
            panelEnseignants.add(l);
        for (JTextField f : new JTextField[]{txtNom, txtPrenom, txtDn, txtEmail, txtMtp})
            panelEnseignants.add(f);
        JTextField[] allFields = {txtNom, txtPrenom, txtDn, txtEmail, txtMtp};
        JButton btnAdd = new JButton("Ajouter");
        JButton btnDelete = new JButton("Supprimer");
        btnAdd.setBounds(x1, 388, 170, 35);
        btnDelete.setBounds(x2, 388, 170, 35);
        styleButton(btnAdd);
        styleButton(btnDelete);
        btnAdd.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String dn = txtDn.getText().trim();
            String email = txtEmail.getText().trim();
            String mtp = txtMtp.getText().trim();
            if (nom.isEmpty() || prenom.isEmpty() || dn.isEmpty() || email.isEmpty() || mtp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
                return;
            }
            int newPersonId = dao.addPerson(nom, prenom, dn, email, "enseignant", mtp);
            if (newPersonId == -1) {
                JOptionPane.showMessageDialog(this, "Échec de l'ajout dans la table person.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dao.addEnseignant(newPersonId, "")) {
                JOptionPane.showMessageDialog(this, "Enseignant ajouté avec succès !");
                for (JTextField f : allFields) f.setText("");
                updateEnseignantsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Person créé (ID=" + newPersonId + ") mais échec pour enseignant.", "Erreur partielle", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> {
            int selected = tableEnseignants.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un enseignant dans le tableau.");
                return;
            }
            int id_prof = (int) tableEnseignants.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet enseignant et son compte person ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            if (dao.deleteEnseignant(id_prof)) {
                JOptionPane.showMessageDialog(this, "Enseignant supprimé !");
                updateEnseignantsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelEnseignants.add(btnAdd);
        panelEnseignants.add(btnDelete);
        updateEnseignantsTable();
    }
    private void updateEnseignantsTable() {
        try {
            List<Object[]> rows = dao.getEnseignants();
            DefaultTableModel model = (DefaultTableModel) tableEnseignants.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) model.addRow(row);
        } catch (Exception e) {
            System.out.println("updateEnseignantsTable error: " + e.getMessage());
        }
    }
    private void initAffectMatierePanel() {
        panelAffectMatiere.setLayout(null);
        JLabel lblTitle = new JLabel("Affecter une matière à un enseignant");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(20, 20, 400, 30);
        panelAffectMatiere.add(lblTitle);
        panelAffectMatiere.add(new JLabel("Enseignant") {{ setBounds(20, 80, 200, 20); }});
        cmbProfAffect = new JComboBox<>();
        cmbProfAffect.setBounds(20, 100, 300, 30);
        panelAffectMatiere.add(cmbProfAffect);
        panelAffectMatiere.add(new JLabel("Matière") {{ setBounds(20, 150, 200, 20); }});
        cmbMatiereAffectProf = new JComboBox<>();
        cmbMatiereAffectProf.setBounds(20, 170, 300, 30);
        panelAffectMatiere.add(cmbMatiereAffectProf);
        JButton btnAffecter = new JButton("Affecter");
        btnAffecter.setBounds(20, 220, 150, 35);
        styleButton(btnAffecter);
        btnAffecter.addActionListener(e -> {
            if (cmbProfAffect.getItemCount() == 0 || cmbMatiereAffectProf.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Aucun enseignant ou matière disponible.");
                return;
            }
            int id_prof = Integer.parseInt(((String) cmbProfAffect.getSelectedItem()).split(" — ")[0].trim());
            String nomMatiere = (String) cmbMatiereAffectProf.getSelectedItem();
            if (dao.affecterMatiereEnseignant(id_prof, nomMatiere)) {
                JOptionPane.showMessageDialog(this, "Matière « " + nomMatiere + " » affectée !");
            } else {
                JOptionPane.showMessageDialog(this, "Échec de l'affectation.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelAffectMatiere.add(btnAffecter);
    }
    private void initAffectEtudiantPanel() {
        panelAffectEtudiant.setLayout(null);
        JLabel lblTitle = new JLabel("Affecter un étudiant à une matière");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(20, 20, 400, 30);
        panelAffectEtudiant.add(lblTitle);
        panelAffectEtudiant.add(new JLabel("Étudiant") {{ setBounds(20, 80, 200, 20); }});
        cmbEtuAffect = new JComboBox<>();
        cmbEtuAffect.setBounds(20, 100, 300, 30);
        panelAffectEtudiant.add(cmbEtuAffect);
        panelAffectEtudiant.add(new JLabel("Matière") {{ setBounds(20, 150, 200, 20); }});
        cmbMatiereAffectEtu = new JComboBox<>();
        cmbMatiereAffectEtu.setBounds(20, 170, 300, 30);
        panelAffectEtudiant.add(cmbMatiereAffectEtu);
        JButton btnAffecter = new JButton("Affecter");
        btnAffecter.setBounds(20, 220, 150, 35);
        styleButton(btnAffecter);
        btnAffecter.addActionListener(e -> {
            if (cmbEtuAffect.getItemCount() == 0 || cmbMatiereAffectEtu.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Aucun étudiant ou matière disponible.");
                return;
            }
            int id_etu = Integer.parseInt(((String) cmbEtuAffect.getSelectedItem()).split(" — ")[0].trim());
            String nomMatiere = (String) cmbMatiereAffectEtu.getSelectedItem();
            if (dao.affecterEtudiantMatiere(id_etu, nomMatiere)) {
                JOptionPane.showMessageDialog(this, "Étudiant affecté à « " + nomMatiere + " » !");
            } else {
                JOptionPane.showMessageDialog(this, "Échec — cet étudiant est peut-être déjà inscrit à cette matière.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelAffectEtudiant.add(btnAffecter);
    }
    public void initializeSidePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setBounds(790, 0, 300, 600);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        JButton btn1 = new JButton("Gérer étudiants");
        JButton btn2 = new JButton("Gérer enseignants");
        JButton btn3 = new JButton("Affecter matière");
        JButton btn4 = new JButton("Affecter étudiant");
        JButton btn5 = new JButton("Déconnecter");
        btn1.addActionListener(e -> showOnly(panelEtudiants));
        btn2.addActionListener(e -> showOnly(panelEnseignants));
        btn3.addActionListener(e -> {
            reloadAffectMatiereComboBoxes();
            showOnly(panelAffectMatiere);
        });
        btn4.addActionListener(e -> {
            reloadAffectEtudiantComboBoxes();
            showOnly(panelAffectEtudiant);
        });
        btn5.addActionListener(e -> { dispose(); new HomeUI(); });
        for (JButton btn : new JButton[]{btn1, btn2, btn3, btn4, btn5}) {
            btn.setMaximumSize(new Dimension(240, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            styleButton(btn);
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        add(panel);
    }
    private void showOnly(JPanel target) {
        for (JPanel p : new JPanel[]{panelEtudiants, panelEnseignants, panelAffectMatiere, panelAffectEtudiant})
            p.setVisible(p == target);
    }
    public AdminUI(int id) {
        setTitle("Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setResizable(false);
        setLayout(null);
        initializeLayeredPanel();
        initializeSidePanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}