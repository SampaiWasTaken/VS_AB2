package Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{public static void main(String[] args)
{
    try
    {
        Socket socket = new Socket("localhost", 8080);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new java.io.InputStreamReader(System.in));
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String input;
        while ((input = stdIn.readLine()) != null)
        {
            System.out.println("echo: " + input);
            out.println(input);
            if (input.startsWith("get"))
            {
                //TODO write to file with bytes

            }
            else
                System.out.println("S: " + socketIn.readLine());
        }
    } catch (IOException e)
    {
        System.out.println("Error: " + e.getMessage());
    }
}
}
