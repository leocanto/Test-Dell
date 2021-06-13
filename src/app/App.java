package app;

import java.io.*;
// -30,023927 , -51,219871 
public class App {
    public static void main(String[] args) throws  IOException {
        Menu.mostrarOpcoes();
        int input = Menu.lerRespostaDoUsuario();
        Menu m = new Menu();

        while (input != 9){ 
            m.dashBoard(input);
            input = Menu.lerRespostaDoUsuario();
        }
    }
}








