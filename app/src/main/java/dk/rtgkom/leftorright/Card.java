package dk.rtgkom.leftorright;

import android.support.v7.widget.helper.ItemTouchHelper;

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
    private int direction;

    public Card(int color, int arrow){
        this.color = color;
        this.arrow = arrow;
        if ((this.color == COLOR_BLUE && this.arrow == ARROW_LEFT) ||
                (this.color == COLOR_PURPLE && this.arrow == ARROW_RIGHT)){
            this.direction = ItemTouchHelper.START;
        } else {
            this.direction = ItemTouchHelper.END;
        }
    }

    public int getArrow() {
        return arrow;
    }

    public int getDirection() {
        return direction;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
