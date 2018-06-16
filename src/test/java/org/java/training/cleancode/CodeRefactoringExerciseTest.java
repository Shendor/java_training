package org.java.training.cleancode;

import com.vocalink.training.rpggame.model.Attack;
import com.vocalink.training.rpggame.model.AttackType;
import com.vocalink.training.rpggame.model.DotAttack;
import com.vocalink.training.rpggame.model.DotType;
import com.vocalink.training.rpggame.model.NpcUnit;
import com.vocalink.training.rpggame.model.Protection;
import com.vocalink.training.rpggame.model.Resist;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CodeRefactoringExerciseTest {

    private CodeRefactoringExercise codeRefactoringExercise;
    private NpcUnit player;

    @Before
    public void setUp() throws Exception {
        codeRefactoringExercise = new CodeRefactoringExercise();
        player = new NpcUnit("player");
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_AndPlayerHasOnlyProtectionSkill_ThenEnemiesNotAttacked() throws Exception {
        player.setSkills(Collections.singletonList(createProtection(30, AttackType.Physical)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(100f, npc.getHealth(), 0.001);
        }
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_ThenPlayerAttacksAllEnemiesWithPhysicalAttack() throws Exception {
        player.setSkills(Collections.singletonList(createPhysicalAttackSkill(30, AttackType.Physical)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(80f, npc.getHealth(), 0.001);
        }
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_ThenPlayerAttacksAllEnemiesWithFireAttack() throws Exception {
        player.setSkills(Collections.singletonList(createPhysicalAttackSkill(40, AttackType.Fire)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(60f, npc.getHealth(), 0.001);
        }
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_AndEnemiesHaveProtectionFromPoison_ThenNotGotDotFromPlayer() throws Exception {
        player.setSkills(Collections.singletonList(createDotAttack(DotType.Poison, 1)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(0, npc.getDots().size());
        }
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_AndEnemiesNotHaveProtectionFromBleed_ThenGotDotFromPlayer() throws Exception {
        player.setSkills(Collections.singletonList(createDotAttack(DotType.Bleed, 1)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(1, npc.getDots().size());
        }
    }

    @Test
    public void testMovePlayer_WhenAllEnemiesSpottedHim_AndPlayerHasDotWithNoChanceOfSuccess_ThenEnemiesNotReceiveDot() throws Exception {
        player.setSkills(Collections.singletonList(createDotAttack(DotType.Bleed, 0)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(0, npc.getDots().size());
        }
    }

    @Test
    public void testPlayerDoesPoisonDotAttack_AndEnemiesHasResistPoisonWithNoChanceOfSuccess_ThenEnemiesReceiveDot() throws Exception {
        codeRefactoringExercise.getNpcs().stream()
                .flatMap(npc -> npc.getSkills().stream())
                .filter(skill -> skill instanceof Resist)
                .forEach(skill -> skill.setChance(0));

        player.setSkills(Collections.singletonList(createDotAttack(DotType.Poison, 1)));

        codeRefactoringExercise.movePlayer(player, new Point(0, 0));

        for (NpcUnit npc : codeRefactoringExercise.getNpcs()) {
            assertEquals(1, npc.getDots().size());
        }
    }

    private Attack createPhysicalAttackSkill(float damage, AttackType attackType) {
        Attack attack = new Attack();
        attack.setDamage(damage);
        attack.setAttackType(attackType);
        return attack;
    }

    private DotAttack createDotAttack(DotType dotType, float chance) {
        DotAttack dotAttack = new DotAttack();
        dotAttack.setDotType(dotType);
        dotAttack.setChance(chance);
        return dotAttack;
    }

    private Protection createProtection(float protectionPoints, AttackType protectFrom) {
        Protection protection = new Protection();
        protection.setProtectionPoints(protectionPoints);
        protection.setProtectionFrom(protectFrom);

        return protection;
    }
}