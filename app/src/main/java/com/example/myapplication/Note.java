package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String noteTitle;
    private String noteDescription;
    private String noteTime;
    private Long creationDate;
    private Long changeDate;
    private Boolean isDeadlineNeeded;

    public Note() {
    }

    public Note(String noteTitle, String noteDescription, String noteTime, Long creationDate, Long changeDate, Boolean isDeadlineNeeded) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteTime = noteTime;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.isDeadlineNeeded = isDeadlineNeeded;
    }

    public Note(Parcel in) {
        this.noteTitle = in.readString();
        this.noteDescription = in.readString();
        this.noteTime = in.readString();
        this.creationDate = in.readLong();
        this.changeDate = in.readLong();
        this.isDeadlineNeeded = Boolean.parseBoolean(in.readString());
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public Long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

    public Boolean getDeadlineNeeded() {
        return isDeadlineNeeded;
    }

    public void setDeadlineNeeded(Boolean deadlineNeeded) {
        isDeadlineNeeded = deadlineNeeded;
    }

    @Override
    public String toString() {
        return "Note[" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", noteTime='" + noteTime + '\'' +
                ", creationDate=" + creationDate +
                ", changeDate=" + changeDate +
                ", isDeadlineNeeded=" + isDeadlineNeeded +
                ']';
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.noteTitle);
        parcel.writeString(this.noteDescription);
        parcel.writeString(this.noteTime);
        parcel.writeLong(this.creationDate);
        parcel.writeLong(this.changeDate);
        parcel.writeString(String.valueOf(this.isDeadlineNeeded));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {

        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
