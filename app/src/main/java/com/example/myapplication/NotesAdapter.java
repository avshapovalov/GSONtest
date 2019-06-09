package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList;
    private Note note;
    private Context mContext;

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitleForAdapter;
        private TextView noteDescriptionForAdapter;
        private TextView noteTimeForAdapter;
        private ImageButton priorityLevel;

        public NotesViewHolder(View view) {
            super(view);
            noteTitleForAdapter = (TextView) view.findViewById(R.id.noteTitle);
            noteDescriptionForAdapter = (TextView) view.findViewById(R.id.noteDescription);
            noteTimeForAdapter = (TextView) view.findViewById(R.id.noteDeadlineView);
            priorityLevel = (ImageButton) view.findViewById(R.id.priorityLevel);
            itemView.setClickable(true);
        }
    }

    public NotesAdapter(List<Note> myDataset) {
        notesList = myDataset;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        mContext = holder.itemView.getContext();
        note = notesList.get(position);
        holder.noteTitleForAdapter.setText(String.valueOf(note.getNoteTitle()));
        holder.noteDescriptionForAdapter.setText(String.valueOf(note.getNoteDescription()));
        holder.noteTimeForAdapter.setText(String.valueOf(note.getNoteTime()));

        hideLabels(holder);
        setLevelColor(holder);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder
                        .setIcon(R.drawable.ic_delete_sweep_white_24dp)
                        .setMessage("Удалить заметку?")
                        .setCancelable(false)
                        .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                note = notesList.get(position);
                                NoteRepository noteRepository = new NoteRepository();
                                noteRepository.deleteNote(mContext, note);
                                notesList.remove(note);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = notesList.get(position);
                Intent intent = new Intent(mContext, NewNote.class);
                intent.putExtra(NewNote.NOTE_ID, String.valueOf(note.getCreationDate()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private void setLevelColor(NotesViewHolder holder) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date deadlineDate = formatter.parse(note.getNoteTime());
            int difference =
                    ((int) ((deadlineDate.getTime() / (24 * 60 * 60 * 1000))
                            - (int) (Calendar.getInstance().getTime().getTime() / (24 * 60 * 60 * 1000))));

            if (difference <= 5 && difference > 0) {
                holder.priorityLevel.setBackgroundColor(Color.parseColor("#ED7500"));
            } else if (difference < 0) {
                holder.priorityLevel.setBackgroundColor(Color.parseColor("#E53935"));
            } else {
                holder.priorityLevel.setBackgroundColor(Color.parseColor("#258E04"));
            }

        } catch (ParseException e) {
            holder.priorityLevel.setBackgroundColor(Color.parseColor("#258E04"));
            e.printStackTrace();
        }
    }

    private void hideLabels (NotesViewHolder holder) {
        if (String.valueOf(note.getNoteTitle()).isEmpty()) {
            holder.noteTitleForAdapter.setVisibility(View.GONE);
        }

        if (String.valueOf(note.getNoteDescription()).isEmpty()) {
            holder.noteDescriptionForAdapter.setVisibility(View.GONE);
        }

        if (String.valueOf(note.getNoteTime()).isEmpty()) {
            holder.noteTimeForAdapter.setVisibility(View.GONE);
        }

    }
}