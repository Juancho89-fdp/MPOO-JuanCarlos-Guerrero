import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'calcularEstancia' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING tipoVehiculo
     *  2. STRING fechaEntrada
     *  3. STRING horaEntrada
     *  4. STRING fechaSalida
     *  5. STRING horaSalida
     */
    enum TipoVehiculo {
        MOTOCICLETA(15.0, 100.0),
        AUTOMOVIL(25.0, 180.0),
        CAMIONETA(35.0, 250.0),
        ELECTRICO(20.0, 150.0);

        public final double tarifaHora;
        public final double maximo24h;

        TipoVehiculo(double tarifaHora, double maximo24h) {
            this.tarifaHora = tarifaHora;
            this.maximo24h = maximo24h;
        }
    }

    enum TipoEstancia {
        NORMAL, NOCTURNA, FIN_SEMANA, MIXTA
    }

    static class DetalleCobro {
        long horasCobradas;
        double costoBase;
        double costoFinal;
        TipoEstancia tipoEstancia;

        public void aplicarReglas(boolean esFinSemana, boolean esNocturna, boolean esElectrico) {
            this.costoFinal = this.costoBase;

            if (esFinSemana && esNocturna) {
                this.tipoEstancia = TipoEstancia.MIXTA;
            } else if (esFinSemana) {
                this.tipoEstancia = TipoEstancia.FIN_SEMANA;
            } else if (esNocturna) {
                this.tipoEstancia = TipoEstancia.NOCTURNA;
            } else {
                this.tipoEstancia = TipoEstancia.NORMAL;
            }

            if (esFinSemana) {
                this.costoFinal *= 1.20;
            }
            if (esNocturna) {
                this.costoFinal *= 1.15;
            }
            if (esElectrico) {
                this.costoFinal *= 0.90;
            }
        }
    }
    
    public static String calcularEstancia(String tipoVehiculo, String fechaEntrada, String horaEntrada, String fechaSalida, String horaSalida) {
        Calendar entrada = crearCalendar(fechaEntrada, horaEntrada);
        Calendar salida = crearCalendar(fechaSalida, horaSalida);

        if (!salida.after(entrada)) {
            return "INVALID";
        }
        TipoVehiculo vehiculo = TipoVehiculo.valueOf(tipoVehiculo.toUpperCase());
        DetalleCobro detalle = new DetalleCobro();

        long diffMillis = salida.getTimeInMillis() - entrada.getTimeInMillis();
        detalle.horasCobradas = (long) Math.ceil(diffMillis / (1000.0 * 60 * 60)); 

        long horasRestantes = detalle.horasCobradas;
        double baseCalculada = 0.0;

        while (horasRestantes > 0) {
            long horasBloque = Math.min(horasRestantes, 24);
            baseCalculada += Math.min(horasBloque * vehiculo.tarifaHora, vehiculo.maximo24h);
            horasRestantes -= horasBloque;
        }
        detalle.costoBase = baseCalculada;

        boolean esFinSemana = (entrada.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
        entrada.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
        salida.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
        salida.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

        int hEntrada = entrada.get(Calendar.HOUR_OF_DAY);
        int hSalida = salida.get(Calendar.HOUR_OF_DAY);
        boolean fechasDiferentes = (entrada.get(Calendar.YEAR) != salida.get(Calendar.YEAR) || 
        entrada.get(Calendar.DAY_OF_YEAR) != salida.get(Calendar.DAY_OF_YEAR));

        boolean esNocturna = (hEntrada >= 20 || hSalida < 6 || fechasDiferentes);
        boolean esElectrico = (vehiculo == TipoVehiculo.ELECTRICO);
        detalle.aplicarReglas(esFinSemana, esNocturna, esElectrico);
        
        return String.format(Locale.US, "%d %.2f %s", detalle.horasCobradas, detalle.costoFinal, detalle.tipoEstancia.name());
    }

private static Calendar crearCalendar(String fecha,    String hora) {
        String[] partesFecha = fecha.split("/");
        String[] partesHora = hora.split(":");
        Calendar c = Calendar.getInstance();
        c.clear();
        
        int dia = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]) - 1;
        int anio = Integer.parseInt(partesFecha[2]);
        int h = Integer.parseInt(partesHora[0]);
        int m = Integer.parseInt(partesHora[1]);

        c.set(anio, mes, dia, h, m, 0);
        return c;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String outputPath = System.getenv("OUTPUT_PATH");
        BufferedWriter bufferedWriter;
        if (outputPath != null) {
            bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        } else {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        
        String tipoVehiculo = bufferedReader.readLine();

        String fechaEntrada = bufferedReader.readLine();

        String horaEntrada = bufferedReader.readLine();

        String fechaSalida = bufferedReader.readLine();

        String horaSalida = bufferedReader.readLine();

        String result = Result.calcularEstancia(tipoVehiculo, fechaEntrada, horaEntrada, fechaSalida, horaSalida);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}