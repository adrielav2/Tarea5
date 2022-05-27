 package Server;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;
    
    Socket cliente;
    ObjectOutputStream output;
    
    boolean extremoActivo = false;
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);
        ventana.setTitle("Server");

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        Server server = new Server(dot, this);
        Thread hilo = new Thread(server);
        hilo.start();

        moveDot();
        run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {  
        
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while (true){
            dot.move();
            moveDot();
            try {
                Thread.sleep(1000);
                if(extremoActivo)
                {
                    cliente = new Socket("192.168.1.116",8888);
                    output = new ObjectOutputStream(cliente.getOutputStream());
                    output.writeObject(dot);
                    output.flush();
                    output.close();
                    cliente.close();
                }
            } catch (InterruptedException e) {
                //TODO: handle exception
            } catch (IOException e)
            {
                System.out.println("No se ha establecido conexion  el cliente servidor");
            }
        }
    }

}