package app;

import java.io.*;     
public class App {
    public static void main(String[] args) throws  IOException {
        Menu m = new Menu();
        int input;
        
        do{ 
            input = Menu.lerRespostaDoUsuario();
            m.dashBoard(input);
        }while (input != 5);
    }
}








