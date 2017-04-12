package kz.askar.canvasgroup1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Zhakenov on 4/8/2017.
 */

public class DrawView extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder surfaceHolder;
    boolean isRunning = false;

    int width = 0;
    int height = 0;

    Kvadrat kvadrat = null;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
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
        if(kvadrat!=null){
            kvadrat.move(width, height);
        }
    }

    public void draw(Canvas canvas){
        if(width==0) width = canvas.getWidth();
        if(height==0) height = canvas.getHeight();
        if(kvadrat == null) kvadrat = new Kvadrat(0, 0, height/10, height/10);

        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        canvas.drawARGB(55, 55, 55, 55);

        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(kvadrat.getRect(), p);
    }
}
