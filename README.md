# LeleMarket-Android

**一次模块化编程的实践**

### 模块化编程角色分类，lib、comonpent和module

### 如何共享、分离模块间Application的初始化工作
      
- 采用模块实现IModuleApplication接口，在BaseApplication通过反射的方式调用模块Application的初始化

### 如何在lib、module里使用ButterKnife
- 工程的build.gradle文件，添加`classpath "com.jakewharton:butterknife-gradle-plugin:10.2.0"`
- module或者lib的build.gradle文件，添加`pply plugin "com.jakewharton.butterknife"`
- 使用注解的时候，用R2代替R  

### 如何使用Dagger2实现依赖注入

### 如何使用解决模块页面跳转
- 使用Alibaba的ARouter

### 如何解决模块间的资源命名重复
- 模块的任何资源以模块名开头
- 通过build.gradle文件添加`resourcePrefix "common_"`起到强制作用，否则编译失败

### 如何解决模块间资源重复
- 抽出`Common`模块，存放公共、重复的资源

### 如何将模块在App和Module之间切换

### 如何统一管理模块的dependencies

### 如何使用Relam数据库
