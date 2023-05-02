package Comparators;

import DTOs.Prisoner;

public class prisonerLevelOfMisconductComparator implements IFilter {

    private double minimumLOM;

    public prisonerLevelOfMisconductComparator(double minimumLOM) {
        this.minimumLOM = minimumLOM;
    }
    @Override
    public boolean matches(Object obj) {
        Prisoner prisoner = (Prisoner) obj;
        return prisoner.getLevel_of_misconduct() >= minimumLOM;
    }
}
