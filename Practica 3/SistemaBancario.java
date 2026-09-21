import java.util.Scanner;

public class SistemaBancario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CuentaBancariaService service = new CuentaBancariaService();
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n         SISTEMA BANCARIO         ");
            System.out.println("1. Transferir dinero");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Salir");
            System.out.print("\nSeleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Cuenta origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Cuenta destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("Cantidad: ");
                    double cantidadTransfer = Double.parseDouble(scanner.nextLine());
                    boolean exitoTransfer = service.transferir(origen, destino, cantidadTransfer);
                    System.out.println(exitoTransfer ? "Transferencia exitosa." : "No se pudo realizar la transferencia.");
                    break;

                case 2:
                    System.out.print("Cuenta destino: ");
                    String cuentaDeposito = scanner.nextLine();
                    System.out.print("Cantidad: ");
                    double cantidadDeposito = Double.parseDouble(scanner.nextLine());
                    boolean exitoDeposito = service.depositar(cuentaDeposito, cantidadDeposito);
                    System.out.println(exitoDeposito ? "Deposito exitoso." : "No se pudo realizar el deposito.");
                    break;

                case 3:
                    System.out.print("Cuenta origen: ");
                    String cuentaRetiro = scanner.nextLine();
                    System.out.print("Cantidad: ");
                    double cantidadRetiro = Double.parseDouble(scanner.nextLine());
                    boolean exitoRetiro = service.retirar(cuentaRetiro, cantidadRetiro);
                    System.out.println(exitoRetiro ? "Retiro exitoso." : "No se pudo realizar el retiro.");
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

            if (opcion != 4) {
                service.mostrarCuentas();
            }
        }

        scanner.close();
    }
}