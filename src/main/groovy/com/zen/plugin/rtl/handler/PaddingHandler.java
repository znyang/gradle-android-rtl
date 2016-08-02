package com.zen.plugin.rtl.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PaddingHandler
 *
 * @author yangz
 * @version 2016/8/2
 */
public class PaddingHandler implements AttrHandler {

    private static final String PADDING_REGEX = "<[^>]*?([\\s^\\n]+)(android:(paddingLeft|paddingRight)[\\s]*=[\\s]*\"([^\"]+)\")[^>]*>";

    private static final String PADDING_LEFT = "paddingLeft";
    private static final String PADDING_RIGHT = "paddingRight";
    private static final String PADDING_START = "paddingStart";
    private static final String PADDING_END = "paddingEnd";

    private static final String[] PADDING_RTL_ATTRS = {
            PADDING_LEFT, PADDING_START,
            PADDING_RIGHT, PADDING_END,
    };

    private static final List<String> PADDING_ATTRS_REGEX = new ArrayList<>();

    static {
        for (String attr : PADDING_RTL_ATTRS) {
            PADDING_ATTRS_REGEX.add(String.format(REGEX_FORMAT, attr));
        }
    }

    private static class PaddingMatcherResult {
        private String space;
        private String property;
        private String value;

        PaddingMatcherResult(Matcher matcher) {
            space = matcher.group(1);
            property = matcher.group(2);
            value = matcher.group(3);
            space = space.replace("\r\n", "").replace("\n", "");
        }
    }

    @Override
    public String getRegex() {
        return PADDING_REGEX;
    }

    @Override
    public String modifyAttr(String text, Matcher matcher) {
        String content = matcher.group(0);

        Matcher left = Pattern.compile(PADDING_ATTRS_REGEX.get(0)).matcher(content);
        Matcher right = Pattern.compile(PADDING_ATTRS_REGEX.get(2)).matcher(content);
        Matcher start = Pattern.compile(PADDING_ATTRS_REGEX.get(1)).matcher(content);
        Matcher end = Pattern.compile(PADDING_ATTRS_REGEX.get(3)).matcher(content);

        String saveContent = content;
        if (left.find()) {
            PaddingMatcherResult leftResult = new PaddingMatcherResult(left);
            String rightValue = "0dp";
            if (!right.find()) {
                content = fixedAttrs(PADDING_RIGHT, content, leftResult.space, leftResult.property, rightValue);
            } else {
                rightValue = right.group(3); // get value
            }
            if (!end.find()) {
                content = fixedAttrs(PADDING_END, content, leftResult.space, leftResult.property, rightValue);
            }
            if (!start.find()) {
                content = fixedAttrs(PADDING_START, content, leftResult.space, leftResult.property, leftResult.value);
            }
        } else if (right.find()) {
            PaddingMatcherResult rightResult = new PaddingMatcherResult(right);
            String leftValue = "0dp";

            content = fixedAttrs(PADDING_LEFT, content, rightResult.space, rightResult.property, leftValue);
            if (!start.find()) {
                content = fixedAttrs(PADDING_START, content, rightResult.space, rightResult.property, leftValue);
            }
            if (!end.find()) {
                content = fixedAttrs(PADDING_END, content, rightResult.space, rightResult.property, rightResult.value);
            }
        }
        if (!content.equals(saveContent)) {
            return text.replace(saveContent, content);
        }
        return null;
    }

    private static String fixedAttrs(String fixedAttr, String content,
                                     String space, String property, String value) {
        int index = content.indexOf(property);
        String fixed = String.format(FIXED_FORMATTER, fixedAttr, value);
        return content.substring(0, index) + fixed + space + content.substring(index);
    }
}
