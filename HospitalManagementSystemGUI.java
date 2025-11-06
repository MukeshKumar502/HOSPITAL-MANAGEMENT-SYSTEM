import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

// --- Patient Class ---
class Patient {
    private int id;
    private String name;
    private int age;
    private String disease;
    private String doctor;

    public Patient(int id, String name, int age, String disease, String doctor) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.doctor = doctor;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDisease() { return disease; }
    public String getDoctor() { return doctor; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nName: " + name +
               "\nAge: " + age +
               "\nDisease: " + disease +
               "\nDoctor: " + doctor +
               "\n-----------------------------\n";
    }
}

// --- GUI Class ---
public class HospitalManagementSystemGUI extends JFrame {
    private JTextField txtId, txtName, txtAge, txtDisease, txtDoctor;
    private JButton btnAdd, btnView, btnUpdate, btnDelete, btnClear;
    private JTextArea outputArea;
    private LinkedList<Patient> patients = new LinkedList<>();

    public HospitalManagementSystemGUI() {
        setTitle("🏥 Hospital Management System (LinkedList)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 550);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // --- Header ---
        JLabel title = new JLabel("🏥 Hospital Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        add(title, BorderLayout.NORTH);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Patient Information"));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(new JLabel("Patient ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        inputPanel.add(txtAge);

        inputPanel.add(new JLabel("Disease:"));
        txtDisease = new JTextField();
        inputPanel.add(txtDisease);

        inputPanel.add(new JLabel("Doctor:"));
        txtDoctor = new JTextField();
        inputPanel.add(txtDoctor);

        add(inputPanel, BorderLayout.WEST);

        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("➕ Add Patient");
        btnView = new JButton("👁 View Patients");
        btnUpdate = new JButton("✏️ Update Patient");
        btnDelete = new JButton("🗑 Delete Patient");
        btnClear = new JButton("🧹 Clear Fields");

        for (JButton btn : new JButton[]{btnAdd, btnView, btnUpdate, btnDelete, btnClear}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.EAST);

        // --- Output Area ---
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Patient Records"));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // --- Button Listeners ---
        btnAdd.addActionListener(e -> addPatient());
        btnView.addActionListener(e -> viewPatients());
        btnUpdate.addActionListener(e -> updatePatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnClear.addActionListener(e -> clearFields());
    }

    // --- Add Patient ---
    private void addPatient() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int age = Integer.parseInt(txtAge.getText());
            String name = txtName.getText().trim();
            String disease = txtDisease.getText().trim();
            String doctor = txtDoctor.getText().trim();

            for (Patient p : patients) {
                if (p.getId() == id) {
                    outputArea.setText("⚠️ Patient ID already exists!");
                    return;
                }
            }

            patients.add(new Patient(id, name, age, disease, doctor));
            outputArea.setText("✅ Patient added successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            outputArea.setText("❌ Please enter valid numeric values for ID and Age.");
        }
    }

    // --- View Patients ---
    private void viewPatients() {
        if (patients.isEmpty()) {
            outputArea.setText("⚠️ No patients available!");
            return;
        }

        outputArea.setText("");
        for (Patient p : patients) {
            outputArea.append(p.toString());
        }
    }


private void updatePatient() {
    try {
        // Get ID entered by user
        int id = Integer.parseInt(txtId.getText());
        boolean found = false;

        // Loop through LinkedList
        for (Patient p : patients) {
            // Check if ID matches
            if (p.getId() == id) {

                // Update patient data using text fields
                p.setName(txtName.getText());
                p.setAge(Integer.parseInt(txtAge.getText()));
                p.setDisease(txtDisease.getText());
                p.setDoctor(txtDoctor.getText());

                outputArea.setText("✅ Patient updated successfully!");
                found = true;
                break;  // stop the loop once updated
            }
        }

        // If no patient found with given ID
        if (!found)
            outputArea.setText("⚠️ Patient not found!");
    } catch (NumberFormatException e) {
        outputArea.setText("❌ Invalid ID or Age!");
    }
}


    // --- Delete Patient ---
    private void deletePatient() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean removed = patients.removeIf(p -> p.getId() == id);

            if (removed)
                outputArea.setText("✅ Patient deleted successfully!");
            else
                outputArea.setText("⚠️ Patient not found!");
        } catch (NumberFormatException e) {
            outputArea.setText("❌ Invalid ID!");
        }
    }

    // --- Clear Fields ---
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtDisease.setText("");
        txtDoctor.setText("");
    }

    // --- Main Method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalManagementSystemGUI gui = new HospitalManagementSystemGUI();
            gui.setVisible(true);
        });
    }
}
