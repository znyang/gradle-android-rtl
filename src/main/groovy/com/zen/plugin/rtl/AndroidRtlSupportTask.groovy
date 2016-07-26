package com.zen.plugin.rtl

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * AndroidRtlSupportTask
 *
 * @author yangz
 * @version 2016/7/25
 */
class AndroidRtlSupportTask extends DefaultTask {

    FileTree mFileTree;
    String mIntoPath;

    @TaskAction
    protected void generate() {
        if (mFileTree == null) {
            System.err.println("rtl.from has not found!! ")
            return;
        }
        if (mIntoPath == null || mIntoPath.empty) {
            System.err.println("rtl.into has not found!! ")
            return
        }

        removeDir()
        createRtlLayout()
    }

    private void removeDir() {
        File target = new File(mIntoPath)
        if (target.exists()) {
            boolean success = target.delete()
            System.out.println("delete " + target + " " + (success ? "success" : "fail!"))
        }
        target.mkdirs()
    }

    private createRtlLayout() {
        mFileTree.forEach({
            file ->
                String content = file.text
                String fixed = AndroidRtlUtil.modifyRtl(content)
                if (fixed != null) {
                    File output = new File(mIntoPath, file.getName())
                    output.text = fixed
                    System.out.println("create " + output.path)
                }
        })
    }

    public void from(FileTree path) {
        mFileTree = path
    }

    public void into(String path) {
        mIntoPath = path
    }

}
