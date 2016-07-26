package com.zen.plugin.rtl

import org.gradle.api.file.FileTree

/**
 * AndroidRtlExtension
 *
 * @author yangz
 * @version 2016/7/25
 */
class AndroidRtlExtension {

    private FileTree from
    private String into

    FileTree getFrom() {
        return from
    }

    void setFrom(FileTree from) {
        this.from = from
    }

    String getInto() {
        return into
    }

    void setInto(String into) {
        this.into = into
    }
}
