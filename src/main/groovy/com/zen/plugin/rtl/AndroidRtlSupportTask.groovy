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

        autoSetInto()
        removeDir()
        createRtlLayout()
    }

    private void autoSetInto() {
        if (mIntoPath != null && !mIntoPath.isEmpty()) {
            return;
        }
        mIntoPath = null;
    }

    private void removeDir() {
        if (mIntoPath == null) {
            return;
        }
        File target = new File(mIntoPath)
        if (target.exists()) {
            boolean success = target.delete()
            System.out.println("delete " + target + " " + (success ? "success" : "fail!"))
        }
        target.mkdirs()
    }

    private void createRtlLayout() {
        long start = System.currentTimeMillis()
        mFileTree.forEach({
            file ->
                String content = file.text
                String fixed = AndroidRtlUtil.modifyRtl(content)
                if (fixed != null) {
                    createModifyFile(file, fixed, mIntoPath)
                }
        })
        long cost = System.currentTimeMillis() - start
        System.out.println("cost time: " + cost + "ms")
    }

    static void createModifyFile(File file, String fixed, String into) {
        File output;
        if (into == null) {
            output = new File(file.getPath())
        } else {
            output = new File(into, file.getName())
        }
        output.text = fixed
        System.out.println("create " + output.path)
    }

    public void from(FileTree path) {
        mFileTree = path
    }

    public void into(String path) {
        mIntoPath = path
    }

}
