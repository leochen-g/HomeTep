package jdl.android.test;
import java.util.List;

import jdl.android.test.Data_Recording;
import jdl.android.test.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;

public class Data_Recording extends Activity{
	final int HEIGHT=980;   //设置画图范围高度
    final int WIDTH=800;    //画图范围宽度
    SurfaceView surface = null;
    private SurfaceHolder holder = null;    //画图使用，可以控制一个SurfaceView
    int d[]=new int[100];
    
    
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_recording);
        
		surface = (SurfaceView)findViewById(R.id.show);
        //初始化SurfaceHolder对象
        holder = surface.getHolder();  
        holder.setFixedSize(WIDTH+20, HEIGHT+100);  //设置画布大小，要比实际的绘图位置大一点
        holder.addCallback(new Callback() {  
            public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){ 
                drawBack(holder); 
                //如果没有这句话，会使得在开始运行程序，整个屏幕没有白色的画布出现
                //直到按下按键，因为在按键中有对drawBack(SurfaceHolder holder)的调用
            }
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub				
			} 
        });
    }
	 private void drawBack(SurfaceHolder holder){ 
	        Canvas canvas = holder.lockCanvas(); //锁定画布
	        //绘制白色背景 
	        canvas.drawColor(Color.WHITE); 
	        Paint p = new Paint(); 
	        Paint p1 = new Paint(); 
	        Paint p2 = new Paint(); 
	        Paint p3 = new Paint(); 
	        p.setColor(Color.DKGRAY); 
	        p1.setColor(Color.RED); 
	        p2.setColor(Color.MAGENTA); 
	        p3.setColor(Color.BLUE); 
	        
	        p.setStrokeWidth(3); 
	        
	        p.setTextSize(45);//设置字体大小
	        p1.setTextSize(45);//设置字体大小
	        p2.setTextSize(45);//设置字体大小
	        p3.setTextSize(45);//设置字体大小
	        
	        Object obj[]=  MainActivity.list.toArray(); 
	    	 Log.v("BreakPoint","获得的数据数组"+obj.length);
	        for(int x=0; x<obj.length; x++){
	      
	        	
	        	 d[x]=Integer.parseInt(String.valueOf(obj[x])); ;
	        	
	        	 Log.v("BreakPoint","获得的数据数组"+d[x]);
	        	}
	    	canvas.drawText("测量时间",210,100, p);
	    	canvas.drawText("温度",600,100, p);
	        
	        for(int i=0;i<11;i++){
	        canvas.drawLine(100,HEIGHT-80*i, 700, HEIGHT-80*i, p);
	        }
	        
	        if(MainActivity.list2.size()>=11){
	        	 for(int i = MainActivity.list1.size()-11,m=0; i <MainActivity.list2.size()&&(m<11); i++,m++){
	        		 
	        		 
	        	 if(d[i]>28){	 
	        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*m-5, p1);
	        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*m-5, p1);
	        		 }
	        	 if(d[i]<26){
	 	        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*m-5, p3);
		        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*m, p3);
	        	 
	        	 }
	        	 if((d[i]<=28)&&(d[i]>=26)){
	 	        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*m-5, p2);
		        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*m-5, p2);	 
	        	 }
	        	}
	        	
	        }
	        else
	        {
	        	for(int i = 0; i <MainActivity.list2.size(); i++){
	        		if(d[i]>28){
		        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*i-5, p1);
		        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*i-5, p1);
	        		}
	
		        	if(d[i]<26){
		        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*i-5, p3);
		        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*i-5, p3);
		        	}
	
	        		if((d[i]<=28)&&(d[i]>=26)){
		        	canvas.drawText((String) ((List) MainActivity.list2).get(i),100,180+80*i-5, p2);
		        	canvas.drawText(String.valueOf(d[i])+"℃",600,180+80*i-5, p2);
	
	        	}
	        
	        
	           }
	        }
	        
	        holder.unlockCanvasAndPost(canvas);  //结束锁定 显示在屏幕上
	        holder.lockCanvas(new Rect(0,0,0,0)); //锁定局部区域，其余地方不做改变
	        holder.unlockCanvasAndPost(canvas); 
	    }

	public void data_recording_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
}
