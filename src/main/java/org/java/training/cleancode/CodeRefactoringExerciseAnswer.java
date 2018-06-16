package org.java.training.cleancode;

import org.java.training.rpggame.model.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CodeRefactoringExerciseAnswer {

    private List<NpcUnit> npcs;
    private List<SkillController> attackStrategy;

    public CodeRefactoringExerciseAnswer(EnemyRepository enemyRepository) {
        npcs = enemyRepository.createDefaultEnemies();

        attackStrategy = new ArrayList<>(2);
        attackStrategy.add(new AttackController());
//        attackStrategy.add(new DotSkillController());
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

        attackStrategy.forEach(attackStrategy -> attackStrategy.applySkillsToUnits(player, aggroNpc));
    }

    public List<NpcUnit> getNpcs() {
        return npcs;
    }

    class SkillHandlerFactory {
        private Map<Class<? extends Skill>, SkillHandler<? extends Skill>> skillControllers = new HashMap<>();

        public SkillHandlerFactory() {
            skillControllers.put(Attack.class, new AttackSkillHandler());
            skillControllers.put(DotAttack.class, new DotSkillHandler());
        }

        public SkillHandler<? super Skill> getSkillControllerFor(Class<? extends Skill> skillClass) {
            return (SkillHandler<? super Skill>) skillControllers.get(skillClass);
        }
    }

    interface SkillHandler<T extends Skill> {
        void applySkillOn(T skill, NpcUnit unitToApply);
    }

    class AttackSkillHandler implements SkillHandler<Attack> {

        @Override
        public void applySkillOn(Attack skill, NpcUnit unitToApply) {
            Protection protection = getProtectionFromAttack(unitToApply, skill);
            float actualDamage = skill.getActualDamage();
            if (protection != null) {
                actualDamage -= protection.getProtectionPoints();
            }
            unitToApply.setHealth(unitToApply.getHealth() - actualDamage);
        }

        private Protection getProtectionFromAttack(NpcUnit unit, Attack skill) {
            Protection protection = null;
            for (Skill npcSkill : unit.getSkills()) {
                if (npcSkill instanceof Protection &&
                        ((Protection) npcSkill).getProtectionFrom() == skill.getAttackType()) {
                    protection = (Protection) npcSkill;
                    break;
                }
            }
            return protection;
        }
    }

    class DotSkillHandler implements SkillHandler<DotAttack> {

        @Override
        public void applySkillOn(DotAttack skill, NpcUnit unit) {
            Resist resist = getResistFromAttack(unit, skill);
            if (resist == null || !resist.isChanceSuccessfull()) {
                unit.addDot(skill.getTicks(), skill.getDamageOverTick());
            }
        }

        private Resist getResistFromAttack(NpcUnit unit, DotAttack skill) {
            Resist resist = null;
            for (Skill npcSkill : unit.getSkills()) {
                if (npcSkill instanceof Resist &&
                        ((Resist) npcSkill).getResistType() == skill.getDotType()) {
                    resist = (Resist) npcSkill;
                    break;
                }
            }
            return resist;
        }
    }

    interface SkillController {

        void applySkillsToUnits(NpcUnit skillOwner, List<NpcUnit> units);
    }

    class AttackController implements SkillController {

        public void applySkillsToUnits(NpcUnit skillOwner, List<NpcUnit> units) {
            SkillHandlerFactory factory = new SkillHandlerFactory();
            for (NpcUnit unit : units) {
                skillOwner.getSkills().stream()
                        .filter(Skill::isChanceSuccessfull)
                        .forEach(skill -> factory.getSkillControllerFor(skill.getClass()).applySkillOn(skill, unit));
            }
        }
    }

    static class EnemyRepository {

        private static final int DEFAULT_ENEMIES_QUANTITY = 10;

        public List<NpcUnit> createDefaultEnemies() {
            List<NpcUnit> enemies = new ArrayList<>();

            for (int i = 0; i < DEFAULT_ENEMIES_QUANTITY; i++) {
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

                NpcUnit enemyUnit = new NpcUnit("unit " + i);
                enemyUnit.setPosition(new Point());
                enemyUnit.setHealth(100);
                enemyUnit.setSkills(Arrays.asList(attack, dotAttack, prt, resistFromPoison));
                enemies.add(enemyUnit);
            }
            return enemies;
        }
    }
}
