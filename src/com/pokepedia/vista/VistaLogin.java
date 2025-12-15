package com.pokepedia.vista;

import javax.swing.*;

import com.pokepedia.controlador.ControladorAutenticacion;

import java.awt.*;

public class VistaLogin extends JFrame {
    // Atributos
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final ControladorAutenticacion controller;

    // Constructor
    public VistaLogin(ControladorAutenticacion controller) {
        this.controller = controller;
        inicializarInterfaz();
    }

    /**
     * Inicializa la interfaz de usuario del login
     */
    private void inicializarInterfaz() {
        setTitle("Pokepedia - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 220);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(200, 0, 0));

        JLabel title = new JLabel("POKEPEDIA", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        container.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        JButton loginBtn = new JButton("Entrar");
        loginBtn.setBackground(new Color(220, 0, 0));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(e -> manejarLogin());

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(loginBtn, gbc);

        container.add(form, BorderLayout.CENTER);
        setContentPane(container);
    }

    /**
     * Maneja el evento de login
     */
    private void manejarLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        controller.autenticar(email, password, this);
    }
}

