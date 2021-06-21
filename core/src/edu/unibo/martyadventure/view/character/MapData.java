package edu.unibo.martyadventure.view.character;


/**
 * Character data relative to a given map.
 */
class MapData {

    private final String martyPath;
    private final String biffPath;
    private final String docPath;

    public final int bossHp;
    public final int bullyHP;


    public MapData(final String marty, final String biff, final String doc, final int boss, final int bully) {
        this.martyPath = marty;
        this.biffPath = biff;
        this.docPath = doc;
        this.bossHp = boss;
        this.bullyHP = bully;
    }

    /**
     * @return the path to the texture of the given player character.
     */
    public String getTexturePathOf(final Player player) {
        switch (player) {
        case MARTY:
            return this.martyPath;
        case BIFF:
            return this.biffPath;
        case DOC:
            return this.docPath;
        default:
            throw new IllegalArgumentException("Unknow player '" + player + "'");
        }
    }
}