/*
 * Copyright (C) 2013 YIXIA.COM
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

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.os.StrictMode;
import com.tos.interfaces.PlayerDevice;

import io.vov.vitamio.Vitamio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List
 */
public class VitamioListActivity extends ListActivity implements PlayerDevice {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Vitamio.isInitialized(getApplicationContext());

		setListAdapter(new SimpleAdapter(this, getData(), android.R.layout.simple_list_item_1, new String[] { "title" }, new int[] { android.R.id.text1 }));
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
		addItem(myData, "注册视频流设备", new Intent(this, MediaPlayerDemo.class));
//		addItem(myData, "VideoView", new Intent(this, VideoViewDemo.class));
//		addItem(myData, "MediaMetadata", new Intent(this, MediaMetadataRetrieverDemo.class));
//		addItem(myData, "VideoSubtitle", new Intent(this, VideoSubtitleList.class));
		addItem(myData, "注册播放设备", new Intent(this, VideoViewBuffer.class));
		return myData;
	}

	protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		data.add(temp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
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
		System.out.println("wowo"+msg);
	}

	@Override
	public long getHeartbeatInterval() {
		return 0;
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
