package com.pokepedia.controlador;

import com.pokepedia.dao.PokemonDao;
import com.pokepedia.modelo.Pokemon;
import com.pokepedia.vista.VistaDetallePokemon;
import com.pokepedia.vista.VistaFormularioPokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ControladorPokemon {
    // Atributos
    private final PokemonDao pokemonDao;

    // Constructores
    public ControladorPokemon(PokemonDao pokemonDao) {
        this.pokemonDao = pokemonDao;
    }

    /**
     * Carga todos los pokemones
     * @param filtro - filtro por nombre del pokemon
     * @param onLoaded - callback para cuando se cargan los pokemones
     */
    public void cargarTodos(String filtro, Consumer<List<Pokemon>> onLoaded) {
        List<Pokemon> lista = pokemonDao.buscarTodos(filtro);
        onLoaded.accept(lista);
    }

    /**
     * Muestra el detalle de un pokemon
     * Si el pokemon no existe, muestra un mensaje de error y permanece en la ventana principal
     * Si el pokemon existe, muestra el ventana de detalle con los datos del pokemon
     * @param parent - ventana padre
     * @param pokemonId - id del pokemon
     */
    public void mostrarDetalle(Window parent, int pokemonId) {
        Pokemon p = pokemonDao.buscarPorId(pokemonId);
        if (p == null) {
            JOptionPane.showMessageDialog(parent, "No se encontró el pokémon", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        VistaDetallePokemon dialog = new VistaDetallePokemon(parent, p);
        dialog.setVisible(true);
    }

    /**
     * Muestra el formulario de un pokemon
     * Si el pokemon no existe, aparece la ventana de formulario sin datos
     * Si el pokemon existe, aparece la ventana de formulario con los datos del pokemon
     * Muestra un mensaje de error si no se puede guardar el pokemon y otro si se guarda correctamente
     * @param parent - ventana padre
     * @param pokemon - pokemon a mostrar
     * @param onSaved - callback para cuando se guarda el pokemon
     */
    public void mostrarFormulario(Window parent, Pokemon pokemon, Runnable onSaved) {
        VistaFormularioPokemon dialog = new VistaFormularioPokemon(parent, pokemon);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        Pokemon result = dialog.obtenerResultado();
        if (result != null) {
            boolean ok = result.getId() == 0 ? pokemonDao.insertar(result) : pokemonDao.actualizar(result);
            if (ok) {
                onSaved.run();
                JOptionPane.showMessageDialog(parent, "Guardado correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Elimina un pokemon
     * Muestra un mensaje de confirmación y si se confirma, elimina el pokemon
     * Muestra un mensaje de error si no se puede eliminar el pokemon
     * @param parent - ventana padre
     * @param id - id del pokemon a eliminar
     * @param onDeleted - callback para cuando se elimina el pokemon
     */
    public void eliminar(Window parent, int id, Runnable onDeleted) {
        int opt = JOptionPane.showConfirmDialog(parent, "¿Eliminar este pokémon?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        if (pokemonDao.eliminar(id)) {
            onDeleted.run();
        } else {
            JOptionPane.showMessageDialog(parent, "No se pudo eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

