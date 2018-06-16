package org.java.training.rpggame.model;

import java.util.Random;

public abstract class Skill {

    private static final Random CHANCE_RANDOM = new Random();

    private int level;
    private float chance;

    public Skill() {
        chance = 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }

    public boolean isChanceSuccessfull() {
        return CHANCE_RANDOM.nextFloat() < chance;
    }
}

interface DamageType {

}

