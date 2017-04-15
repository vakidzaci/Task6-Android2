package kz.askar.canvasgroup1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Zhakenov on 4/8/2017.
 */

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    boolean isRunning = false;
    Point point = new Point();
    int width = 0;
    int height = 0;
    Thread t;


    Kvadrat kvadrat = null;
    Kvadrat rock = null;
//    Rect[] rects = new Rect[10];
    ArrayList<Rect> rects =new ArrayList<Rect>();

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        isRunning = true;
        Canvas canvas =surfaceHolder.lockCanvas();
        width=canvas.getWidth();
        height=canvas.getHeight();
        surfaceHolder.unlockCanvasAndPost(canvas);
        for(int i=0;i<6;i++){
            rects.add(new Rect((height/10+10)*i,height/10,(height/10+10)*i+100 ,height/10+100));
        }
        rock =new Kvadrat(getContext(),width/2, (int) (height*0.9),100,50);

//        rock.getRect()
        t =new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    Canvas canvas = surfaceHolder.lockCanvas();
                    if(canvas==null) break;
                    long startTime = System.currentTimeMillis();
                    update();
                    draw(canvas);
                    boolean check =true;
                    for(Rect q : rects){
                        if(check){
//                        if(Math.abs(kvadrat.getRect().centerX()-q.centerX())<50 && Math.abs(kvadrat.getRect().centerY()-q.centerY())<50){
                          if(q.intersect(kvadrat.getRect())){
                              Log.d("points","rock bottom"+ kvadrat.getRect().bottom+ "   q bottom "+q.bottom);
                              Log.d("points","rock top"+ kvadrat.getRect().top+ "   q top "+q.top);
                              if((kvadrat.getRect().right-q.left<10
                                      ||
                                      q.right-kvadrat.getRect().left<10)

                                      ){
                                  kvadrat.xSign=kvadrat.xSign*(-1);
                                  Log.d("crash","right or left");
                              }
                              else {
                                  kvadrat.ySign=kvadrat.ySign*(-1);
                                  Log.d("crash","bottom");
                              }
                              q.offset(-500,-500);
                              rects.remove(q);
                              check=false;
                              break;
                        }
                    }}
                    if(kvadrat.getRect().intersect(rock.getRect()))kvadrat.ySign=kvadrat.ySign*(-1);
                    long drawTime = System.currentTimeMillis() - startTime;
                    float fps = 1000/(drawTime==0?1:drawTime);
                    Log.d("fps", fps+"");
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    if(kvadrat.b || rects.size()==0)isRunning=false;

                }
            }
        });
    }

    private void method() {;
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
        if(kvadrat == null) kvadrat = new Kvadrat(getContext(),width/2,height/2, height/15, height/15);

        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        canvas.drawARGB(55, 55, 55, 55);

        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        for(int i=0;i<rects.size();i++){
            canvas.drawRect(rects.get(i),p);

        }
        p.setColor(Color.RED);
        canvas.drawRect(kvadrat.getRect(), p);
        p.setColor(Color.BLUE);
        canvas.drawRect(rock.getRect(),p);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int)event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                point.set(x,y);
                Log.d("MyLogs ","x  "+x + "  y   "+y);
                break;
        }
        return super.onTouchEvent(event);
    }
}
