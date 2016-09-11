package com.danmuplayer.test;

import com.danmuplayer.constants.Constants;
import com.danmuplayer.util.HttpUtil;
import org.junit.Test;

/**
 * Created by clay on 2016/9/10.
 */
public class TestClawUtil {

    @Test
    public void testDownloadXML() {
        HttpUtil.writeRequestEntityIntoFile("http://comment.bilibili.tv/10064709.xml", "danmures/bilibili/test.xml");
    }
}
