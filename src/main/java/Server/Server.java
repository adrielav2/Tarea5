package Server;

import java.net.*;
import java.io.*;
import Common.*;

public class Server implements Runnable{
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;
    
    GUI gui;

    public Server(Dot d, GUI gui){
        dot = d;
        this.gui = gui;
        try {
            server = new ServerSocket(9999);
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    public void run(){
        System.out.println("Ya empezamos por aca en server");
        try {
            while(true){
                client = server.accept();
                gui.extremoActivo = true; 
                input = new ObjectInputStream(client.getInputStream());
                dot.target = (Target)input.readObject();
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
    
}
