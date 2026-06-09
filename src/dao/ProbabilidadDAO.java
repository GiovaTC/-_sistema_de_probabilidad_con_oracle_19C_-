package dao;

import conexion.ConexionOracle;
import modelo.Probabilidad;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProbabilidadDAO {

    public boolean guardar(Probabilidad p) {

        try {
            Connection con =
                    ConexionOracle.conectar();

            String sql = """
                    INSERT INTO
                    PROBABILIDADES_J
                    (
                        EVENTOS_FAVORABLES,
                        EVENTOS_POSIBLES,
                        RESULTADO
                    )
                    VALUES
                    (
                        ?,
                        ?,
                        ?
                    )
                    """;

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    p.getEventosFavorables());

            ps.setInt(
                    2,
                    p.getEventosPosibles());

            ps.setDouble(
                    3,
                    p.getResultado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}   
