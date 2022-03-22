package Modelo;
import java.io.*;
import java.util.function.Function;
public class Analizador {
    public static void analiza(long[] data, Function F,  PrintWriter out) throws IOException{
        long[] aux =new long[data.length];
        for(int i=0;i<aux.length;i++)
            aux[i]=data[i];
        Long inicio=System.nanoTime();
        F.apply(aux);
        Long duracion=System.nanoTime()-inicio;
        out.print(duracion+"\t");
    }
}
