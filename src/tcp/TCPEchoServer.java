package tcp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TCPEchoServer {
    public static void main(String[] args) throws Exception {
        int port = 3000;

        ServerSocketChannel listeningSocket = ServerSocketChannel.open();

        listeningSocket.bind(new InetSocketAddress(port));

        while (true){
            // accept the client connection request
            // and establish a dedicated channel with the new client
            //serverChannel represents this channel
            SocketChannel serverChannel = listeningSocket.accept();

            ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
            int bytesRead = serverChannel.read(clientBuffer);

            clientBuffer.flip();

            byte[] byteArray = new byte[bytesRead];

            clientBuffer.get(byteArray);

            String clientMessage = new String (byteArray);

            System.out.println(clientMessage);

            ByteBuffer replyBuffer = ByteBuffer.wrap(clientMessage.getBytes());

            serverChannel.write(replyBuffer);

            serverChannel.close();
        }
    }
}
