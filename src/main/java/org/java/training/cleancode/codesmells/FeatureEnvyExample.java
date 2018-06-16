package org.java.training.cleancode.codesmells;

public class FeatureEnvyExample {

    class Battle {

        public void damageUnit(Unit attackingUnit, Unit defendingUnit) {
            int totalStrength = attackingUnit.getBaseStrength() +
                    attackingUnit.getWeaponPower() +
                    attackingUnit.getEnhancedPower();

            int totalDefence = defendingUnit.getBaseStrength() +
                    defendingUnit.getWeaponPower() +
                    defendingUnit.getEnhancedPower();

            defendingUnit.setHealth(defendingUnit.getHealth() - totalStrength - totalDefence);
        }
    }

    class Unit {
        private int baseStrength;
        private int weaponPower;
        private int enhancedPower;
        private int baseDefence;
        private int armorDefense;
        private int enhancedDefence;

        private int health;

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public int getBaseStrength() {
            return baseStrength;
        }

        public void setBaseStrength(int baseStrength) {
            this.baseStrength = baseStrength;
        }

        public int getWeaponPower() {
            return weaponPower;
        }

        public void setWeaponPower(int weaponPower) {
            this.weaponPower = weaponPower;
        }

        public int getEnhancedPower() {
            return enhancedPower;
        }

        public void setEnhancedPower(int enhancedPower) {
            this.enhancedPower = enhancedPower;
        }

        public int getBaseDefence() {
            return baseDefence;
        }

        public void setBaseDefence(int baseDefence) {
            this.baseDefence = baseDefence;
        }

        public int getArmorDefense() {
            return armorDefense;
        }

        public void setArmorDefense(int armorDefense) {
            this.armorDefense = armorDefense;
        }

        public int getEnhancedDefence() {
            return enhancedDefence;
        }

        public void setEnhancedDefence(int enhancedDefence) {
            this.enhancedDefence = enhancedDefence;
        }
    }
}
