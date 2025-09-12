package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * The purpose of this exercise is to simulate a packet transfer
 * */

public class UDPServer {
    public static void main(String[] args) throws Exception {
        int serverPort = 3000;
        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);

        while(true) {
            serverSocket.receive(clientRequest);

            InetAddress clientIP = clientRequest.getAddress();
            int clientPort = clientRequest.getPort();

            byte [] content = Arrays.copyOf(
                    clientRequest.getData(),
                    clientRequest.getLength()
            );
            String clientMessage = new String(content);
            System.out.println(clientMessage);

            if (clientMessage.equalsIgnoreCase("ping")) {
                String pong = "pong";
                DatagramPacket reply = new DatagramPacket(
                        pong.getBytes(),
                        pong.getBytes().length,
                        clientIP,
                        clientPort
                );
                serverSocket.send(reply);
            }

            else if (clientMessage.equalsIgnoreCase("time")) {
                LocalDateTime now = LocalDateTime.now();
                String time = now.toLocalTime().toString();
                DatagramPacket reply = new DatagramPacket(
                        time.getBytes(),
                        time.getBytes().length,
                        clientIP,
                        clientPort
                );
                serverSocket.send(reply);
            }
            else {
                System.out.println("Invalid Client Request");
            }
        }
    }
}