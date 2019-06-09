package com.example.myapplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class NotesComparator implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int sComp;
        try {
            sComp = (-1) * ((formatter.parse(o1.getNoteTime()))).compareTo(formatter.parse(o2.getNoteTime()));
        } catch (ParseException e) {
            if (String.valueOf(o1.getNoteTime()).isEmpty() == false && String.valueOf(o2.getNoteTime()).isEmpty() == true) {
                sComp = 1;
            } else if (String.valueOf(o1.getNoteTime()).isEmpty() == true && String.valueOf(o2.getNoteTime()).isEmpty() == false) {
                sComp = -1;
            } else {
                sComp = 0;
            }
            e.printStackTrace();
        }

        if (sComp != 0) {
            return sComp;
        } else {
            int sComp2 = o1.getChangeDate().compareTo(o2.getChangeDate());
            return sComp2;
        }
    }
}
