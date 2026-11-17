package mx.unam.fi.smartvend.infrastructure.inventario;

import mx.unam.fi.smartvend.domain.inventario.*;
import mx.unam.fi.smartvend.domain.producto.Producto;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InventarioImpl implements Inventario {
    
    // Clase interna para manejar el estado mutable
    private static class Registro {
        Producto producto;
        double precio;
        int existencias;
        Registro(Producto p, double pr, int ex) { this.producto = p; this.precio = pr; this.existencias = ex; }
    }

    private final Map<String, Registro> catalogo = new HashMap<>();

    @Override
    public ResultadoRegistro registrarProducto(Producto producto, double precio) {
        if (producto == null || precio <= 0) return ResultadoRegistro.PRECIO_INVALIDO;
        if (catalogo.containsKey(producto.getCodigo())) return ResultadoRegistro.CODIGO_DUPLICADO;
        catalogo.put(producto.getCodigo(), new Registro(producto, precio, 0));
        return ResultadoRegistro.PRODUCTO_REGISTRADO;
    }

    @Override
    public Optional<ProductoInventario> buscarProducto(String codigo) {
        Registro r = catalogo.get(codigo);
        if (r == null) return Optional.empty();
        return Optional.of(new ProductoInventario(r.producto, r.precio, r.existencias));
    }

    @Override
    public ResultadoReabastecimiento reabastecer(String codigo, int cantidad) {
        if (cantidad <= 0) return ResultadoReabastecimiento.CANTIDAD_INVALIDA;
        Registro r = catalogo.get(codigo);
        if (r == null) return ResultadoReabastecimiento.PRODUCTO_NO_ENCONTRADO;
        r.existencias += cantidad;
        return ResultadoReabastecimiento.REABASTECIMIENTO_REALIZADO;
    }

    @Override
    public ResultadoEntrega despachar(String codigo) {
        Registro r = catalogo.get(codigo);
        if (r == null) return ResultadoEntrega.PRODUCTO_NO_ENCONTRADO;
        if (r.existencias <= 0) return ResultadoEntrega.SIN_EXISTENCIAS;
        r.existencias--;
        return ResultadoEntrega.PRODUCTO_PREPARADO;
    }
}