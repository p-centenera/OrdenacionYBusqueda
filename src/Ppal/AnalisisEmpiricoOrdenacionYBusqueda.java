package Ppal;

import Modelo.Analizador;
import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.*;
import java.io.*;
import java.util.Arrays;

public class AnalisisEmpiricoOrdenacionYBusqueda {

    public static void main(String[] args) {
    Function<long[], long[]>  BubbleSort = L->
    {
        for (int i = 1; i < L.length; i++)
            for (int j = L.length - 1; j >= i; j--)
                if (L[j - 1] > L[j]) {
                    long Aux=L[j];
                    L[j]=L[j-1];
                    L[j-1]=Aux;
                }
        return L;
    };


        long[] [] JuegoPruebasAleatorio =new long[6][];
        long [] []  JuegoPruebasOrdenado =new long[6][];
        long [] []  JuegoPruebasInverso =new long[6][];
        long tam= 100;

        Random random =new Random();

        for (int i=0;i<6;i++){
            JuegoPruebasAleatorio[i]= random.longs().map(Math::abs).
                    limit(tam).toArray();
            JuegoPruebasOrdenado[i]=Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            JuegoPruebasInverso[i]=Stream.iterate(tam, x -> x - 1).limit(tam).mapToLong(x->x).toArray();
            tam*=10;
        }
        try{
            tam=100;
            FileWriter fichero = new FileWriter("tiempos.txt");
            PrintWriter out = new PrintWriter(fichero);
            long[] resultado;
            for (int i=0;i<2;i++){
                out.print(tam+"\t");
                System.out.println("tamaño: "+tam);
                resultado=Analizador.analiza(JuegoPruebasAleatorio[i],BubbleSort,out);
                System.out.println("aleatorio: "+Arrays.toString(JuegoPruebasAleatorio[i]));
                System.out.println("ordenado: "+Arrays.toString(resultado));

                resultado=Analizador.analiza(JuegoPruebasOrdenado[i],BubbleSort,out);
                System.out.println("ya ordenado: "+Arrays.toString(JuegoPruebasOrdenado[i]));
                System.out.println("ordenado: "+Arrays.toString(resultado));

                resultado=Analizador.analiza(JuegoPruebasInverso[i],BubbleSort,out);
                System.out.println("inverso: "+Arrays.toString(JuegoPruebasInverso[i]));
                System.out.println("ordenado: "+Arrays.toString(resultado));
                out.println();
                 tam*=10;
            }
            out.close();

        }catch ( Exception e){
        System.out.print("error en fichero");
        }
        System.out.print("Fin");
    }
}
