package BusinessObjects;

import DAOs.MySqlPrisonerDao;
import DAOs.PrisonerDaoInterface;
import DTOs.Prisoner;
import Exceptions.DaoException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrisonerDaoInterface IUserDao = new MySqlPrisonerDao();
        Scanner userInput = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("""
                    
                    Choose an option from below:
                    0 - Exit program
                    1 - Display all prisoners
                    2 - Display a prisoner by their ID
                    3 - Delete a prisoner by their ID
                    4 - Add a new prisoner""");
            try {
                choice = userInput.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting program...");
                        return;
                    case 1:
                        try {
                            List<Prisoner> prisoners = IUserDao.findAllPrisoners();
                            for (Prisoner prisoner:prisoners) {
                                System.out.println(prisoner.toString());
                            }
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        int idToGet;
                        System.out.print("Enter the ID to search by: ");
                        try {
                            idToGet = userInput.nextInt();
                            System.out.println(IUserDao.findPrisonerById(idToGet));
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
                            if (IUserDao.deletePrisonerById(idToDelete) == 1) System.out.println("Prisoner removed from system");
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
                            if (IUserDao.addPrisoner(createPrisoner(userInput)) == 1) System.out.println("Prisoner added to system");
                            else System.out.println("Prisoner not added to system");
                        }
                        catch (DaoException e) {
                            e.printStackTrace();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter a number");
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
            userInput.nextLine();
            System.out.println("Enter the first name of the convict");
            String first_name = userInput.nextLine();

            System.out.println("Enter the last name of the convict");
            String last_name = userInput.nextLine();

            System.out.println("Enter the release date of the convict in the form yyyy-mm-dd (e.g 2025-03-11)");
            String release_date = userInput.nextLine();
            //regex for release date here

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

}