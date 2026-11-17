package mx.unam.fi.smartvend.domain.pago;

public class MonederoTarjeta implements Monedero {
    private double fondos;
    public MonederoTarjeta(double fondosIniciales) {
        if (fondosIniciales < 0) throw new IllegalArgumentException("Fondos negativos");
        this.fondos = fondosIniciales;
    }
    @Override
    public ResultadoCobro cobrar(double precio, double cantidadRecibida) {
        if (cantidadRecibida < precio) return new ResultadoCobro(EstadoCobro.MONTO_INVALIDO, 0.0);
        // Tarjeta no genera cambio, se cobra exacto el precio
        this.fondos += precio;
        return new ResultadoCobro(EstadoCobro.COBRO_REALIZADO, 0.0);
    }
    @Override
    public double consultarFondos() { return this.fondos; }
}