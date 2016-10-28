package com.prime.primeclient.AnalyticsActivity.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prime.primeclient.R;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeTwo;

import java.util.ArrayList;

/**
 * Created by Admin on 10/26/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<TypeOne> objectsByYear;
    private ArrayList<TypeTwo> objectsByYear2;
    private Context context;
    Typeface font;


    public RecyclerAdapter(ArrayList<TypeOne> objectsByYear, ArrayList<TypeTwo> objectsByYear2, Context context){
        this.objectsByYear = objectsByYear;
        this.objectsByYear2 = objectsByYear2;
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "babasneuer.otf");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            this.mTextView =(TextView) v.findViewById(R.id.amount);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(objectsByYear!=null){
             v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_income, parent, false);
        }

        if(objectsByYear2!=null){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_outcome, parent, false);
        }


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
            if(objectsByYear!=null){

                holder.mTextView.setTypeface(font);
                holder.mTextView.setText(String.valueOf(objectsByYear.get(position).restaurantBonusAmount));
            }

            else{
                holder.mTextView.setTypeface(font);
                holder.mTextView.setText(String.valueOf(objectsByYear2.get(position).paymentAmount));
            }
    }

    @Override
    public int getItemCount() {
        if(objectsByYear!=null){
            return objectsByYear.size();
        }

        if(objectsByYear2!=null){
            return objectsByYear2.size();
        }
        else{
            return 0;
        }
    }

}