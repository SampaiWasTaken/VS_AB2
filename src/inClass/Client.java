package inClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new java.io.InputStreamReader(System.in));

            String input;
            while ((input = stdIn.readLine()) != null)
            {
                System.out.println("echo: " + input);
                out.println(input);
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
