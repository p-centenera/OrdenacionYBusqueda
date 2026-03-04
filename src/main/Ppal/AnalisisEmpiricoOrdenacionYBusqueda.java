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
    public static long[] seleccionsort(long[] L){
        for (int i = 0; i < L.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < L.length; j++)
                if (L[j] < L[min]) min = j;
            long aux=L[i];
            L[i]=L[min];
            L[min]=aux;
        }
        return L;
    }
    public static long[] insertionsort(long[] L){
        for (int i = 1; i < L.length; i++) {
            long cart = L[i];
            int j = i - 1;
            while (j >= 0 && L[j] > cart) {
                L[j + 1] = L[j];
                j--;
            }
            L[j + 1] = cart;
        }
        return L;
    }
    public static long [] bubblesort(long[] L){
        for (int i = 1; i < L.length; i++)
            for (int j = L.length - 1; j >= i; j--)
                if (L[j - 1] > L[j]) {
                    long Aux=L[j];
                    L[j]=L[j-1];
                    L[j-1]=Aux;
                }
        return L;
    }
    public static long [] quicksort(long[] A, int izq, int der) {
        if (izq >= der) return A; // caso base: el array se ha ordenado
        long pivote=A[(izq+der)/2]; // tomamos primer elemento como pivote
        int i=izq;         // i realiza la búsqueda de izquierda a derecha
        int j=der;         // j realiza la búsqueda de derecha a izquierda
        long aux;

        while(i < j){                          // mientras no se crucen las búsquedas
            while(A[i] < pivote ) i++; // busca elemento mayor que pivote
            while(A[j] > pivote) j--;           // busca elemento menor que pivote
            if (i <=j) {                        // si no se han cruzado
                aux= A[i];                      // los intercambia
                A[i]=A[j];
                A[j]=aux;
            }
        }


            quicksort(A,izq,j-1);          // ordenamos subarray izquierdo

            quicksort(A,j+1,der);          // ordenamos subarray derecho
        return A;
    }
    public static long [] mergesort(long []A,int izq, int der){
        if (izq < der){
            int m=(izq+der)/2;
            mergesort(A,izq, m);
            mergesort(A,m+1, der);
            merge(A,izq, m, der);
        }
        return A;
    }
    public static long[] merge(long []A,int izq, int m, int der){
        int i, j, k;
        long [] B = new long[A.length]; //array auxiliar
        for (i=izq; i<=der; i++)      //copia ambas mitades en el array auxiliar
            B[i]=A[i];

        i=izq; j=m+1; k=izq;

        while (i<=m && j<=der) //copia el siguiente elemento más grande
            if (B[i]<=B[j])
                A[k++]=B[i++];
            else
                A[k++]=B[j++];

        while (i<=m)         //copia los elementos que quedan de la
            A[k++]=B[i++]; //primera mitad (si los hay)
        return A;
    }
    public static void main(String[] args) throws IOException{
       Function<long[], long[]> seleccionsort = x -> seleccionsort(x);
       Function<long[], long[]> insertionsort = x -> insertionsort(x);
       Function<long[], long[]> bubblesort = x -> bubblesort(x);
       Function<long[], long[]> quicksort = x -> quicksort(x,0,x.length-1);
       Function<long[], long[]> mergesort = x -> mergesort(x,0,x.length-1);
    Function<long[], long[]> [] AlgoritmosOrdenacion = new Function[]{
            seleccionsort,
            insertionsort,
            bubblesort,
            quicksort,
            mergesort
    };
    String [] NombresAlgoritmos={"SeleccionSort","InsertionSort","BubbleSort","QuickSort","MergeSort"};



        long[] [] JuegoPruebasAleatorio =new long[6][];
        long [] []  JuegoPruebasOrdenado =new long[6][];
        long [] []  JuegoPruebasInverso =new long[6][];
        long tam= 10;

        Random random =new Random();

        for (int i=0;i<5;i++){
            JuegoPruebasAleatorio[i]= Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            desordena(JuegoPruebasAleatorio[i]);
            JuegoPruebasOrdenado[i]=Stream.iterate( (long) 0, x -> x + 1).limit(tam).mapToLong(x->x).toArray();
            JuegoPruebasInverso[i]=Stream.iterate(tam-1, x -> x - 1).limit(tam).mapToLong(x->x).toArray();
            tam*=10;
        }

        tam=10;
        FileWriter fichero = new FileWriter("tiempos.txt");
        PrintWriter out;

        out= new PrintWriter(fichero);

        long[] resultado;
        for (int i=0;i<5;i++){
                out.print(tam+"\n");
                System.out.println("tamaño: "+tam);
                for (int j=0;j<AlgoritmosOrdenacion.length;j++) {
                    out.print(NombresAlgoritmos[j] + "\n");
                    System.out.println(NombresAlgoritmos[j]);
                    try{

                        resultado = Analizador.analiza(JuegoPruebasAleatorio[i], AlgoritmosOrdenacion[j], out);

                        if (tam<10000){
                            System.out.println("Ejemplar aleatorio: "+Arrays.toString(JuegoPruebasAleatorio[i]));
                            System.out.println("Resultado: "+Arrays.toString(resultado));
                        }
                    }catch (StackOverflowError e){
                        System.out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas aleatorio, no soporta el tamaño "+tam);
                        out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas aleatorio, no soporta el tamaño "+tam);
                    }
                    try {
                        resultado = Analizador.analiza(JuegoPruebasOrdenado[i], AlgoritmosOrdenacion[j], out);
                    }catch (StackOverflowError e){
                        System.out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas ordenado, no soporta el tamaño "+tam);
                        out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas ordenado, no soporta el tamaño "+tam);
                    }
                    try{
                        resultado = Analizador.analiza(JuegoPruebasInverso[i], AlgoritmosOrdenacion[j], out);

                    }catch (StackOverflowError e){
                        System.out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas inverso, no soporta el tamaño "+tam);
                        out.println("Desbordamiento de pila. Algoritmo"+NombresAlgoritmos[j]+", juego de pruebas inverso, no soporta el tamaño "+tam);
                    }
                    out.println();
                }
                tam*=10;
            }
            out.close();


        System.out.print("Fin");
    }
}
