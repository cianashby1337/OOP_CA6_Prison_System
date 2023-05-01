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
    void findPrisonerById() throws DaoException {
        System.out.println("Test 2 - Assert that the found prisoner's ID no. matches the searched number (1)");
        Prisoner testPrisoner = IPrisonerDAO.findPrisonerById(1);
        if (testPrisoner == null) fail("Returned value was null. Ensure the database contains a prisoner with an id of 1 before assuming failure");
        assertTrue(testPrisoner.getPrisoner_id() == 1,"A prisoner was returned, but an ID mismatch was detected");
    }

    @org.junit.jupiter.api.Test
    void deletePrisonerById() throws DaoException {
        System.out.println("Test 3 - Assert a removal has taken place within a list (seeks prisoner with id no. 1)");
        assertEquals(1,IPrisonerDAO.deletePrisonerById(1),"Ensure there was a prisoner with an id of 1 to be removed, before assuming failure");
    }

    @org.junit.jupiter.api.Test
    void addPrisoner() {
    }
}