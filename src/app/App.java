package app;

import java.util.*;
import java.io.*;

public class App {
    public static Scanner in = new Scanner(System.in);


    public static void print(String s) {
        System.out.println("\n" + s);
    }

    public static void main(String[] args) throws  IOException {
        Menu.mostrarOpcoes();
        int input = Menu.lerRespostaDoUsuario();
        Menu m = new Menu();
        
        while (input != 9) {
            m.dashBoard(input);
            input = Menu.lerRespostaDoUsuario();
        }
    }
}








