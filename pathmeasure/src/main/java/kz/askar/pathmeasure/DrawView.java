package kz.askar.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import kz.askar.pathmeasure.Kvadrat;

/**
 * Created by Zhakenov on 4/8/2017.
 */

public class DrawView extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder surfaceHolder;
    boolean isRunning = false;

    int width = 0;
    int height = 0;
    Matrix matrix = new Matrix();
    Path path;
    PathMeasure pathMeasure;
    float length = 0;
    float distance = 0;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);

        path = new Path();
        path.reset();
        path.moveTo(200, 300);
        path.lineTo(121, 500);
        path.cubicTo(350, 100, 600, 800, 121, 1000);
        path.close();

        pathMeasure = new PathMeasure(path, false);
        length = pathMeasure.getLength();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        isRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    Canvas canvas = surfaceHolder.lockCanvas();
                    if(canvas==null) break;
                    long startTime = System.currentTimeMillis();
                    update();
                    draw(canvas);
                    long drawTime = System.currentTimeMillis() - startTime;
                    float fps = 1000/(drawTime==0?1:drawTime);
                    Log.d("fps", fps+"");
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    public void update(){
        distance+=3;
        if(distance>length) distance = 0;

        matrix = new Matrix();
        pathMeasure.getMatrix(distance, matrix, PathMeasure.POSITION_MATRIX_FLAG +
                PathMeasure.TANGENT_MATRIX_FLAG);

    }

    public void draw(Canvas canvas){
        if(width==0) width = canvas.getWidth();
        if(height==0) height = canvas.getHeight();

        canvas.drawColor(Color.WHITE);

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, p);
        canvas.drawCircle(350, 100, 20, p);
        canvas.drawCircle(600, 800, 20, p);


        RectF rect = new RectF(-100, -100, 100, 100);
        canvas.drawRect(rect, p);
        canvas.setMatrix(new Matrix());
        canvas.setMatrix(matrix);
        canvas.drawRect(rect, p);


        //game with matix
//        Rect rect = new Rect(200, 200, 400, 400);
//        Matrix matrix = new Matrix();
//        matrix.setScale(2, 2, 300, 300);
//        matrix.preTranslate(100, 100);
//        matrix.postRotate(30, 500, 500);
//
//
//        canvas.drawRect(rect, p);
//        p.setColor(Color.RED);
//        canvas.setMatrix(matrix);
//        canvas.drawRect(rect, p);
    }
}
