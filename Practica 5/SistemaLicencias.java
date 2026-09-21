import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'evaluarLicencia' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING fechaActual
     *  2. STRING fechaVencimiento
     *  3. STRING tipoLicencia
     *  4. INTEGER renovacionesPrevias
     */
enum TipoLicencia {
        BASICA(1000.0, 1, Calendar.YEAR),
        PROFESIONAL(1500.0, 2, Calendar.YEAR),
        EMPRESARIAL(2500.0, 3, Calendar.YEAR),
        TEMPORAL(600.0, 6, Calendar.MONTH);

        public final double costoBase;
        public final int duracion;
        public final int unidadTiempo;

        TipoLicencia(double costoBase, int duracion, int unidadTiempo) {
            this.costoBase = costoBase;
            this.duracion = duracion;
            this.unidadTiempo = unidadTiempo;
        }
    }

    enum EstadoLicencia {
        VIGENTE, PROXIMA_A_VENCER, VENCIDA, BLOQUEADA
    }

    static class DetalleRenovacion {
        long diferenciaDias;
        EstadoLicencia estado;
        double costoBase;
        double costoFinal;
        String nuevaFechaStr;

        public void aplicarReglas(Calendar actual, Calendar vencimiento, TipoLicencia licencia, int renovacionesPrevias) {
            if (this.diferenciaDias > 30) {
                this.estado = EstadoLicencia.VIGENTE;
            } else if (this.diferenciaDias >= 0) {
                this.estado = EstadoLicencia.PROXIMA_A_VENCER;
            } else if (this.diferenciaDias >= -90) {
                this.estado = EstadoLicencia.VENCIDA;
            } else {
                this.estado = EstadoLicencia.BLOQUEADA;
            }

            if (this.estado == EstadoLicencia.BLOQUEADA) {
                this.costoFinal = 0.0;
                this.nuevaFechaStr = "NO_DISPONIBLE";
                return;
            }

            this.costoFinal = this.costoBase;
            Calendar baseNuevaVigencia = vencimiento;

            switch (this.estado) {
                case VIGENTE:
                    this.costoFinal *= 0.90;
                    break;
                case PROXIMA_A_VENCER:
                    break;
                case VENCIDA:
                    this.costoFinal *= 1.20;
                    baseNuevaVigencia = actual;
                    break;
                default:
                    break;
            }

            if (renovacionesPrevias > 3) {
                this.costoFinal *= 0.95;
            }

            Calendar nuevaVigencia = (Calendar) baseNuevaVigencia.clone();
            nuevaVigencia.add(licencia.unidadTiempo, licencia.duracion); 
            
            this.nuevaFechaStr = String.format("%02d/%02d/%d", 
                nuevaVigencia.get(Calendar.DAY_OF_MONTH), 
                nuevaVigencia.get(Calendar.MONTH) + 1, 
                nuevaVigencia.get(Calendar.YEAR));
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "%s %d %.2f %s", estado.name(), diferenciaDias, costoFinal, nuevaFechaStr);
        }
    }
    
    public static String evaluarLicencia(String fechaActual, String fechaVencimiento, String tipoLicencia, int renovacionesPrevias) {
        Calendar actual = crearCalendar(fechaActual);
        Calendar vencimiento = crearCalendar(fechaVencimiento);
        TipoLicencia licencia = TipoLicencia.valueOf(tipoLicencia.toUpperCase());

        DetalleRenovacion detalle = new DetalleRenovacion();
        detalle.costoBase = licencia.costoBase; 

        long diffMillis = vencimiento.getTimeInMillis() - actual.getTimeInMillis();
        detalle.diferenciaDias = diffMillis / (1000L * 60 * 60 * 24);

        detalle.aplicarReglas(actual, vencimiento, licencia, renovacionesPrevias);

        return detalle.toString();
    }

private static Calendar crearCalendar(String fecha) {
        String[] partes = fecha.split("/");
        Calendar c = Calendar.getInstance();
        c.clear();
        
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]) - 1;
        int anio = Integer.parseInt(partes[2]);

        c.set(anio, mes, dia);
        return c;
    }
}

public class Solution {

    public static void main(String[] args)
            throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(System.in)
                );
                
        String outputPath = System.getenv("OUTPUT_PATH");
        BufferedWriter bufferedWriter;
        if (outputPath != null) {
            bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        } else {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        String fechaActual =
                bufferedReader.readLine();

        String fechaVencimiento =
                bufferedReader.readLine();

        String tipoLicencia =
                bufferedReader.readLine();

        int renovacionesPrevias =
                Integer.parseInt(
                        bufferedReader
                                .readLine()
                                .trim()
                );

        String result =
                Result.evaluarLicencia(
                        fechaActual,
                        fechaVencimiento,
                        tipoLicencia,
                        renovacionesPrevias
                );

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
