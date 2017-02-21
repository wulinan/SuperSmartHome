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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.tos.interfaces.PlayerDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.Broadcast;
import com.tos.utils.BroadcastListener;

import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewBuffer extends Activity implements OnInfoListener, OnBufferingUpdateListener,PlayerDevice {

  /**
   * TODO: Set the path variable to a streaming video URL or a local media file
   * path.
   */
  private String path = "http://192.168.43.121:9999/test1.mp4";
  private Uri uri;
  private VideoView mVideoView;
  private ProgressBar pb;
  private TextView downloadRateView, loadRateView;

  private Broadcast broadcast;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
		Vitamio.isInitialized(getApplicationContext());

    setContentView(R.layout.videobuffer);
    mVideoView = (VideoView) findViewById(R.id.buffer);
    pb = (ProgressBar) findViewById(R.id.probar);
//    broadcast = new Broadcast(2018,2017);
//    broadcast.registerListener(new BroadcastListener() {
//      @Override
//      public void messageArrived(String msg) {
//        System.out.println("wowo "+msg+" dd");
//        String[] msgs= msg.split(":");
//        String ip = msgs[0];
//        int port = Integer.parseInt(msgs[1]);
//        System.out.println("wowo "+port+" dd");
//      }
//    });
//
////    broadcast.startReceive();
//    broadcast.sendData("query_ip_port");
    TosServiceManager.getInstance().setWorkDir(getBaseContext().getFilesDir().getPath().toString());
    System.out.println("wowo"+getBaseContext().getFilesDir().getPath().toString());
    TosServiceManager.getInstance().registerDevice(DeviceType.Player,this,0);
    downloadRateView = (TextView) findViewById(R.id.download_rate);
    loadRateView = (TextView) findViewById(R.id.load_rate);
    if (path == "") {
      // Tell the user to provide a media file URL/path.
      Toast.makeText(
          VideoViewBuffer.this,
          "Please edit VideoBuffer Activity, and set path"
              + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
      return;
    } else {
      /*
       * Alternatively,for streaming media you can use
       * mVideoView.setVideoURI(Uri.parse(URLstring));
       */
      uri = Uri.parse(path);
      mVideoView.setVideoURI(uri);
      mVideoView.setMediaController(new MediaController(this));
      mVideoView.requestFocus();
      mVideoView.setOnInfoListener(this);
      mVideoView.setOnBufferingUpdateListener(this);
      mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
          // optional need Vitamio 4.0
          mediaPlayer.setPlaybackSpeed(1.0f);
        }
      });
    }

  }

  @Override
  public boolean onInfo(MediaPlayer mp, int what, int extra) {
    switch (what) {
    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
      if (mVideoView.isPlaying()) {
        mVideoView.pause();
        pb.setVisibility(View.VISIBLE);
        downloadRateView.setText("");
        loadRateView.setText("");
        downloadRateView.setVisibility(View.VISIBLE);
        loadRateView.setVisibility(View.VISIBLE);

      }
      break;
    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
      mVideoView.start();
      pb.setVisibility(View.GONE);
      downloadRateView.setVisibility(View.GONE);
      loadRateView.setVisibility(View.GONE);
      break;
    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
      downloadRateView.setText("" + extra + "kb/s" + "  ");
      break;
    }
    return true;
  }

  @Override
  public void onBufferingUpdate(MediaPlayer mp, int percent) {
    loadRateView.setText(percent + "%");
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
    System.out.println("wowo registered"+msg);
  }

  @Override
  public long getHeartbeatInterval() {
    return 10;
  }

  @Override
  public String playRemote(String remoteFileUrl) {
    return null;
  }

  @Override
  public String playLocal(String local) {
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
