package BusinessObjects;

import DAOs.MySqlPrisonerDao;
import DAOs.PrisonerDaoInterface;
import DTOs.Prisoner;
import Exceptions.DaoException;

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
                    2 - Display a prisoner by their ID""");
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
                        int id;
                        System.out.print("Enter the ID to search by: ");
                        try {
                            id = userInput.nextInt();
                            System.out.println(IUserDao.findPrisonerById(id));
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
}