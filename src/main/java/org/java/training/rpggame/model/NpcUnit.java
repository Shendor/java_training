package org.java.training.rpggame.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NpcUnit {

    private String name;
    private Point position;
    private int aggroArea;
    private float health;
    private List<Skill> skills;
    private List<Dot> dots;

    public NpcUnit(String name) {
        this.name = name;
        dots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getAggroArea() {
        return aggroArea;
    }

    public void setAggroArea(int aggroArea) {
        this.aggroArea = aggroArea;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addDot(int ticks, float damage) {
        dots.add(new Dot(ticks, damage));
    }

    public List<Dot> getDots() {
        return dots;
    }

    class Dot {
        private int ticks;
        private float damage;

        public Dot(int ticks, float damage) {
            this.ticks = ticks;
            this.damage = damage;
        }

        public int getTicks() {
            return ticks;
        }

        public float getDamage() {
            return damage;
        }
    }
}
