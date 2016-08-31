package com.example.framgia.mvp.myinterface;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgia.mvp.model.Note;
import com.example.framgia.mvp.view.NotesViewHolder;

/**
 * Created by framgia on 29/08/2016.
 */
public interface MVP_Main {
    interface RequiredViewOps {
        Context getAppContext();
        Context getActivityContext();
        void showToast(Toast toast);
        void showProgress();
        void hideProgress();
        void showAlert(AlertDialog alertDialog);
        void notifyDataSetChanged();
        void clearEditText();
        void notifyItemRemoved(int position);
        void notifyItemInserted(int layoutPosition);
        void notifyItemmRangeChanged(int positionStart, int itemCount);
    }

    interface ProvidedPresenterOps {
        void onDestroy(boolean isChangingConfiguration);
        void setView(RequiredViewOps view);
        void clickNewNote(EditText editText);
        void clickDeleteNote(Note note, int adapterPos, int layoutPos);
        int getNotesCount();
        NotesViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(NotesViewHolder holder, int position);
    }

    interface RequiredPresenterOps {
        Context getAppContext();
        Context getActivityContext();
    }

    interface ProvidedModelOps {
        void onDestroy(boolean isChangingConfiguration);
        boolean deleteNote(Note note, int adapterPos);
        int getNotesCount();
        Note getNote(int position);
        int insertNote(Note note);
        boolean loadData();
    }
}
