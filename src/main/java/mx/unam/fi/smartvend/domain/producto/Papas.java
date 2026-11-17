package mx.unam.fi.smartvend.domain.producto;
import java.time.LocalDate;
public class Papas extends Producto {
    public Papas(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.SNACK);
    }
}