package dk.rtgkom.leftorright;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainUIHandler mainUIHandler;
    private GameThread gameThread;

    private AppCompatButton startButton;
    private TextView scoreBoard;
    private View topScroller;
    private LinearLayoutCompat cardsWrapper;
    private RecyclerView cardsContainer;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoard = (TextView) findViewById(R.id.score_board);
        topScroller = findViewById(R.id.top_scroller);
        cardsWrapper = (LinearLayoutCompat) findViewById(R.id.cards_wrapper);
        cardsContainer = (RecyclerView) findViewById(R.id.cards_container);
        startButton = (AppCompatButton) findViewById(R.id.start_button);

        //Game thread and ui hanlder initialization
        mainUIHandler = new MainUIHandler(MainActivity.this){};
        gameThread = new GameThread(mainUIHandler);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.GONE);
                if (!gameThread.isRunning) {
                    gameThread.start();
                }
                ViewGroup.LayoutParams params = topScroller.getLayoutParams();
                params.height = cardsWrapper.getHeight();
                topScroller.setLayoutParams(params);
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
    }


}
