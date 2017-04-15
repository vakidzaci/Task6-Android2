package kz.askar.canvasgroup1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    DrawView mPaintSurface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPaintSurface = new DrawView(this);
        setContentView(mPaintSurface);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_UP:
                if(!mPaintSurface.t.isAlive())
                mPaintSurface.t.start();
                break;
            case MotionEvent.ACTION_MOVE:
                mPaintSurface.rock.x=(int)event.getX();
                break;
        }
        return true;
    }
}
