package game;

public final class Config {
    /**
     * Ticks per second
     */
    public static final long GAME_TPS = 20;

    /**
     * Size of the tile, in pixel.
     * 1.0 field unit == TILE_SIZE pixel on the screen.
     * Change it base on your texture size.
     */
    public static final long TILE_SIZE = 32;
    /**
     * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
     * in other words, the texture will be display as it without scaling.
     */
    public static final long TILE_HORIZONTAL = 30;
    /**
     * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
     * in other words, the texture will be display as it without scaling.
     */
    public static final long TILE_VERTICAL = 20;
    /**
     * An arbitrary number just to make some code run a little faster.
     * Do not touch.
     */
    public static final int _TILE_MAP_COUNT = (int) (TILE_HORIZONTAL * TILE_VERTICAL);


    /**
     * Size of the screen.
     */
    public static final long SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;
    /**
     * Size of the screen.
     */
    public static final long SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;


    //Other config related to other entities in the game.

    //region Bullet
    public static final long NORMAL_BULLET_TTL = 30;
    public static final long NORMAL_BULLET_STRENGTH = 30;
    public static final double NORMAL_BULLET_SPEED = 0.3;

    public static final long MACHINE_GUN_BULLET_TTL = 15;
    public static final long MACHINE_GUN_BULLET_STRENGTH = 20;
    public static final double MACHINE_GUN_BULLET_SPEED = 0.4;

    public static final long SNIPER_BULLET_TTL = 60;
    public static final long SNIPER_BULLET_STRENGTH = 120;
    public static final double SNIPER_BULLET_SPEED = 0.5;
    //endregion

    //region Tower
    public static final long NORMAL_TOWER_SPEED = 30;
    public static final double NORMAL_TOWER_RANGE = 5.0;

    public static final long MACHINE_GUN_TOWER_SPEED = 5;
    public static final double MACHINE_GUN_TOWER_RANGE = 4.0;

    public static final long SNIPER_TOWER_SPEED = 60;
    public static final double SNIPER_TOWER_RANGE = 8.0;
    //endregion

    //region Enemy
    public static final double NORMAL_SIZE = 0.9;
    public static final long NORMAL_HEALTH = 100;
    public static final long NORMAL_ARMOR = 3;
    public static final double NORMAL_SPEED = 0.3;
    public static final long NORMAL_REWARD = 1;

    public static final double SMALLER_SIZE = 0.7;
    public static final long SMALLER_HEALTH = 50;
    public static final long SMALLER_ARMOR = 0;
    public static final double SMALLER_SPEED = 0.4;
    public static final long SMALLER_REWARD = 2;

    public static final double TANKER_SIZE = 1.1;
    public static final long TANKER_HEALTH = 300;
    public static final long TANKER_ARMOR = 5;
    public static final double TANKER_SPEED = 0.2;
    public static final long TANKER_REWARD = 3;

    public static final double BOSS_SIZE = 1.3;
    public static final long BOSS_HEALTH = 500;
    public static final long BOSS_ARMOR = 8;
    public static final double BOSS_SPEED = 0.3;
    public static final long BOSS_REWARD = 10;
    //endregion

    private Config() {
    }


}
