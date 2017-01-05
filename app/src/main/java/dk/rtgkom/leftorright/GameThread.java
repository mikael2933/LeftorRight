package dk.rtgkom.leftorright;

import android.os.Handler;

/**
 * Created by multimikael on 05-01-2017.
 */

public class GameThread extends Thread {
    private MainUIHandler mainUIHandler;

    public GameThread(MainUIHandler handler) {
        this.mainUIHandler = handler;
    }

    @Override
    public synchronized void start() {
        super.start();
        Handler handler = new Handler();
        RunnableGameTick runnable = new RunnableGameTick(mainUIHandler, handler);
        handler.postDelayed(runnable, 500);
    }
}
