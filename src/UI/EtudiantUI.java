package UI;

import dao.EtudiantDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EtudiantUI extends JFrame {
    private final int id_etd;
    private JPanel panelEtudiant, panelMatieres, panelnotes;
    private JTable tableEtudiants;
    private JTable matiereTable;
    private JTable notesTable;
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
    public void initializeLayeredPanel(int id_etd) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);
        panelEtudiant = new JPanel();
        panelEtudiant.setBackground(Color.lightGray);
        panelEtudiant.setBounds(0, 0, 800, 600);
        panelEtudiant.setVisible(true);
        EnseignantTable();
        panelMatieres = new JPanel();
        panelMatieres.setBackground(Color.lightGray);
        panelMatieres.setBounds(0, 0, 800, 600);
        panelMatieres.setVisible(false);
        Matieretable();
        panelnotes = new JPanel();
        panelnotes.setBackground(Color.lightGray);
        panelnotes.setBounds(0, 0, 800, 600);
        panelnotes.setVisible(false);
        Notestable(id_etd);
        layeredPane.add(panelEtudiant, Integer.valueOf(0));
        layeredPane.add(panelMatieres, Integer.valueOf(1));
        layeredPane.add(panelnotes, Integer.valueOf(2));
        this.add(layeredPane);
    }
    private void Notestable(int id_etd) {
        String[] column = {"Matiere", "Note"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        notesTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(notesTable);
        scrollPane.setBounds(100, 50, 600, 400);
        styleTable(notesTable);
        panelnotes.setLayout(null);
        panelnotes.add(scrollPane);
        updateNoteFromDAO(id_etd);
    }
    private void updateNoteFromDAO(int id_etd) {
        try {
            EtudiantDAO dao = new EtudiantDAO();
            List<Object[]> rows = dao.getNotes(id_etd);

            DefaultTableModel model = (DefaultTableModel) notesTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateTableFromDAO() {
        try {
            EtudiantDAO dao = new EtudiantDAO();
            List<Object[]> rows = dao.getEnseignants(id_etd);

            DefaultTableModel model = (DefaultTableModel) tableEtudiants.getModel();
            model.setRowCount(0);

            for (Object[] row : rows) {
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void updateMatiereFromDAO(int id_etd) {
        try {
            EtudiantDAO dao = new EtudiantDAO();
            List<Object[]> rows = dao.getMatieres(id_etd);

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
    public void Matieretable() {
        String[] column = {"matiere","coefficient"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        matiereTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(matiereTable);
        scrollPane.setBounds(100, 50, 600, 400);
        styleTable(matiereTable);
        panelMatieres.setLayout(null);
        panelMatieres.add(scrollPane);
        updateMatiereFromDAO(id_etd);
    }
    public void EnseignantTable() {
        String[] columns = {"Nom", "Prenom" ,"matiere"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tableEtudiants = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEtudiants);
        scrollPane.setBounds(100, 50, 600, 400);
        styleTable(tableEtudiants);
        panelEtudiant.setLayout(null);
        panelEtudiant.add(scrollPane);
        updateTableFromDAO();
    }
    public void initializeSidePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setBounds(790, 0, 300, 600);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(20, 20)));

        Dimension btnSize = new Dimension(200, 40);

        JButton button = new JButton("consulter enseignants");
        styleButton(button);
        button.setMaximumSize(btnSize);
        button.setPreferredSize(btnSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e->{
            panelEtudiant.setVisible(true);
            panelMatieres.setVisible(false);
            panelnotes.setVisible(false);
        });

        JButton button2 = new JButton("consulter matiéres");
        styleButton(button2);
        button2.setMaximumSize(btnSize);
        button2.setPreferredSize(btnSize);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener(e->{
            panelEtudiant.setVisible(false);
            panelMatieres.setVisible(true);
            panelnotes.setVisible(false);
        });

        JButton button3 = new JButton("consulter notes");
        styleButton(button3);
        button3.setMaximumSize(btnSize);
        button3.setPreferredSize(btnSize);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.addActionListener(e->{
            panelEtudiant.setVisible(false);
            panelMatieres.setVisible(false);
            panelnotes.setVisible(true);
        });

        JButton btnBack = new JButton("Retour");
        styleButton(btnBack);
        btnBack.setMaximumSize(btnSize);
        btnBack.setPreferredSize(btnSize);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> {
            dispose();
            new HomeUI();
        });

        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(button2);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(button3);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnBack);
        add(panel);
    }
    public EtudiantUI(int id_etd){
        this.id_etd = id_etd;
        setTitle("Etudiant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setResizable(false);
        setLayout(null);
        initializeLayeredPanel(id_etd);
        initializeSidePanel();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}