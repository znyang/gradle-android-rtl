package com.zen.plugin.rtl.handler;

import java.util.regex.Matcher;

/**
 * ValueHandler
 *
 * @author yangz
 * @version 2016/8/3
 */
public class ValueHandler implements AttrHandler {

    private static final String VALUE_REGEX_FORMATTER =
            "<[^>]*?([\\s]+)(android:%s[\\s]*=[\\s]*\"([^\"]*(left|right)[^\"]*)\")[^>]*>";

    private final String baseAttrRegex;

    public ValueHandler(String baseAttr) {
        baseAttrRegex = String.format(VALUE_REGEX_FORMATTER, baseAttr);
    }

    @Override
    public String getRegex() {
        return baseAttrRegex;
    }

    @Override
    public String modifyAttr(String text, Matcher matcher) {
        String content = matcher.group(0);
        String value = matcher.group(3);

        String[] vs = value.split("\\|");
        StringBuilder builder = new StringBuilder();
        for (String v : vs) {
            builder.append(convertValue(v)).append('|');
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);

            String modifyContent = content.replace(value, builder.toString());
            return text.replace(content, modifyContent);
        }
        return null;
    }

    private static String convertValue(String value) {
        if ("left".equals(value)) {
            return "start";
        } else if ("right".equals(value)) {
            return "end";
        }
        return value;
    }
}
