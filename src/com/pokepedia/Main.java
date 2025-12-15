package com.pokepedia;

import com.pokepedia.controlador.ControladorAutenticacion;
import com.pokepedia.dao.UsuarioDao;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorAutenticacion controladorAutenticacion = new ControladorAutenticacion(new UsuarioDao());
            controladorAutenticacion.mostrarLogin();
        });
    }
}

