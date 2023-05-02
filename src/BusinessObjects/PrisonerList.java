package BusinessObjects;

import Comparators.IFilter;
import DTOs.Prisoner;

import java.util.ArrayList;
import java.util.List;

public class PrisonerList {

    private final List<Prisoner> prisonerList;


    public PrisonerList(List<Prisoner> prisonerList) {
        this.prisonerList = new ArrayList<>(prisonerList);
    }

    public void add(Prisoner p) {
        this.prisonerList.add(p);
    }

    public List<Prisoner> filterBy(IFilter filter) {
        List<Prisoner> returnList = new ArrayList<Prisoner>();
        for (Prisoner p:this.prisonerList) {
            if(filter.matches(p)) returnList.add(p);
        }
        return returnList;
    }
}
