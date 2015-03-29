package com.ej.quicksamples.explorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.ej.quicksamples.R;
import com.ej.quicksamples.player.PlayerActivity;
import com.ej.quicksamples.utils.Constants;

/**
 * Activity of Music Files Explorer
 *
 * @author Emil Jarosiewicz
 */
public class ExplorerActivity extends Activity implements ExplorerActivityView, AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.searchText)
    EditText searchText;

    @InjectView(R.id.buttonText)
    TextView buttonText;

    FileListAdapter fileListAdapter;

    private ExplorerPresenter explorerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.in_transition_explo, R.anim.out_transition_explo);
        setContentView(R.layout.activity_explorer);
        ButterKnife.inject(this);
        fileListAdapter = new FileListAdapter(this);
        fileListAdapter.setDir(Environment.getRootDirectory().getAbsolutePath());
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fileListAdapter.getFilter().filter(s.toString());
            }
        });
        explorerPresenter = new ExplorerPresenterImpl(this);
        buttonText.setText(getResources().getString(R.string.buttonNumber) + " " +
                getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1));
        explorerPresenter.onInit(fileListAdapter);
    }


    /**
     * Loads file list
     */
    @Override
    public void loadFileList() {
        listView.setAdapter(fileListAdapter);
        listView.setOnItemClickListener(this);
    }


    /**
     * Starting Player Activity
     *
     * @param path Path of the file
     */
    @Override
    public void itemClickFinish(String path) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.FILE_PATH, path);
        intent.putExtra(Constants.BUTTON_NUMBER, getIntent().getIntExtra(Constants.BUTTON_NUMBER, 1));
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (fileListAdapter.getItemViewType((int)id)==Constants.TYPE_ITEM)
            explorerPresenter.onItemClick(position, fileListAdapter);
    }
}
