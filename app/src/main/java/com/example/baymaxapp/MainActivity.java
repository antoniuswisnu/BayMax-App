package com.example.baymaxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();

    private ConstraintLayout my_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.my_img_view);
        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
        my_layout = findViewById(R.id.my_layout);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();
        float centerX = vWidth / 2f;
        float centerY = vHeight / 2f;
        float radiusX = vWidth / 3f;
        float radiusY = vHeight / 4f;

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);

        mImgView.setVisibility(View.VISIBLE);


        my_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateBaymax(view);
                mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(mColorBackground);
                drawHead(centerX, centerY, radiusX, radiusY);
                drawRightEye(vWidth, vHeight);
                drawLeftEye(vWidth, vHeight);
                drawEyeConnector(vWidth, vHeight);
                mCanvas.rotate(360, centerX, centerY);
            }
        });

    }

    public void drawHead(float centerX, float centerY, float radiusX, float radiusY) {
        mCanvas.drawOval(centerX - radiusY, centerY - radiusX, centerX + radiusY,
                centerY + radiusX, mHeadPaint);
    }

    public void drawRightEye (float vWidth, float vHeight) {
        mCanvas.drawCircle(vWidth / 2 - 200,vHeight / 2, 80, mCirclePaint);
    }

    public void drawLeftEye (float vWidth, float vHeight) {
        mCanvas.drawCircle(vWidth / 2 + 200,vHeight / 2, 80, mCirclePaint);
    }

    public void drawEyeConnector (float vWidth, float vHeight) {
        mCanvas.drawRect(vWidth / 2 - 175, vHeight / 2 + 20,
                vWidth / 2 + 175, vHeight / 2 -20, mCirclePaint);
    }

    public void animateBaymax(View view){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImgView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(1000);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mImgView, "rotationY", 180);
        rotationAnimator.setDuration(1000);

        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        alphaAnimator2.setDuration(1000);
        alphaAnimator2.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaAnimator, rotationAnimator, alphaAnimator2);
        animatorSet.start();
    }

}