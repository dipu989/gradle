/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.plugins.cpp

import org.gradle.api.file.FileCollection

import org.gradle.process.internal.DefaultExecAction
import org.gradle.api.internal.tasks.compile.Compiler
import org.gradle.api.tasks.WorkResult

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GppCppCompiler implements CppCompiler {

    private static Logger logger = LoggerFactory.getLogger(GppCppCompiler)

    FileCollection source
    File destinationDir

    // doesn't make sense here, but is part of Compiler
    Iterable<File> classpath

    File output

    String gpp = "g++"

    WorkResult execute() {
        def compilerInvocation = new DefaultExecAction().with {
            workingDir destinationDir
            executable gpp
            args source.files*.absolutePath
            args "-o", output
        }

        def result = compilerInvocation.execute()
        result.assertNormalExitValue()
        return { true } as WorkResult
    }

}