package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        int serverPort = 3000;
        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);

        while (true) {
            serverSocket.receive(clientRequest);

            InetAddress clientIP = clientRequest.getAddress();
            int clientPort = clientRequest.getPort();

            byte [] content = Arrays.copyOf(
                    clientRequest.getData(),
                    clientRequest.getLength()
            );

            String clientMessage = new String(content);
            System.out.println(clientMessage);

            clientMessage = clientMessage.toLowerCase();

            switch (clientMessage) {
                case "ping": {
                    String pong = "pong";
                    DatagramPacket reply = new DatagramPacket(
                            pong.getBytes(),
                            pong.getBytes().length,
                            clientIP,
                            clientPort
                    );
                    serverSocket.send(reply);
                    break;
                }
                case "time": {
                    LocalDateTime now = LocalDateTime.now();
                    String time = now.toLocalTime().toString();
                    DatagramPacket reply = new DatagramPacket(
                            time.getBytes(),
                            time.getBytes().length,
                            clientIP,
                            clientPort
                    );
                    serverSocket.send(reply);
                    break;
                }
                case "date": {
                    LocalDateTime now = LocalDateTime.now();
                    String date = now.toLocalDate().toString();
                    DatagramPacket reply = new DatagramPacket(
                            date.getBytes(),
                            date.getBytes().length,
                            clientIP,
                            clientPort
                    );
                    serverSocket.send(reply);
                    break;
                }
                default: {
                    System.out.println("Invalid Client Request");
                    break;
                }
            }
        }
    }
}