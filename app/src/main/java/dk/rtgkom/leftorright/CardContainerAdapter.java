package dk.rtgkom.leftorright;

import android.content.ClipData;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by multimikael on 04-01-2017.
 */

public class CardContainerAdapter extends RecyclerView.Adapter<CardContainerAdapter.CardViewHolder>
        implements CardHelperAdapter{

    private int rootHeight;
    private int cardsSwiped;
    private List<Card> cards;

    private LinearLayoutCompat cardsWrapper;
    private MainUIHandler mainUIHandler;

    public CardContainerAdapter(MainUIHandler mainUIHandler,
                                LinearLayoutCompat cardsWrapper){
        cardsSwiped = 0;
        cards = generateNewCards();
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
        Card card = cards.get(position);
        if (card.getColor() == Card.COLOR_BLUE){
            holder.cardView.setBackgroundColor(Color.BLUE);
        } else {
            holder.cardView.setBackgroundColor(Color.MAGENTA);
        }
        if (card.getArrow() == Card.ARROW_LEFT) {
            holder.arrowImageView.setImageResource(R.drawable.ic_fast_rewind_black_24dp);
        } else {
            holder.arrowImageView.setImageResource(R.drawable.ic_fast_forward_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onCardDismiss(int position, int direction) {
        System.out.println("Dismiss : " + position);
        //notifyItemRemoved(position);
        System.out.println("Received direction : " + direction);
        System.out.println("Card direction : " + cards.get(position).getDirection());
        if (cards.get(position).getDirection() == direction) {
            //If correct swipe direction
            mainUIHandler.getHandler().sendEmptyMessage(MainUIHandler.INCREASE_SCORE);
        }
        cardsSwiped++;
        System.out.println("Cards swiped : " + cardsSwiped);
        if(cardsSwiped > 10) {
            cards = generateNewCards();
            cardsSwiped = 0;
        }
        mainUIHandler.getHandler().sendEmptyMessage(MainUIHandler.TOP_EXTEND);
        cards.remove(position);
        notifyDataSetChanged();
    }

    private List<Card> generateNewCards(){
        List<Card>cards = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<20; i++) {
            int color = random.nextInt(2);
            int arrow = random.nextInt(2);
            cards.add(new Card(color, arrow));
            System.out.println("color : " + cards.get(i).getColor());
            System.out.println("arrow : " + cards.get(i).getArrow());
        }
        return cards;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        protected View itemView;
        protected CardView cardView;
        protected LinearLayoutCompat root;
        protected AppCompatImageView arrowImageView;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.root = (LinearLayoutCompat) itemView.findViewById(R.id.card_root);
            this.arrowImageView = (AppCompatImageView) itemView.findViewById(R.id.arrow_image_view);
        }
    }
}
