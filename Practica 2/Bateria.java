public class Bateria {
    private String tipo;
    private int anioFabricacion;
    private String fabricante;
    private double nivelActual;

    public Bateria(String tipo, int anioFabricacion, String fabricante) {
        this.tipo = tipo;
        this.anioFabricacion = anioFabricacion;
        this.fabricante = fabricante;
        this.nivelActual = 100.0;
    }

    public void consumir(double cantidad) {
        nivelActual -= cantidad;
        if (nivelActual < 0) {
            nivelActual = 0;
        }
    }

    public void recargar(double cantidad) {
        nivelActual += cantidad;
        if (nivelActual > 100) {
            nivelActual = 100;
        }
    }

    public boolean estaCargada() {
        return nivelActual > 0;
    }

    public double getNivelActual() {
        return nivelActual;
    }

    public String getTipo() {
        return tipo;
    }
}