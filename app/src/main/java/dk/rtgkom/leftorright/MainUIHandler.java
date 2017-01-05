package dk.rtgkom.leftorright;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by multimikael on 05-01-2017.
 */

public class MainUIHandler {
    public static final int TOP_REDUCE = 0;
    public static final int TOP_EXTEND = 1;

    private UIHandler handler;

    public MainUIHandler(MainActivity mainActivity,
                         View topScroller,
                         LinearLayoutCompat cardsWrapper,
                         RecyclerView cardsContainer){
        handler = new UIHandler(mainActivity, topScroller, cardsWrapper, cardsContainer);
    }

    public static class UIHandler extends Handler {

        private final WeakReference<MainActivity> weakReference;

        private View topScroller;
        private LinearLayoutCompat cardsWrapper;
        private RecyclerView cardsContainer;

        public UIHandler(MainActivity mainActivity,
                         View topScroller,
                         LinearLayoutCompat cardsWrapper,
                         RecyclerView cardsContainer) {
            weakReference = new WeakReference<MainActivity>(mainActivity);
            this.topScroller = topScroller;
            this.cardsWrapper = cardsWrapper;
            this.cardsContainer = cardsContainer;
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case TOP_REDUCE:
                        System.out.println("REDUCE : " + cardsWrapper.getHeight()/10);
                        reduceTopBy(cardsWrapper.getHeight()/10);
                        break;
                    case TOP_EXTEND:
                        System.out.println("EXTEND : " + cardsWrapper.getHeight()/4);
                        extendTopBy((int) (cardsWrapper.getHeight()*0.35));
                        break;
                }
            }
        }

        private void extendTopBy(int size) {
            ViewGroup.LayoutParams params = topScroller.getLayoutParams();
            params.height += size;
            topScroller.setLayoutParams(params);
        }

        private void reduceTopBy(int size) {
            ViewGroup.LayoutParams params = topScroller.getLayoutParams();
            params.height -= size;
            topScroller.setLayoutParams(params);
        }
    }

    public UIHandler getHandler() {
        return handler;
    }
}
