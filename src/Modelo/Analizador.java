package Modelo;
import java.io.*;
import java.util.function.Function;

public class Analizador {
    public static long [] analiza(long[] data, Function <long[],long[]> F,  PrintWriter out) {
        long[] aux = data.clone();
        long inicio=System.nanoTime();
        F.apply(aux);
        long duracion=System.nanoTime()-inicio;
        out.print(duracion+"\t");
        return aux;
    }
}
