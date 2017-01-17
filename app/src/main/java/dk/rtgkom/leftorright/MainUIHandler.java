package dk.rtgkom.leftorright;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by multimikael on 05-01-2017.
 */

public class MainUIHandler {
    public static final int TOP_REDUCE = 0;
    public static final int TOP_EXTEND = 1;
    public static final int INCREASE_SCORE = 2;

    private UIHandler handler;

    public MainUIHandler(MainActivity mainActivity,
                         View topScroller,
                         LinearLayoutCompat cardsWrapper,
                         RecyclerView cardsContainer,
                         TextView scoreBoard){
        handler = new UIHandler(mainActivity, topScroller, cardsWrapper, cardsContainer, scoreBoard);
    }

    public static class UIHandler extends Handler {

        private final WeakReference<MainActivity> weakReference;

        private int scoreCount;
        private View topScroller;
        private LinearLayoutCompat cardsWrapper;
        private RecyclerView cardsContainer;
        private TextView scoreBoard;

        public UIHandler(MainActivity mainActivity,
                         View topScroller,
                         LinearLayoutCompat cardsWrapper,
                         RecyclerView cardsContainer,
                         TextView scoreBoard) {
            weakReference = new WeakReference<MainActivity>(mainActivity);
            this.topScroller = topScroller;
            this.cardsWrapper = cardsWrapper;
            this.cardsContainer = cardsContainer;
            this.scoreBoard = scoreBoard;

            scoreCount = 0;
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case TOP_REDUCE:
                        reduceTopBy(cardsWrapper.getHeight()/10);
                        break;
                    case TOP_EXTEND:
                        extendTopBy((int) (cardsWrapper.getHeight()/4));
                        break;
                    case INCREASE_SCORE:
                        increaseScoreBy(1);
                        break;
                }
            }
        }

        private void increaseScoreBy(int amount) {
            scoreCount++;
            scoreBoard.setText(scoreCount+"");
        }

        private void reduceTopBy(int size) {
            if (topScroller.getHeight() > 0) {
                ViewGroup.LayoutParams params = topScroller.getLayoutParams();
                params.height -= size;
                topScroller.setLayoutParams(params);
            }
        }

        private void extendTopBy(int size) {
            ViewGroup.LayoutParams params = topScroller.getLayoutParams();
            params.height += size;
            topScroller.setLayoutParams(params);
        }

    }

    public UIHandler getHandler() {
        return handler;
    }
}
