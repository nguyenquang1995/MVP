package com.example.framgia.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framgia.mvp.R;

/**
 * Created by framgia on 29/08/2016.
 */
public class NotesViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout container;
    public TextView text, date;
    public NotesViewHolder(View itemView) {
        super(itemView);
        setUpViews(itemView);
    }
    private void setUpViews(View view) {
        container = (RelativeLayout) view.findViewById(R.id.holder_container);
        text = (TextView) view.findViewById(R.id.note_text);
        date = (TextView) view.findViewById(R.id.note_date);
    }
}
