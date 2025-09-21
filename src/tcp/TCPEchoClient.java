package tcp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class TCPEchoClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Need <serverIP> and <serverPort>.");
            return;
        }
        int serverPort = Integer.parseInt(args[1]);
        Scanner keyboard = new Scanner (System.in);
        String message = keyboard.nextLine();

        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(args[0], serverPort));

        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        channel.write(buffer);

        //wait to receive server reply
        ByteBuffer replyBuffer = ByteBuffer.allocate(1024);

        //receive the reply from the server through the channel
        // and store the reply into the buffer
        // bytesRead tells the actual number of bytes just received
        // the data read from the channel is actually put into the byteBuffer
        int bytesRead = channel.read(replyBuffer);

        // close socket, all operations from now on are local
        channel.close();

        //!! flip the buffer mode from writing into the buffer to reading it
        replyBuffer.flip();

        byte[] byteArray = new byte[bytesRead];

        //read data from the buffer into the array
        replyBuffer.get(byteArray);

        System.out.println(new String(byteArray));

    }
}

