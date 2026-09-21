public class CuentaBancariaService {
    private CuentaBancaria[] cuentas;

    public CuentaBancariaService() {
        cuentas = new CuentaBancaria[5];
        cuentas[0] = new CuentaBancaria("001", "Juan Carlos Guerrero", 1000.0);
        cuentas[1] = new CuentaBancaria("002", "Ana Lopez", 500.0);
        cuentas[2] = new CuentaBancaria("003", "Carlos Ruiz", 750.0);
        cuentas[3] = new CuentaBancaria("004", "Maria Fernandez", 2000.0);
        cuentas[4] = new CuentaBancaria("005", "Pedro Sanchez", 300.0);
    }

    private CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    public boolean depositar(String cuentaDestino, double cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        CuentaBancaria destino = buscarCuenta(cuentaDestino);
        if (destino == null) {
            return false;
        }
        return destino.depositar(cantidad);
    }

    public boolean retirar(String cuentaOrigen, double cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        CuentaBancaria origen = buscarCuenta(cuentaOrigen);
        if (origen == null) {
            return false;
        }
        return origen.retirar(cantidad);
    }

    public boolean transferir(String cuentaOrigen, String cuentaDestino, double cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        if (cuentaOrigen.equals(cuentaDestino)) {
            return false;
        }
        CuentaBancaria origen = buscarCuenta(cuentaOrigen);
        CuentaBancaria destino = buscarCuenta(cuentaDestino);
        if (origen == null || destino == null) {
            return false;
        }
        if (cantidad > origen.getSaldo()) {
            return false;
        }
        boolean retiroExitoso = origen.retirar(cantidad);
        if (!retiroExitoso) {
            return false;
        }
        boolean depositoExitoso = destino.depositar(cantidad);
        if (!depositoExitoso) {
            origen.depositar(cantidad); // revierte si el depósito falla
            return false;
        }
        return true;
    }

    public void mostrarCuentas() {
        for (int i = 0; i < cuentas.length; i++) {
            System.out.println(cuentas[i]);
        }
    }
}