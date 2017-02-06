package dk.rtgkom.leftorright;

import android.os.Handler;

/**
 * Created by multimikael on 05-01-2017.
 */

public class GameThread extends Thread {
    private MainUIHandler mainUIHandler;
    public boolean isRunning;

    public GameThread(MainUIHandler handler) {
        this.mainUIHandler = handler;
        isRunning = false;
    }

    @Override
    public synchronized void start() {
        super.start();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        isRunning = true;
        Handler handler = new Handler();
        RunnableGameTick runnable = new RunnableGameTick(mainUIHandler, handler);
        handler.postDelayed(runnable, 500);
    }
}
