package DAOs;

import DTOs.Prisoner;
import Exceptions.DaoException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlPrisonerDaoTest {
    PrisonerDaoInterface IPrisonerDAO = new MySqlPrisonerDao();

    @org.junit.jupiter.api.Test
    void findAllPrisoners() throws DaoException {
        System.out.println("Test 1 - Assert that an empty list is not returned");
        List<Prisoner> prisonerList = IPrisonerDAO.findAllPrisoners();
        assertTrue(prisonerList.size() > 0, "Ensure that the database is empty before assuming failure");
    }

    @org.junit.jupiter.api.Test
    void findPrisonerById() {
    }

    @org.junit.jupiter.api.Test
    void deletePrisonerById() {
    }

    @org.junit.jupiter.api.Test
    void addPrisoner() {
    }
}