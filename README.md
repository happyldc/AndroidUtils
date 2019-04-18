# AndroidUtils
## 使用
### 添加依赖
 1.在Project下的gradle中添加添加JitPack仓库
 

    allprojects {
        repositories {
          //...其他仓库
            maven { url 'https://www.jitpack.io' }
        }
    }
 2.在项目中添加OnResultHelper的依赖,其中XYZ表示版本号 如 1.0.0
 

    dependencies {
            implementation 'com.github.happyldc:AndroidUtils:X.Y.Z'
    }
### 调用

使用本库需要在应用初始化时调用Utils.init()进行初始化。


 
