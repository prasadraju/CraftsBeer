package com.thoughtworks.craftsbeer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtworks.craftsbeer.MainActivity;
import com.thoughtworks.craftsbeer.R;
import com.thoughtworks.craftsbeer.data.Beers;

import java.util.List;


/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.InsectHolder> {


    private List<Beers> beersList;
    private Context mContext;
    int globalQuantity = 0;

    public BeersAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_beer, parent, false);

        return new InsectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InsectHolder holder, int position) {

        final Beers insect = beersList.get(position);
        if (insect.getName().length() >= 30) {
            String sortName = insect.getName().substring(0, 30);
            holder.name.setText(sortName + "...");
        } else {
            holder.name.setText(insect.getName());
        }

        holder.beer_style.setText(insect.getStyle());
        if (insect.getAbv() == null || insect.getAbv().equals("")) {
            holder.alcohol_content.setText("0.0");
        } else {
            holder.alcohol_content.setText("" + insect.getAbv());
        }


        holder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = insect.getQuantity();
                quantity = quantity + 1;
                globalQuantity=globalQuantity+1;
                insect.setQuantity(quantity);
                holder.qnt.setText("" + insect.getQuantity());
                ((MainActivity) mContext).addItemToCart(globalQuantity);

            }
        });

        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = insect.getQuantity();
                if (quantity >= 1) {
                    quantity = quantity - 1;
                    globalQuantity=globalQuantity-1;
                    insect.setQuantity(quantity);
                    holder.qnt.setText("" + quantity);
                    ((MainActivity) mContext).addItemToCart(globalQuantity);
                }

            }
        });


    }


    public void updateList(List<Beers> temp) {

        this.beersList = temp;
        notifyDataSetChanged();

    }


    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView name;
        public TextView beer_style;
        public TextView alcohol_content;
        public TextView qnt;
        ImageView iv_minus, iv_plus;


        public InsectHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            beer_style = (TextView) itemView.findViewById(R.id.beer_style);
            alcohol_content = (TextView) itemView.findViewById(R.id.alcohol_content);
            qnt = (TextView) itemView.findViewById(R.id.qnt);
            iv_plus = (ImageView) itemView.findViewById(R.id.iv_plus);
            iv_minus = (ImageView) itemView.findViewById(R.id.iv_minus);

        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();

        }
    }

    @Override
    public int getItemCount() {
        return beersList != null ? beersList.size() : 0;
    }


}

