package com.zen.plugin.rtl;

import org.junit.Test;

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
            "</RelativeLayout>";

    @Test
    public void testModifyRtl() throws Exception {
        System.out.println(AndroidRtlUtil.modifyRtl(testLayout));
    }
}
