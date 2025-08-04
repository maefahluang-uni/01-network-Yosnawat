package th.mfu;

import java.io.*;
import java.net.*;

// call mockup server at port 8080
public class MockWebClient {
    public static void main(String[] args) {
        try {
            // Create a socket to connect to the web server on port 8080
            Socket clientSocket = new Socket("localhost", 8080);
            System.out.println("Connected to Mock Web Server on port 8080");

            // Create input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Send an HTTP GET request to the web server
            String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
            out.println(request);
            out.flush();
            System.out.println("Sent HTTP GET request to server");

            // Read the response from the web server and print out to console
            String responseLine;
            System.out.println("\n--- Server Response ---");
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
                // Break after reading the HTML content to avoid hanging
                if (responseLine.contains("</html>")) {
                    break;
                }
            }

            // Close the socket
            clientSocket.close();
            System.out.println("\n--- Connection closed ---");
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
