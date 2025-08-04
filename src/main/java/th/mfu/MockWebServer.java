package th.mfu;

import java.io.*;
import java.net.*;

public class MockWebServer implements Runnable {

    private int port;

    public MockWebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            // Create a server socket bound to specified port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Mock Web Server running on port " + port + "...");

            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                try {
                    // Create input and output streams for the client socket
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    // Read the request from the client using BufferedReader
                    String requestLine = in.readLine();
                     System.out.println("Received request: " + requestLine);
                    // Send a response to the client
                    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n"
                            + "<html><body>Hello, Web! on Port " + port + "</body></html>";
                    out.println(response);
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                } finally {
                    // Close the client socket
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing client socket: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error on port " + port + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Thread server1 = new Thread(new MockWebServer(8080));
        server1.start();

        Thread server2 = new Thread(new MockWebServer(8081));
        server2.start();

        // type any key to stop the server
        System.out.println("Press any key to stop the server...");
        try {
            System.in.read();

            // Stop the mock web server
            server1.interrupt();
            server2.interrupt();
            System.out.println("Mock web server stopped.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
