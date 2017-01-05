package dk.rtgkom.leftorright;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MainUIHandler mainUIHandler;

    private View topScroller;
    private LinearLayoutCompat cardsWrapper;
    private RecyclerView cardsContainer;
    private LinearLayoutManager layoutManager;
    private Runnable runnableGameTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topScroller = findViewById(R.id.top_scroller);
        cardsWrapper = (LinearLayoutCompat) findViewById(R.id.cards_wrapper);
        cardsContainer = (RecyclerView) findViewById(R.id.cards_container);
        mainUIHandler = new MainUIHandler(this, topScroller,
                cardsWrapper, cardsContainer){};

        layoutManager = new LinearLayoutManager(MainActivity.this){
            @Override
            public boolean canScrollVertically() {
                //return super.canScrollVertically();
                return false;
            }
        };
        cardsContainer.setLayoutManager(layoutManager);
        cardsContainer.setHasFixedSize(true);
        CardContainerAdapter adapter = new CardContainerAdapter(mainUIHandler, cardsWrapper);
        cardsContainer.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new CardTouchHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(cardsContainer);
    }

    public MainUIHandler getMainUIHandler() {
        return mainUIHandler;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GameThread gameThread = new GameThread(mainUIHandler);
        gameThread.start();
    }


}
