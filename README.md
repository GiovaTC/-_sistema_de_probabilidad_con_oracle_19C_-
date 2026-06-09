# -_sistema_de_probabilidad_con_oracle_19C_- :.
# Sistema de Probabilidad con Oracle 19c:

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/d7863e47-e565-464c-9d1f-7831fc847cc3" />  

```

Proyecto desarrollado en **Java 21 + IntelliJ IDEA + Swing + Oracle Database 19c**, donde el programa calcula probabilidades, almacena los resultados en Oracle y permite consultar el historial de cálculos.

---

# Características

- Calcular probabilidad simple.
- Guardar cálculos en Oracle.
- Consultar historial.
- Eliminar registros.
- Interfaz gráfica con Swing.

---

# Estructura del Proyecto

```text
ProbabilidadOracle/

├── src/
│   ├── conexion/
│   │   └── ConexionOracle.java
│   │
│   ├── modelo/
│   │   └── Probabilidad.java
│   │
│   ├── dao/
│   │   └── ProbabilidadDAO.java
│   │
│   ├── vista/
│   │   └── VentanaPrincipal.java
│   │
│   └── Main.java
│
└── ojdbc11.jar
```

---

# Script Oracle 19c

```sql
CREATE TABLE PROBABILIDADES_J (

    ID NUMBER GENERATED ALWAYS AS IDENTITY,

    EVENTOS_FAVORABLES NUMBER NOT NULL,

    EVENTOS_POSIBLES NUMBER NOT NULL,

    RESULTADO NUMBER(10,4),

    FECHA_REGISTRO DATE DEFAULT SYSDATE,

    CONSTRAINT PK_PROBABILIDADES_J
    PRIMARY KEY(ID)

);
```

---

# Fórmula Utilizada

```text
P(A) = Eventos Favorables / Eventos Posibles
```

## Ejemplo

```text
Favorables = 3
Posibles = 10

P(A) = 3 / 10 = 0.30

30%
```

---

# Dependencia Oracle JDBC

## Descargar

```text
ojdbc11.jar
```

Desde:

```text
Oracle JDBC Drivers
```

## Agregar al Proyecto

```text
File
→ Project Structure
→ Libraries
→ Add JAR
```

---

# Clase ConexionOracle.java

```java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER =
            "system";

    private static final String PASSWORD =
            "oracle";

    public static Connection conectar() {

        try {

            Class.forName(
                "oracle.jdbc.driver.OracleDriver"
            );

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println(
                    "Error conexión: "
                    + e.getMessage()
            );

            return null;
        }
    }
}
```

---

# Clase Modelo

## Probabilidad.java

```java
package modelo;

public class Probabilidad {

    private int id;

    private int eventosFavorables;

    private int eventosPosibles;

    private double resultado;

    public Probabilidad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventosFavorables() {
        return eventosFavorables;
    }

    public void setEventosFavorables(int eventosFavorables) {
        this.eventosFavorables = eventosFavorables;
    }

    public int getEventosPosibles() {
        return eventosPosibles;
    }

    public void setEventosPosibles(int eventosPosibles) {
        this.eventosPosibles = eventosPosibles;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }
}
```

---

# DAO

## ProbabilidadDAO.java

```java
package dao;

import conexion.ConexionOracle;
import modelo.Probabilidad;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProbabilidadDAO {

    public boolean guardar(
            Probabilidad p) {

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
```

---

# Interfaz Swing

## VentanaPrincipal.java

```java
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
```

---

# Clase Principal

## Main.java

```java
import vista.VentanaPrincipal;

public class Main {

    public static void main(
            String[] args) {

        new VentanaPrincipal();
    }
}
```

---

# Ejecución

## 1. Crear la tabla en Oracle 19c

Ejecutar el script SQL:

```sql
CREATE TABLE PROBABILIDADES_J (
    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    EVENTOS_FAVORABLES NUMBER NOT NULL,
    EVENTOS_POSIBLES NUMBER NOT NULL,
    RESULTADO NUMBER(10,4),
    FECHA_REGISTRO DATE DEFAULT SYSDATE,
    CONSTRAINT PK_PROBABILIDADES_J
    PRIMARY KEY(ID)
);
```

## 2. Agregar Driver JDBC

```text
ojdbc11.jar
```

al proyecto IntelliJ IDEA.

## 3. Configurar Credenciales

Modificar en:

```java
ConexionOracle.java
```

los parámetros:

```java
private static final String URL =
        "jdbc:oracle:thin:@localhost:1521:XE";

private static final String USER =
        "system";

private static final String PASSWORD =
        "oracle";
```

## 4. Ejecutar

```text
Main.java
```

---

# Resultado Esperado

La aplicación permitirá:

1. Ingresar eventos favorables.
2. Ingresar eventos posibles.
3. Calcular la probabilidad.
4. Mostrar el resultado.
5. Guardar automáticamente en Oracle.

---

# Mejoras Futuras

## Interfaz

- JTable para mostrar historial.
- Búsquedas por fecha.
- Filtros avanzados.
- Diseño moderno con FlatLaf.

## CRUD Completo

- Insertar.
- Consultar.
- Actualizar.
- Eliminar.

## Estadística y Probabilidad

- Probabilidad condicional.
- Distribución binomial.
- Distribución normal.
- Distribución de Poisson.
- Teorema de Bayes.

## Utilidades

- Generador de números aleatorios.
- Exportar a Excel.
- Exportar a PDF.
- Reportes estadísticos.

## Gráficas

- JFreeChart.
- Histogramas.
- Diagramas de barras.
- Curvas de distribución.

---

# Versión Académica Recomendada

Para un proyecto universitario más completo se recomienda implementar:

- Arquitectura MVC.
- Java 21.
- Swing.
- Oracle Database 19c.
- DAO Pattern.
- JTable.
- CRUD Completo.
- Reportes PDF.
- Gráficas estadísticas.
- Validaciones.
- Manejo de excepciones.
- Diseño profesional de escritorio.

Esto permitiría obtener una aplicación similar a un sistema empresarial real conectado a Oracle Database 19c .
:. . / .
