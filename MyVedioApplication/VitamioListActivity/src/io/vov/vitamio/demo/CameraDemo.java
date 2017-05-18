package io.vov.vitamio.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tos.interfaces.StreamMediaDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.Command;
import com.tos.utils.Message;

public class CameraDemo extends Activity implements StreamMediaDevice{
	SurfaceView sView;
	SurfaceHolder surfaceHolder;
	int screenWidth, screenHeight;	
	Camera camera;                    // 定义系统所用的照相机	
	boolean isPreview = false;        //是否在浏览中
	private String ipname = null;

	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
     	requestWindowFeature(Window.FEATURE_NO_TITLE);
     	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
		TosServiceManager.getInstance().setWorkDir(getBaseContext().getFilesDir().getPath().toString());
		System.out.println("[debug debug]"+getBaseContext().getFilesDir().getPath().toString());
		TosServiceManager.getInstance().registerDevice(DeviceType.StreamMedia,this,0);


		//此处可执行登录处理
		Message msg = new Message("0", Command.Query.toCmd(), Command.GetPlayerIp.toCmd(),"");
		TosServiceManager.getInstance().sendMessage(msg.toJson());
		Toast.makeText(
				CameraDemo.this,
				"等待服务器广播ip", Toast.LENGTH_LONG).show();

//		 获取IP地址
//        Intent intent = getIntent();
//        Bundle data = intent.getExtras();
//		if (data!=null)
//        	ipname = data.getString("ipname");
		System.out.println(ipname+"----0000000828272728219191");
        		
		screenWidth = 640;
		screenHeight = 480;		
		sView = (SurfaceView) findViewById(R.id.sView);                  // 获取界面中SurfaceView组件		
		surfaceHolder = sView.getHolder();                               // 获得SurfaceView的SurfaceHolder
		
		// 为surfaceHolder添加一个回调监听器
		surfaceHolder.addCallback(new Callback() {
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {				
			}
			@Override
			public void surfaceCreated(SurfaceHolder holder) {							
				initCamera();                                            // 打开摄像头
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 如果camera不为null ,释放摄像头
				if (camera != null) {
					if (isPreview)
						camera.stopPreview();
					camera.release();
					camera = null;
				}
			    System.exit(0);
			}		
		});
		// 设置该SurfaceView自己不维护缓冲    
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
    }
    
    private void initCamera() {
		if(ipname ==null){
			camera = Camera.open();
			Camera.Parameters parameters = camera.getParameters();
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
//    	if (!isPreview) {
//			camera = Camera.open();
//		}
		if (camera != null && !isPreview) {
			try{
				Camera.Parameters parameters = camera.getParameters();				
				parameters.setPreviewSize(screenWidth, screenHeight);    // 设置预览照片的大小				
				parameters.setPreviewFpsRange(15,20);                    // 每秒显示20~30帧
				parameters.setPictureFormat(ImageFormat.NV21);           // 设置图片格式
				parameters.setPictureSize(screenWidth, screenHeight);    // 设置照片的大小
				//camera.setParameters(parameters);                      // android2.3.3以后不需要此行代码
				camera.setPreviewDisplay(surfaceHolder);                 // 通过SurfaceView显示取景画面				
		        camera.setPreviewCallback(new StreamIt(ipname));         // 设置回调的类
				camera.startPreview();                                   // 开始预览				
				camera.autoFocus(null);                                  // 自动对焦
			} catch (Exception e) {
				e.printStackTrace();
			}
			isPreview = true;
		}
    }

	@Override
	public String getrRegesterUuid() {
		return null;
	}

	@Override
	public String heartBeat() {
		return null;
	}

	@Override
	public boolean turnOn() {
		return false;
	}

	@Override
	public boolean turnOff() {
		return false;
	}

	@Override
	public boolean restart() {
		return false;
	}

	@Override
	public boolean reset() {
		return false;
	}

	@Override
	public float syncTime(float timestamp) {
		return 1000;
	}

	@Override
	public String queryInfo(String code) {
		return null;
	}

	@Override
	public void registered(String msg) {

	}

	@Override
	public long getHeartbeatInterval() {
		return 100000;
	}
	@Override
	public String queryArrive(String code,Message msg) {
		if (Command.GetPlayerIp.toCmd().equals(code)){
			Bundle data = new Bundle();
			ipname = msg.getOperate_data();
			System.out.println(ipname+"----------------------");
		this.initCamera();


		}
		return null;
	}

	@Override
	public String getStreamUrl(String mediaName) {
		return null;
	}

	@Override
	public List<String> getAllMediaNames() {
		return null;
	}
}

class StreamIt implements Camera.PreviewCallback {
	private String ipname;
	public StreamIt(String ipname){
		this.ipname = ipname;
	}
	
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Size size = camera.getParameters().getPreviewSize();          
        try{
			if (ipname == null)
				return;
        	//调用image.compressToJpeg（）将YUV格式图像数据data转为jpg格式
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
            if(image!=null){
            	ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                image.compressToJpeg(new Rect(0, 0, 640, 560), 20, outstream);
                outstream.flush();
                //启用线程将图像数据发送出去
                Thread th = new MyThread(outstream,ipname);
                th.start();               
            }  
        }catch(Exception ex){  
            Log.e("Sys","Error:"+ex.getMessage());  
        }        
    }
}
    
class MyThread extends Thread{	
	private byte byteBuffer[] = new byte[1024];
	private OutputStream outsocket;	
	private ByteArrayOutputStream myoutputstream;
	private String ipname;
	
	public MyThread(ByteArrayOutputStream myoutputstream,String ipname){
		this.myoutputstream = myoutputstream;
		this.ipname = ipname;
        try {
			myoutputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public void run() {
        try{
        	//将图像数据通过Socket发送出去
            Socket tempSocket = new Socket(ipname, 6000);
            outsocket = tempSocket.getOutputStream();
            ByteArrayInputStream inputstream = new ByteArrayInputStream(myoutputstream.toByteArray());
            int amount;
            while ((amount = inputstream.read(byteBuffer)) != -1) {
                outsocket.write(byteBuffer, 0, amount);
            }
            myoutputstream.flush();
            myoutputstream.close();
            tempSocket.close();                   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}