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
            public void mouseExited(java.awt.event.MouseEvent evt)  { btn.setBackground(new Color(52, 152, 219)); }
        });
    }

    public void initializeLayeredPanel() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 600);

        panelEtudiants = new JPanel();
        panelEtudiants.setBackground(Color.lightGray);
        panelEtudiants.setBounds(0, 0, 600, 600);
        panelEtudiants.setVisible(true);
        initEtudiantsPanel();

        panelEnseignants = new JPanel();
        panelEnseignants.setBackground(Color.lightGray);
        panelEnseignants.setBounds(0, 0, 600, 600);
        panelEnseignants.setVisible(false);
        initEnseignantsPanel();

        panelAffectMatiere = new JPanel();
        panelAffectMatiere.setBackground(Color.lightGray);
        panelAffectMatiere.setBounds(0, 0, 600, 600);
        panelAffectMatiere.setVisible(false);
        initAffectMatierePanel();

        panelAffectEtudiant = new JPanel();
        panelAffectEtudiant.setBackground(Color.lightGray);
        panelAffectEtudiant.setBounds(0, 0, 600, 600);
        panelAffectEtudiant.setVisible(false);
        initAffectEtudiantPanel();

        layeredPane.add(panelEtudiants,      Integer.valueOf(0));
        layeredPane.add(panelEnseignants,    Integer.valueOf(1));
        layeredPane.add(panelAffectMatiere,  Integer.valueOf(2));
        layeredPane.add(panelAffectEtudiant, Integer.valueOf(3));
        this.add(layeredPane);
    }
    private void initEtudiantsPanel() {
        panelEtudiants.setLayout(null);
        String[] columns = {"ID", "Nom", "Prenom", "Moyenne", "Niveau"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEtudiants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEtudiants);
        scrollPane.setBounds(20, 20, 560, 280);
        styleTable(tableEtudiants);
        panelEtudiants.add(scrollPane);
        JTextField txtIdEtu  = new JTextField();
        JTextField txtMoy    = new JTextField();
        JTextField txtNiveau = new JTextField();
        txtIdEtu.setBounds(20,   330, 150, 30);
        txtMoy.setBounds(180,    330, 150, 30);
        txtNiveau.setBounds(340, 330, 150, 30);
        panelEtudiants.add(new JLabel("ID Étudiant") {{ setBounds(20,  310, 150, 20); }});
        panelEtudiants.add(new JLabel("Moyenne")     {{ setBounds(180, 310, 150, 20); }});
        panelEtudiants.add(new JLabel("Niveau")      {{ setBounds(340, 310, 150, 20); }});
        panelEtudiants.add(txtIdEtu);
        panelEtudiants.add(txtMoy);
        panelEtudiants.add(txtNiveau);
        JButton btnAdd    = new JButton("Ajouter");
        JButton btnDelete = new JButton("Supprimer");
        btnAdd.setBounds(20,   375, 150, 35);
        btnDelete.setBounds(180, 375, 150, 35);
        styleButton(btnAdd);
        styleButton(btnDelete);
        btnAdd.addActionListener(e -> {
            try {
                int id_etu    = Integer.parseInt(txtIdEtu.getText().trim());
                double moy    = Double.parseDouble(txtMoy.getText().trim());
                String niveau = txtNiveau.getText().trim();
                if (niveau.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Le niveau ne peut pas être vide.");
                    return;
                }
                if (dao.addEtudiant(id_etu, moy, niveau)) {
                    JOptionPane.showMessageDialog(this, "Étudiant ajouté !");
                    txtIdEtu.setText(""); txtMoy.setText(""); txtNiveau.setText("");
                    updateEtudiantsTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Échec — vérifiez que l'ID existe dans la table person.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID et Moyenne doivent être des nombres valides.");
            }
        });
        btnDelete.addActionListener(e -> {
            int selected = tableEtudiants.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant dans le tableau.");
                return;
            }
            int id_etu = (int) tableEtudiants.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet étudiant ?", "Confirmation", JOptionPane.YES_NO_OPTION);
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
        String[] columns = {"ID", "Nom", "Prenom", "Spécialité"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEnseignants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEnseignants);
        scrollPane.setBounds(20, 20, 560, 280);
        styleTable(tableEnseignants);
        panelEnseignants.add(scrollPane);
        JTextField txtIdProf     = new JTextField();
        JTextField txtSpeciality = new JTextField();
        txtIdProf.setBounds(20,    330, 150, 30);
        txtSpeciality.setBounds(180, 330, 200, 30);
        panelEnseignants.add(new JLabel("ID Enseignant") {{ setBounds(20,  310, 150, 20); }});
        panelEnseignants.add(new JLabel("Spécialité")    {{ setBounds(180, 310, 200, 20); }});
        panelEnseignants.add(txtIdProf);
        panelEnseignants.add(txtSpeciality);
        JButton btnAdd    = new JButton("Ajouter");
        JButton btnDelete = new JButton("Supprimer");
        btnAdd.setBounds(20,   375, 150, 35);
        btnDelete.setBounds(180, 375, 150, 35);
        styleButton(btnAdd);
        styleButton(btnDelete);
        btnAdd.addActionListener(e -> {
            try {
                int id_prof       = Integer.parseInt(txtIdProf.getText().trim());
                String speciality = txtSpeciality.getText().trim();
                if (speciality.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La spécialité ne peut pas être vide.");
                    return;
                }
                if (dao.addEnseignant(id_prof, speciality)) {
                    JOptionPane.showMessageDialog(this, "Enseignant ajouté !");
                    txtIdProf.setText(""); txtSpeciality.setText("");
                    updateEnseignantsTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Échec — vérifiez que l'ID existe dans la table person.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'ID doit être un nombre valide.");
            }
        });
        btnDelete.addActionListener(e -> {
            int selected = tableEnseignants.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un enseignant dans le tableau.");
                return;
            }
            int id_prof = (int) tableEnseignants.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet enseignant ?", "Confirmation", JOptionPane.YES_NO_OPTION);
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
        JComboBox<String> cmbProf = new JComboBox<>();
        cmbProf.setBounds(20, 100, 300, 30);
        for (Object[] row : dao.getEnseignants())
            cmbProf.addItem(row[0] + " — " + row[1] + " " + row[2]);
        panelAffectMatiere.add(cmbProf);
        panelAffectMatiere.add(new JLabel("Matière") {{ setBounds(20, 150, 200, 20); }});
        JComboBox<String> cmbMatiere = new JComboBox<>();
        cmbMatiere.setBounds(20, 170, 300, 30);
        for (Object[] row : dao.getMatieres())
            cmbMatiere.addItem((String) row[0]);
        panelAffectMatiere.add(cmbMatiere);
        JButton btnAffecter = new JButton("Affecter");
        btnAffecter.setBounds(20, 220, 150, 35);
        styleButton(btnAffecter);
        btnAffecter.addActionListener(e -> {
            if (cmbProf.getItemCount() == 0 || cmbMatiere.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Aucun enseignant ou matière disponible.");
                return;
            }
            int id_prof       = Integer.parseInt(((String) cmbProf.getSelectedItem()).split(" — ")[0].trim());
            String nomMatiere = (String) cmbMatiere.getSelectedItem();
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
        JComboBox<String> cmbEtu = new JComboBox<>();
        cmbEtu.setBounds(20, 100, 300, 30);
        for (Object[] row : dao.getEtudiants())
            cmbEtu.addItem(row[0] + " — " + row[1] + " " + row[2]);
        panelAffectEtudiant.add(cmbEtu);
        panelAffectEtudiant.add(new JLabel("Matière") {{ setBounds(20, 150, 200, 20); }});
        JComboBox<String> cmbMatiere = new JComboBox<>();
        cmbMatiere.setBounds(20, 170, 300, 30);
        for (Object[] row : dao.getMatieres())
            cmbMatiere.addItem((String) row[0]);
        panelAffectEtudiant.add(cmbMatiere);
        JButton btnAffecter = new JButton("Affecter");
        btnAffecter.setBounds(20, 220, 150, 35);
        styleButton(btnAffecter);
        btnAffecter.addActionListener(e -> {
            if (cmbEtu.getItemCount() == 0 || cmbMatiere.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Aucun étudiant ou matière disponible.");
                return;
            }
            int id_etu        = Integer.parseInt(((String) cmbEtu.getSelectedItem()).split(" — ")[0].trim());
            String nomMatiere = (String) cmbMatiere.getSelectedItem();
            if (dao.affecterEtudiantMatiere(id_etu, nomMatiere)) {
                JOptionPane.showMessageDialog(this, "Étudiant affecté à « " + nomMatiere + " » !");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Échec — cet étudiant est peut-être déjà inscrit à cette matière.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelAffectEtudiant.add(btnAffecter);
    }
    public void initializeSidePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setBounds(600, 0, 300, 600);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        JButton btn1 = new JButton("Gérer étudiants");
        JButton btn2 = new JButton("Gérer enseignants");
        JButton btn3 = new JButton("Affecter matière");
        JButton btn4 = new JButton("Affecter étudiant");
        JButton btn5 = new JButton("Retour");
        btn1.addActionListener(e -> showOnly(panelEtudiants));
        btn2.addActionListener(e -> showOnly(panelEnseignants));
        btn3.addActionListener(e -> showOnly(panelAffectMatiere));
        btn4.addActionListener(e -> showOnly(panelAffectEtudiant));
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
        setSize(900, 600);
        setResizable(false);
        setLayout(null);
        initializeLayeredPanel();
        initializeSidePanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}