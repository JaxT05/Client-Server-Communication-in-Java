package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 2){
            System.out.println("Invalid Parameters. Need two arguments: serverIP and serverPort");
            return;
        }
        Scanner keyboardReader = new Scanner(System.in);
        String input = keyboardReader.nextLine();
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);
        DatagramPacket request = new DatagramPacket(
                input.getBytes(),
                input.getBytes().length,
                serverIP,
                serverPort
        );
        socket.send(request);

        DatagramPacket reply = new DatagramPacket(new byte [1024], 1024);
        socket.receive(reply);
        byte[] serverMessage = Arrays.copyOf(
                reply.getData(),
                reply.getLength()
        );
        System.out.println(new String(serverMessage));
        socket.close();
    }
}
