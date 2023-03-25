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
                Prisoner p = getSinglePrisoner(resultSet);
                prisonerList.add(p);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllPrisoners() " + e.getMessage());
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
                throw new DaoException("findAllPrisoners() " + e.getMessage());
            }
        }
        return prisonerList;
    }

    @Override
    public Prisoner findPrisonerById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Prisoner prisoner = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM prisoners WHERE prisoner_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                prisoner = getSinglePrisoner(resultSet);
                }
        } catch (SQLException e)
        {
            throw new DaoException("findPrisonerById() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findPrisonerById() " + e.getMessage());
            }
        }
        return prisoner;     // reference to User object, or null value
    }

    @Override
    public int deletePrisonerById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "DELETE FROM prisoners WHERE prisoner_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e)
        {
            throw new DaoException("deletePrisonerById() " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("deletePrisonerById() " + e.getMessage());
            }
        }
    }

    @Override
    public Prisoner addPrisoner(Prisoner addedPrisoner) throws DaoException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Prisoner prisoner = null;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "INSERT INTO `prisoners`(`prisoner_id`, `first_name`, `last_name`, `level_of_misconduct`, `imprisonment_date`, `release_date`) VALUES (0,?,?,0,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, addedPrisoner.getFirst_name());
            ps.setString(2, addedPrisoner.getLast_name());
            ps.setDate(3, addedPrisoner.getImprisonment_date());
            ps.setDate(4, addedPrisoner.getRelease_date());
            int result = ps.executeUpdate();

            if (result == 1) {
                connection = this.getConnection();
                query = "SELECT * FROM prisoners WHERE first_name = ? && last_name = ? && imprisonment_date = ? && release_date = ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, addedPrisoner.getFirst_name());
                ps.setString(2, addedPrisoner.getLast_name());
                ps.setDate(3, addedPrisoner.getImprisonment_date());
                ps.setDate(4, addedPrisoner.getRelease_date());
                resultSet = ps.executeQuery();
                if (resultSet.next())
                {
                    prisoner = getSinglePrisoner(resultSet);
                }
            }
            else return null;
        } catch (SQLException e)
        {
            throw new DaoException("addPrisoner() " + e.getMessage());
        } catch (NullPointerException e) {
            return null;
        }finally
        {
            try {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("addPrisoner() " + e.getMessage());
            }
        }
        return prisoner;
    }

    Prisoner getSinglePrisoner(ResultSet resultSet) throws SQLException {
        int prisoner_id = resultSet.getInt("prisoner_id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        double level_of_misconduct = resultSet.getDouble("level_of_misconduct");
        Date imprisonment_date = resultSet.getDate("imprisonment_date");
        Date release_date = resultSet.getDate("release_date");
        return new Prisoner(prisoner_id, first_name, last_name, level_of_misconduct, imprisonment_date, release_date);
    }
}

