package com.pokepedia.vista;

import javax.swing.*;

import com.pokepedia.modelo.Pokemon;

import java.awt.*;

public class VistaFormularioPokemon extends JDialog {
    // Atributos
    private final JTextField nombreField = new JTextField(20);
    private final JTextField tipo1Field = new JTextField(10);
    private final JTextField tipo2Field = new JTextField(10);
    private final JSpinner hpSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private final JSpinner ataqueSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private final JSpinner defensaSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private final JTextField regionField = new JTextField(10);
    private final JTextArea descripcionArea = new JTextArea(4, 20);

    private Pokemon result;
    
    public VistaFormularioPokemon(Window owner, Pokemon pokemon) {
        super(owner, "Pokémon", ModalityType.APPLICATION_MODAL);
        setSize(450, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        agregarFila(form, gbc, row++, "Nombre:", nombreField);
        agregarFila(form, gbc, row++, "Tipo 1:", tipo1Field);
        agregarFila(form, gbc, row++, "Tipo 2:", tipo2Field);
        agregarFila(form, gbc, row++, "HP:", hpSpinner);
        agregarFila(form, gbc, row++, "Ataque:", ataqueSpinner);
        agregarFila(form, gbc, row++, "Defensa:", defensaSpinner);
        agregarFila(form, gbc, row++, "Región:", regionField);

        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        JScrollPane descPane = new JScrollPane(descripcionArea);
        agregarFila(form, gbc, row, "Descripción:", descPane);

        JPanel actions = new JPanel();
        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");
        actions.add(guardar);
        actions.add(cancelar);

        guardar.addActionListener(e -> guardarPokemon(pokemon));
        cancelar.addActionListener(e -> dispose());

        cargarPokemon(pokemon);

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    /**
     * Añade una fila al formulario
     * @param panel - panel del formulario
     * @param gbc - gridBagConstraints
     * @param y - fila
     * @param label - label de la fila
     * @param field - campo de la fila
     */
    private void agregarFila(JPanel panel, GridBagConstraints gbc, int y, String label, Component field) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = y;
        panel.add(field, gbc);
    }

    /**
     * Carga los datos del pokemon en el formulario
     * @param p - pokemon a cargar
     */
    private void cargarPokemon(Pokemon p) {
        if (p == null) return;
        nombreField.setText(p.getNombre());
        tipo1Field.setText(p.getTipo1());
        tipo2Field.setText(p.getTipo2());
        hpSpinner.setValue(p.getHp() == 0 ? 1 : p.getHp());
        ataqueSpinner.setValue(p.getAtaque() == 0 ? 1 : p.getAtaque());
        defensaSpinner.setValue(p.getDefensa() == 0 ? 1 : p.getDefensa());
        regionField.setText(p.getRegion());
        descripcionArea.setText(p.getDescripcion());
    }

    /**
     * Guarda los datos del pokemon en el formulario
     * @param original - pokemon original
     */
    private void guardarPokemon(Pokemon original) {
        if (nombreField.getText().trim().isEmpty() || tipo1Field.getText().trim().isEmpty() || regionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, Tipo1 y Región son obligatorios");
            return;
        }
        Pokemon p = original == null ? new Pokemon() : original;
        p.setNombre(nombreField.getText().trim());
        p.setTipo1(tipo1Field.getText().trim());
        p.setTipo2(tipo2Field.getText().trim().isEmpty() ? null : tipo2Field.getText().trim());
        p.setHp((Integer) hpSpinner.getValue());
        p.setAtaque((Integer) ataqueSpinner.getValue());
        p.setDefensa((Integer) defensaSpinner.getValue());
        p.setRegion(regionField.getText().trim());
        p.setDescripcion(descripcionArea.getText().trim());

        result = p;
        dispose();
    }

    public Pokemon obtenerResultado() {
        return result;
    }
}

