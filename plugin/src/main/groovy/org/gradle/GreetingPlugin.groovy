package org.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin

// 自定义Plugin需要继承plugin并且重写apply方法
class GreetingPlugin implements Plugin<Project> {
    void apply(Project target) {
        // 创建自定义属性
        target.extensions.create("greeting", GreetingPluginExtension)
        target.greeting.extensions.create("info", NestedGreetingPluginExtension)

        // 从DefaultTask创建自定义task
        target.task('hello', type: GreetingTask);

        // 创建task
        target.task('sayHello') << {
            println "${target.greeting.message} && ${target.greeting.greeter}"
        }

        target.task('sayNestedHello') << {
            println target.greeting.message
            println target.greeting.info.email
        }
    }
}

// 自定义属性
class GreetingPluginExtension {
    String message = "default message";
    String greeter;
    NestedGreetingPluginExtension nested = new NestedGreetingPluginExtension();
}

// 嵌套属性
class NestedGreetingPluginExtension {
    String email = "default@gmail.com";
}
