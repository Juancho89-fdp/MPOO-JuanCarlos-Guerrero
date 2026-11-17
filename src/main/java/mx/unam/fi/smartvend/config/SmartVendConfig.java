package mx.unam.fi.smartvend.config;

import mx.unam.fi.smartvend.domain.pago.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartVendConfig {
    @Bean(name = "monederoEfectivo")
    public Monedero monederoEfectivo() { return new MonederoEfectivo(500.0); }

    @Bean(name = "monederoTarjeta")
    public Monedero monederoTarjeta() { return new MonederoTarjeta(250.0); }
}