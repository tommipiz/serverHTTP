package it.pizzutilo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ServerSocket ss = new ServerSocket(8080);

        while (true) {
            Socket s = ss.accept();

            BufferedReader in= new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String[] firstLine = in.readLine().split(" ");
            String method = firstLine[0];
            String resource = firstLine[1];
            String version = firstLine[2];
            System.out.println(method);
            System.out.println(resource);

            String header;

            do {
                header = in.readLine();
                System.out.println(header);
            } while(!header.isEmpty());
            System.out.println(" richiesta terminata");

            String responseBody = "Ciao a tutti";
            out.writeBytes("HTTP/1.1 200 OK\n");
            out.writeBytes("Content-Type: text/plain\n");
            out.writeBytes("Content-Length: " + responseBody.length() + "\n");
            out.writeBytes("\n");
            out.writeBytes(responseBody);

        }
    }
}