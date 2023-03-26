package Ex2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            InetAddress ipAddr = InetAddress.getByName("239.255.255.255");
            MulticastSocket socket = new MulticastSocket();

            String request = "REQ#8079#";
            DatagramPacket packet = new DatagramPacket(request.getBytes(), request.getBytes().length, ipAddr, 8080);
            socket.send(packet);
            socket.close();
            DatagramSocket udpSocket = new DatagramSocket(8079);

            long[] times = new long[3];
            for (int i = 0; i < 3; i++)
            {
                DatagramPacket udpPacket = new DatagramPacket(new byte[1024], 1024);
                udpSocket.receive(udpPacket);
                String msg = new String(udpPacket.getData(), 0, udpPacket.getLength());
                System.out.println("Received: " + msg);
                times[i] = Long.parseLong(msg.split("#")[1]);
            }

            System.out.println("Average Time is: " + (times[0] + times[1] + times[2]) / 3 + "ms");
            udpSocket.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
