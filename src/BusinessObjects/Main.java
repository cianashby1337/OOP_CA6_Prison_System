package BusinessObjects;

import Comparators.prisonerLevelOfMisconductComparator;
import DAOs.MySqlPrisonerDao;
import DAOs.PrisonerDaoInterface;
import DTOs.Prisoner;
import Exceptions.DaoException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        PrisonerDaoInterface IPrisonerDao = new MySqlPrisonerDao();
        HashSet<Integer> cachedIds = new HashSet<>();

        Scanner userInput = new Scanner(System.in);
        int choice, idToGet;
        while (true) {
            System.out.println("""
                    
                    Choose an option from below:
                    0 - Exit program
                    1 - Display all prisoners
                    2 - Display a prisoner by their ID
                    3 - Delete a prisoner by their ID
                    4 - Add a new prisoner
                    5 - Display prisoners to be sent to solitary (level of misconduct 3.15 and over
                    6 - Display cached prisoner IDs
                    7 - Display all prisoners, in JSON format
                    8 - Display a prisoner by their ID, in JSON format
                    9 - Test Client/Server connectivity""");
            try {
                choice = userInput.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting program...");
                        return;
                    case 1:
                        try {
                            List<Prisoner> prisoners = IPrisonerDao.findAllPrisoners();
                            for (Prisoner prisoner:prisoners) {
                                System.out.println(prisoner.toString());
                                cachedIds.add(prisoner.getPrisoner_id());
                            }
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.print("Enter the ID to search by: ");
                        try {
                            idToGet = userInput.nextInt();
                            if (cachedIds.contains(idToGet)) System.out.println(IPrisonerDao.findPrisonerById(idToGet));
                            else System.out.println("""
                        ID not found in cache, try retrieving all prisoners (1 - Display all prisoners).
                        If a prisoner by that ID is still not found, they probably don't exist in the system""");
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
                        }
                        break;
                    case 3:
                        int idToDelete;
                        System.out.print("Enter the ID to search by: ");
                        try {
                            idToDelete = userInput.nextInt();
                            if (IPrisonerDao.deletePrisonerById(idToDelete) == 1) System.out.println("Prisoner removed from system");
                            else System.out.println("Prisoner not removed from system");
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
                        }
                        break;

                    case 4:
                        try{
                            userInput.nextLine();
                            Prisoner p = IPrisonerDao.addPrisoner(createPrisoner(userInput));
                            if (p != null) {
                                System.out.println("Prisoner added to system. Displaying their record now");
                                System.out.println(p);
                            }
                            else System.out.println("Prisoner not added to system");
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
                        }
                        break;

                    case 5:
                        try {
                            PrisonerList list = new PrisonerList(IPrisonerDao.findAllPrisoners());
                            List<Prisoner> finalList = new ArrayList<>(list.filterBy(new prisonerLevelOfMisconductComparator(3.15)));
                            for (Prisoner p: finalList) {
                                System.out.println(p);
                            }
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        for (Integer id:cachedIds) {
                            System.out.println(id);
                        }
                        break;
                    case 7:
                        try {
                            System.out.println(IPrisonerDao.findAllPlayersJson());
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        System.out.print("Enter the ID to search by: ");
                        try {
                            idToGet = userInput.nextInt();
                            System.out.println(IPrisonerDao.findPlayerByIdJson(idToGet));
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
                        }
                        break;
                    case 9:
                        userInput.nextLine();
                        try {
                            Socket socket = new Socket("localhost",8080);

                            System.out.println("Connected to server");

                            System.out.println("""
                                    Please one of the following commands for any of their operations:
                                    Find (id) - Find a prisoner by ID
                                    Rollcall - Display all prisoners
                                    Imprison - Add a prisoner
                                    Release - Delete a prisoner by ID
                                    """);

                            String command = userInput.nextLine();

                            OutputStream os = socket.getOutputStream();
                            PrintWriter out = new PrintWriter(os,true);
                            if (command.equalsIgnoreCase("imprison")) {
                                Gson gsonParser = new Gson();
                                String prisonerJson;
                                prisonerJson = gsonParser.toJson(createPrisoner(userInput), Prisoner.class);
                                out.write(command+prisonerJson+"\n");
                            }
                            else out.write(command+"\n");
                            out.flush();

                            Scanner inStream = new Scanner(socket.getInputStream());

                            String input = inStream.nextLine();
                            if (input.toLowerCase().startsWith("found")) {
                                Prisoner p = prisonerJSONToString(input.substring(5));
                                System.out.println(p);
                            }
                            else if (input.toLowerCase().startsWith("rollcall")) {
                                List<Prisoner> prisoner = prisonerJSONToList(input);
                                for (Prisoner p : prisoner) {
                                    System.out.println(p);
                                }
                            }
                            else if (input.toLowerCase().startsWith("imprison")) {
                                Prisoner p = prisonerJSONToString(input.substring(8));
                                System.out.println(p);
                            }
                            else if (input.toLowerCase().startsWith("release")) {
                                System.out.println(input.substring(7));
                            }
                            else System.out.println("Server says: " + input);
                            out.close();
                            inStream.close();
                            socket.close();
                        } catch (UnknownHostException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        System.out.println("Invalid number, please try again");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Incorrect entry, please try again");
                userInput.next();
            }
        }

    }
    public static Prisoner createPrisoner(Scanner userInput) {
        try {
            System.out.println("Enter the first name of the convict");
            String first_name = userInput.nextLine();
            if(!nameValidation(first_name)) return null;

            System.out.println("Enter the last name of the convict");
            String last_name = userInput.nextLine();
            if(!nameValidation(last_name)) return null;

            System.out.println("Enter the release date of the convict in the form yyyy-mm-dd with a real year, day and month (e.g 2025-03-11)");
            String release_date = userInput.nextLine();

            // I got assistance with regex for release date from here https://www.regular-expressions.info/dates.html
            if(!release_date.matches("2[0-9][0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])")) {
                System.out.println("The date of release is not in the correct format");
                if (!release_date.contains("-")) System.out.println("> No hyphens (-) were included, you will need to use hyphens to separate the year-month-day");
                if (!release_date.matches("[0-9]")) System.out.println("> No numbers were included. You must provide numbers representing the year, month and day");
                return null;
            }

            Date checked_release_date = Date.valueOf(release_date);

            //The code for the imprisonment date code came from a mix of an intellij suggestion for Date.valueOf()
            //and an example from this link: https://www.javatpoint.com/java-get-current-date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            Date imprisonment_date = Date.valueOf(dtf.format(now));

            //Check if release date is less than or equal to imprisonment date, do not return if true
            if (imprisonment_date.after(checked_release_date)) {
                System.out.println("The release date cannot be before the current date");
                return null;
            }
            else if (imprisonment_date.equals(checked_release_date)) {
                System.out.println("The release date cannot be the same as today");
                return null;
            }
            return new Prisoner(first_name,last_name,checked_release_date,imprisonment_date);
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please try again");
        }
        catch (IllegalArgumentException e) {
            System.out.println("The date was not correct");
        }
        return null;
    }

    public static boolean nameValidation(String name) {
        if(!name.matches("^([A-Za-zÁ-Úá-ú -])+$") || name.length() > 30) {
            if (!name.matches("^([A-Za-zÁ-Úá-ú -])+$")) System.out.println("> The name should only contain A-z, Á-z, spaces and hyphens");
            if(name.length() > 30) System.out.println("> The name cannot be greater than 30 characters");
            return false;
        }
        return true;
    }

    public static Prisoner prisonerJSONToString(String json) {
        Gson gsonParser = new Gson();
        return gsonParser.fromJson(json, Prisoner.class);
    }
    public static List<Prisoner> prisonerJSONToList(String json) {
        List<Prisoner> prisonerList = new ArrayList<>();
        json = json.substring(8);
        Gson gsonParser = new Gson();
        while (json.length() > 0) {
            prisonerList.add(gsonParser.fromJson(json.substring(0,json.indexOf("}")+1), Prisoner.class));
            json = json.substring(json.indexOf("}")+1);
        }
        return prisonerList;
    }

}