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

import java.util.List;


public class MySqlUserDao extends MySqlDao implements UserDaoInterface
{


    @Override
    public List<Prisoner> findAllPrisoners() throws DaoException {
        return null;
    }

    @Override
    public Prisoner findPrisonerById(int id) throws DaoException {
        return null;
    }
}

