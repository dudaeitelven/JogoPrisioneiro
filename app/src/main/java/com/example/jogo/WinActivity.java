package com.example.jogo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class WinActivity extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button buttonOk;
    private ImageView imageView;

    public WinActivity(Activity activity) {
        super(activity);
        this.activity = activity;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        WindowManager wm = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        View layoutView = findViewById(R.id.dialogCustomLayout);
        layoutView.getLayoutParams().width = metrics.widthPixels - 200;

        init();
    }

    private void init(){
        buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.win);
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }
}
