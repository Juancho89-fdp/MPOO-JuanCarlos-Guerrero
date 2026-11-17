package mx.unam.fi.smartvend.application.service;

import mx.unam.fi.smartvend.domain.compra.*;
import mx.unam.fi.smartvend.domain.inventario.*;
import mx.unam.fi.smartvend.domain.pago.*;
import mx.unam.fi.smartvend.domain.producto.*;
import mx.unam.fi.smartvend.domain.pago.MetodoPago;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MaquinaExpendedora {

    private final Inventario inventario;
    private final Monedero monederoEfectivo;
    private final Monedero monederoTarjeta;

    public MaquinaExpendedora(Inventario inventario, 
                              @Qualifier("monederoEfectivo") Monedero efectivo,
                              @Qualifier("monederoTarjeta") Monedero tarjeta) {
        this.inventario = inventario;
        this.monederoEfectivo = efectivo;
        this.monederoTarjeta = tarjeta;
    }

    public ResultadoRegistro registrarProducto(String nombre, String codigo, LocalDate fecha, CategoriaProducto cat, double precio) {
        Producto producto;
        String nombreMinus = nombre.toLowerCase();
        // Tu lógica de la Práctica 9
        if (cat == CategoriaProducto.BEBIDA) {
            if (nombreMinus.contains("agua")) producto = new Agua(codigo, nombre, fecha);
            else producto = new Refresco(codigo, nombre, fecha);
        } else {
            if (nombreMinus.contains("galleta") || nombreMinus.contains("oreo")) producto = new Galletas(codigo, nombre, fecha);
            else producto = new Papas(codigo, nombre, fecha);
        }
        return inventario.registrarProducto(producto, precio);
    }
        
    public java.util.Optional<mx.unam.fi.smartvend.domain.inventario.ProductoInventario> buscarProducto(String codigo) {
        return inventario.buscarProducto(codigo);
    }
    public ResultadoReabastecimiento reabastecer(String codigo, int cantidad) {
        return inventario.reabastecer(codigo, cantidad);
    }

    public ResultadoCompra comprar(String codigoProducto, double cantidadRecibida, MetodoPago metodo) {
        Optional<ProductoInventario> opt = inventario.buscarProducto(codigoProducto);
        if (opt.isEmpty()) return new ResultadoCompra(EstadoCompra.PRODUCTO_NO_ENCONTRADO, null, 0.0);
        
        ProductoInventario prodInv = opt.get();
        if (prodInv.existencias() <= 0) return new ResultadoCompra(EstadoCompra.SIN_EXISTENCIAS, prodInv.producto(), 0.0);
        
        double precio = prodInv.precio();
        Monedero monedero = (metodo == MetodoPago.EFECTIVO) ? monederoEfectivo : monederoTarjeta;
        
        ResultadoCobro resCobro = monedero.cobrar(precio, cantidadRecibida);
        
        if (resCobro.estado() == EstadoCobro.SIN_FONDOS_SUFICIENTES) 
            return new ResultadoCompra(EstadoCompra.SIN_FONDOS_SUFICIENTES, prodInv.producto(), 0.0);
        if (resCobro.estado() == EstadoCobro.MONTO_INVALIDO) 
            return new ResultadoCompra(EstadoCompra.PAGO_INSUFICIENTE, prodInv.producto(), 0.0);
        
        inventario.despachar(codigoProducto);
        return new ResultadoCompra(EstadoCompra.VENTA_REALIZADA, prodInv.producto(), resCobro.cambio());
    }

    public double consultarFondosEfectivo() { return monederoEfectivo.consultarFondos(); }
    public double consultarFondosTarjeta() { return monederoTarjeta.consultarFondos(); }
}