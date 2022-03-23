package Modelo;
import java.io.*;
import java.util.function.Function;

/**
 * @author Paloma Centenera
 */
public class Analizador {
    /**
     * @param data: Dato de prueba para correr el análisis
     * @param F: función a analizar
     * @param out: Fichero de salida para el tiempode ejecución
     * @return resultado de la función a analizar
     */
    public static long [] analiza(long[] data, Function <long[],long[]> F,  PrintWriter out) {
        long[] aux = data.clone();
        long inicio=System.nanoTime();
        F.apply(aux);
        long duracion=System.nanoTime()-inicio;
        out.print(duracion+"\t");
        return aux;
    }
}
