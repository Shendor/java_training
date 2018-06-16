package org.java.training.cleancode.codesmells;

public class MessageChainExample {

    class UnitUiController {
        public void displayUnitClass(Unit unit) {
            UnitClass unitClass = unit.getUnitDetails().getUnitClassDetails().getUnitClass();
            // display unit class
        }
    }

    class PartyCreationController {
        public void addUnitToCurrentParty(Unit unit) {
            UnitClass unitClass = unit.getUnitDetails().getUnitClassDetails().getUnitClass();
            // check if unit class can be added to the party
        }
    }

    class Unit {
        private UnitDetails unitDetails;

        public Unit(UnitDetails unitDetails) {
            this.unitDetails = unitDetails;
        }

        public UnitDetails getUnitDetails() {
            return unitDetails;
        }
    }

    class UnitDetails {
        private UnitClassDetails unitClassDetails;

        public UnitClassDetails getUnitClassDetails() {
            return unitClassDetails;
        }

        public void setUnitClassDetails(UnitClassDetails unitClassDetails) {
            this.unitClassDetails = unitClassDetails;
        }
    }

    class UnitClassDetails {
        private UnitClass unitClass;

        public UnitClass getUnitClass() {
            return unitClass;
        }

        public void setUnitClass(UnitClass unitClass) {
            this.unitClass = unitClass;
        }
    }

    enum UnitClass {
        Mage,
        Warrior
    }

}
