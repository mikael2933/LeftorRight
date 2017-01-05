package dk.rtgkom.leftorright;

import android.os.Handler;

/**
 * Created by multimikael on 05-01-2017.
 */

public class RunnableGameTick implements Runnable {

    private MainUIHandler mainUIHandler;
    private Handler gameThreadHandler;

    public RunnableGameTick(MainUIHandler mainUIHandler, Handler gameThreadhandler){
        this.mainUIHandler = mainUIHandler;
        this.gameThreadHandler = gameThreadhandler;
    }

    @Override
    public void run() {
        System.out.println("LOL");
        gameThreadHandler.postDelayed(this, 500);
        mainUIHandler.getHandler().sendEmptyMessage(MainUIHandler.TOP_REDUCE);
    }
}
