static class PilaDinamica {

    private double[] elementos;
    private int cima;

    public PilaDinamica(int capacidadInicial) {
        elementos = new double[capacidadInicial];
        cima = -1;
    }

    public void push(double valor) {
        if (cima == elementos.length - 1) {
            ampliarCapacidad();
        }
        cima++;
        elementos[cima] = valor;
    }

    public double pop() {
        if (isEmpty()) {
            return Double.NaN;
        }
        double valor = elementos[cima];
        cima--;
        return valor;
    }

    public double peek() {
        if (isEmpty()) {
            return Double.NaN;
        }
        return elementos[cima];
    }

    public boolean isEmpty() {
        return cima == -1;
    }

    public int size() {
        return cima + 1;
    }

    public int capacity() {
        return elementos.length;
    }

    private void ampliarCapacidad() {
        double[] nuevo = new double[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            nuevo[i] = elementos[i];
        }
        elementos = nuevo;
    }
}