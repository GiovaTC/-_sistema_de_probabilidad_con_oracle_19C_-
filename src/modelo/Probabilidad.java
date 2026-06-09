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
