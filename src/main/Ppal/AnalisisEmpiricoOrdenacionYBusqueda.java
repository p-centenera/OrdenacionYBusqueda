package Ppal;

import Modelo.Analizador;
import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.*;
import java.io.*;
import java.util.Arrays;

/**
 * @author Paloma Centenera
 */
public class AnalisisEmpiricoOrdenacionYBusqueda {

    /**
     * Análisis empírico de los algoritmos de ordenación y búsqueda.
     * Ejemplares: array de elementos aleatorios, array oredenado y array en orden inverso. Tamaños 100, 1000, 10000, 100000, 1000000, 10000000.
     * El resultado del análisis, los tiempos de ejecución de los algoritmos para los distintos ejemplares, se graba en el fichero tiempos.txt, en el directorio del proyecto
     */
   public static void desordena(long[] L){
       Random random=new Random(L.length);
       for (int i=0;i<L.length;i++){
           int p=random.nextInt(L.length);
           long aux=L[i];
           L[i]=L[p];
           L[p]=aux;
       }
   }
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
            JuegoPruebasAleatorio[i]= Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            desordena(JuegoPruebasAleatorio[i]);
            JuegoPruebasOrdenado[i]=Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            JuegoPruebasInverso[i]=Stream.iterate(tam-1, x -> x - 1).limit(tam).mapToLong(x->x).toArray();
            tam*=10;
        }
        try{
            tam=100;
            FileWriter fichero = new FileWriter("tiempos.txt");
            PrintWriter out = new PrintWriter(fichero);
            long[] resultado;
            for (int i=0;i<6;i++){
                out.print(tam+"\t");
                System.out.println("tamaño: "+tam);
                resultado=Analizador.analiza(JuegoPruebasAleatorio[i],BubbleSort,out);
                //System.out.println("aleatorio: "+Arrays.toString(JuegoPruebasAleatorio[i]));
                //System.out.println("ordenado: "+Arrays.toString(resultado));

                resultado=Analizador.analiza(JuegoPruebasOrdenado[i],BubbleSort,out);
                //System.out.println("ya ordenado: "+Arrays.toString(JuegoPruebasOrdenado[i]));
                //System.out.println("ordenado: "+Arrays.toString(resultado));

                resultado=Analizador.analiza(JuegoPruebasInverso[i],BubbleSort,out);
                //System.out.println("inverso: "+Arrays.toString(JuegoPruebasInverso[i]));
               // System.out.println("ordenado: "+Arrays.toString(resultado));
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
