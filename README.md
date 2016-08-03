# gradle-android-rtl

[![](https://jitpack.io/v/znyang/gradle-android-rtl.svg)](https://jitpack.io/#znyang/gradle-android-rtl)

## 概述

用于自动修复布局文件中未添加RTL支持的标签，如果你有一个大工程需要处理。

## Android RTL Support

[Native RTL support in Android 4.2](http://android-developers.blogspot.tw/2013/03/native-rtl-support-in-android-42.html)

## Android Studio插件

在AndroidStudio中通过`Refactor->Add RTL Support Where Possible...` 可以调出自动修复工具，这个工具也挺方便的。

与该工具的差别：

* 性能更好。在处理大批量文件修改时，用AS工具会出现卡顿
* 支持padding标签的补齐

## 检查修改点

`Android>Lint>Internationalization>Bidirectional Text`

![](./img/1.jpg)

![](./img/2.jpg)

## 配置

```gradle
buildscript {
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.znyang:gradle-android-rtl:0.0.4'
    }
}

apply plugin: 'com.zen.plugin.rtl'

rtl {
    from = fileTree("${project.projectDir}/src/main/res/layout/")
    // into = "${project.buildDir}/outputs/rtl/res/layout"
}
```

## 输出

```
gradle androidRtl
```

需要修复的layout文件会生成在rtl.into的目录下，可以在确认后覆盖到你的layout目录下，再进行一遍检查！

**注：未定义rtl.into的情况下，修复的文件会直接覆盖原文件！**

支持的标签如下：

```java
{
    "layout_toLeftOf", "layout_toStartOf",
    "layout_toRightOf", "layout_toEndOf",

    "layout_marginLeft", "layout_marginStart",
    "layout_marginRight", "layout_marginEnd",

    "layout_alignLeft", "layout_alignStart",
    "layout_alignRight", "layout_alignEnd",

    "layout_alignParentLeft", "layout_alignParentStart",
    "layout_alignParentRight", "layout_alignParentEnd",

    "drawableLeft", "drawableStart",
    "drawableRight", "drawableEnd",
}

// padding只要存在left/right，就会补充四个标签
{
    "paddingLeft", "paddingStart",
    "paddingRight", "paddingEnd"
}

// 支持对以下标签的value替换(left->start, right->end)
{
    "layout_gravity",
    "gravity"
}
```
