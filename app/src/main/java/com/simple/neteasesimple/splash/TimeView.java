package com.simple.neteasesimple.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.simple.neteasesimple.R;

public class TimeView extends View {
    //文字画笔
    TextPaint mTextPaint;
    Paint circleP;
    Paint outerP;
    String content = "跳过";
    //文字的间距
    int pading = 5;
    //内圆的直径
    int inner;
    //外圈的直径
    int all;
    //外圈的角度
    int dgree;
    RectF outerRect;

    OnTimeClickListener mListener;

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到xml定义的属性
        TypedArray arry = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        int innerColor = arry.getColor(R.styleable.TimeView_innerColor,Color.BLUE);
        int outerColor =  arry.getColor(R.styleable.TimeView_ringColor,Color.GREEN);

        mTextPaint = new TextPaint();
        //抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.WHITE);

        //内圈画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(innerColor);


        outerP = new Paint();
        outerP.setFlags(Paint.ANTI_ALIAS_FLAG);
        outerP.setColor(outerColor);
        outerP.setStyle(Paint.Style.STROKE);
        outerP.setStrokeWidth(pading);

        //文字的宽度
        float text_Width =  mTextPaint.measureText(content);
        //内圆圈的直径
        inner = (int)text_Width+2*pading;
        //外圆圈的直径
        all = inner+2*pading;

        outerRect = new RectF(pading/2,pading/2,all-pading/2,all-pading/2);
        //使用完成后一定要回收
        arry.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //文字的宽度+内圆的边距*2+画笔的宽度*2
        setMeasuredDimension(all,all);
    }

    public void setListener(OnTimeClickListener listener){
        this.mListener = listener;
    }


    public void setD(int d){
        this.dgree =d;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.RED);
        canvas.drawCircle(all/2,all/2,inner/2,circleP);

        //旋转-90
        canvas.save();
        canvas.rotate(-90,all/2,all/2);
        canvas.drawArc(outerRect,0f,dgree,false,outerP);
        canvas.restore();

        float y = (canvas.getHeight()/2);
        float de = mTextPaint.descent();//+
        float a = mTextPaint.ascent();//-

        //x 左边距
        //y 顶部到baseLine的距离
        canvas.drawText(content,2*pading,y-((de+a)/2),mTextPaint);
    }

    public void setProgess(int total ,int now){
        int space = 360/total;
        dgree = space*now;
        //ui线程
        invalidate();
        //子线程
        //postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                setAlpha(0.3f);
                break;
            case  MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                if(mListener!=null){
                    mListener.onClickTime(this);
                }
                break;
        }
        return true;
    }
}
