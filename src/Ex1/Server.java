package Ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Server
{
    public static void main(String[] args)
    {
        String dir = args[0];
        try
        {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                ServerThread serverThread = new ServerThread(socket, dir);
                serverThread.start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

    }
}

class ServerThread extends Thread
{
    private Socket socket;
    private String dir;

    public ServerThread(Socket socket, String dir)
    {
        this.socket = socket;
        this.dir = dir;
    }

    @Override
    public void run()
    {
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
            String input;


            while ((input = in.readLine()) != null)
            {
                if (input.startsWith("exit"))
                {
                    System.out.println("Client disconnected");
                    socket.close();
                    break;
                }
                out.println(parseInput(input));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private String parseInput(String input) throws IOException
    {
        String[] tokens = input.split(" ");
        File directory = new File(dir);
        Set<String> files = new HashSet<>();
        for (File file : Objects.requireNonNull(directory.listFiles()))
            files.add(file.getName());

        return switch (tokens[0].toLowerCase())
                {
                    case "list" -> files.toString();
                    case "get" ->
                            files.contains(tokens[1]) ? Arrays.toString(Files.readAllBytes(Paths.get(dir + "\\" + tokens[1]))) : null;
                    default -> "Unknown command";
                };
    }
}
