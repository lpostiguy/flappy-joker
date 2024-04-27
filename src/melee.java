public class melee extends Character implements Hero {
    // Getters
    public int getAttackDamage() {
        return 100;
    }

    public int getCoinDropAmount() {
        return 5;
    }

    public int getCoinStealAmount() {
        return 0;
    }

    // Constructor
    public melee() {
        super();
    }
}
