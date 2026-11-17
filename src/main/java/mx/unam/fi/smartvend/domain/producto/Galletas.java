package mx.unam.fi.smartvend.domain.producto;
import java.time.LocalDate;
public class Galletas extends Producto {
    public Galletas(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.SNACK);
    }
}