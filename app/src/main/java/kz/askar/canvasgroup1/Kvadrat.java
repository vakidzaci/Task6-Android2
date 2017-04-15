package kz.askar.canvasgroup1;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Zhakenov on 4/8/2017.
 */

public class Kvadrat  extends View {

    int x;
    int y;
    int width;
    int height;
    int speed = 5;

    int xSign = 1;
    int ySign = 1;

    boolean b=false;
    public Kvadrat(Context context,int x, int y, int width, int height){
        super(context);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect getRect(){
        return new Rect(x, y, x+width, y+height);
    }

    public void move(int screenWidth, int screenHeight){
        x+=xSign*speed;
        y+=ySign*speed;

        if(x<=0){
            x = 0;
            xSign = 1;
        }else if(x+width>=screenWidth){
            x = screenWidth - width;
            xSign = -1;
        }

        if(y<=0){
            y = 0;
            ySign = 1;
        }else if(y+height>=screenHeight){
            y = screenHeight - height;
            b=true;
            ySign = -1;
        }
    }
}
