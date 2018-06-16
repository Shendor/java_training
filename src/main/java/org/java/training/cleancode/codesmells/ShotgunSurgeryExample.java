package org.java.training.cleancode.codesmells;

public class ShotgunSurgeryExample {

    class HealingController {
        public void heal(Player player){
            if(player.getHealth() < 100){
                //...
            }
        }
    }

    class HealingPotionController {
        public void drink(Player player){
            if(player.getHealth() < 100){
                //...
            }
        }
    }

    class ArmorController {
        public void applyArmor(Player player){
            if(player.getHealth() == 100){
                //...
            }
        }
    }

    class UiHealthController {
        public void showHealthBar(Player player){
            if(player.getHealth() == 100){
                //...
            }
        }
    }
}

class Player {
    private int health;

    public int getHealth() {
        return health;
    }
}
