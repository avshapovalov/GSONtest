package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteRepository {
    public static final int ACTION_NEW_NOTE = 0;
    public static final int ACTION_UPDATE = 1;
    public static final String TAG = "СМОТРИ СЮДА";
    public static final String JSON_REPOSITORY_NAME = "noteRepository";
    public static final String JSON_REPOSITORY_KEY = "noteRepository";
    private List<Note> noteList = new ArrayList<Note>();

    public NoteRepository() {
    }

    public void saveNote(Context context, Note note) {
        noteList = fillList(context);
        noteList.add(note);
        SharedPreferences sharedPreferences = context.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();
        Toast.makeText(context, "Заметка сохранена", Toast.LENGTH_SHORT).show();
    }

    public List<Note> fillList(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(JSON_REPOSITORY_NAME, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(JSON_REPOSITORY_KEY, "");
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        try {
            noteList = gson.fromJson(jsonPreferences, type);
        } catch (NullPointerException e) {
            noteList = new ArrayList<Note>();
        }
        return noteList;
    }


    public void deleteNote(Context mContext, Note note) {
        noteList = fillList(mContext);
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getCreationDate().equals(note.getCreationDate())) {
                noteList.remove(i);
            }
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();
        Toast.makeText(mContext, "Заметка удалена", Toast.LENGTH_SHORT).show();
    }

    public void updateNote(Context noteEditContext, Note note) {
        noteList = fillList(noteEditContext);
        SharedPreferences sharedPreferences = noteEditContext.getSharedPreferences(JSON_REPOSITORY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getCreationDate().equals(note.getCreationDate())) {
                noteList.get(i).setNoteTitle(note.getNoteTitle());
                noteList.get(i).setNoteDescription(note.getNoteDescription());
                noteList.get(i).setNoteTime(note.getNoteTime());
                noteList.get(i).setChangeDate(note.getChangeDate());
                noteList.get(i).setDeadlineNeeded(note.getDeadlineNeeded());
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        editor.putString(JSON_REPOSITORY_KEY, json);
        editor.commit();
        Toast.makeText(noteEditContext, "Заметка обновлена", Toast.LENGTH_SHORT).show();
    }


}
