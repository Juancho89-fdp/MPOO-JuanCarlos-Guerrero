public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private boolean activa;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.activa = true;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public boolean depositar(double cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        this.saldo += cantidad;
        return true;
    }

    public boolean retirar(double cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        if (cantidad > this.saldo) {
            return false;
        }
        this.saldo -= cantidad;
        return true;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numero='" + numeroCuenta + '\'' +
                ", titular='" + titular + '\'' +
                ", saldo=" + saldo +
                ", activa=" + activa +
                '}';
    }
}