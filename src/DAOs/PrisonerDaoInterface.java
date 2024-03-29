package DAOs;

/** OOP Feb 2022
 * UserDaoInterface
 *
 * Declares the methods that all UserDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophisticated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */


import DTOs.Prisoner;
import Exceptions.DaoException;

import java.util.List;

public interface PrisonerDaoInterface
{
    List<Prisoner> findAllPrisoners() throws DaoException;

    Prisoner findPrisonerById(int id) throws DaoException;

    int deletePrisonerById(int id) throws DaoException;

    Prisoner addPrisoner(Prisoner addedPrisoner) throws DaoException;

    String findAllPlayersJson() throws DaoException;

    String findPlayerByIdJson(int id) throws DaoException;
}

