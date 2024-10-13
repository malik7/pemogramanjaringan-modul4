import java.net.*;
import java.util.Scanner;

public class ModelClientUDP {
    private static final int PORT = 22222;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan pesan yang akan dikirim (ketik 'q' untuk keluar):");
            String message;

            while (!(message = scanner.nextLine()).equalsIgnoreCase("exit")) {
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), PORT);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
