package main;

import main.server.Server;
import main.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final String ip = "localhost";
    private static final int port = 8800;
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            // Iniciar el servidor
            new Thread(() -> {
                try {
                    Server server = new Server();
                    server.start(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Darle tiempo al servidor para iniciar
            Thread.sleep(1000);

           // Iniciar el cliente
           Client client = new Client();
           client.startConnection(ip, port);

           // Leer datos de la remesa del usuario
           System.out.print("Ingrese el número de cuenta: ");
           String cuenta = reader.readLine();

           System.out.print("Ingrese el monto de la remesa: ");
           String monto = reader.readLine();

           // Enviar remesa al servidor
           String response = client.sendMessage("remesa:" + cuenta + ":" + monto);
           System.out.println("Server response: " + response);

           response = client.sendMessage("bye");
           System.out.println("Server response: " + response);

            client.stopConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}