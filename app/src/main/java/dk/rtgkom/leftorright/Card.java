package dk.rtgkom.leftorright;

/**
 * Created by multimikael on 12-01-2017.
 */

public class Card {
    public static final int COLOR_BLUE = 0;
    public static final int COLOR_PURPLE = 1;
    public static final int ARROW_LEFT = 0;
    public static final int ARROW_RIGHT = 1;

    private int color;
    private int arrow;

    public Card(int color, int arrow){
        this.color = color;
        this.arrow = arrow;
    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
