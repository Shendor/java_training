package org.java.training.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GameExample {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {

            GameBoardService gameBoardService = new GameBoardService();

            gameBoardService.createAndJoinTeams();

            gameBoardService.gameOver();
        }
    }

    private static class GameBoardService {

        private static final int TEAM_SIZE = 10;

        private ExecutorService executorService;
        private Team teamA;
        private Team teamB;

        public GameBoardService() {
            executorService = Executors.newFixedThreadPool(20);
            teamA = new Team();
            teamB = new Team();
        }

        public void createAndJoinTeams() {
            createAndJoinTeam(teamA, teamB, "A");
            createAndJoinTeam(teamB, teamA, "B");
        }

        public void gameOver() throws InterruptedException {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println(teamA.getTotalScore() + " - " + teamB.getTotalScore());
            System.out.println(teamA.getTotalHealth() + " - " + teamB.getTotalHealth());
        }

        private void createAndJoinTeam(Team teamToJoin, Team enemyTeam, String teamName) {
            for (int i = 0; i < TEAM_SIZE; i++) {
                executorService.execute(() -> {
                    Player player = new Player("Player" + teamName + "-" + Thread.currentThread().getId(), 5);
                    teamToJoin.joinPlayer(player);
                    while (teamToJoin.getPlayersInTeam() + enemyTeam.getPlayersInTeam() < 20) {
//                        System.out.println("Wait until players join...");
                    }
                    teamToJoin.hitEnemy(player, enemyTeam.getRandomPlayer());
                });
            }
        }
    }

    private static class Team implements Comparable<Team> {

        private static final Random random = new Random();

        private TeamScoreCounter teamScoreCounter;
        private List<Player> players;

        public Team() {
            players = new ArrayList<>();
            teamScoreCounter = new TeamScoreCounter();
        }

        public synchronized void joinPlayer(Player player) {
            players.add(player);
        }

        public synchronized Player getRandomPlayer() {
            int playerNumber = random.nextInt(players.size());
            return players.get(playerNumber == players.size() ? playerNumber - 1 : playerNumber);
        }

        public void hitEnemy(Player player, Player enemy) {
            enemy.receiveDamage(player.getAttackStrength());
            teamScoreCounter.increment();
        }

        public int getTotalScore() {
            return teamScoreCounter.getTotal();
        }

        public synchronized int getPlayersInTeam() {
            return players.size();
        }

        public synchronized int getTotalHealth() {
            return players.stream().map(Player::getHealth).reduce(0, (h1, h2) -> h1 + h2);
        }

        @Override
        public int compareTo(Team gameBoardService) {
            return Integer.compare(getTotalScore(), gameBoardService.getTotalScore());
        }
    }

    private static class Player {
        private String name;
        private AtomicInteger health;
        private int attackStrength;

        public Player(String name, int attackStrength) {
            this.name = name;
            this.attackStrength = attackStrength;
            this.health = new AtomicInteger(100);
        }

        public String getName() {
            return name;
        }

        public int getAttackStrength() {
            return attackStrength;
        }

        public int getHealth() {
            return health.get();
        }

        public void receiveDamage(int damage) {
            health.compareAndSet(health.get(), health.get() - damage);
        }
    }

    private static class TeamScoreCounter {
        private AtomicInteger counter = new AtomicInteger(0);

        public void increment() {
            counter.incrementAndGet();
        }

        public int getTotal() {
            return counter.get();
        }
    }
}
