public static int[] detectarZonaAjuste(int[] vagones) {

    if (vagones.length < 2) {
        return new int[]{-1, -1, 0};
    }

    int minFueraDeOrden = Integer.MAX_VALUE;
    int maxFueraDeOrden = Integer.MIN_VALUE;

    for (int i = 0; i < vagones.length; i++) {
        if (estaFueraDeOrden(i, vagones)) {
            if (vagones[i] < minFueraDeOrden) {
                minFueraDeOrden = vagones[i];
            }
            if (vagones[i] > maxFueraDeOrden) {
                maxFueraDeOrden = vagones[i];
            }
        }
    }

    if (minFueraDeOrden == Integer.MAX_VALUE) {
        return new int[]{-1, -1, 0};
    }

    int inicio = 0;
    while (vagones[inicio] <= minFueraDeOrden) {
        inicio++;
    }

    int fin = vagones.length - 1;
    while (vagones[fin] >= maxFueraDeOrden) {
        fin--;
    }

    int longitud = fin - inicio + 1;

    return new int[]{inicio, fin, longitud};
}

private static boolean estaFueraDeOrden(int i, int[] vagones) {
    int valor = vagones[i];

    if (i == 0) {
        return valor > vagones[i + 1];
    }
    if (i == vagones.length - 1) {
        return valor < vagones[i - 1];
    }
    return valor > vagones[i + 1] || valor < vagones[i - 1];
}