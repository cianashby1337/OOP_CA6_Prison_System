package DAOs;

/** OOP Feb 2022
 *
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 * The Business Logic layer is only permitted to access the database by calling
 * methods provided in the Data Access Layer - i.e. by callimng the DAO methods.
 *
 */



import DTOs.Prisoner;
import Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySqlPrisonerDao extends MySqlDao implements PrisonerDaoInterface
{


    @Override
    public List<Prisoner> findAllPrisoners() throws DaoException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Prisoner> prisonerList = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT * FROM prisoners";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                int prisoner_id = resultSet.getInt("prisoner_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                double level_of_misconduct = resultSet.getDouble("level_of_misconduct");
                Date imprisonment_date = resultSet.getDate("imprisonment_date");
                Date release_date = resultSet.getDate("release_date");
                Prisoner u = new Prisoner(prisoner_id, first_name, last_name, level_of_misconduct, imprisonment_date, release_date);
                prisonerList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllPrisonerresultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return prisonerList;
    }

    @Override
    public Prisoner findPrisonerById(int id) throws DaoException {
        return null;
    }
}

