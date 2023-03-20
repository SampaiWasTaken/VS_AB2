package inClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));

                String input;
                while ((input = in.readLine()) != null)
                {
                    System.out.println("Received: " + input + " from " + socket);
                    out.println(input);
                }
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

