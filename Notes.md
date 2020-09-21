GRADLE PLUGIN STUFF!
- The way this project is structured is such that the greeting plugin, which is a simple, basic Gradle plugin, must be uploaded into a repository. In order to do so, the repo informaiton in BOTH projects must be updated regarding repos. In the plugin file, the new repo must be the FIRST repository listed.


# Stuff for the files USING the plugin:
The pluginManagement block is required in settings.gradle in order to use a custom repository.
Once that block is added, the plugin can be pulled in by 'id' in the plugins{} block of build.gradle.
This 'id' matches the 'id' inside the gradlePlugin{plugins{simplePlugin{}}} block.
It can then be used in the build.gradle file.


# Stuff for the plugin itself:
## build.gradle
plugins{}:
- "maven-publish" library is used for publishing to a maven repository. There is an older method that can be done using just "maven" (I think it was?), but this is the newer method.
- "java-gradle-plugin" tells Gradle to include certain files/dependencies/things that are used in Gradle plugin utilization, so this needs to be included as well.

gradlePlugin{plugins{}}:
I'm not sure if simplePlugin is a thing that needs to be there or is arbitrary or where it comes from, but it works.

gradlePlugin{plugins{simplePlugin{}}}:
The 'id' is what you'll use to import the plugin in the plugins{} block of other Gradle files.
The 'implementationClass' is the name of the class (group included) in the Groovy file (we'll talk about that in a second) that will be brought in when this plugin is imported.

publishing{}:
This block contains everything for the maven-publish plugin to publish your new plugin

publishing{publications{}:
mavenJava(MavenPublication){} is used for Gradle plugins. This may change for other types of projects

publishing{publications{mavenJava(MavenPublication){}}:
Using "from components.java" tells Maven to pull all the data automatically from the Java/Groovy project, rather than defining them manually (which is commented out in this code).

publishing{repositories{maven{}}}:
This is the Maven repository information for where the plugin is to be published.
url is the web URL to the repository itself.
credentials{} can contain the 'username' and 'password' required to authenticate with the repository. These can be read in from an external file in order to keep them out of the actual code if it's sensitive; it wasn't in this testing environment.


## /src/resources
The only file that's necessary here is /src/resources/META-INF.gradle-plugins/[plugin-id].[plugin-implementationClass]. These two pieces both come from the build.gradle's gradlePlugin{} block. Then the implementation-class is the same as those last two pieces but together (see files for example)


## /src/main/groovy/[plugin].groovy
Ah, yes. This is where the actual plugin code lives! Be sure the 'package' name matches the 'group' id from the other files (and this one, too). This probably doesn't actually need to include maven-publish, and it may or may not need java-gradle-plugin. I didn't test those, because I was SO relieved when it finally worked taht I just left them there.

The 'version' is needed, because you need that when you import the plugin inside of a Gradle file from your repository. 

Here's where things get tricky.
The class is where all the stuff happens. BUT...
The EXTENSION (which is a class, yes), which is created by the project.extensions.create() block, is what allows a user to interface with the plugin code. The actual plugin code itself can't take any data from outside. But the class that will be declared to be an extension can be interacted with, and its variables can be defined. This can be seen in the plugin utilization sample at the bottom, where the two variables 'message' and 'greeter' can be changed and the plugin code will be executed using those variables instead. 


## Publishing the plugin to the repo
./gradlew publish


## Misc info
More information about the maven-publish library, as well as a lot of other useful information (including using an Ivy repository, rather than Maven) can be found in the Gradle docs at https://docs.gradle.org/current/userguide/publishing_maven.html


