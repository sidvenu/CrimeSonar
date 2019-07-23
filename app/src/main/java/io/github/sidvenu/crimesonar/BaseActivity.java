package io.github.sidvenu.crimesonar;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

abstract class BaseActivity extends AppCompatActivity {
    ProgressBar progressBar = null;

    void initProgressBar() {
        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
    }

    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressBar();
        hideProgressBar();
    }
}
