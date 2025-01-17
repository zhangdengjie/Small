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

import com.android.build.gradle.tasks.ProcessAndroidResources
import org.gradle.api.Project
import org.gradle.api.Task

class AndroidExtension extends BaseExtension {

    /** File of release variant output */
    protected File outputFile

    /** Task of android packager */
    ProcessAndroidResources aapt

    /** Task of R.class jar */
    Task jar

    /** Map of build-cache file */
    Map buildCaches

    AndroidExtension(Project project) {
        super(project)
    }
}
