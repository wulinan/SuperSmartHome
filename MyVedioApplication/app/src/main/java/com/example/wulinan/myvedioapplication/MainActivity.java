

package com.example.wulinan.myvedioapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.VideoView;
//import android.media.MediaPlayer;
//import android.widget.MediaController;
import android.net.Uri;
//import android.os.Environment;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;


import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
//import io.vov.vitamio.LibsChecker;


//    VideoView videoView;
//    private MediaPlayer mediaPlayer;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        videoView = (VideoView) findViewById(R.id.video_view);
//        MediaController mediaController = new
//                MediaController(this);
//        videoView.setMediaController(mediaController);
//
////        videoView.setVideoURI(Uri.parse("http://www.w3school.com.cn/i/movie.mp4"));
//        videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath() + "/DCIM／VUE/11.mp4");
//        videoView.requestFocus();
//
//
//        videoView.start();
//    }


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private VideoView vitamio_videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //检测Vitamio是否解压解码包，一定要加
//        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
//            return;

        //初始化Vitamio包下的VideoView
        vitamio_videoview = (VideoView) findViewById(R.id.vitamio_videoview);

        //放入网址
        vitamio_videoview.setVideoPath("http://www.modrails.com/videos/passenger_nginx.mov");

        //设置控制栏
        vitamio_videoview.setMediaController(new MediaController(this));

        //获取焦点
        vitamio_videoview.requestFocus();
        //准备播放监听
        vitamio_videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);//设置播放速度
            }
        });

    }

}
/*
public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

			*/
/**
 * Called when the activity is first created.
 *//*

	MediaPlayer player;
	SurfaceView surface;
	SurfaceHolder surfaceHolder;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		surface = (SurfaceView) findViewById(R.id.surface);
		surfaceHolder = surface.getHolder(); // SurfaceHolder是SurfaceView的控制接口
		surfaceHolder.addCallback(this); // 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setDisplay(surfaceHolder);
		// 设置显示视频显示在SurfaceView上
		try {
			player.setDataSource("http://dlqncdn.miaopai.com/stream/3D~8BM-7CZqjZscVBEYr5g__.mp4?ssig=475cb4415cab5865f928a0d72fbee5cb&time_stamp=1486912769485");

					//"http://192.168.0.100:9999/1.mp4");
					//"http://www.w3school.com.cn/i/movie.mp4");
					//Environment.getExternalStorageDirectory().getPath() + "/DCIM／VUE/11.mp4");
			player.prepare();
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (player.isPlaying()) {
			player.stop();
		}
		player.release();
		// Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
	}
}*/
