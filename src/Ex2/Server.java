package Ex2;

import java.io.IOException;
import java.net.*;
import java.util.Date;

public class Server
{
    public static void main(String[] args) throws InterruptedException
    {

        try
        {
            Thread t1 = new Thread(new ServerThread(8090));
            Thread t2 = new Thread(new ServerThread(8091));
            Thread t3 = new Thread(new ServerThread(8092));
            t1.start();
            t2.start();
            t3.start();
        }
        catch (UnknownHostException e)
        {
            System.out.println(e.getMessage());
        }

    }
}

class ServerThread extends Thread
{
    private InetAddress ipAddr;
    private int port;
    private int udp;
    static int count = 0;
    private int id;

    public ServerThread(int udp) throws UnknownHostException
    {
        this.ipAddr = InetAddress.getByName("239.255.255.255");
        this.port = 8080;
        this.udp = udp;
        id = count++;
    }

    @Override
    public void run()
    {
        try
        {
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(ipAddr);
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

            while (true)
            {
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server " + id + " Received: " + msg);

                int clientPort = Integer.parseInt(msg.split("#")[1]);
                Date date = new Date();
                String response = "TS-" + id + "#" + date.getTime() + "#";
                DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.getBytes().length, packet.getAddress(), clientPort);
                DatagramSocket dataSocket = new DatagramSocket(udp);
                dataSocket.send(responsePacket);
                dataSocket.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
