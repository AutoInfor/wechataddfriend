package com.example.administrator.autojxwechat;

import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

//TODO
public class AutoJxWechatrecord {
    static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mUiDevice;

    static String TAG = "AutoJxweichat";

    static String listViewid = "com.tencent.mm:id/c9b";
    static String listView1id = "com.tencent.mm:id/as4";
    static String yanzhenid = "com.tencent.mm:id/kh";

    static String mingziid = "com.tencent.mm:id/as6";
    static String contentid = "com.tencent.mm:id/as8";
    static String backid = "com.tencent.mm:id/hl";
    static String neirong = "我是佳缘与子偕臧";


    @Before
    public void startMMApp() throws IOException {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void weichat() throws UiObjectNotFoundException, InterruptedException {

        int width = mUiDevice.getDisplayWidth();
        int height = mUiDevice.getDisplayHeight();
        UiScrollable  listView= new UiScrollable(new UiSelector().scrollable(true).className("android.widget.ListView").resourceId(listViewid));
        int nIndex = 0;
        sleep(1000);
        UiSelector listView1 = new UiSelector().resourceId(listView1id);
        while (true) {


            for (nIndex = 0; nIndex < 7; nIndex++) {
                Log.d(TAG, "new===============");
                mUiDevice.pressHome();
                UiObject wx=mUiDevice.findObject(new UiSelector().textContains("微信"));
                wx.clickAndWaitForNewWindow(2000);
                sleep(1000);
                UiObject contact = listView.getChildByInstance(listView1, nIndex);
                contact.waitForExists(5000);
                UiObject mingzi=contact.getChild(new UiSelector().resourceId(mingziid));
                mingzi.waitForExists(3000);
                if(!mingzi.exists()){
                    continue;
                }
                String mz = mingzi.getText();
                Log.d(TAG, mz);
                UiObject content = contact.getChild(new UiSelector().resourceId(contentid));
                if(!content.exists()){
                    continue;
                }
                String ct = content.getText();
                Log.d(TAG, ct);
                if (mz.contains("专") || mz.contains("民")  || mz.contains("中")|| mz.contains("离")  || mz.contains("8") || mz.contains("15")|| !ct.contains("开启")) {
                    continue;
                }
                if (mz.contains("鹊")) {
                    neirong="中兴GG";
                }

                contact.clickAndWaitForNewWindow(5000);
                sleep(1000);
                UiObject yanzhen = mUiDevice.findObject(new UiSelector().resourceId(yanzhenid).textContains("开启了朋友"));
                Rect viewRect = yanzhen.getBounds();//获取Rect对象，Rect里面就有我们需要的坐标
                int x = viewRect.right;    //这是的X坐标
                int y = viewRect.bottom;    //这是的Y坐标
                UiObject shenqing = mUiDevice.findObject(new UiSelector().textContains("验证申请"));
                Log.d(TAG, Integer.toString(x));
                Log.d(TAG, Integer.toString(y));
                while (!shenqing.exists()) {
                    mUiDevice.click(x - 100, y - 50);   //稍微缩进点，点击坐标
                    x = x - 100;
                    sleep(1000);
                    if (x < 200) {
                        break;
                    }
                }
                sleep(1000);
                UiObject sendtext3 = mUiDevice.findObject(new UiSelector().className("android.widget.EditText"));
                UiObject queding = mUiDevice.findObject(new UiSelector().text("确定"));
                if (queding.exists()) {
                    sendtext3.setText(neirong);
                    queding.click();
                    sleep(5000);
                }

                sleep(2000);

                mUiDevice.pressBack();

            }
            sleep(1000);
            mUiDevice.swipe(width / 2, height / 8 * 7, width / 2, height / 8 * 1, 100);

        }
    }
}
