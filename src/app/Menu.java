package app;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static final double R = 6372.8;
    public static Scanner in = new Scanner(System.in);
    static double latitude = 0;
    static double longitude = 0;
    static String lagradouroAux;
    static String lagradouro;
    static String[][] matriz = new String[380][8];
    static String[][] latLong = new String[380][8];
    static double[][] dLatLong = new double[380][8];

    //Painel aonde controla os métodos referentes aos inputs realizados pelo usuário.
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
                print("Programa finalizado");
                System.exit(0);
        }
        mostrarOpcoes();
    }

    //Listagem do menu.
    public static void mostrarOpcoes() {
        print("=== MENU ===");
        print("1. Listar todos os pontos de taxi");
        print("2. Informar minha localização");
        print("3. Encontrar pontos próximos");
        print("4. Buscar pontos por logradouro");
        print("5. Terminar programa");
    }

    //Lê a resposta do usuário e armazena no "input", criado na classe main.
    public static int lerRespostaDoUsuario() {
        print("Escolha uma das opções: ");

        try {
            return in.nextInt();
        } catch (Error exception) {
            print("O programa só aceita números inteiros");
        }

        return 10;
    }

    //Pega o input do usuário referente a sua localização e armazena nas variáveis "latitude" e "longitude".
    public static void informarLocalizacao(){
        boolean val = true;
        print("Digite a sua latitude: ");
        do{
            if(in.hasNextDouble()){
                latitude = in.nextDouble();
                val = true;
            }else{
                System.out.println("Entrada inválida");
                val = false;
                in.next();
                informarLocalizacao();
            }
        }while (!(val));
        print("Digite a sua longitude: ");
        do{
            if(in.hasNextDouble()){
                longitude = in.nextDouble();
                val = true;
            }else{
                System.out.println("Entrada inválida");
                val = false;
                in.next();
                informarLocalizacao();
            }
        }while (!(val));
        print("Localização armazenada.");
        
    }

    //Pega o input do usuário referente a região aonde necessita saber a região do ponto de TAXI e armazena na variável "lagradouro".
    public static void informarLagradouro(){
        print("Digite todo ou parte do nome do logradouro: ");
        lagradouroAux = in.next();
        lagradouro = lagradouroAux.toUpperCase();
    }


    //Método utuliza a função "lerArquivo" para fazer a leitura e o armazenamento dos dados CVS e armazenalos em uma matriz. Sendo assim, foi feito uma listagem dos dados de todos os pontos de TAXI do arquivo.
    public void listarTodosPontos() throws IOException{
        lerArquivo();
        
        //Percorre a matriz partindo da segunda linha, realizando o Print no terminal de acordo com os dados desejados, sendo controlados pelas colunas que estão representadas pela variável "j2".
        for (int j = 1; j < 380; j++) {
            System.out.println("> Ponto de TAXI " + j + ":");
            System.out.println("Nome: " + matriz[j][2]);
            System.out.println("Telefone: " + matriz[j][3]);
            System.out.println("Logradouro: " + matriz[j][4]);
            System.out.println("Numero: " + matriz[j][5]);
            System.out.println("Latitude: " + matriz[j][6]);
            System.out.println("Longitude: " + matriz[j][7] + "\n");
        }
    }

    //Método utuliza a função "lerArquivo" para fazer a leitura e o armazenamento dos dados CVS e armazenalos em uma matriz.Com isso, ele tem como objetivo alterar o tipo dos valores de latitude e longitude da matriz, os transformando de String para Double. Para isso acontecer, tive que substituir as virgulas por pontos para ter êxito no método.
    public void TratarArray() throws IOException{
        lerArquivo();

        for (int j = 1; j < 380; j++) {
            for (int j2 = 6; j2 < 8; j2++) {
                if(matriz[j][j2].indexOf(",") != -1){
                latLong[j][j2] = matriz[j][j2].replace(",", ".");
                }else{
                    latLong[j][j2] = matriz[j][j2];
                }
                if(latLong[j][j2].indexOf("\"") != -1){
                    latLong[j][j2] = latLong[j][j2].replace("\"", " ");
                }
                dLatLong[j][j2] = Double.parseDouble(latLong[j][j2]);
            }
        }
    }

    //Método utiliza a função "TratarArray" para conseguir alterar o tipo dos valores de latitude e longitude da matriz, os transformando de String para Double. Com isso, ele tem como objetivo encontrar os 3 pontos de TAXI mais próximos da região do usuário.
    public void EncontrarPontos() throws IOException{
        TratarArray();
        double[] dist = new double[380];
        double[] distAux = new double[380];
        double[] menorDist = new double[3];

        //Foi utilizado a função disponibilizada "haversine", que cálcula a distância entre latitudes e longitudes.
        for (int j = 1; j < 380; j++) {
                    dist[j] = haversine(latitude, longitude, dLatLong[j][6], dLatLong[j][7]);
                    distAux[j] = haversine(latitude, longitude, dLatLong[j][6], dLatLong[j][7]);
             
        }
        Arrays.sort(distAux);

        menorDist[0] = distAux[1];
        menorDist[1] = distAux[2];
        menorDist[2] = distAux[3];

        System.out.println("Os pontos de taxi mais próximos são: ");
        for (int j = 1; j < 380; j++) {
            if(dist[j]==menorDist[0] || dist[j]==menorDist[1] || dist[j]==menorDist[2]){
            System.out.println("Nome: " + matriz[j][2]);
            }
        }
    }

    //Método utuliza a função "lerArquivo" para fazer a leitura e o armazenamento dos dados CVS e armazenalos em uma matriz. Sendo assim, foi feito uma listagem de todos os pontos de TAXI referentes as regiões que contém o nome, ou pelo menos uma parte dele, fornecido pelo usuário.
    public static void BuscarPontos() throws IOException{
        lerArquivo();

        //Percorre a matriz partindo da segunda linha, e se a coluna percorrida for a que representa o "lagradouro", verificamos se a String armazenada na matriz contém o input do usuário, se isso acontecer, será realizado um Print no terminal referente ao Ponto de TAXI da região informada.
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


    //Lê o arquivo CSV fornecido e os dados separados por ";", são armazenados em uma matriz.
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

    //Método fornecido que tem como objetivo realizar o cálculo de distância pela latitude e longitude.
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
    
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
    
    //Método simples para realziar alguns Prints.
    public static void print(String s) {
        System.out.println("\n" + s);
    }

}
