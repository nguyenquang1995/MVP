package com.example.framgia.mvp.model;

import com.example.framgia.mvp.myinterface.MVP_Main;

import java.util.ArrayList;

/**
 * Created by framgia on 31/08/2016.
 */
public class MainModel implements MVP_Main.ProvidedModelOps {
    private MVP_Main.RequiredPresenterOps mPresenter;
    private DAO mDA0;
    public ArrayList<Note> mNotes;
    public MainModel(MVP_Main.RequiredPresenterOps presenterOps) {
        this.mPresenter = presenterOps;
        mDA0 = new DAO(mPresenter.getAppContext());
    }
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mDA0 = null;
            mNotes = null;
        }
    }

    public int getNotePosition(Note note) {
        for (int i=0; i<mNotes.size(); i++){
            if ( note.getId() == mNotes.get(i).getId())
                return i;
        }
        return -1;
    }

    @Override
    public boolean deleteNote(Note note, int adapterPos) {
        long res = mDA0.deleteNote(note);
        if ( res > 0 ) {
            mNotes.remove(adapterPos);
            return true;
        }
        return false;
    }

    @Override
    public int getNotesCount() {
        return mNotes.size();
    }

    @Override
    public Note getNote(int position) {
        return mNotes.get(position);
    }

    @Override
    public int insertNote(Note note) {
        Note insertedNote = mDA0.insertNote(note);
        if ( insertedNote != null ) {
            loadData();
            return getNotePosition(insertedNote);
        }
        return -1;
    }

    @Override
    public boolean loadData() {
        mNotes = mDA0.getAllNotes();
        return mNotes != null;
    }
}
