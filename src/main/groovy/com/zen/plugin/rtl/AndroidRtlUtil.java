package com.zen.plugin.rtl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AndroidRtlUtil
 *
 * @author yangz
 * @version 2016/7/26
 */
public class AndroidRtlUtil {

    private static final String REGEX_FORMAT = "<[^>]*?([\\s]+)(android:%s=\"([^\"]+)\")[^>]+>";
    private static final String FIXED_FORMATTER = "android:%s=\"%s\"\n";

    private static final String[] RTL_ATTRS = {
            "layout_toLeftOf", "layout_toStartOf",
            "layout_toRightOf", "layout_toEndOf",

            "layout_marginLeft", "layout_marginStart",
            "layout_marginRight", "layout_marginEnd",

            "paddingLeft", "paddingStart",
            "paddingRight", "paddingEnd",

            "layout_alignParentLeft", "layout_alignParentStart",
            "layout_alignParentRight", "layout_alignParentEnd",

            "drawableLeft", "drawableStart",
            "drawableRight", "drawableEnd",
    };

    private static final List<String> BASE_ATTRS_REGEX = new ArrayList<>();
    private static final List<String> FIXED_ATTRS_REGEX = new ArrayList<>();
    private static final List<String> FIXED_ATTRS = new ArrayList<>();

    static {
        for (int i = 0, size = RTL_ATTRS.length; i < size; i++) {
            String regex = String.format(REGEX_FORMAT, RTL_ATTRS[i]);
            if ((i & 1) == 0) {
                BASE_ATTRS_REGEX.add(regex);
            } else {
                FIXED_ATTRS_REGEX.add(regex);
                FIXED_ATTRS.add(RTL_ATTRS[i]);
            }
        }
    }

    /**
     * 修改布局文本中的rtl问题
     *
     * @param text 布局文本
     * @return 非空表示进行修复后的布局文本，null表示无需修改
     */
    public static String modifyRtl(String text) {
        boolean fixed = false;
        for (int i = BASE_ATTRS_REGEX.size(); --i >= 0; ) {
            String attr = FIXED_ATTRS.get(i);

            Pattern p = Pattern.compile(BASE_ATTRS_REGEX.get(i));
            Matcher m = p.matcher(text);
            while (m.find()) {
                String content = m.group(0);

                // 判断是否有做RTL支持
                if (!Pattern.compile(FIXED_ATTRS_REGEX.get(i)).matcher(content).find()) {
                    String space = m.group(1);      // 空白处
                    String property = m.group(2);   // 完整属性键值
                    String value = m.group(3);      // 双引号中的值
                    space = space.replace("\r\n", "").replace("\n", "");

                    int index = content.indexOf(property);
                    String fixedAttr = String.format(FIXED_FORMATTER, attr, value);

                    // 添加rtl支持的属性到控件中
                    String result = content.substring(0, index) + fixedAttr + space + content.substring(index);
                    text = text.replace(content, result);

                    fixed = true;
                }
            }
        }
        return fixed ? text : null;
    }

}
