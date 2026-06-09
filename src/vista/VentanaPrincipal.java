package vista;

import dao.ProbabilidadDAO;
import modelo.Probabilidad;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal
        extends JFrame {

    JTextField txtFavorables;
    JTextField txtPosibles;

    JLabel lblResultado;

    JButton btnCalcular;

    public VentanaPrincipal() {

        setTitle(
                "Probabilidad Oracle 19c");

        setSize(500,300);

        setDefaultCloseOperation(
                EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(4,2));

        add(new JLabel(
                "Eventos Favorables"));

        txtFavorables =
                new JTextField();

        add(txtFavorables);

        add(new JLabel(
                "Eventos Posibles"));

        txtPosibles =
                new JTextField();

        add(txtPosibles);

        btnCalcular =
                new JButton(
                        "Calcular y Guardar");

        add(btnCalcular);

        lblResultado =
                new JLabel(
                        "Resultado:");

        add(lblResultado);

        btnCalcular.addActionListener(
                e -> calcular());

        setVisible(true);
    }

    private void calcular() {

        try {

            int favorables =
                    Integer.parseInt(
                            txtFavorables.getText());

            int posibles =
                    Integer.parseInt(
                            txtPosibles.getText());

            double probabilidad =
                    (double) favorables /
                            posibles;

            lblResultado.setText(
                    "Resultado = "
                            + probabilidad);

            Probabilidad p =
                    new Probabilidad();

            p.setEventosFavorables(
                    favorables);

            p.setEventosPosibles(
                    posibles);

            p.setResultado(
                    probabilidad);

            ProbabilidadDAO dao =
                    new ProbabilidadDAO();

            dao.guardar(p);

            JOptionPane.showMessageDialog(
                    this,
                    "Registro Guardado");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Datos inválidos");
        }
    }
}   