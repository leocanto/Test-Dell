package app;

import java.io.*;
import java.util.Scanner;

public class Menu {
    public static final double R = 6372.8;
    public static Scanner in = new Scanner(System.in);
    static double latitude = 0;
    static double longitude = 0;
    static String lagradouro = " ";
    static String[][] matriz = new String[380][8];

    public void dashBoard(int option) throws IOException {
        switch (option) {
            case 1:
                listarTodosPontos();

                break;

            case 2:
                informarLocalizacao();

                break;

            case 3:
                EncontrarPontos();

                break;
            case 4:
                informarLagradouro();
                BuscarPontos();

                break;

            case 5:
                print("Finalizando o Programa");
                System.exit(0);
        }
        mostrarOpcoes();
    }

    public static void mostrarOpcoes() {
        print("\nBem-vindo!\n");
        print("1 -> Listar todos os pontos de taxi");
        print("2 -> Informar minha localização");
        print("3 -> Encontrar pontos próximos");
        print("4 -> Buscar pontos por logradouro");
        print("5 -> Terminar programa");;
    }

    public static int lerRespostaDoUsuario() {
        print("Digite o número do comando que deseja executar:");

        try {
            return in.nextInt();
        } catch (NumberFormatException exception) {
            print("O programa só aceita números inteiros");
        }
        return 10;
    }

    public static void informarLocalizacao(){
        print("Informe a sua latitude: ");
        latitude = in.nextDouble();
        print("Informe a sua longitude: ");
        longitude = in.nextDouble(); 
    }

    public static void informarLagradouro(){
        print("> Para exibir o nome dos pontos de TAXI da região");
        print("Digite o nome do lagradouro: ");
        lagradouro = in.next();
    }

    public void listarTodosPontos() throws IOException{
        lerArquivo();
        
        for (int j = 1; j < 380; j++) {
            for (int j2 = 0; j2 < 8; j2++) {
                if(j2 == 2){
                    System.out.println("> Ponto de TAXI " + j + ":");
                    System.out.println("Nome: " + matriz[j][j2]);
                }else if(j2 == 3){
                    System.out.println("Telefone: " + matriz[j][j2]);
                }else if(j2 == 4){
                    System.out.println("Logradouro: " + matriz[j][j2]);
                }else if(j2 == 5){
                    System.out.println("Numero: " + matriz[j][j2]);
                }else if(j2 == 6){
                    System.out.println("Latitude: " + matriz[j][j2]);
                }else if(j2 == 7){
                    System.out.println("Longitude: " + matriz[j][j2] + "\n");
                }
            }
        }
    }

    public void EncontrarPontos() throws IOException{
        //Double.parseDouble(new String())
        lerArquivo();

        String[] arrayLat = new String[379];
        String[] arrayLong = new String[379];
        

        for (int j = 1; j < 380; j++) {
            for (int j2 = 0; j2 < 8; j2++) {
                if(j2 == 5){
                    arrayLat[j] = matriz[j][j2];
                    arrayLong[j] = matriz[j][j2+1];
                }
            }
        }

    }

    public static void BuscarPontos() throws IOException{
        lerArquivo();

        System.out.println("\n" + "> Pontos de TAXI da regiao: ");
        for (int j = 1; j < 380; j++) {
            for (int j2 = 0; j2 < 8; j2++) {
                if(j2 == 4){
                   if(matriz[j][j2].contains(lagradouro)==true){
                       System.out.println("\n" +"Endereco do ponto de TAXI: " + matriz[j][j2]);
                       System.out.println("Nome do ponto de TAXI: " + matriz[j][j2-2]);
                   }
                }
            }
        }
    }

    public static void lerArquivo() throws IOException {
        Scanner sc = new Scanner(new File("pontos_taxi.csv"));
        sc.useDelimiter(" ");
        int i = 0;
        while (sc.hasNext()) 
        {
            String[] r = sc.nextLine().split(";");
            for (int j = 0; j < r.length; j++) {
                matriz[i][j] = r[j];
            }
            i++;
        }
        sc.close();
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
    
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
    
    public static void print(String s) {
        System.out.println("\n" + s);
    }

}
