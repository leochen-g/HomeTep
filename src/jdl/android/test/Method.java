package jdl.android.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Method {

	public static String str[] = { "发烧了", "正常", "低温了" };
	public static String[] Text = { "30", "31", "32", "33", "34", "35", "36",
			"37", "38", "39", "40", "41", "42" };// 定义主界面坐标

	public static void drawBack(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas(); // 锁定画布
		// 绘制白色背景
		canvas.drawColor(Color.WHITE);
		Paint p = new Paint();
		Paint p1 = new Paint();
		Paint p2 = new Paint();
		Paint p3 = new Paint();
		Paint p4 = new Paint();
		Paint p5 = new Paint();
		Paint p6 = new Paint();
		Paint p7 = new Paint();
		Paint p8 = new Paint();
		Paint p9 = new Paint();
		p.setColor(Color.GRAY);
		p3.setColor(Color.BLACK);
		p1.setColor(Color.YELLOW);
		p2.setColor(Color.RED);
		p6.setColor(Color.DKGRAY);
		p4.setColor(Color.BLUE);
		p7.setColor(Color.BLUE);
		p5.setColor(Color.MAGENTA);
		p8.setColor(Color.MAGENTA);
		p9.setColor(Color.RED);
		p3.setTextSize(30);// 设置字体大小
		p.setStyle(Paint.Style.FILL);
		p1.setStyle(Paint.Style.FILL);
		// p2.setStyle(Paint.Style.STROKE);
		p6.setStyle(Paint.Style.STROKE);
		// p7.setStyle(Paint.Style.STROKE);
		// p8.setStyle(Paint.Style.STROKE);
		// p3.setStyle(Paint.Style.FILL);
		p.setStyle(Paint.Style.FILL);
		p.setStrokeWidth(1);
		p2.setStrokeWidth(25);
		p4.setStrokeWidth(25);
		p5.setStrokeWidth(25);
		p6.setStrokeWidth(10);
		p7.setStrokeWidth(10);
		p8.setStrokeWidth(10);
		p9.setStrokeWidth(10);
		p3.setStrokeWidth(3);
		p6.setTextSize(30);
		p7.setTextSize(100);
		p8.setTextSize(100);
		p9.setTextSize(100);
		p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		p1.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		p2.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		p3.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		p4.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		p5.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

		RectF oval = new RectF(100, 150, 400, 400);
		canvas.drawArc(oval, 30, 230, false, p6);
		canvas.drawLine(150, 335, 385, 335, p6);

		RectF re4 = new RectF(220, 130, 260, 165);
		canvas.drawOval(re4, p3);

		canvas.drawRect(500, 0, 540, 660, p);
		RectF re3 = new RectF(445, 645, 595, 765);
		canvas.drawOval(re3, p);
		if (MainActivity.temp4 >= 38) {
			// 画当前温度图形

			canvas.drawText(String.valueOf(MainActivity.temp4) + "℃", 170, 300,
					p9);
			RectF re21 = new RectF(450, 650, 590, 760);
			canvas.drawOval(re21, p2);
			canvas.drawLine(520, 660, 520, Y_Label(), p2);
			canvas.drawText(str[0], 120, 600, p9);
		}
		if ((MainActivity.temp4 < 38) && (MainActivity.temp4 > 36)) {
			canvas.drawText(String.valueOf(MainActivity.temp4) + "℃", 170, 300,
					p8);
			RectF re21 = new RectF(450, 650, 590, 760);
			canvas.drawOval(re21, p5);
			canvas.drawLine(520, 660, 520, Y_Label(), p5);
			canvas.drawText(str[1], 120, 600, p8);
		}
		if (MainActivity.temp4 <= 36 && MainActivity.temp4 >= 30) {
			RectF re21 = new RectF(450, 650, 590, 760);
			canvas.drawOval(re21, p4);
			canvas.drawLine(520, 660, 520, Y_Label(), p4);
			canvas.drawText(String.valueOf(MainActivity.temp4) + "℃", 170, 300,
					p7);
			canvas.drawText(str[2], 120, 600, p7);

		}

		RectF re2 = new RectF(470, 665, 570, 745);
		canvas.drawOval(re2, p1);

		// canvas.drawCircle(470, 600, 50, p);
		for (int i = 0; i < 13; i++) {
			canvas.drawLine(540, (610 - 50 * i), 570, (610 - 50 * i), p3);
			for (int m = 0; m < 13; m++) {
				canvas.drawLine(540, (610 - 50 * i - m * 5), 560,
						(610 - 50 * i - m * 5), p);
			}
			canvas.drawText(Text[i], 580, 620 - 50 * i, p3);
		}

		holder.unlockCanvasAndPost(canvas); // 结束锁定 显示在屏幕上
		holder.lockCanvas(new Rect(0, 0, 0, 0)); // 锁定局部区域，其余地方不做改变
		holder.unlockCanvasAndPost(canvas);

	}

	private static int Y_Label() {
		// TODO Auto-generated method stub

		int n1 = MainActivity.temp4 - 30;
		return 610 - n1 * 50;
		// return 200;
	}

}
