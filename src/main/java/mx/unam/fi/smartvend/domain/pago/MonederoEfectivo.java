package mx.unam.fi.smartvend.domain.pago;

public class MonederoEfectivo implements Monedero {
    private double fondos;
    public MonederoEfectivo(double fondosIniciales) {
        if (fondosIniciales < 0) throw new IllegalArgumentException("Fondos negativos");
        this.fondos = fondosIniciales;
    }
    @Override
    public ResultadoCobro cobrar(double precio, double cantidadRecibida) {
        if (cantidadRecibida < precio) return new ResultadoCobro(EstadoCobro.MONTO_INVALIDO, 0.0);
        double cambio = cantidadRecibida - precio;
        if (this.fondos < cambio) return new ResultadoCobro(EstadoCobro.SIN_FONDOS_SUFICIENTES, 0.0);
        this.fondos += precio;
        return new ResultadoCobro(EstadoCobro.COBRO_REALIZADO, cambio);
    }
    @Override
    public double consultarFondos() { return this.fondos; }
}