package com.pokepedia.dao;

import com.pokepedia.config.DatabaseConfig;
import com.pokepedia.modelo.Pokemon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDao {

    /**
     * Retorna una lista de pokemones si se encuentran, de lo contrario retorna una lista vacía
     * Si se encuentra un pokemon, se convierte a un objeto Pokemon y se añade a la lista
     * Si no se encuentra ningún pokemon, se retorna una lista vacía
     * @param filtroNombre - filtro por nombre del pokemon
     * @return List<Pokemon> - lista de pokemones
     * @throws SQLException - si ocurre un error al buscar los pokemones
     */
    public List<Pokemon> buscarTodos(String filtroNombre) {
        List<Pokemon> lista = new ArrayList<>();
        String base = "SELECT id, nombre, tipo1, tipo2, hp, ataque, defensa, region, descripcion FROM pokemon";
        boolean hasFilter = filtroNombre != null && !filtroNombre.trim().isEmpty();
        String sql = hasFilter ? base + " WHERE LOWER(nombre) LIKE ?" : base;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (hasFilter) {
                ps.setString(1, "%" + filtroNombre.toLowerCase() + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Retorna un objeto Pokemon si se encuentra el pokemon, de lo contrario retorna null
     * @param id - id del pokemon
     * @return Pokemon - pokemon encontrado
     * @throws SQLException - si ocurre un error al buscar el pokemon
     */
    public Pokemon buscarPorId(int id) {
        String sql = "SELECT id, nombre, tipo1, tipo2, hp, ataque, defensa, region, descripcion FROM pokemon WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearFila(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta un pokemon en la base de datos
     * @param p - pokemon a insertar
     * @return boolean - true si se insertó el pokemon, false en caso contrario
     * @throws SQLException - si ocurre un error al insertar el pokemon
     */
    public boolean insertar(Pokemon p) {
        String sql = "INSERT INTO pokemon (nombre, tipo1, tipo2, hp, ataque, defensa, region, descripcion) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            rellenarSentencia(ps, p);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un pokemon en la base de datos
     * @param p - pokemon a actualizar
     * @return boolean - true si se actualizó el pokemon, false en caso contrario
     * @throws SQLException - si ocurre un error al actualizar el pokemon
     */
    public boolean actualizar(Pokemon p) {
        String sql = "UPDATE pokemon SET nombre=?, tipo1=?, tipo2=?, hp=?, ataque=?, defensa=?, region=?, descripcion=? WHERE id=?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            rellenarSentencia(ps, p);
            ps.setInt(9, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un pokemon de la base de datos
     * @param id - id del pokemon a eliminar
     * @return boolean - true si se eliminó el pokemon, false en caso contrario
     * @throws SQLException - si ocurre un error al eliminar el pokemon
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pokemon WHERE id=?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Rellena el PreparedStatement con los datos del pokemon
     * @param ps - PreparedStatement a rellenar
     * @param p - pokemon a rellenar
     * @throws SQLException - si ocurre un error al rellenar el PreparedStatement
     */
    private void rellenarSentencia(PreparedStatement ps, Pokemon p) throws SQLException {
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getTipo1());
        ps.setString(3, p.getTipo2());
        ps.setInt(4, p.getHp());
        ps.setInt(5, p.getAtaque());
        ps.setInt(6, p.getDefensa());
        ps.setString(7, p.getRegion());
        ps.setString(8, p.getDescripcion());
    }

    /**
     * Convierte el ResultSet a un objeto Pokemon
     * @param rs - ResultSet a convertir
     * @return Pokemon - pokemon convertido
     * @throws SQLException - si ocurre un error al convertir el ResultSet
     */
    private Pokemon mapearFila(ResultSet rs) throws SQLException {
        return new Pokemon(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("tipo1"),
                rs.getString("tipo2"),
                rs.getInt("hp"),
                rs.getInt("ataque"),
                rs.getInt("defensa"),
                rs.getString("region"),
                rs.getString("descripcion")
        );
    }
}

