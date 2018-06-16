package org.java.training.rpggame.model;

public class DotAttack extends Skill {

    private float damageOverTick;
    private int ticks;
    private DotType dotType;

    public float getDamageOverTick() {
        return damageOverTick;
    }

    public void setDamageOverTick(float damageOverTick) {
        this.damageOverTick = damageOverTick;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public DotType getDotType() {
        return dotType;
    }

    public void setDotType(DotType dotType) {
        this.dotType = dotType;
    }
}
