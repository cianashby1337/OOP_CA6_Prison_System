package DTOs;

import java.sql.Date;

/**                                                     OOP Feb 2022
 *  Data Transfer Object (DTO)
 *
 * This POJO (Plain Old Java Object) is called the Data Transfer Object (DTO).
 * (or, alternatively, the Model Object or the Value Object).
 * It is used to transfer data between the DAO and the Business Objects.
 * Here, it represents a row of data from the User database table.
 * The DAO creates and populates a User object (DTO) with data retrieved from
 * the resultSet and passes the User object to the Business Layer.
 *
 * Collections of DTOs( e.g. ArrayList<User> ) may also be passed
 * between the Data Access Layer (DAOs) and the Business Layer objects.
 */

public class Prisoner
{
    private int prisoner_id;
    private String first_name;
    private String last_name;
    private double level_of_misconduct;
    private Date imprisonment_date;
    private Date release_date;

    public Prisoner(int userId, String first_name, String last_name, double level_of_misconduct, Date imprisonment_date, Date release_date)
    {
        //For retrieving a prisoner from the system
        this.prisoner_id = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.level_of_misconduct = level_of_misconduct;
        this.imprisonment_date = imprisonment_date;
        this.release_date = release_date;
    }

    public Prisoner( String first_name, String last_name, Date release_date, Date imprisonment_date)
    {
        this.prisoner_id = 0;
        this.first_name = first_name;
        this.last_name = last_name;
        this.level_of_misconduct = 0;
        this.imprisonment_date = imprisonment_date;
        this.release_date = release_date;
    }


    public int getPrisoner_id()
    {
        return prisoner_id;
    }

    public void setPrisoner_id(int prisoner_id)
    {
        this.prisoner_id = prisoner_id;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public double getLevel_of_misconduct()
    {
        return level_of_misconduct;
    }

    public void setLevel_of_misconduct(double level_of_misconduct)
    {
        this.level_of_misconduct = level_of_misconduct;
    }

    public Date getImprisonment_date() {
        return imprisonment_date;
    }

    public void setImprisonment_date(Date imprisonment_date) {
        this.imprisonment_date = imprisonment_date;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString()
    {
        return "Prisoner" + prisoner_id + "\nForename: " + first_name + "\nSurname: " + last_name +
                "\nLOM: " + level_of_misconduct +
                "\nImprisonment Date: " + imprisonment_date + "\nRelease Date: " + release_date + "\n";
    }

}
