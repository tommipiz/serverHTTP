package com.pizzutilo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String line[] = in.readLine().split(" ");
            String method = line[0];
            String resource = line[1];
            String version = line[2];
            String header;
            System.out.println(method+" "+resource+" "+version);
            do {
                header = in.readLine();
                //System.out.println(header);
            } while (header.isEmpty());
            if (resource.equals("/")) {
                resource = "/index.html";
            }
            System.out.println(resource);

            String type;
            File file = new File("htdocs" + resource);
            if (file.exists()) {
                switch (resource.substring(resource.lastIndexOf(".") +1)) {
                    case "html":
                        type = "text/html";
                        break;
                    case "txt":
                        type = "text/plain";
                        break;
                    case "jpg":
                        type = "image/jpg";
                        break;
                    case "css":
                        type = "text/css";
                        break;
                    default:
                        type = "text/html";
                        break;
                }
                System.out.println("tipo:"+type);
                out.writeBytes(version + " 200 OK\n");
                out.writeBytes("Content-Type: " + type +"\n");
                out.writeBytes("Content-Length: " + file.length() +"\n");
                out.writeBytes("\n");
                InputStream input = new FileInputStream(file);
                byte[] buf = new byte[8192];
                int n;
                while ((n = input.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                input.close();
            } else {
                File file_error = new File("htdocs/error.html");
                out.writeBytes(version + " 404 Not Found\n");
                out.writeBytes("Content-Length: "+ file_error.length() +"\n");
                out.writeBytes("Content-Type: text/html\n");
                out.writeBytes("\n");
                InputStream input = new FileInputStream(file_error);
                byte[] buf = new byte[8192];
                int n;
                while ((n = input.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                input.close();
                
            }
            s.close();
        }
    }
}