package com.example.framgia.mvp.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.framgia.mvp.R;
import com.example.framgia.mvp.model.MainModel;
import com.example.framgia.mvp.myinterface.MVP_Main;
import com.example.framgia.mvp.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
    MVP_Main.RequiredViewOps {
    private MVP_Main.ProvidedPresenterOps mPresenter;
    private EditText mTextNewNote;
    private ListNotes mListAdapter;
    private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setUpMVP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    private void setUpViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        mTextNewNote = (EditText) findViewById(R.id.edit_note);
        mListAdapter = new ListNotes();
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        RecyclerView mList = (RecyclerView) findViewById(R.id.list_notes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
    }

    private void setUpMVP() {
        mPresenter = new MainPresenter(this);
        MainModel model = new MainModel((MainPresenter)mPresenter);
        ((MainPresenter) mPresenter).setModel(model);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab: {
                mPresenter.clickNewNote(mTextNewNote);
            }
        }
    }

    @Override
    public Context getAppContext() {
        return getActivityContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showAlert(AlertDialog alertDialog) {
        alertDialog.show();
    }

    @Override
    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearEditText() {
        mTextNewNote.setText("");
    }

    @Override
    public void notifyItemRemoved(int position) {
        mListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mListAdapter.notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemmRangeChanged(int positionStart, int itemCount) {
        mListAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }
    private class ListNotes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mPresenter.bindViewHolder((NotesViewHolder)holder, position);
        }

        @Override
        public int getItemCount() {
            return mPresenter.getNotesCount();
        }
    }
}
