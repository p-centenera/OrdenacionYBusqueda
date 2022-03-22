package Ppal;

import Modelo.Analizador;
import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.*;
import java.io.*;

public class AnalisisEmpiricoOrdenacionYBusqueda {
    public static void swap( long A, long B){
        long Aux=A;
        A=B;
        B=Aux;

    }
    public static void main(String[] args) {
    Function<long[], long[]>  BubbleSort = L->
    {
        for (int i = 1; i < L.length; i++)
            for (int j = L.length - 1; j > i; j--)
                if (L[j - 1] > L[j])
                    swap(L[j - 1] , L[j]);
        return L;
    };


        long[] [] JuegoPruebasAleatorio =new long[6][];
        long [] []  JuegoPruebasOrdenado =new long[6][];
        long [] []  JuegoPruebasInverso =new long[6][];
        Long tam= new Long(100);

        Random random =new Random();

        for (int i=0;i<6;i++){
            JuegoPruebasAleatorio[i]= random.longs().
                    limit(tam).toArray();
            JuegoPruebasOrdenado[i]=Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            JuegoPruebasInverso[i]=Stream.iterate(tam, x -> x - 1).limit(tam).mapToLong(x->x).toArray();
            tam*=10;
        }
        try{
            tam=Long.valueOf(100);
            FileWriter fichero = new FileWriter("tiempos.txt");
            PrintWriter out = new PrintWriter(fichero);
            for (int i=0;i<6;i++){
                out.print(tam+"\t");
                Analizador.analiza(JuegoPruebasAleatorio[i],BubbleSort,out);
                Analizador.analiza(JuegoPruebasOrdenado[i],BubbleSort,out);
                Analizador.analiza(JuegoPruebasInverso[i],BubbleSort,out);
                out.println();
                System.out.println("tamaño: "+tam);
                tam*=10;
            }
            out.close();

        }catch ( Exception e){
        System.out.print("error en fichero");
        }
        System.out.print("Fin");
    }
}
