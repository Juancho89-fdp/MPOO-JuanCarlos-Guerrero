package mx.unam.fi.smartvend.api.controller;

import mx.unam.fi.smartvend.api.dto.ProductoRequestDTO;
import mx.unam.fi.smartvend.application.service.MaquinaExpendedora;
import mx.unam.fi.smartvend.domain.compra.ResultadoCompra;
import mx.unam.fi.smartvend.domain.inventario.*;
import mx.unam.fi.smartvend.domain.pago.MetodoPago;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SmartVendController {

    private final MaquinaExpendedora maquina;

    public SmartVendController(MaquinaExpendedora maquina) {
        this.maquina = maquina;
    }

    @PostMapping("/productos")
    public ResponseEntity<?> registrar(@RequestBody ProductoRequestDTO dto) {
        var res = maquina.registrarProducto(dto.nombre(), dto.codigo(), dto.fechaCaducidad(), dto.categoria(), dto.precio());
        if (res == ResultadoRegistro.PRODUCTO_REGISTRADO) return ResponseEntity.status(201).body(Map.of("mensaje", "Registrado"));
        return ResponseEntity.badRequest().body(Map.of("error", res.toString()));
    }

    @GetMapping("/productos/{codigo}")
    public ResponseEntity<?> buscar(@PathVariable String codigo) {
        return maquina.buscarProducto(codigo) // Nota: Asegúrate de crear este método en MaquinaExpendedora
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "No encontrado")));
    }

    @PatchMapping("/productos/{codigo}/existencias")
    public ResponseEntity<?> reabastecer(@PathVariable String codigo, @RequestBody Map<String, Integer> body) {
        var res = maquina.reabastecer(codigo, body.get("cantidad"));
        if (res == ResultadoReabastecimiento.REABASTECIMIENTO_REALIZADO) return ResponseEntity.ok(Map.of("mensaje", "Ok"));
        return ResponseEntity.badRequest().body(Map.of("error", res.toString()));
    }

    @PostMapping("/producto/{codigo}/comprar")
    public ResponseEntity<?> comprar(@PathVariable String codigo, @RequestBody Map<String, Object> body) {
        double recibido = Double.parseDouble(body.get("cantidadRecibida").toString());
        MetodoPago metodo = MetodoPago.valueOf(body.get("metodoPago").toString());
        ResultadoCompra res = maquina.comprar(codigo, recibido, metodo);
        
        if (res.estado().name().equals("VENTA_REALIZADA")) {
            return ResponseEntity.ok(Map.of("mensaje", "Compra exitosa", "cambio", res.cambio(), "producto", res.producto().getNombre()));
        }
        return ResponseEntity.status(402).body(Map.of("error", res.estado().toString()));
    }

    @GetMapping("/fondos")
    public ResponseEntity<?> fondos() {
        double ef = maquina.consultarFondosEfectivo();
        double tar = maquina.consultarFondosTarjeta();
        return ResponseEntity.ok(Map.of("efectivo", ef, "tarjeta", tar, "total", ef + tar));
    }
}