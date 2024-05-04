/**
 * Interface definition for a hero in the game. Heroes in the game have
 * propertie such as attack damage, coin drop amount, and coin steal amount.
 */
public interface Hero {
    // Getters

    /**
     * Retrieves the attack damage of the hero.
     *
     * @return the attack damage of the hero
     */
    int getAttackDamage();

    /**
     * Retrieves the amount of coins dropped by the hero.
     *
     * @return the coin drop amount of the hero
     */
    int getCoinDropAmount();

    /**
     * Retrieves the amount of coins stolen by the hero.
     *
     * @return the coin steal amount of the hero
     */
    int getCoinStealAmount();
}
