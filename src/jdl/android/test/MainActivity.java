package jdl.android.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static int temp4 = 18;
	public static int num = 0;
	public static List list = new ArrayList();
	public static Collection list1 = new ArrayList();
	public static Collection list2 = new ArrayList();

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	private static final int REQUEST_NEW_CARD = 3;
	// public static String XLabel[]= null;
	// public static int[] Data;
	// public static String[] XLabel;

	String data1 = "0000";
	String data2 = "0000";
	String Button_Buffer = "00000000";
	String seekBar_BufferString = "82";
	String messege_String = "";

	// public static String[] XLabel={};
	public String[] Text = { "20", "21", "22", "23", "24", "25", "26", "27",
			"28", "29" };
	// Layout Views
	private TextView mTitle; // 标题

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BluetoothService mCardService = null;
	Context mContext = null;
	// G_
	final int HEIGHT1 = 700; // 设置画图范围高度
	final int WIDTH1 = 800; // 画图范围宽度
	SurfaceView surface1 = null;
	private SurfaceHolder holder1 = null; // 画图使用，可以控制一个SurfaceView
	private int count = 0;
	private int data01;
	private int data11 = 0;
	private int data21 = 100;
	private String str = "";// 存储发过来的数据（温度值）
	private String ss = "";// 暂存当前发过来的字节
	private Button but_connect;

	// _G
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		surface1 = (SurfaceView) findViewById(R.id.show1);
		but_connect = (Button) findViewById(R.id.but_connect);

		Button bt1 = (Button) findViewById(R.id.bt1);
		bt1.setOnClickListener(new Bt1());

		Button bt4 = (Button) findViewById(R.id.bt4);
		bt4.setOnClickListener(new Bt4());

		// 初始化SurfaceHolder对象
		holder1 = surface1.getHolder();
		holder1.setFixedSize(WIDTH1 + 20, HEIGHT1 + 100); // 设置画布大小，要比实际的绘图位置大一点
		holder1.addCallback(new Callback() {
			public void surfaceChanged(SurfaceHolder holder1, int format,
					int width, int height) {
				Method.drawBack(holder1);
				// 如果没有这句话，会使得在开始运行程序，整个屏幕没有白色的画布出现
				// 直到按下按键，因为在按键中有对drawBack(SurfaceHolder holder)的调用
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder1) {
				// TODO Auto-generated method stub
			}
		});

		mTitle = (TextView) findViewById(R.id.title_left_text); // 左边应用标题
		mTitle.setText(R.string.app_name);
		mTitle = (TextView) findViewById(R.id.title_right_text); // 右边连接状态
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "蓝牙不可用！", Toast.LENGTH_LONG).show();
			Log.e("MainActivity", "3");
			finish();
			return;

		}

		but_connect.setOnClickListener(new But_connect());

		/*
		 * 按钮2 ----请求重发
		 */
		Button bt2 = (Button) findViewById(R.id.bt2);
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// 判断是否已经连接
				if (mCardService.getState() == BluetoothService.STATE_CONNECTED) {
					requestSend();
				} else// 若没有连接
				{
					Toast.makeText(getApplicationContext(),
							R.string.not_connected, Toast.LENGTH_SHORT).show();
				}
			}
		});

		/*
		 * 清空温度显示值
		 */
		Button bt3 = (Button) findViewById(R.id.bt3);
		bt3.setOnClickListener(new OnClickListener() {

			TextView et3 = (TextView) findViewById(R.id.et3);// 显示最高温度值的TextView
			TextView et4 = (TextView) findViewById(R.id.et4);// 显示摄氏度符号的TextView
			TextView et5 = (TextView) findViewById(R.id.et5);// 显示最低温度值的TextView
			TextView et6 = (TextView) findViewById(R.id.et6);// 显示摄氏度符号的TextView

			@Override
			public void onClick(View paramView) {
				et3.setText("");
				et4.setText("");
				et5.setText("");
				et6.setText("");
				data11 = 0;
				data21 = 100;
				// Method.str[]={" "," "," "};
				temp4 = 0;
			}
		});

	}

	// 此处是接收到的数据处理，也就是接受到的数据进行转换变成温度显示，（重要）
	public String onReceiveMess(String data) {
		TextView et3 = (TextView) findViewById(R.id.et3);// 显示温度值的TextView
		TextView et4 = (TextView) findViewById(R.id.et4);// 显示温度值的TextView
		TextView et5 = (TextView) findViewById(R.id.et5);// 显示摄氏度符号的TextView
		TextView et6 = (TextView) findViewById(R.id.et6);// 显示摄氏度符号的TextView

		String data_str = new String(data);

		String temp_signal = new String("℃");

		if (data.length() != 2) {// 发送的数据为2位，若不为2位数据，报错请求重发数据
			str = "";// 清空数据
			ss = "";
			count = 0;

			requestSend();// 请求重发
		}

		// private int data01;
		// private int data11 = 0;
		// private int data21 = 100;
		if (data.length() == 2) {
			data01 = Integer.parseInt(data.trim());
			if (data01 < 44 && data01 > 29) {
				temp4 = data01;
			}

			list.add(temp4);

			SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
			SimpleDateFormat formatter1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String strBeginDate = formatter.format(new Date());
			String strBeginDate1 = formatter1.format(new Date());
			list1.add(strBeginDate);
			list2.add(strBeginDate1);
			Log.v("BreakPoint", "得到的list数组长度为：" + list.size());

			Method.drawBack(holder1);

			num++;

			if ((data11 < data01) && (data01 < 44)) {
				data11 = data01;
			}
			if ((data21 > data01) && (data01 > 29)) {
				data21 = data01;
			}
			data = String.valueOf(data01);
			data1 = String.valueOf(data11);
			data2 = String.valueOf(data21);

			et3.setText(data1);
			et5.setText(temp_signal);
			et4.setText(data2);
			et6.setText(temp_signal);

		}
		return data;
	}

	// 设备连接按钮
	class But_connect implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent serverIntent = new Intent(MainActivity.this,
					DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

		}
	}

	@Override
	public void onStart() {
		// G
		Toast.makeText(getApplicationContext(), "程序开始执行", Toast.LENGTH_SHORT)
				.show();

		super.onStart();
		// 确保蓝牙打开，然后读取名片信息显示在节目上（未打开则在返回信息处理中setupCards()）
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mCardService == null)
				setupCards(); // 初始化ListView对象
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mCardService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mCardService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth chat services
				mCardService.start();
			}
		}
	}

	private void setupCards() {
		// 完成显示内容后，初始化蓝牙服务对象
		mCardService = new BluetoothService(mHandler);
		// Initialize the buffer for outgoing messages
		setmOutStringBuffer(new StringBuffer(""));
	}

	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (mCardService != null) {
			mCardService.stop();
		}
	}

	// 开启蓝牙可见
	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	// 处理线程返回信息
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.v("BreakPoint", "进入handleMessage线程");
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				Log.v("BreakPoint", "如果状态改变");
				Toast.makeText(MainActivity.this, "change", Toast.LENGTH_LONG);
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);
					// mConversationArrayAdapter.clear();
					break;
				case BluetoothService.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					break;
				}
				break;
			// 发送了信息，显示在本地屏幕上（重写，显示结果即可）
			case MESSAGE_WRITE:

				break;
			// 收到了信息，显示在本地屏幕上（重写，加入到通信录中）
			case MESSAGE_READ:

				byte[] readBuf = (byte[]) msg.obj;

				// G
				Log.v("BreakPoint", "把接收到的数据转化为Byte类型");
				// 第一个参数是字节数组，第二个为偏移量，（内容是从第一个位置写入的），第三个参数是长度。
				ss = new String(readBuf, 0, msg.arg1);
				// System.out.print(ss);
				count++;// 计数
				/*
				 * 瞬间温度变化过快可能会影响结果，原因目前不明，可能与线程的调度有关系
				 */

				char end_char = ss.charAt(0);// String类型转为char，并取第一个char，这个是用于停止位判断的。
				// 传过来就转成了String类型，用String类型，无法精确比较字符是否相同。
				Log.v("BreakPoint", "停止位判断");
				if (end_char == 'C') {

					onReceiveMess(str);

					str = "";
					count = 0;
					ss = "";
					Log.v("BreakPoint", "数据清零");
					// requestSend();

				}

				str = str + ss;
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"已成功连接到 " + mConnectedDeviceName, Toast.LENGTH_SHORT)
						.show();
				// G
				requestSend();// 初次连接成功后请求重发
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}

	};

	// 处理其他Activity的返回结果
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// 当点击了设备列表后返回所指向设备的MAC地址，根据此MAC进行主动连接
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mCardService.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// 由确认打开蓝牙Activity返回，若打开成功初始化名片列表
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupCards();
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		case REQUEST_NEW_CARD:
			// 由添加新名片Activity返回，若添加了新card重新读取并显示
			if (resultCode == Activity.RESULT_OK) {
				setupCards();
			}
			break;
		}
	}

	/*
	 * 通过蓝牙发送字节数据 ，多字节也可以
	 */

	private void sendByte(byte[] data) {
		// 检查是否已经连接
		if (mCardService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 这里还可以加入对数据的处理（加入信息类别等）
		byte[] send = data;
		mCardService.write(send);
		// Reset out string buffer to zero and clear the edit text field
		mOutStringBuffer.setLength(0);
		Log.v("BreakPoint", "蓝牙发送数据");
	}

	/*
	 * 若数据不正确，请求重发
	 */
	private void requestSend() {
		String tmp = "a";
		byte[] arq;
		arq = tmp.getBytes();
		sendByte(arq);
		Log.v("BreakPoint", "请求重发");
	}

	/*
	 * 处理收到的信息 温度数据预设为只是两位数以内
	 */

	public void setmOutStringBuffer(StringBuffer mOutStringBuffer) {
		this.mOutStringBuffer = mOutStringBuffer;
	}

	public StringBuffer getmOutStringBuffer() {
		return mOutStringBuffer;
	}

	// 测量明细
	class Bt1 implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent serverIntent = new Intent(MainActivity.this,
					Data_Recording.class);
			startActivity(serverIntent);
			MainActivity.this.onPause();
		}
	}

	// 显示波形
	class Bt4 implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent serverIntent = new Intent(MainActivity.this,
					Waveform_Display.class);
			startActivity(serverIntent);
			MainActivity.this.onPause();
		}
	}

}