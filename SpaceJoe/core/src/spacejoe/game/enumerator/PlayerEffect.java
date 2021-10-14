package spacejoe.game.enumerator;

import spacejoe.game.gameobject.player.Player;

/**
 * Mozne efekty predmetov typu UsableItem.
 * SHIELD - zvysuje hodnotu shield
 * REPAIR - zvysuje hodnotu hp
 */
public enum PlayerEffect {
    /**
     * Pridava hracovi shield o dany pocet po dobu 10 sekund.
     * Po skonceni efektu sa hracov stit resetuje na 0.
     */
    SHIELD("SLD", "SHIELD", 10) {
        @Override
        public void applyEffect(Player player, int amount) {
            if (player.getShield() <= 0) {
                player.addShield(amount);
            }
        }

        @Override
        public void removeEffect(Player player) {
            player.subShield(player.getShield());
        }
    },

    /**
     * Opravuje hraca - pridava dane mnozstvo HP.
     * Tento efekt je trvaly - nedochadza k odstraneniu.
     */
    REPAIR("RPR", "REPAIR", 5) {
        @Override
        public void applyEffect(Player player, int amount) {
            if (Player.getDefaultMaxHp() > player.getHp()) {
                player.addHp(amount);
            }
        }

        @Override
        public void removeEffect(Player player) {
            // tento efekt je trvaly
        }
    },

    /**
     * Efekt zamedzuje prehrievaniu zbrane hraca, funguje tak ze nastavi hodnotu heat na vysoke zaporne cislo.
     * Po uplynuti doby trvania je hodnota heat nastavena na 0.
     */
    COOLER("WCL", "W-COOLER", 10) {
        @Override
        public void applyEffect(Player player, int amount) {
            player.getWeapon().setHeat(-100);
        }

        @Override
        public void removeEffect(Player player) {
            player.getWeapon().setHeat(0);
        }
    };

    private String shortIdentifier;
    private String name;
    private int timeLimit;
    private float deltaTimer;

    /**
     * @param shortIdentifier Stringove idcko daneho efektu
     * @param timeLimit doba trvania efektu
     */
    PlayerEffect(String shortIdentifier, String name, int timeLimit) {
        this.shortIdentifier = shortIdentifier;
        this.name = name;
        this.timeLimit = timeLimit;
        this.deltaTimer = 0;
    }

    public String getShortIdentifier() {
        return shortIdentifier;
    }

    public String getName() {
        return name;
    }

    /**
     * Aplikuje dany efekt na hraca.
     * @param player hrac
     * @param amount mnozstvo efektu o kolko sa zvysi dana hodnota
     */
    public abstract void applyEffect(Player player, int amount);

    /**
     * Odstrani dany efekt hraca.
     * @param player hrac
     */
    public abstract void removeEffect(Player player);

    /**
     * Sprava efekta na hraca
     * @param player hrac
     * @param delta cas delta
     * @return true ak efekt stale pretrvava, false ak uplynul cas trvania
     */
    public boolean manageEffect(Player player, float delta) {
        this.deltaTimer += delta;
        if (this.deltaTimer >= this.timeLimit) {
            this.removeEffect(player);
            this.deltaTimer = 0;
            return false;
        }
        return true;
    }
}
