public static String cifrarMensaje(String mensaje, String alfabetoInterior, int posicionInicial, int intervaloRotacion) {
    StringBuilder resultado = new StringBuilder();
    int desplazamiento = posicionInicial;
    int contador = 0;

    for (int i = 0; i < mensaje.length(); i++) {
        char c = mensaje.charAt(i);

        if (c == ' ') {
            resultado.append(' ');
            continue;
        }

        int posicionLetra = c - 'A';
        int indice = ((posicionLetra - desplazamiento) % 26 + 26) % 26;
        resultado.append(alfabetoInterior.charAt(indice));

        contador++;
        if (contador == intervaloRotacion) {
            desplazamiento++;
            contador = 0;
        }
    }

    return resultado.toString();
}