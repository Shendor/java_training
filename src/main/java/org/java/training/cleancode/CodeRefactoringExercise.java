package org.java.training.cleancode;

import org.java.training.rpggame.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeRefactoringExercise {

    List<NpcUnit> npcs;

    public CodeRefactoringExercise() {
        npcs = createNpcs();
    }

    public void movePlayer(NpcUnit player, Point point) {
        player.setPosition(point);

        List<NpcUnit> aggroNpc =
                npcs.stream()
                        .filter(npc -> {
                            double x = npc.getPosition().getX();
                            double y = npc.getPosition().getY();
                            int aggroArea = npc.getAggroArea();

                            return aggroArea >= Math.abs(point.getX() - x) && aggroArea >= Math.abs(point.getY() - y);
                        })
                        .collect(Collectors.toList());

        new GameWorld().attack(player, aggroNpc);
    }

    public List<NpcUnit> getNpcs() {
        return npcs;
    }

    // create default enemy units
    private List<NpcUnit> createNpcs() {
        List<NpcUnit> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            //create default skills with default values
            Attack attack = new Attack();
            attack.setAttackType(AttackType.Physical);
            attack.setDamage(20);
            attack.setCriticalChance(0.3f);
            attack.setCritDamage(1.5f);

            Protection prt = new Protection();
            prt.setProtectionFrom(AttackType.Physical);
            prt.setProtectionPoints(10);

            Resist resistFromPoison = new Resist();
            resistFromPoison.setResistType(DotType.Poison);

            DotAttack dotAttack = new DotAttack();
            dotAttack.setDotType(DotType.Poison);
            dotAttack.setDamageOverTick(1);
            dotAttack.setTicks(5);

            NpcUnit u = new NpcUnit("unit " + i);
            u.setPosition(new Point());
            u.setSkills(Arrays.asList(attack, dotAttack, prt, resistFromPoison));
            u.setHealth(100);
            list.add(u);
        }
        return list;
    }

    class GameWorld {

        public void attack(NpcUnit player, List<NpcUnit> units) {
            for (int i = 0; i < units.size(); i++) {
                NpcUnit u = units.get(i);
                for (int j = 0; j < player.getSkills().size(); j++) {
                    Skill skill = player.getSkills().get(j);
                    if (skill.isChanceSuccessfull()) {
                        if (skill instanceof Attack) {
                            Protection protection = null;
                            for (Skill npcSkill : u.getSkills()) {
                                if (npcSkill instanceof Protection &&
                                        ((Protection) npcSkill).getProtectionFrom() == ((Attack) skill).getAttackType()) {
                                    protection = (Protection) npcSkill;
                                    break;
                                }
                            }
                            float actualDamage = ((Attack) skill).getActualDamage();
                            if (protection != null) {
                                actualDamage -= protection.getProtectionPoints();
                            }
                            u.setHealth(u.getHealth() - actualDamage);
                        } else if (skill instanceof DotAttack) {
                            Resist resist = null;
                            for (Skill npcSkill : u.getSkills()) {
                                if (npcSkill instanceof Resist &&
                                        ((Resist) npcSkill).getResistType() == ((DotAttack) skill).getDotType()) {
                                    resist = (Resist) npcSkill;
                                    break;
                                }
                            }
                            if (resist == null || !resist.isChanceSuccessfull()) {
                                u.addDot(((DotAttack) skill).getTicks(), ((DotAttack) skill).getDamageOverTick());
                            }
                        }
                    }
                }
            }
        }
    }

}
