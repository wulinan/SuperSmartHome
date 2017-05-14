/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.tos.interfaces.StreamMediaDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.Message;
import com.tos.utils.VideoStreamServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;


public class MediaPlayerDemo extends Activity implements StreamMediaDevice {
    private static final String TAG = "[my debug]";
    private Button mlocalvideo;
	private Button mlocalvideoSurface;
	private Button mstreamvideo;
	private Button mlocalaudio;
    private  final  int FILE_SELECT_CODE = 0;

    public static int ClientPort = 9999;
    private static String filePath = "/Users/wulinan/";
    private  String mediaName = "";
    public VideoStreamServer server;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.mediaplayer_1);
		mlocalaudio = (Button) findViewById(R.id.localaudio);
		mlocalaudio.setOnClickListener(mLocalAudioListener);

	}

	private OnClickListener mLocalAudioListener = new OnClickListener() {
		public void onClick(View v) {
//			Intent intent = new Intent(MediaPlayerDemo.this.getApplication(), MediaPlayerDemo_Audio.class);
//			intent.putExtra(MEDIA, LOCAL_AUDIO);
//			startActivity(intent);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(
                        Intent.createChooser(intent, "Select a File to play"),
                        FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(MediaPlayerDemo.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
		}
	};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file

                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
					Toast.makeText(MediaPlayerDemo.this, path, Toast.LENGTH_SHORT).show();
                    File file = new File(path);
                    if(file.isDirectory()){
                    }else{

                        path = file.getParentFile().getAbsolutePath();
                        try {
                            String hostIp = InetAddress.getLocalHost().getHostAddress();
//                            System.out.println(hostIp);
						System.out.println("send data...."+ hostIp);
                        } catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        mediaName = file.getName();
                        Toast.makeText(MediaPlayerDemo.this, file.getParentFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }
                    startServer(path);
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public  void  startServer(String path){

        VideoStreamServer.main(ClientPort, path);
        TosServiceManager.getInstance().setWorkDir(getBaseContext().getFilesDir().getPath().toString());


        TosServiceManager.getInstance().registerDevice(DeviceType.StreamMedia,this,0);
        Toast.makeText(
                MediaPlayerDemo.this,
                "等待服务器广播ip", Toast.LENGTH_LONG).show();

    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @Override
    public String getrRegesterUuid() {
        return null;
    }

    @Override
    public String heartBeat() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean turnOn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean turnOff() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean restart() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean reset() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public float syncTime(float timestamp) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String queryInfo(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStreamUrl(String mediaName) {
        if(mediaName == null){
            try {
                String hostIp = getIPAddress(true);
                InetAddress.getLocalHost().getHostAddress();
//                InetAddress.getLocalHost().get
                String url = String.format("http://%s:9999/%s",hostIp,this.mediaName);
                System.out.println(url);
                return url;
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    @Override
    public List<String> getAllMediaNames() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void registered(String msg) {
        // TODO Auto-generated method stub

    }



    @Override
    public long getHeartbeatInterval() {
        // TODO Auto-generated method stub
        return 10000;
    }

    @Override
    public String queryArrive(String code, Message msg) {
        return null;
    }


}
