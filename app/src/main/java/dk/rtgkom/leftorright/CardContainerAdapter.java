package dk.rtgkom.leftorright;

import android.content.ClipData;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

/**
 * Created by multimikael on 04-01-2017.
 */

public class CardContainerAdapter extends RecyclerView.Adapter<CardContainerAdapter.CardViewHolder>
        implements CardHelperAdapter{

    private int rootHeight;
    private int cardsSwiped;
    private Card[] cardsArray;

    private LinearLayoutCompat cardsWrapper;
    private MainUIHandler mainUIHandler;

    public CardContainerAdapter(MainUIHandler mainUIHandler, LinearLayoutCompat cardsWrapper){
        cardsSwiped = 0;
        cardsArray = generateNewCards();
        this.mainUIHandler = mainUIHandler;
        this.cardsWrapper = cardsWrapper;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_card, parent,
                false);
        v.setLayoutParams(new RecyclerView.LayoutParams(parent.getWidth(),
                cardsWrapper.getHeight()/4));
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Card[] generateNewCards(){
        Card[] cards = new Card[20];
        Random random = new Random();
        for (int i=0; i<20; i++) {
            int color = random.nextInt(2);
            int arrow = random.nextInt(2);
            cards[i] = new Card(color, arrow);
            System.out.println("color : " + cards[i].getColor());
        }
        return cards;
    }

    @Override
    public void onCardDismiss(int position) {
        System.out.println("Dismiss : " + position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
        cardsSwiped++;
        System.out.println(cardsSwiped);
        if(cardsSwiped > 10) {
            cardsArray = generateNewCards();
            notifyDataSetChanged();
            cardsSwiped = 0;
        }
        mainUIHandler.getHandler().sendEmptyMessage(MainUIHandler.TOP_EXTEND);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        protected View itemView;
        protected LinearLayoutCompat root;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.root = (LinearLayoutCompat) itemView.findViewById(R.id.card_root);
        }
    }
}
