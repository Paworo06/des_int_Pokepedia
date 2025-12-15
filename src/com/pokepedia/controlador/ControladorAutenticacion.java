package com.pokepedia.controlador;

import com.pokepedia.dao.UsuarioDao;
import com.pokepedia.modelo.Usuario;
import com.pokepedia.vista.VistaLogin;
import com.pokepedia.vista.VistaPrincipal;

import javax.swing.*;

public class ControladorAutenticacion {
    // Atributos
    private final UsuarioDao usuarioDao;

    // Constructor
    public ControladorAutenticacion(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    /**
     * Muestra la ventana de login
     */
    public void mostrarLogin() {
        SwingUtilities.invokeLater(() -> {
            VistaLogin login = new VistaLogin(this);
            login.setVisible(true);
        });
    }

    /**
     * Autentica al usuario
     * Si el usuario no existe, muestra un mensaje de error y permanece en la ventana de login
     * Si el usuario existe, cierra la ventana de login y muestra la ventana principal
     * @param email - email del usuario
     * @param password - contraseña del usuario
     * @param loginFrame - ventana de login
     */
    public void autenticar(String email, String password, JFrame loginFrame) {
        Usuario usuario = usuarioDao.buscarPorEmailYContrasena(email, password);
        if (usuario == null) {
            JOptionPane.showMessageDialog(loginFrame, "Correo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loginFrame.dispose();
        abrirPrincipal(usuario);
    }

    /**
     * Muestra la ventana principal con el usuario autenticado
     * @param usuario - usuario autenticado
     */
    private void abrirPrincipal(Usuario usuario) {
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal main = new VistaPrincipal(usuario);
            main.setVisible(true);
        });
    }
}

