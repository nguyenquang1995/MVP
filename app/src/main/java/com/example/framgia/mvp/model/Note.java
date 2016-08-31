package com.example.framgia.mvp.model;

import android.content.ContentValues;

/**
 * Created by framgia on 29/08/2016.
 */
public class Note {
    private int id = -1;
    private String mText;
    private String mDate;
    public Note() {
    }
    public Note(String  mText, String mDate) {
        this.mText = mText;
        this.mDate = mDate;
    }
    public ContentValues getValues() {
        ContentValues contentValues = new ContentValues();
        if(id != -1) contentValues.put(DBSchema.TB_NOTES.ID, id);
        contentValues.put(DBSchema.TB_NOTES.NOTE, mText);
        contentValues.put(DBSchema.TB_NOTES.DATE, mDate);
        return contentValues;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }
}
