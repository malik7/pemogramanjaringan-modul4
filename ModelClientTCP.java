import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ModelClientTCP {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 11111;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan path file yang akan dikirim: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        String fileName = file.getName();

        try (Socket socket = new Socket(serverAddress, port);
             FileInputStream fileInputStream = new FileInputStream(filePath);
             OutputStream outputStream = socket.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            
            dataOutputStream.writeUTF(fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File '" + fileName + "' berhasil dikirim ke server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}