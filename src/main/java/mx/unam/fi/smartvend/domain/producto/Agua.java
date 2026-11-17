package mx.unam.fi.smartvend.domain.producto;
import java.time.LocalDate;
public class Agua extends Producto {
    public Agua(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.BEBIDA);
    }
}