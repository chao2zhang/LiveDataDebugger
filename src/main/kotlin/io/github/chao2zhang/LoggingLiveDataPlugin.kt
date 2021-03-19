package io.github.chao2zhang

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class LoggingLiveDataPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withType(AppPlugin::class.java) { plugin ->
            project.extensions.configure(AppExtension::class.java) { appExtension ->
                appExtension.registerTransform(LoggingLiveDataTransform(project.logger))
            }
        }
    }
}