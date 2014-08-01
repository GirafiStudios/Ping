package dmillerw.ping.data;

/**
 * @author dmillerw
 */
public enum PingType {

    BACKGROUND,
    ALERT,
    GOTO,
    LOOK,
    MINE;

    public final float minU;
    public final float minV;
    public final float maxU;
    public final float maxV;

    private PingType() {
        int x = 32 * ordinal();
        int y = 0;
        float f = (float) (0.009999999776482582D / (double) 256);
        float f1 = (float) (0.009999999776482582D / (double) 256);
        this.minU = (float) x / (float) ((double) 256) + f;
        this.maxU = (float) (x + 32) / (float) ((double) 256) - f;
        this.minV = (float) y / (float) 256 + f1;
        this.maxV = (float) (y + 32) / (float) 256 - f1;
    }
}
