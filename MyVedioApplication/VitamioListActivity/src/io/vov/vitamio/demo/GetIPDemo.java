package io.vov.vitamio.demo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.tos.interfaces.StreamMediaDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.Command;
import com.tos.utils.Message;

import java.util.List;

public class GetIPDemo extends Activity  implements StreamMediaDevice{
	String ipname = null;
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




      	final Builder builder = new Builder(this);   //定义一个AlertDialog.Builder对象
		builder.setTitle("登录服务器对话框");                          // 设置对话框的标题
		

		// 为对话框设置一个“登录”按钮
		builder.setPositiveButton("获取地址"
			// 为按钮设置监听器
			, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//此处可执行登录处理
					Message msg = new Message("0", Command.Query.toCmd(), Command.GetPlayerIp.toCmd(),"");
					TosServiceManager.getInstance().sendMessage(msg.toJson());
					Toast.makeText(
							GetIPDemo.this,
							"等待服务器广播ip", Toast.LENGTH_LONG).show();

//					Bundle data = new Bundle();
//					data.putString("ipname",ipname);
//					Intent intent = new Intent(GetIPDemo.this,CameraDemo.class);
//					intent.putExtras(data);
//					startActivity(intent);
				}
			});
		// 为对话框设置一个“取消”按钮
		builder.setNegativeButton("取消"
			,  new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//取消登录，不做任何事情。
					System.exit(1);
				}
			});
		//创建、并显示对话框
		builder.create().show();
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
		return 0;
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
            data.putString("ipname",ipname);
            Intent intent = new Intent(GetIPDemo.this,CameraDemo.class);
            intent.putExtras(data);
            startActivity(intent);
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