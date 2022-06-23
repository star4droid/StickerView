package com.star4droid.StickerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.MotionEvent;
import android.os.Handler;
import android.os.Looper;
import android.content.Context;
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
									if(!drag){
									frame.setRotation((float)angle-45-90);
									selected.setRotation((float)angle-45-90);
									if(OnEvent != null) OnEvent.onRotation((float)angle-45-90);
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
									 frame.setLayoutParams(new FrameLayout.LayoutParams((int) (cx),(int) (cy)));
									selected.setLayoutParams(new FrameLayout.LayoutParams((int) (cx-dw),(int) (cy-dh)));
									if(OnEvent != null) OnEvent.onSizeChanged((int)(cx-dw),(int)(cy-dh));
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
setLayoutParams(new FrameLayout.LayoutParams((int) (v.getMeasuredWidth()+3),(int) (v.getMeasuredHeight()+3)));
setX(v.getX()-3);
setY(v.getY()-3);
setRotation(v.getRotation());
scale.setX(/*v.getX()+*/v.getMeasuredWidth()-scale.getMeasuredWidth());
scale.setY(/*v.getX()+*/v.getMeasuredHeight()-scale.getMeasuredHeight());
close.setX(/*v.getX()+*/v.getMeasuredWidth()-close.getMeasuredWidth());
rotate.setY(/*v.getY()+*/v.getMeasuredHeight()-rotate.getMeasuredHeight());
close.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getWidth() / 5),(int) (this.getHeight() / 5)));
rotate.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getWidth() / 5),(int) (this.getHeight() / 5)));
scale.setLayoutParams(new FrameLayout.LayoutParams((int) (this.getWidth() / 5),(int) (this.getHeight() / 5)));
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
@Override
    public boolean onTouchEvent(MotionEvent event) {
switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
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
					drag=true;
					StartPT = new PointF(getX(),getY());
					SX=getX()-selected.getX();
					SY=getY()-selected.getY();
					break;
					case MotionEvent.ACTION_UP : 
					select(selected);
					drag=false;
					break;
					default : break;
				} return true;
} 
}
