package com.pokepedia.vista;

import com.pokepedia.controlador.ControladorPokemon;
import com.pokepedia.dao.PokemonDao;
import com.pokepedia.modelo.Pokemon;
import com.pokepedia.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.util.List;

public class VistaPrincipal extends JFrame {
    // Atributos
    private final Usuario usuario;
    private final ControladorPokemon controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private final JTextField searchField = new JTextField();
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;

    // Constructor
    public VistaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        this.controller = new ControladorPokemon(new PokemonDao());

        setTitle("Pokepedia - " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        JPanel header = construirCabecera();
        JScrollPane tablePane = construirTabla();
        JPanel actions = construirAcciones();

        JPanel container = new JPanel(new BorderLayout());
        container.add(header, BorderLayout.NORTH);
        container.add(tablePane, BorderLayout.CENTER);
        container.add(actions, BorderLayout.SOUTH);
        setContentPane(container);

        // Botones deshabilitados para usuarios no administradores
        boolean admin = usuario.isAdmin();
        btnNuevo.setEnabled(admin);
        btnEditar.setEnabled(admin);
        btnEliminar.setEnabled(admin);
        configurarFiltroBusqueda();
        cargarDatos();
    }

    /**
     * Construye el encabezado de la ventana
     * @return JPanel - encabezado de la ventana
     */
    private JPanel construirCabecera() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(200, 0, 0));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Pokepedia", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Usuario: " + usuario.getNombre() + " | Rol: " + usuario.getRol());
        userLabel.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel searchLabel = new JLabel("Buscar por nombre:");
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        header.add(searchPanel, BorderLayout.SOUTH);
        return header;
    }

    /**
     * Construye la tabla de pokemones
     * @return JScrollPane - tabla de pokemones
     */
    private JScrollPane construirTabla() {
        String[] columns = {"ID", "Nombre", "Tipo 1", "Tipo 2", "HP", "Ataque", "Defensa", "Región"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 0, 0));
        table.getTableHeader().setBackground(new Color(220, 0, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(table.getFont().deriveFont(Font.BOLD));
        return new JScrollPane(table);
    }

    /**
     * Construye los botones de la ventana
     * @return JPanel - botones de la ventana
     */
    private JPanel construirAcciones() {
        JPanel actions = new JPanel();
        actions.setBackground(Color.WHITE);

        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        JButton btnDetalle = new JButton("Detalle");
        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnSalir = new JButton("Cerrar sesión");

        btnNuevo.addActionListener(e -> controller.mostrarFormulario(this, new Pokemon(), this::cargarDatos));
        btnEditar.addActionListener(e -> manejarEdicion());
        btnEliminar.addActionListener(e -> manejarEliminacion());
        btnDetalle.addActionListener(e -> manejarDetalle());
        btnRefrescar.addActionListener(e -> cargarDatos());
        btnSalir.addActionListener(e -> dispose());

        actions.add(btnNuevo);
        actions.add(btnEditar);
        actions.add(btnEliminar);
        actions.add(btnDetalle);
        actions.add(btnRefrescar);
        actions.add(btnSalir);
        return actions;
    }

    /**
     * Asigna el filtro de búsqueda a la tabla
     */
    private void configurarFiltroBusqueda() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            String text = searchField.getText();
            if (text == null || text.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });
    }

    /**
     * Carga los datos de la tabla
     */
    private void cargarDatos() {
        controller.cargarTodos(null, this::rellenarTabla);
    }

    /**
     * Rellena la tabla con los datos
     * @param data - datos a rellenar
     */
    private void rellenarTabla(List<Pokemon> data) {
        tableModel.setRowCount(0);
        for (Pokemon p : data) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTipo1(),
                    p.getTipo2(),
                    p.getHp(),
                    p.getAtaque(),
                    p.getDefensa(),
                    p.getRegion()
            });
        }
    }

    /**
     * Obtiene el id del pokemon seleccionado
     * @return Integer - id del pokemon seleccionado
     */
    private Integer obtenerIdPokemonSeleccionado() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pokémon primero");
            return null;
        }
        int modelRow = table.convertRowIndexToModel(viewRow);
        return (Integer) tableModel.getValueAt(modelRow, 0);
    }

    /**
     * Maneja el evento de detalle
     */
    private void manejarDetalle() {
        Integer id = obtenerIdPokemonSeleccionado();
        if (id != null) {
            controller.mostrarDetalle(this, id);
        }
    }

    /**
     * Maneja el evento de edición
     */
    private void manejarEdicion() {
        Integer id = obtenerIdPokemonSeleccionado();
        if (id == null) return;
        Pokemon p = new PokemonDao().buscarPorId(id);
        if (p != null) {
            controller.mostrarFormulario(this, p, this::cargarDatos);
        }
    }

    /**
     * Maneja el evento de eliminación
     */
    private void manejarEliminacion() {
        Integer id = obtenerIdPokemonSeleccionado();
        if (id != null) {
            controller.eliminar(this, id, this::cargarDatos);
        }
    }
}

/**
 * Interfaz para manejar el evento de búsqueda
 * Implementa el listener de DocumentListener, lo hago aquí para no tener que
 * repetir el código en cada lugar que lo necesite.
 */
@FunctionalInterface
interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
    void update(javax.swing.event.DocumentEvent e);

    @Override
    default void insertUpdate(javax.swing.event.DocumentEvent e) {
        update(e);
    }

    @Override
    default void removeUpdate(javax.swing.event.DocumentEvent e) {
        update(e);
    }

    @Override
    default void changedUpdate(javax.swing.event.DocumentEvent e) {
        update(e);
    }
}

