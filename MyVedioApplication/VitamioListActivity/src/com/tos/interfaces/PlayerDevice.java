package com.tos.interfaces;

import java.util.List;

public interface PlayerDevice extends Device {
	
	/**
	 * 播放某一个文件
	 * @param remoteFileUrl
	 * @return 如果播放成功，返回一个id，否则返回null
	 */
	public String playRemote(String remoteFileUrl);
	
	/**
	 * 播放某一个文件
	 * @param local
	 * @return 如果播放成功，返回一个id，否则返回null
	 */
	public String playLocal(String local);
	
	/**
	 *列出当前本地的所有文件 
	 * @return
	 */
	public List<String> listLocalMedia();
	
	/**
	 * 暂停正在播放的一个文件
	 * @param playId 正在播放媒体的id
	 * @return
	 */
	public boolean pause(String playId);
	
	/**
	 * 恢复一个暂停的媒体
	 * @param playId
	 * @return
	 */
	public boolean resume(String playId);
	
	/**让一个媒体快进
	 * 
	 * @param playId
	 * @return
	 */
	public boolean fastFoward(String playId);
	
	/**
	 * 让一个媒体快退
	 * @param playId
	 * @return
	 */
	public boolean rewind(String playId);
	
	/**
	 * 调大音量
	 * @param playId
	 * @return
	 */
	public boolean volumeUp(String playId);
	
	/**
	 * 调小音量
	 * @param playId
	 * @return
	 */
	public boolean volumeDonw(String playId);
	
	/**
	 * 播放下一个媒体
	 * @return
	 */
	public boolean next();
	
	/**
	 * 设置循环模式
	 * @param pattern
	 * @return
	 */
	public boolean setCyclicalPattern(String pattern);
}
