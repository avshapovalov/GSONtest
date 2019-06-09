package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteRepository {
    public static final int ACTION_NEW_NOTE = 0;
    public static final int ACTION_UPDATE = 1;
    public static final String JSON_REPOSITORY_NAME = "noteRepository.json";
    public static final String JSON_ARRAY = "noteRepository";
    public static final String FIELD_TITLE = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DEADLINE = "deadline";
    public static final String FIELD_CREATIONDATE = "creationDate";
    public static final String FIELD_CHANGEDATE = "changeDate";
    public static final String FIELD_IS_DEADLINE_NEEDED = "isDeadlineNeeded";
    @SerializedName("noteList")
    private List<Note> noteList = new ArrayList<Note>();

    public NoteRepository() {
    }

    public void saveNote(Context context, Note note) {
        noteList = fillList(context);
        noteList.add(note);
        Type listType = new TypeToken<ArrayList<Note>>() {
        }.getType();

        Gson gson = new Gson();
        String json = gson.toJson(noteList, listType);
        File file = new File(context.getFilesDir(), JSON_REPOSITORY_NAME);
        try {
            gson.toJson(json, new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("СМОТРИ СЮДА", json);
        Toast.makeText(context, "Заметка сохранена", Toast.LENGTH_SHORT).show();
    }

    public List<Note> fillList(Context context) {
        Gson gson = new Gson();
        File file = new File(context.getFilesDir(), JSON_REPOSITORY_NAME);
        //GsonBuilder gsonBuilder = new GsonBuilder().setLenient();
        Type listType = new TypeToken<List<Note>>() {
        }.getType();
        if (file.exists()) {
            Log.d("СМОТРИ СЮДА", "Файл есть");
            try {
                Reader reader = new FileReader(file);
                Note[] notes = gson.fromJson(reader, listType);
                noteList = Arrays.asList(notes);
                Log.d("СМОТРИ СЮДА", "список: " + notes.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return noteList;
    }


    public void deleteNote(Context mContext, Note note) {
        File dir = new File(mContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();

        for (int i = 0; i < children.length; i++) {
            if (mContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).contains(String.valueOf(note.getCreationDate()))) {
                mContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
            }
        }
        for (int i = 0; i < children.length; i++) {
            if (new File(dir, children[i]).getName().equals(note.getCreationDate() + ".xml")) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public Note getNote(Context noteEditContext, String noteID) {
        File dir = new File(noteEditContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        Note note = new Note();
        for (int i = 0; i < children.length; i++) {
            if (noteEditContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).getLong(NoteRepository.FIELD_CREATIONDATE, 0) == Long.parseLong(noteID)) {
                SharedPreferences sharedPref = noteEditContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE);
                note.setNoteTitle(sharedPref.getString(NoteRepository.FIELD_TITLE, "Unknown"));
                note.setNoteDescription(sharedPref.getString(NoteRepository.FIELD_DESCRIPTION, ""));
                note.setNoteTime(sharedPref.getString(NoteRepository.FIELD_DEADLINE, ""));
                note.setCreationDate(sharedPref.getLong(NoteRepository.FIELD_CREATIONDATE, 0));
                note.setChangeDate(sharedPref.getLong(NoteRepository.FIELD_CHANGEDATE, 0));
                note.setDeadlineNeeded(sharedPref.getBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, false));
            }
        }
        return note;
    }

    public void updateNote(Context noteEditContext, Note note) {
        File dir = new File(noteEditContext.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            if (noteEditContext.getSharedPreferences(children[i].replace(".xml", ""),
                    Context.MODE_PRIVATE).getLong(NoteRepository.FIELD_CREATIONDATE, 0) == note.getCreationDate()) {
                SharedPreferences sharedPref = noteEditContext.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString(NoteRepository.FIELD_TITLE, note.getNoteTitle());
                editor.putString(NoteRepository.FIELD_DESCRIPTION, note.getNoteDescription());
                editor.putString(NoteRepository.FIELD_DEADLINE, note.getNoteTime());
                editor.putLong(NoteRepository.FIELD_CREATIONDATE, note.getCreationDate());
                editor.putLong(NoteRepository.FIELD_CHANGEDATE, note.getChangeDate());
                editor.putBoolean(NoteRepository.FIELD_IS_DEADLINE_NEEDED, note.getDeadlineNeeded());
                editor.apply();
                Toast.makeText(noteEditContext, "Заметка обновлена", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
