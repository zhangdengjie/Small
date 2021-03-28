/*
 * Copyright 2015-present wequick.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package net.wequick.gradle

import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * 插件基类
 */
abstract class BasePlugin implements Plugin<Project> {

    public static final String SMALL_AAR_PREFIX = "net.wequick.small:small:"
    public static final String SMALL_BINDING_AAR_PREFIX = "small.support:databinding:"
    public static final String SMALL_LIBS = 'smallLibs'

    protected boolean isBuildingBundle
    protected boolean isBuildingLib

    protected Project project

    void apply(Project project) {
        this.project = project

        def sp = project.gradle.startParameter
        def p = sp.projectDir
        def t = sp.taskNames[0]
        if (p == null || p == project.rootProject.projectDir) {
            // gradlew buildLib | buildBundle
            if (t == 'buildLib') isBuildingLib = true
            else if (t == 'buildBundle') isBuildingBundle = true
        } else if (t == 'assembleRelease' || t == 'aR') {
            // gradlew -p [project.name] assembleRelease
            if (pluginType == PluginType.Library) isBuildingLib = true
            else isBuildingBundle = true
        }

        createExtension()

        configureProject()

        createTask()
    }

    protected void createExtension() {
        // Add the 'small' extension object
        project.extensions.create('small', getExtensionClass(), project)
        small.type = getPluginType()
    }

    protected void configureProject() {
        // Tidy up while gradle build finished
        project.gradle.buildFinished { BuildResult result ->
            if (result.failure == null) return
            tidyUp()
        }
    }

    protected void createTask() {}

    protected <T extends BaseExtension> T getSmall() {
        return (T) project.small
    }

    protected PluginType getPluginType() { return PluginType.Unknown }

    /** Restore state for DEBUG mode */
    protected void tidyUp() { }

    protected abstract Class<? extends BaseExtension> getExtensionClass()
}
