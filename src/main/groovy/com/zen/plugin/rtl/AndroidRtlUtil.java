package com.zen.plugin.rtl;

import com.zen.plugin.rtl.handler.AttrHandler;
import com.zen.plugin.rtl.handler.DefaultAttrHandler;
import com.zen.plugin.rtl.handler.PaddingHandler;
import com.zen.plugin.rtl.handler.ValueHandler;

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

    private static final String[] RTL_ATTRS = {
            "layout_toLeftOf", "layout_toStartOf",
            "layout_toRightOf", "layout_toEndOf",

            "layout_marginLeft", "layout_marginStart",
            "layout_marginRight", "layout_marginEnd",

            "layout_alignLeft", "layout_alignStart",
            "layout_alignRight", "layout_alignEnd",

            "layout_alignParentLeft", "layout_alignParentStart",
            "layout_alignParentRight", "layout_alignParentEnd",

            "drawableLeft", "drawableStart",
            "drawableRight", "drawableEnd",
    };

    // 将这些标签中value的left->start, right->end
    private static final String[] RTL_VALUE_ATTRS = {
            "layout_gravity",
            "gravity"
    };

    private static List<AttrHandler> ATTR_HANDLERS = new ArrayList<>();

    static {
        for (int i = 0, size = RTL_ATTRS.length; i < size; i += 2) {
            ATTR_HANDLERS.add(new DefaultAttrHandler(RTL_ATTRS[i], RTL_ATTRS[i + 1]));
        }
        ATTR_HANDLERS.add(new PaddingHandler());
        for (String value : RTL_VALUE_ATTRS) {
            ATTR_HANDLERS.add(new ValueHandler(value));
        }
    }

    /**
     * 修改布局文本中的rtl问题
     *
     * @param text 布局文本
     * @return 非空表示进行修复后的布局文本，null表示无需修改
     */
    public static String modifyRtl(String text) {
        return modifyRtl(text, ATTR_HANDLERS);
    }

    private static String modifyRtl(String text, List<AttrHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return text;
        }
        boolean hasFixed = false;
        for (AttrHandler handler : handlers) {
            Matcher m = Pattern.compile(handler.getRegex()).matcher(text);
            while (m.find()) {
                String modify = handler.modifyAttr(text, m);
                if (modify != null) {
                    text = modify;
                    hasFixed = true;
                }
            }
        }
        return hasFixed ? text : null;
    }

}
