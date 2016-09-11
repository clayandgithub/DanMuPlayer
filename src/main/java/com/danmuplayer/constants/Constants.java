package com.danmuplayer.constants;

import java.awt.*;

public class Constants {

    public static final String DANMU_RESOURCE_DIR = "danmures";
    
    public static final String BILIBILI_DANMU_RESOURCE_DIR = "danmures/bilibili";

    public static final String BILIBILI_DANMU_FILE_PATH_PATTERN = BILIBILI_DANMU_RESOURCE_DIR + "/VIDEO_NAME.xml";
	
	public static final String BILIBILI_DANMU_URL_PATH_PATTERN = "http://comment.bilibili.tv/CID.xml";
	
	public static final int MSPF = 25;

	public static final int DANMU_BASE_SPEED = 3;
	
	public static final int WIDTH = 512;
	
	public static final int HEIGHT = 128;

	public static final long FINAL_DELAY_TIME = 5000;

	public static final Font DEFAULT_FONT = new Font("微软雅黑", Font.PLAIN, 14);

	public static final String BILIBILI_URL_SAMPLE = "例：http://www.bilibili.com/video/av2203684/";

	public static final int MAX_LINE_COUNT = 20;

	public static final String[] DANMU_RESOURCE_OPTIONS = {"本地文件", "bilibili"};
	public static final int LOCAL_FILE_OPTION_INDEX = 0;
	public static final int BILIBILI_OPTION_INDEX = 1;

}
