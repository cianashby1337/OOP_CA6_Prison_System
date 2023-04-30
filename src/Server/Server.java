package Server;

import DAOs.MySqlPrisonerDao;
import DAOs.PrisonerDaoInterface;
import Exceptions.DaoException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server started");

            while (true) {
                Socket socket = ss.accept();
                PrisonerDaoInterface IPrisonerDao = new MySqlPrisonerDao();

                System.out.println("A client has connected");

                Scanner in = new Scanner(socket.getInputStream());
                String command = in.next();

                System.out.println("Message recieved from client: " + command);

                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os,true);
                if (command.toLowerCase().startsWith("find"))
                {
                    try {
                        out.print("Found " + IPrisonerDao.findPlayerByIdJson(in.nextInt()));
                    }
                    catch (InputMismatchException e) {
                        out.print("Invalid ID type provided");
                    }
                }
                else if(command.equalsIgnoreCase("rollcall")) {
                    out.print("rollcall" + IPrisonerDao.findAllPlayersJson());
                }
                else out.print("Please enter another command");
                out.flush();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
