package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientThread extends Thread{
    Socket s;

    public ClientThread(Socket s){
        this.s = s;
    }

    public void run(){
        try{
            Scanner scanner = new Scanner(System.in);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            System.out.print("Connessione effettuata. Digita Esci per uscire.\n");
            String action;

            do{
                System.out.print("Inserisci un numero: ");
                action = scanner.nextLine();

                switch(action){
                    case "1":
                        System.out.println("Inserisci una lettera");
                        String lettera = scanner.nextLine();
                        out.writeBytes(lettera + "\n");
                        out.writeBytes("1\n");
                        break;
                    case "2":
                        System.out.println("Prova ad indovinare la parola");
                        String parola = scanner.nextLine();
                        out.writeBytes(parola + "\n");
                        out.writeBytes("2\n");

                        String vincita = in.readLine();
                        if(!vincita.equals("")){
                            System.out.println(vincita);
                            action = "ESCI";
                        }
                        break;
                    case "ESCI":
                        out.writeBytes(action + "\n");
                        break;
                    default:
                        System.out.println("Numero sbagliato");
                }
            }while(!action.equals("ESCI"));
            
            scanner.close();
            s.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
