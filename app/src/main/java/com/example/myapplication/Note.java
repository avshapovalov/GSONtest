package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Note {
    @SerializedName("creationDate")
    private Long creationDate;
    @SerializedName("changeDate")
    private Long changeDate;
    @SerializedName("isDeadlineNeeded")
    private Boolean isDeadlineNeeded;
    @SerializedName("deadline")
    private String noteTime;
    @SerializedName("description")
    private String noteDescription;
    @SerializedName("name")
    private String noteTitle;

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
}
