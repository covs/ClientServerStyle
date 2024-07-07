package main.server;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private ServerSocket serverSocket;
    private Set<String> validAccounts;

    public Server() {
        // Inicializamos algunas cuentas válidas para verificar
        validAccounts = new HashSet<>();
        validAccounts.add("123456");
        validAccounts.add("654321");
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            new ClientHandler(serverSocket.accept(), validAccounts).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private Set<String> validAccounts;

        public ClientHandler(Socket socket, Set<String> validAccounts) {
            this.clientSocket = socket;
            this.validAccounts = validAccounts;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("remesa:")) {
                        
                        String[] parts = inputLine.split(":");
                        String cuenta = parts[1];
                        String montoStr = parts[2];

                        boolean result = procesarRemesa(cuenta, montoStr);
                        out.println(result ? "Transaccion correcta" : "Cuenta no existe, intente nuevamente.");
                    } else if ("bye".equalsIgnoreCase(inputLine)) {
                        out.println("Comunicacion finalizada.");
                        break;
                    } else {
                        out.println("unrecognized message");
                    }
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean procesarRemesa(String cuenta, String montoStr) {
            if (!validAccounts.contains(cuenta)) {
                return false;
            }
            try {
                double monto = Double.parseDouble(montoStr);
                return monto > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
