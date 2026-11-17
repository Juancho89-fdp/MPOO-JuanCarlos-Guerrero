import java.util.Random;

public class Batalla {
    public static void main(String[] args) {
        Random random = new Random();

        InfoPropietario dueño1 = new InfoPropietario("Juan Carlos Guerrero", "GUHJ050808HMCRRNA0");
        Bateria bateria1 = new Bateria("LiPo", 2025, "PowerCell");
        Robot robot1 = new Robot("Titan", "Pesado", 40.0, dueño1, bateria1);

        InfoPropietario dueño2 = new InfoPropietario("Ana López", "LOPA000202MDFXXX02");
        Bateria bateria2 = new Bateria("LiIon", 2024, "VoltMax");
        Robot robot2 = new Robot("Fenix", "Ligero", 80.0, dueño2, bateria2);

        InfoPropietario dueño3 = new InfoPropietario("Carlos Ruiz", "RUCA000303HDFXXX03");
        Bateria bateria3 = new Bateria("NiMH", 2023, "EnergyCorp");
        Robot robot3 = new Robot("Cyclone", "Medio", 60.0, dueño3, bateria3);

        System.out.println(Robot.getTotalRobots() + " robots participarán en la batalla.\n");
        System.out.println("--- Estado inicial ---");
        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);
        double distanciaAleatoria = random.nextInt(101);        // 0 a 100
        double velocidadAleatoria = 2.0 + random.nextDouble() * 8.0;

        robot1.avanzar(distanciaAleatoria);
        robot1.cambiarVelocidad(velocidadAleatoria);
        robot2.avanzar(random.nextInt(101));
        robot3.recargarBateria(15);

        System.out.println("\n--- Estado después de la batalla ---");
        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);

        Robot robotFavorito = robot2;
        robotFavorito.cambiarVelocidad(50.0);

        System.out.println("\n--- Prueba de referencias ---");
        System.out.println("robot2       : " + robot2);
        System.out.println("robotFavorito: " + robotFavorito);

        Robot robot2Copia = new Robot("Fenix", "Ligero", 80.0, dueño2, bateria2);
        System.out.println("\n--- Prueba de equals ---");
        System.out.println("robot2.equals(robot2Copia)      -> " + robot2.equals(robot2Copia));
        System.out.println("robot2.equals(robotFavorito)    -> " + robot2.equals(robotFavorito));
    }
}
