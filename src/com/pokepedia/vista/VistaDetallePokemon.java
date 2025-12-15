package com.pokepedia.vista;

import javax.swing.*;

import com.pokepedia.modelo.Pokemon;

import java.awt.*;

public class VistaDetallePokemon extends JDialog {

    // Constructor
    public VistaDetallePokemon(Window owner, Pokemon pokemon) {
        super(owner, "Detalle de " + pokemon.getNombre(), ModalityType.APPLICATION_MODAL);
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        agregarFila(panel, gbc, row++, "ID:", String.valueOf(pokemon.getId()));
        agregarFila(panel, gbc, row++, "Nombre:", pokemon.getNombre());
        agregarFila(panel, gbc, row++, "Tipo 1:", pokemon.getTipo1());
        agregarFila(panel, gbc, row++, "Tipo 2:", pokemon.getTipo2());
        agregarFila(panel, gbc, row++, "HP:", String.valueOf(pokemon.getHp()));
        agregarFila(panel, gbc, row++, "Ataque:", String.valueOf(pokemon.getAtaque()));
        agregarFila(panel, gbc, row++, "Defensa:", String.valueOf(pokemon.getDefensa()));
        agregarFila(panel, gbc, row++, "Región:", pokemon.getRegion());

        JTextArea descArea = new JTextArea(pokemon.getDescripcion());
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createTitledBorder("Descripción"));

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(descArea), BorderLayout.CENTER);
    }

    /**
     * Añade una fila al panel
     * @param panel - panel
     * @param gbc - gridBagConstraints
     * @param y - fila
     * @param label - label de la fila
     * @param value - valor de la fila
     */
    private void agregarFila(JPanel panel, GridBagConstraints gbc, int y, String label, String value) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(value == null ? "-" : value), gbc);
    }
}

