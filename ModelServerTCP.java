import java.io.*;
import java.net.*;

public class ModelServerTCP {
    public static void main(String[] args) {
        int port = 11111;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server TCP pada port: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {

            String fileName = dataInputStream.readUTF();
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File '" + fileName + "' berhasil diterima dari " + clientSocket.getInetAddress());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}