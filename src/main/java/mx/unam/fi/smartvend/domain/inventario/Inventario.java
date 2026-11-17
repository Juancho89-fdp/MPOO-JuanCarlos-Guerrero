package mx.unam.fi.smartvend.domain.inventario;
import mx.unam.fi.smartvend.domain.producto.Producto;
import java.util.Optional;

public interface Inventario {
    ResultadoRegistro registrarProducto(Producto producto, double precio);
    Optional<ProductoInventario> buscarProducto(String codigo);
    ResultadoReabastecimiento reabastecer(String codigo, int cantidad);
    ResultadoEntrega despachar(String codigo);
}