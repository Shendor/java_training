package org.java.training.rpggame.model;

public class Protection extends Skill {

    private float protectionPoints;
    private AttackType protectionFrom;

    public float getProtectionPoints() {
        return protectionPoints;
    }

    public void setProtectionPoints(float protectionPoints) {
        this.protectionPoints = protectionPoints;
    }

    public AttackType getProtectionFrom() {
        return protectionFrom;
    }

    public void setProtectionFrom(AttackType protectionFrom) {
        this.protectionFrom = protectionFrom;
    }
}
