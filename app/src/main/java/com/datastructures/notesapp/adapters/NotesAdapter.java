package com.datastructures.notesapp.adapters;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.datastructures.notesapp.R;
import com.datastructures.notesapp.pojo.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context context;
    private List<Note> notesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView noteDetails;
        public TextView dot;
        public TextView timestamp;
        public ConstraintLayout constraintLayout;
        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            noteDetails = view.findViewById(R.id.note_details_TV);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
            constraintLayout = view.findViewById(R.id.note_background);
            constraintLayout.setBackgroundTintList(getColor());
        }

        private ColorStateList getColor() {
            Random random = new Random();
            return ColorStateList.valueOf(Color.argb(100
                    , random.nextInt()% 256
                    , random.nextInt()% 256
                    , random.nextInt()% 256));
        }
    }


    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = notesList.get(position);

        holder.note.setText(note.getNote());
        holder.noteDetails.setText(note.getDetails());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(note.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}