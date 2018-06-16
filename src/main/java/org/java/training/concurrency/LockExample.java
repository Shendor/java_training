package org.java.training.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockExample {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public static void main(String[] args) {

        MaterialsStockpile materialsStockpile = new MaterialsStockpile();
        materialsStockpile.addMaterialToStockpile(new Material("Iron", 1000));
        materialsStockpile.addMaterialToStockpile(new Material("Wood", 2000));
        materialsStockpile.addMaterialToStockpile(new Material("Crystal", 3000));

        ExcavatedMaterialsMonitor excavatedMaterialsMonitor = new ExcavatedMaterialsMonitor(materialsStockpile);
        excavatedMaterialsMonitor.monitorExcavatedMaterials();

        WorkerUnits workerUnits = new WorkerUnits(materialsStockpile);
        workerUnits.locateMaterialsAndExcavate();

        workerUnits.stopWork();
        excavatedMaterialsMonitor.stopMonitoring();
    }

    private static class ExcavatedMaterialsMonitor {
        private ExecutorService executorService;
        private MaterialsStockpile materialsStockpile;

        public ExcavatedMaterialsMonitor(MaterialsStockpile materialsStockpile) {
            this.materialsStockpile = materialsStockpile;
        }

        public void monitorExcavatedMaterials() {
            if (executorService == null || executorService.isShutdown()) {
                executorService = Executors.newFixedThreadPool(materialsStockpile.getMaterials().size());
            }

            for (Material material : materialsStockpile.getMaterials()) {
                executorService.execute(() -> {
                    do {
                        try {
//                            lock.readLock().lock();
                            System.out.println(String.format("%s = %d", material.getMaterialType(), material.getAmount()));
                        } finally {
//                            lock.readLock().unlock();
                        }
                    } while (material.getAmount() > 0);
                });
            }
        }

        public void stopMonitoring() {
            executorService.shutdown();
        }
    }

    private static class WorkerUnits {
        private ExecutorService executorService;
        private MaterialsStockpile materialsStockpile;
        private Map<String, Integer> workersPerMaterial;

        public WorkerUnits(MaterialsStockpile materialsStockpile) {
            this.materialsStockpile = materialsStockpile;
            workersPerMaterial = new HashMap<>();
            workersPerMaterial.put("Iron", 3);
            workersPerMaterial.put("Wood", 4);
            workersPerMaterial.put("Crystal", 2);
        }

        public void locateMaterialsAndExcavate() {
            if (executorService == null || executorService.isShutdown()) {
                executorService = Executors.newCachedThreadPool();
            }

            materialsStockpile.getMaterials().stream()
                    .filter(material -> workersPerMaterial.containsKey(material.getMaterialType()))
                    .forEach(material -> {
                        for (int workerNumber = 0; workerNumber < workersPerMaterial.get(material.getMaterialType()); workerNumber++) {
                            executorService.execute(() -> {
                                Worker worker = new Worker("Worker-" + Thread.currentThread().getId());
                                while (material.getAmount() > 0) {
                                    try {
                                        lock.writeLock().lock();
                                        worker.collect(material);
                                    } catch (InterruptedException ignored) {
                                    } finally {
                                        lock.writeLock().unlock();
                                    }
                                }
                            });
                        }
                    });
        }

        public void stopWork() {
            executorService.shutdown();
        }
    }

    private static class MaterialsStockpile {

        private Collection<Material> materials;

        public MaterialsStockpile() {
            materials = new ArrayList<>();
        }

        public void addMaterialToStockpile(Material material) {
            materials.add(material);
        }

        public Collection<Material> getMaterials() {
            return materials;
        }
    }

    private static class Worker {

        private String name;
        private Material material;
        private int collectionSpeed;

        public Worker(String name) {
            this.name = name;
            this.collectionSpeed = 100;
        }

        public void collect(Material material) throws InterruptedException {
            if (this.material == null) {
                this.material = material;
            }

            if (this.material.getAmount() >= collectionSpeed &&
                    this.material.getMaterialType().equalsIgnoreCase(material.getMaterialType())) {
                this.material.takeAmount(collectionSpeed);
                Thread.sleep(getCollectionSpeed());
            }
        }

        public Material getCollectedMaterial() {
            return material;
        }

        public String getName() {
            return name;
        }

        public int getCollectionSpeed() {
            return collectionSpeed;
        }
    }

    private static class Material {

        private String materialType;
        private int amount;

        public Material(String materialType, int amount) {
            this.materialType = materialType;
            this.amount = amount;
        }

        public void takeAmount(int amount) {
            this.amount -= amount;
        }

        public String getMaterialType() {
            return materialType;
        }

        public int getAmount() {
            return amount;
        }
    }
}
