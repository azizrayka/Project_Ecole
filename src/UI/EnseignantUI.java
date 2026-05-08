package UI;

import dao.EnseignantDAO;
import module.Enseignant;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnseignantUI extends JFrame {
    private JPanel panelEtudiant, panelMatieres, panelnotes;
    private JTable tableEtudiants;
    private JTable matiereTable;
    private final int id_prof;
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
    public void updateTableFromDAO() {
        try {
            EnseignantDAO dao = new EnseignantDAO();
            List<Object[]> rows = dao.getAll();

            DefaultTableModel model = (DefaultTableModel) tableEtudiants.getModel();
            model.setRowCount(0);

            for (Object[] row : rows) {
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void Etudiantable() {
        String[] columns = { "id", "Nom", "Prenom", "Niveau", "matiere", "note" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEtudiants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEtudiants);
        scrollPane.setBounds(20, 50, 600, 400);
        styleTable(tableEtudiants);
        panelEtudiant.setLayout(null);
        panelEtudiant.add(scrollPane);
        updateTableFromDAO();
    }
    public void Matieretable() {
        String[] column = {"matieres"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        matiereTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(matiereTable);
        scrollPane.setBounds(20, 50, 600, 400);
        styleTable(matiereTable);
        panelMatieres.setLayout(null);
        panelMatieres.add(scrollPane);
        updateMatiereFromDAO(id_prof);
    }
    private void updateMatiereFromDAO(int id_prof) {
        try {
            EnseignantDAO dao = new EnseignantDAO();
            List<Object[]> rows = dao.getMatiere(id_prof);

            DefaultTableModel model = (DefaultTableModel) matiereTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            System.out.println("Matieres loaded: " + rows.size());
        } catch (Exception e) {
            System.out.println("updateMatiereFromDAO error: " + e.getMessage());
        }
    }
    public void initializeSidePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setBounds(790, 0, 300, 600);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(20, 20)));

        Dimension btnSize = new Dimension(200, 40);

        JButton button = new JButton("consulter Etudiants");
        button.setMaximumSize(btnSize);
        button.setPreferredSize(btnSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e->{
            panelEtudiant.setVisible(true);
            panelMatieres.setVisible(false);
            panelnotes.setVisible(false);
        });
        JButton button2 = new JButton("consulter matiéres");
        button2.setMaximumSize(btnSize);
        button2.setPreferredSize(btnSize);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener(e->{
            panelEtudiant.setVisible(false);
            panelMatieres.setVisible(true);
            panelnotes.setVisible(false);
        });
        JButton button3 = new JButton("gerer notes");
        button3.setMaximumSize(btnSize);
        button3.setPreferredSize(btnSize);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.addActionListener(e->{
            panelEtudiant.setVisible(false);
            panelMatieres.setVisible(false);
            panelnotes.setVisible(true);
        });
        JButton btnBack = new JButton("Retour");
        btnBack.setMaximumSize(btnSize);
        btnBack.setPreferredSize(btnSize);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setBounds(270, 440, 200, 40);
        styleButton(btnBack);
        btnBack.addActionListener(e -> {
            dispose();
            new HomeUI();
        });
        styleButton(button);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        styleButton(button2);
        panel.add(button2);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        styleButton(button3);
        panel.add(button3);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        styleButton(btnBack);
        panel.add(btnBack);
        add(panel);
    }
    public void initializeLayeredPanel(int id_prof) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);

        panelEtudiant = new JPanel();
        panelEtudiant.setBackground(Color.lightGray);
        panelEtudiant.setBounds(0, 0, 800, 600);
        panelEtudiant.setVisible(true);
        Etudiantable();

        panelMatieres = new JPanel();
        panelMatieres.setBackground(Color.lightGray);
        panelMatieres.setBounds(0, 0, 800, 600);
        panelMatieres.setVisible(false);
        Matieretable();

        panelnotes = new JPanel();
        panelnotes.setBackground(Color.lightGray);
        panelnotes.setBounds(0, 0, 800, 600);
        panelnotes.setVisible(false);
        Notespanel(id_prof);

        layeredPane.add(panelEtudiant, Integer.valueOf(0));
        layeredPane.add(panelMatieres, Integer.valueOf(1));
        layeredPane.add(panelnotes, Integer.valueOf(2));
        this.add(layeredPane);
    }
    private void Notespanel(int id_prof) {
        panelnotes.setLayout(null);
        JPanel form = new JPanel();
        form.setBackground(Color.white);
        form.setBounds(20, 20, 600, 150);
        form.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtEtudiantId = new JTextField();
        JTextField txtMatiere = new JTextField();
        JTextField txtNote = new JTextField();

        form.add(new JLabel("ID Etudiant:"));
        form.add(txtEtudiantId);
        form.add(new JLabel("Matière:"));
        form.add(txtMatiere);
        form.add(new JLabel("Note:"));
        form.add(txtNote);

        JPanel actions = new JPanel();
        actions.setBackground(Color.white);
        actions.setBounds(20, 180, 460, 50);
        actions.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAdd = new JButton("Ajouter");
        JButton btnUpdate = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");

        styleButton(btnAdd);
        styleButton(btnUpdate);
        styleButton(btnDelete);

        actions.add(btnAdd);
        actions.add(btnUpdate);
        actions.add(btnDelete);

        panelnotes.add(form);
        panelnotes.add(actions);

        btnAdd.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtEtudiantId.getText());
                String matiere = txtMatiere.getText();
                double note = Double.parseDouble(txtNote.getText());
                Enseignant eng = new Enseignant(id, matiere, note, id_prof);
                EnseignantDAO dao = new EnseignantDAO();
                if(dao.add(eng)) {
                    JOptionPane.showMessageDialog(this, "Note ajoutée !");
                    txtEtudiantId.setText("");
                    txtMatiere.setText("");
                    txtNote.setText("");
                    updateTableFromDAO();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.");
            }
        });
        btnUpdate.addActionListener(e -> {
            try{
                int id = Integer.parseInt(txtEtudiantId.getText());
                String matiere = txtMatiere.getText();
                double note = Double.parseDouble(txtNote.getText());
                Enseignant eng = new Enseignant(id, matiere, note, id_prof);
                EnseignantDAO dao = new EnseignantDAO();
                if(dao.update(eng)) {
                    JOptionPane.showMessageDialog(this, "Note updated !");
                    txtEtudiantId.setText("");
                    txtMatiere.setText("");
                    txtNote.setText("");
                    updateTableFromDAO();
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.");
            }
        });
        btnDelete.addActionListener(e -> {
            try{
                int id = Integer.parseInt(txtEtudiantId.getText());
                String matiere = txtMatiere.getText();
                Enseignant eng = new Enseignant(id, matiere, 0, id_prof);
                EnseignantDAO dao = new EnseignantDAO();
                if(dao.delete(eng)) {
                    JOptionPane.showMessageDialog(this, "Note deleted !");
                    txtEtudiantId.setText("");
                    txtMatiere.setText("");
                    txtNote.setText("");
                    updateTableFromDAO();
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.");
            }
        });
    }
    public EnseignantUI(int id_prof) {
        this.id_prof = id_prof;
        setTitle("Enseignant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setResizable(false);
        setLayout(null);
        initializeLayeredPanel(id_prof);
        initializeSidePanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
