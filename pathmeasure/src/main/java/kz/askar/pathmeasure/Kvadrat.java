package kz.askar.pathmeasure;

import android.graphics.Rect;

/**
 * Created by Zhakenov on 4/8/2017.
 */

public class Kvadrat {

    int x;
    int y;
    int width;
    int height;
    int speed = 5;

    int xSign = 1;
    int ySign = 1;

    public Kvadrat(int x, int y, int width, int height){
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
            ySign = -1;
        }
    }
}
