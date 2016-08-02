package com.zen.plugin.rtl.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DefaultAttrHandler
 *
 * @author yangz
 * @version 2016/8/2
 */
public class DefaultAttrHandler implements AttrHandler {

    private final String fixedAttr;
    private final String baseAttrRegex;
    private final String fixedAttrRegex;

    public DefaultAttrHandler(String baseAttr, String fixedAttr) {
        this.fixedAttr = fixedAttr;
        baseAttrRegex = String.format(REGEX_FORMAT, baseAttr);
        fixedAttrRegex = String.format(REGEX_FORMAT, fixedAttr);
    }

    @Override
    public String getRegex() {
        return baseAttrRegex;
    }

    @Override
    public String modifyAttr(String text, Matcher m) {
        String content = m.group(0);
        // 判断是否有做RTL支持
        if (Pattern.compile(fixedAttrRegex).matcher(content).find()) {
            return null;
        }
        String space = m.group(1);      // 空白处
        String property = m.group(2);   // 完整属性键值
        String value = m.group(3);      // 双引号中的值
        space = space.replace("\r\n", "").replace("\n", "");

        int index = content.indexOf(property);
        String f = String.format(FIXED_FORMATTER, fixedAttr, value);

        // 添加rtl支持的属性到控件中
        String result = content.substring(0, index) + f + space + content.substring(index);
        return text.replace(content, result);
    }
}
