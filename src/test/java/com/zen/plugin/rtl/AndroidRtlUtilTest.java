package com.zen.plugin.rtl;

import org.junit.Test;
import org.testng.Assert;

/**
 * com.zen.plugin.rtl.AndroidRtlUtilTest
 *
 * @author yangz
 * @version 2016/8/1
 */
public class AndroidRtlUtilTest {

    String testLayout = "" +
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "                android:layout_width=\"match_parent\"\n" +
            "                android:layout_height=\"match_parent\">\n" +
            "\n" +
            "    <TextView\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:layout_toRightOf = \"@+id/iv_rtl\"\n" +
            "        android:paddingRight = \"10dp\"/>\n" +
            "\n" +
            "    <TextView\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:layout_toRightOf =\"@+id/iv_rtl\"\n" +
            "        android:paddingLeft= \"10dp\"/>\n" +
            "\n" +
            "    <TextView\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:layout_toRightOf=\"@+id/iv_rtl\"\n" +
            "        android:paddingLeft=\"16dp\"\n" +
            "        android:paddingRight=\"10dp\"/>\n" +
            "\n" +
            "    <TextView\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:layout_toRightOf=\"@+id/iv_rtl\"/>\n" +
            "\n" +
            "    <LinearLayout\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:gravity = \"left|center_vertical\"\n" +
            "        android:orientation=\"horizontal\">\n" +
            "\n" +
            "        <TextView\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:layout_gravity = \"fill_vertical|left|center_horizontal\"\n" +
            "            android:text=\"test1\"/>\n" +
            "\n" +
            "        <TextView\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:layout_gravity = \"left|center_horizontal\"\n" +
            "            android:text=\"test2\"/>\n" +
            "        <TextView\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:layout_gravity = \"center_horizontal|left\"\n" +
            "            android:text=\"test2\"/>\n" +
            "    </LinearLayout>\n" +
            "</RelativeLayout>";

    @Test
    public void testModifyRtl() throws Exception {
        String modify = AndroidRtlUtil.modifyRtl(testLayout);
        System.out.println(modify);
        Assert.assertNull(AndroidRtlUtil.modifyRtl(modify));
    }
}
