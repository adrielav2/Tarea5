/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Common.Dot;
import java.io.*;
import java.net.*;

/**
 *
 * @author david
 */
public class ServerCliente implements Runnable{

    ServerSocket serverCliente;
    Socket client;
    ObjectInputStream input;
    Dot dot;
    
    
    GUI gui;

    ServerCliente(Dot d, GUI gui) {
        dot = d;
        this.gui = gui;
        try {
            serverCliente = new ServerSocket(8888);
        } catch (IOException e) {
            //TODO: handle exception
        }

    }

    public void run() {
        System.out.println("Ya empezamos por aca en server");
        try {
            while (true) {
                System.out.println("Abrimos conex en cliente servidor");
                client = serverCliente.accept();
                input = new ObjectInputStream(client.getInputStream());
                dot = (Dot) input.readObject();
                gui.moveNewDot(dot);
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
}
