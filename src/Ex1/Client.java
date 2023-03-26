package Ex1;

import java.io.*;
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
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input;
            while ((input = stdIn.readLine()) != null)
            {
                if(input.startsWith("exit"))
                {
                    out.println(input);
                    socket.close();
                    break;
                }
                System.out.println("echo: " + input);
                out.println(input);
                if (input.startsWith("get"))
                {
                    String res = socketIn.readLine();
                    if(res.equals("null"))
                        System.out.println("File not found");
                    else
                        writeToFile(res);
                    socket.close();
                    break;
                }
                else
                    System.out.println("S: " + socketIn.readLine());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void writeToFile(String input) throws IOException
    {
        String[] tokens = input.substring(1, input.length() - 1).split(", ");
        byte[] bytes = new byte[tokens.length];
        for (int i = 0; i < tokens.length; i++)
            bytes[i] = Byte.parseByte(tokens[i].trim());

        File receivedFile = new File("receivedFile.txt");
        FileOutputStream fos = new FileOutputStream(receivedFile);
        fos.write(bytes);
    }
}
