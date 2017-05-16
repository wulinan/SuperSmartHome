package io.vov.vitamio.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.tos.interfaces.PlayerDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.Command;
import com.tos.utils.Message;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by wulinan on 17/5/14.
 */

public class CameraShow extends Activity implements PlayerDevice {
    private ImageView view;
    ServerSocket ss;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.save);

        TosServiceManager.getInstance().setWorkDir(getBaseContext().getFilesDir().getPath().toString());
        System.out.println("[debug debug]"+getBaseContext().getFilesDir().getPath().toString());
        TosServiceManager.getInstance().registerDevice(DeviceType.Player,this,0);

        view = (ImageView)findViewById(R.id.show);

        try {
            ss = new ServerSocket(6000);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true){
                    Socket s = null;
                    try {
                        s = ss.accept();
                        s.setSoTimeout(1000000);
                        System.out.println("连接成功!");
                        InputStream in = s.getInputStream();
//                        BufferedInputStream ins = new BufferedInputStream(in);
                        Bitmap bt =  BitmapFactory.decodeStream(in);
                        view.setImageBitmap(bt);
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//        System.out.println("连接成功!");

                }
            }
        };
//        Thread t = new Thread(r);
//        t.start();
    runOnUiThread(r);
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
        return 1000000;
    }

    @Override
    public String queryArrive(String code, Message msg) {
        return null;
    }

    @Override
    public String playRemote(String remoteFileUrl, float time) {
        return null;
    }

    @Override
    public String playLocal(String local, float t) {
        return null;
    }

    @Override
    public List<String> listLocalMedia() {
        return null;
    }

    @Override
    public boolean pause(String playId) {
        return false;
    }

    @Override
    public boolean resume(String playId) {
        return false;
    }

    @Override
    public boolean fastFoward(String playId) {
        return false;
    }

    @Override
    public boolean rewind(String playId) {
        return false;
    }

    @Override
    public boolean volumeUp(String playId) {
        return false;
    }

    @Override
    public boolean volumeDonw(String playId) {
        return false;
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public boolean setCyclicalPattern(String pattern) {
        return false;
    }
}


