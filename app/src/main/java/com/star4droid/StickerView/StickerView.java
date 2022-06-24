package com.star4droid.StickerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.MotionEvent;
import android.os.Handler;
import android.os.Looper;
import android.content.Context;
import android.util.TypedValue;
import android.graphics.PointF;
import android.graphics.Color;
public class StickerView extends FrameLayout {
View selected;
ImageView scale;
ImageView rotate;
ImageView close;
public StickerView(Context ctx){
super(ctx);
scale= new ImageView(ctx);
rotate =new ImageView(ctx);
close = new ImageView(ctx);
addView(scale);
addView(rotate);
addView(close);
/*float minThis= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100, ctx.getResources().getDisplayMetrics());
float minOther = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50, ctx.getResources().getDisplayMetrics());
setMinimumWidth(100);
setMinimumHeight(100);
scale.setMinimumWidth(50);
close.setMinimumWidth(50);
rotate.setMinimumWidth(50);*/
close.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View _view){
OnEvent.onClose();
}
});
setVisibility(View.GONE);
setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)1, 0xFF607D8B, Color.TRANSPARENT));
rotate.setImageResource(R.drawable.ic_loop_black);
scale.setImageResource(R.drawable.ic_call_made_black);
close.setImageResource(R.drawable.ic_highlight_remove_black);
final  StickerView frame =this; 
rotate.setOnTouchListener(new View.OnTouchListener(){
		            private float initialTouchX;
		            private float initialTouchY;
		            private float initialX;
		            private float initialY;
					private float sx;
					private float sy;
					private int ex;
					private int ey;
					private float centerX;
					private float centerY;
					private float dry;
		            public boolean onTouch(View view, MotionEvent motionEvent) {
			                int n = motionEvent.getAction();
							if(n==MotionEvent.ACTION_UP){
								if(motionEvent.getX()==sx){
									if(motionEvent.getY()==sy){
										//onclick
									}
								}
							}
							if(n==MotionEvent.ACTION_DOWN){
								sx=motionEvent.getX();
								sy=motionEvent.getY();
								rotation=view.getRotation();
								centerY=frame.getY()+frame.getMeasuredHeight()/2;
								centerX=frame.getX()+frame.getMeasuredWidth()/2;
							}
			                if (n != 0) {
				                    if (n != 2) {
					                        return true;
					                    }
										//}
				                    float cx = this.initialX + (int)(motionEvent.getRawX() - this.initialTouchX);
				                    float cy = this.initialY + (int)(motionEvent.getRawY() - this.initialTouchY);
				                    //frame.setLayoutParams(new FrameLayout.LayoutParams((int) (cx),(int) (cy)));
									
									double angle = Math.atan2(motionEvent.getRawY() - centerY, motionEvent.getRawX() - centerX) * 180 / Math.PI;
			angle=angle-45-90;
			if(angle<0) angle+=360;
									if(!drag){
									frame.setRotation((float)angle);
									selected.setRotation((float)angle);
									if(OnEvent != null) OnEvent.onRotation((float)angle);
									}
				                    return true;
				                }
			                this.initialX = frame.getX()+frame.getMeasuredWidth()/2;
			                this.initialY = frame.getY()+frame.getMeasuredHeight()/2;
			                this.initialTouchX = motionEvent.getRawX();
			                this.initialTouchY = motionEvent.getRawY();
			                return true;
			            }
		        });
scale.setOnTouchListener(new View.OnTouchListener(){
		            private float initialTouchX;
		            float initialTouchY;
		            private int initialX;
		            private int initialY;
					private float sx;
					private float sy;
					private int ex;
					private int ey;
					private int dw;
					private int dh;
					private float xx;
					private float yy;
		            public boolean onTouch(View view, MotionEvent motionEvent) {
			                int n = motionEvent.getAction();
							if(n==MotionEvent.ACTION_UP){
								if(motionEvent.getX()==sx){
									if(motionEvent.getY()==sy){
										//onclick
									}
								}
							}
							if(n==MotionEvent.ACTION_DOWN){
								sx=motionEvent.getX();
								sy=motionEvent.getY();
								xx=selected.getX();
								yy=selected.getY();
								dw=selected.getMeasuredWidth()-frame.getMeasuredWidth();
								dh=selected.getMeasuredHeight()-frame.getMeasuredHeight();
							}
			                if (n != 0) {
				                    if (n != 2) {
					                        return true;
					                    }
										//}
				                    float cx = this.initialX + (int)(motionEvent.getRawX() - this.initialTouchX);
				                    float cy = this.initialY + (int)(motionEvent.getRawY() - this.initialTouchY);
				                 if((cy>0)&&(cx>0)&&(!drag)){ 
		 //frame.setLayoutParams(new FrameLayout.LayoutParams((int) (cx),(int) (cy)));
		 selected.setLayoutParams(new FrameLayout.LayoutParams((int) (cx-dw),(int) (cy-dh)));
		 nh=(int)(cy-dh);
		 nw=(int)(cx-dw);
	if(OnEvent != null) OnEvent.onSizeChanged((int)(cy-dh),(int)(cx-dw));
									}
									selected.setX(xx);
									selected.setY(yy);
									frame.select(selected);
				                    return true;
				                }
			                this.initialX = frame.getMeasuredWidth();
			                this.initialY = frame.getMeasuredHeight();
			                this.initialTouchX = motionEvent.getRawX();
			                this.initialTouchY = motionEvent.getRawY();
			                return true;
			            }
		        });
}
int nh=9999;//lazy way
int nw;
interface onEvent {
public void onRotation(float rotation);
public void onClose();
public void onSizeChanged(int height,int width);
}
onEvent OnEvent;
public void setOnEvent(onEvent i){
	OnEvent =i;
}
private class selectThread extends Thread {
@Override
public void run(){
try {
Thread.sleep(100);
} catch(java.lang.InterruptedException e){}
new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				select(selected);
			}
		});
}
} 

public void select(View v){
selected=v;
setVisibility(View.VISIBLE);
if(nh==9999){
nw=(int)(v.getMeasuredWidth()+3);
nh =(int)(v.getMeasuredHeight()+3);
}
setLayoutParams(new FrameLayout.LayoutParams(nw,nh));
if(!(nw>100)){getLayoutParams().width=100;}
if(!(nh>100)){getLayoutParams().height=100;}
setX(v.getX()-3);
setY(v.getY()-3);
setRotation(v.getRotation());
boolean b=(getMeasuredWidth()/5)>=50;
if(b&&((getMeasuredHeight()/5)>=50)) {
close.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),(int) (this.getMeasuredHeight() / 5)));
rotate.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),(int) (this.getMeasuredHeight() / 5)));
scale.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),(int) (this.getMeasuredHeight() / 5)));
} else if(b){
close.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),50));
rotate.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),50));
scale.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getMeasuredWidth() / 5),50));
} else if(((getMeasuredHeight()/5)>=50)) {
close.setLayoutParams(new FrameLayout.LayoutParams(50,(int) (this.getMeasuredHeight() / 5)));
scale.setLayoutParams(new FrameLayout.LayoutParams(50,(int) (this.getMeasuredHeight() / 5)));
rotate.setLayoutParams(new FrameLayout.LayoutParams(50,(int) (this.getMeasuredHeight() / 5)));
} else {
close.setLayoutParams(new FrameLayout.LayoutParams(50,50));
scale.setLayoutParams(new FrameLayout.LayoutParams(50,50));
rotate.setLayoutParams(new FrameLayout.LayoutParams(50,50));
}
scale.setX(getMeasuredWidth()-scale.getMeasuredWidth());
scale.setY(getMeasuredHeight()-scale.getMeasuredHeight());
close.setX(getMeasuredWidth()-close.getMeasuredWidth());
rotate.setY(getMeasuredHeight()-rotate.getMeasuredHeight());
if(rotate.getMeasuredHeight()==0) new selectThread().start();
} 
public void unselect(){
setVisibility(View.GONE);
} 
 private PointF DownPT = new PointF();
private PointF StartPT = new PointF();
private float SX;
private boolean drag=false;
private float SY;
private float rotation;
@Override
    public boolean onTouchEvent(MotionEvent event) {
switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
					//setRotation(0);
					//selected.setRotation(0);
					PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
					setX((int)(StartPT.x+mv.x));
					setY((int)(StartPT.y+mv.y));
					selected.setX((int)(StartPT.x+mv.x+SX));
					selected.setY((int)(StartPT.y+mv.y+SY));
					StartPT = new PointF(getX(),getY());
					break;
					case MotionEvent.ACTION_DOWN : 
					DownPT.x = event.getX();
					DownPT.y = event.getY();
					rotation=getRotation();
					setRotation(0);
					setAlpha(0);
					setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)1, Color.TRANSPARENT, Color.TRANSPARENT));
					//selected.setRotation(0);
					drag=true;
					StartPT = new PointF(getX(),getY());
					SX=getX()-selected.getX();
					SY=getY()-selected.getY();
					break;
					case MotionEvent.ACTION_UP : 
					setRotation(rotation);
					setAlpha(1);
					selected.setRotation(rotation);
					setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)1, 0xFF607D8B, Color.TRANSPARENT));
					select(selected);
					drag=false;
					break;
					default : break;
				} return true;
} 
}