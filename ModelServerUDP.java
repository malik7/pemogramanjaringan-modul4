import java.net.*;
import java.util.*;

public class ModelServerUDP {
    private static final int PORT = 22222;
    private static Set<InetAddress> clients = new HashSet<>();

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server UDP pada port " + PORT);
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                InetAddress senderAddress = packet.getAddress();
                clients.add(senderAddress);
                System.out.println("Pesan diterima: " + message + " dari " + senderAddress);

                for (InetAddress client : clients) {
                    DatagramPacket responsePacket = new DatagramPacket(
                            packet.getData(), packet.getLength(), client, packet.getPort());
                    socket.send(responsePacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
