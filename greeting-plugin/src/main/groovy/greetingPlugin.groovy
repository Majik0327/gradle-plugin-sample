package com.majik

import org.gradle.api.Plugin
import org.gradle.api.Project

plugins {
    id 'java-gradle-plugin'
    id 'maven-publish'
    id 'java'
}

group = 'com.majik'
version = '0.0.1'
sourceCompatibility = '1.8'

class GreetingPluginExtension {
    String message = 'Hello, dere!'
    String greeter = 'Puppetyr'
}

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Add the 'greeting' extension object
        def extension = project.extensions.create('greeting', GreetingPluginExtension)

        // Add a task that prints the string from the extension object
        project.task('hello') {
            doLast {
                println "${extension.message} from ${extension.greeter}"
            }
        }
    }
}