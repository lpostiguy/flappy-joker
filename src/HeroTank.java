public class HeroTank extends Character implements Hero {
    // Getters
    public int getAttackDamage() {
        return 50;
    }

    public int getCoinDropAmount() {
        return 7;
    }

    public int getCoinStealAmount() {
        return 0;
    }

    // Constructor
    public HeroTank() {
        super();
    }
}
