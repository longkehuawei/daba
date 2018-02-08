package com.longke.shot.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.longke.shot.R;
import com.longke.shot.entity.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longke on 2018/2/3.
 */

public class PointView extends View {
    private float mRadio = 3;
    private int mPointColor = Color.BLACK;
    private Paint mPaint;
    private float x;
    private float y;
    private float pre=1.0f;
    private boolean isShowRed=true;

    public void setShowRed(boolean showRed) {
        isShowRed = showRed;
        invalidate();
    }

    List<Info.DataBean.ShootDetailListBean> mShootDetailListBean=new ArrayList<>();

    public void setShootDetailListBean(List<Info.DataBean.ShootDetailListBean> shootDetailListBean) {
        mShootDetailListBean = shootDetailListBean;
        invalidate();
    }

    public void setBilu(float pre) {
        this.pre=pre;
    }

    public PointView(Context context) {
        super(context);
        initPaint();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.PointView);
            mRadio = ta.getDimension(R.styleable.PointView_radio, dip2px(getContext(),3));
            mPointColor = ta.getColor(R.styleable.PointView_pointColor, Color.YELLOW);
            x=mRadio;
            y=mRadio;
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
        initPaint();
    }

    private void initPaint(){
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(mPointColor);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<mShootDetailListBean.size();i++){
            if(i==mShootDetailListBean.size()-1){
                mPaint.setColor(Color.parseColor("#00FF00"));
            }else{
                mPaint.setColor(mPointColor);
            }
            Info.DataBean.ShootDetailListBean shootDetailListBean=mShootDetailListBean.get(i);
            canvas.drawCircle(shootDetailListBean.getX()*pre,shootDetailListBean.getY()*pre,shootDetailListBean.getWidth()*pre/2,mPaint);
            if(isShowRed){
                mPaint.setColor(Color.parseColor("#ffff00"));
                mPaint.setTextSize(20);
                canvas.drawText(""+(i+1), shootDetailListBean.getX()*pre-shootDetailListBean.getWidth()*pre/2, shootDetailListBean.getY()*pre-shootDetailListBean.getWidth()*pre/2, mPaint);
            }

        }
        if(mShootDetailListBean.size()==0){
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth = 0;
        int resultHeight = 0;
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specWidthMode == MeasureSpec.AT_MOST) {//wrap_content
            resultWidth = (int) (mRadio * 2);
        }else{//match_parent
            resultWidth = specWidthSize;
        }

        if (specHeightMode == MeasureSpec.AT_MOST) {//wrap_content
            resultHeight = (int) (mRadio * 2);
        }else{//match_parent
            resultHeight = specHeightSize;
        }

        setMeasuredDimension(resultWidth,resultHeight);
    }
    public void setColor(int color){
        initPaint();
        mPaint.setColor(color);
        invalidate();
    }

    public void setRadio(int radio){
        if (radio > 0) {
            mRadio = dip2px(getContext(),radio);
        }
        requestLayout();
    }



    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
