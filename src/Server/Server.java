package Server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server started");

            while (true) {
                Socket socket = ss.accept();

                System.out.println("A client has connected");

                Scanner in = new Scanner(socket.getInputStream());
                String command = in.nextLine();

                System.out.println("Message recieved from client: " + command);

                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os,true);
                if (command.startsWith("Echo")) {
                    out.print("Echo back!");
                }
                else out.print("Please enter another command");
                out.flush();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}