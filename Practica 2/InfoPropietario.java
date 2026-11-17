public class InfoPropietario {
    private String nombrePropietario;
    private String curp;

    public InfoPropietario(String nombrePropietario, String curp) {
        this.nombrePropietario = nombrePropietario;
        this.curp = curp;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getCurp() {
        return curp;
    }
}