package org.java.training.rpggame.model;

import java.util.Random;

public class Attack extends Skill {

    private static final Random CRIT_CHANCE_RANDOM = new Random();

    private float damage;
    private float criticalChance;
    private float critDamage;
    private AttackType attackType;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(float criticalChance) {
        this.criticalChance = criticalChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public float getActualDamage() {
        return isCriticalChanceSuccessfull() ? (1 + critDamage) * damage : damage;
    }

    private boolean isCriticalChanceSuccessfull() {
        return CRIT_CHANCE_RANDOM.nextFloat() < criticalChance;
    }
}
