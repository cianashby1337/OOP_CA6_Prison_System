package Server;

import DAOs.MySqlPrisonerDao;
import DAOs.PrisonerDaoInterface;
import DTOs.Prisoner;
import Exceptions.DaoException;
import com.google.gson.Gson;

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
                String command = in.nextLine();

                System.out.println("Message recieved from client: " + command);

                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os,true);
                if (command.toLowerCase().startsWith("find"))
                {
                    try {
                        String result = IPrisonerDao.findPlayerByIdJson(Integer.parseInt(command.substring(5)));
                        if (result.equals("No prisoner was found")) out.print(result);
                        else out.print("Found " + result);
                    }
                    catch (NumberFormatException e) {
                        out.print("Invalid ID type provided");
                    }
                }
                else if(command.equalsIgnoreCase("rollcall"))
                {
                    out.print("rollcall" + IPrisonerDao.findAllPlayersJson());
                }
                else if (command.toLowerCase().startsWith("imprison"))
                {
                    Gson gsonParser = new Gson();
                    Prisoner p = IPrisonerDao.addPrisoner(gsonParser.fromJson(command.substring(8), Prisoner.class));
                    if (p == null) out.print("There was an error in the attempt to insert a new prisoner");
                    else out.print("imprison" + gsonParser.toJson(p));
                }
                else if (command.toLowerCase().startsWith("release")) {
                    if (command.length() < 9) out.print("Please enter something after the \"release\" command");
                    else {
                        if(IPrisonerDao.deletePrisonerById(Integer.parseInt(command.substring(8))) == 1) {
                            out.print("release" + command.substring(8) + " was released");
                        }
                        else out.print("release" + command.substring(8) + " was not found, and could not be deleted");
                    };
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
