package com.example.philipcorriveau.customgestures.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.philipcorriveau.customgestures.Constants;

/**
 * Created by philipcorriveau on 10/10/14.
 */
public class DrawingView extends View{

    private Paint paint;
    private Paint alphaPaint;
    private Path path;
    private Path previousPath;

    private ValueAnimator fadeOutAnimator;
    private boolean animating;

    public DrawingView(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(Constants.DRAWING_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        alphaPaint = new Paint();
        alphaPaint.set(paint);

        path = new Path();
        previousPath = new Path();
        setupAnimator();
    }

    public void setupAnimator() {
        fadeOutAnimator = ValueAnimator.ofInt(255, 0);
        fadeOutAnimator.setDuration(500);
        fadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                alphaPaint.setAlpha(animatedValue);
                invalidate();
            }
        });
        fadeOutAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        if (!previousPath.isEmpty()) {
            canvas.drawPath(previousPath, alphaPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d("touch", "ACTION_DOWN");
                path.moveTo(event.getX(), event.getY());
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d("touch", "ACTION_MOVE");
                path.lineTo(event.getX(), event.getY());
                break;
            case (MotionEvent.ACTION_UP) :
                Log.d("touch", "ACTION_UP");
                previousPath.set(path);
                path.reset();
                if (animating) {
                    fadeOutAnimator.cancel();
                }
                fadeOutAnimator.start();
                break;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("touch", "ACTION_CANCEL");
                return true;
        }

        invalidate();
        return super.onTouchEvent(event);
    }
}
