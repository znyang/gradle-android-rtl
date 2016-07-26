package com.zen.plugin.rtl

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidRtlPlugin implements Plugin<Project> {
    private static final String EXTENSION_NAME = 'rtl';

    @Override
    void apply(Project project) {
        project.extensions.create(EXTENSION_NAME, AndroidRtlExtension)

        project.afterEvaluate {
            applyAndroid(project)
        }
    }

    private static void applyAndroid(Project project) {
        def extension = project.extensions[EXTENSION_NAME] as AndroidRtlExtension
        def intoPath = extension.into ?: "${project.buildDir}/outputs/rtl/res/layout/"

        def task = project.tasks.create("androidRtl", AndroidRtlSupportTask)
        task.description = "Generate RTL Support Files"
        task.group = 'Android'
        task.from(extension.from)
        task.into(intoPath)
    }

}
