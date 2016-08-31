package com.example.framgia.mvp.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgia.mvp.R;
import com.example.framgia.mvp.model.Note;
import com.example.framgia.mvp.myinterface.MVP_Main;
import com.example.framgia.mvp.view.NotesViewHolder;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by framgia on 29/08/2016.
 */
public class MainPresenter
    implements MVP_Main.ProvidedPresenterOps, MVP_Main.RequiredPresenterOps {
    private WeakReference<MVP_Main.RequiredViewOps> mView;
    private MVP_Main.ProvidedModelOps mModel;

    public MainPresenter(MVP_Main.RequiredViewOps viewOps) {
        mView = new WeakReference<>(viewOps);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    private MVP_Main.RequiredViewOps getView() throws NullPointerException {
        if (mView != null) {
            return mView.get();
        } else throw new NullPointerException("View is unavailable");
    }

    @Override
    public void setView(MVP_Main.RequiredViewOps view) {
        mView = new WeakReference<MVP_Main.RequiredViewOps>(view);
    }

    public void setModel(MVP_Main.ProvidedModelOps model) {
        mModel = model;
        loadData();
    }

    private void loadData() {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return mModel.loadData();
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    try {
                        getView().hideProgress();
                        if (!aBoolean) {
                            getView().showToast(makeToast("Error"));
                        } else {
                            getView().notifyDataSetChanged();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getActivityContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public int getNotesCount() {
        return mModel.getNotesCount();
    }

    @Override
    public NotesViewHolder createViewHolder(ViewGroup parent, int viewType) {
        NotesViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(R.layout.holder_notes, parent, false);
        viewHolder = new NotesViewHolder(viewTaskRow);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(final NotesViewHolder holder, int position) {
        final Note note = mModel.getNote(position);
        holder.text.setText(note.getText());
        holder.date.setText(note.getDate());
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void clickNewNote(EditText editText) {
        getView().showProgress();
        final String noteText = editText.getText().toString();
        if(!noteText.isEmpty()) {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return mModel.insertNote(makeNote(noteText));
                }

                @Override
                protected void onPostExecute(Integer adapterPosition) {
                    try {
                        if(adapterPosition > -1) {
                            getView().clearEditText();
                            getView().notifyItemInserted(adapterPosition);
                            getView().notifyItemmRangeChanged(adapterPosition, mModel.getNotesCount());
                        } else {
                            getView().hideProgress();
                            getView().showToast(makeToast("Error creating note"));
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } else {
            try {
                getView().showToast(makeToast("Cannot add a blank note!"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public Note makeNote(String noteText) {
        Note note = new Note();
        note.setText(noteText);
        note.setDate(getDate());
        return note;
    }
    private String getDate() {
        return new SimpleDateFormat("HH:mm:ss - MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
    public void clickDeleteNote(final Note note, final int adapterPos, final int layoutPos) {
    }
}
