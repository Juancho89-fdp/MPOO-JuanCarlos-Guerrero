import java.util.UUID;
import java.util.Objects;

public class Robot {
    private UUID id;
    private String nombre;
    private String categoria;
    private double velocidadMaxima;
    private double velocidadActual;
    private double distanciaRecorrida;
    private InfoPropietario propietario;
    private Bateria bateria;

    private static int totalRobots = 0;

    public Robot(String nombre, String categoria, double velocidadMaxima,
                 InfoPropietario propietario, Bateria bateria) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.categoria = categoria;
        this.velocidadMaxima = velocidadMaxima;
        this.velocidadActual = 0;
        this.distanciaRecorrida = 0;
        this.propietario = propietario;
        this.bateria = bateria;
        totalRobots++;
    }

    public void avanzar(double distancia) {
        if (bateria.estaCargada()) {
            distanciaRecorrida += distancia;
            bateria.consumir(distancia * 0.5);
        }
    }

    public void detenerse() {
        velocidadActual = 0;
    }

    public void cambiarVelocidad(double nuevaVelocidad) {
        if (nuevaVelocidad > velocidadMaxima) {
            velocidadActual = velocidadMaxima;
        } else if (nuevaVelocidad < 0) {
            velocidadActual = 0;
        } else {
            velocidadActual = nuevaVelocidad;
        }
    }

    public void recargarBateria(double cantidad) {
        bateria.recargar(cantidad);
    }

    public boolean estaOperativo() {
        return bateria.estaCargada();
    }

    public static int getTotalRobots() {
        return totalRobots;
    }
        @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", velocidadActual=" + velocidadActual +
                ", distanciaRecorrida=" + distanciaRecorrida +
                ", bateria=" + bateria.getNivelActual() + "%" +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Robot otro = (Robot) obj;
        return id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}