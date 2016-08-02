package com.zen.plugin.rtl.handler;

import java.util.regex.Matcher;

/**
 * AttrHandler
 *
 * @author yangz
 * @version 2016/8/2
 */
public interface AttrHandler {

    String REGEX_FORMAT = "<[^>]*?([\\s]+)(android:%s[\\s]*=[\\s]*\"([^\"]+)\")[^>]*>";
    String FIXED_FORMATTER = "android:%s=\"%s\"\n";

    String getRegex();

    String modifyAttr(String text, Matcher matcher);

}
