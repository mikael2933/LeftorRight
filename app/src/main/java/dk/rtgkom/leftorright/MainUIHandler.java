package dk.rtgkom.leftorright;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
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
    public static final int STOP_GAME = 3;

    private UIHandler handler;

    public MainUIHandler(MainActivity mainActivity){
        handler = new UIHandler(mainActivity);
    }

    public static class UIHandler extends Handler {

        private final WeakReference<MainActivity> weakReference;

        private int scoreCount;
        private View topScroller;
        private LinearLayoutCompat cardsWrapper;
        private TextView scoreBoard;
        private AppCompatButton startButton;

        public UIHandler(MainActivity mainActivity) {
            weakReference = new WeakReference<MainActivity>(mainActivity);
            this.topScroller = mainActivity.findViewById(R.id.top_scroller);
            this.cardsWrapper = (LinearLayoutCompat) mainActivity.findViewById(R.id.cards_wrapper);
            this.scoreBoard = (TextView) mainActivity.findViewById(R.id.score_board);
            this.startButton = (AppCompatButton) mainActivity.findViewById(R.id.start_button);

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
                    case STOP_GAME:
                        stopGame();
                        break;
                }
            }
        }

        private void increaseScoreBy(int amount) {
            scoreCount++;
            scoreBoard.setText(Integer.toString(scoreCount));
        }

        private void reduceTopBy(int size) {
            if (topScroller.getHeight() > 0) {
                ViewGroup.LayoutParams params = topScroller.getLayoutParams();
                params.height -= size;
                topScroller.setLayoutParams(params);
            } else {
                stopGame();
            }
        }

        private void extendTopBy(int size) {
            ViewGroup.LayoutParams params = topScroller.getLayoutParams();
            params.height += size;
            topScroller.setLayoutParams(params);
        }

        private void stopGame() {
            startButton.setVisibility(View.VISIBLE);
            scoreCount = 0;
        }

    }

    public UIHandler getHandler() {
        return handler;
    }
}
