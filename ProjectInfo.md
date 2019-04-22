# 吾家项目 Android文档

## 软件架构

mvp + (retrofit + okhttp) + rxjava(异步+事件流)

项目采用模块化开发，以及最为流行的MVP架构，整个项目由多个功能和业务模块构成。项目将app作为整个项目的入口，app依赖所有业务moudle。


## 基础库
项目基础库：common,uikit,imageloader,businesslib。
### common
封装了mvp框架的基类，okhttp的请求工具类，以及业务所需的utils类（如日期时间、文件操作、字符串验证、格式化、json序列化等）。

	
|  依赖  | 说明  |
|  ----  | ----  |
| support  | 基础组件 |
| butterknife | 注解 |
| gson  | 数据解析 |
| rxjava2  | 异步线程 |
| retrofit|网络辅助工具|
|okhttp|网络请求|
|  eventbus|事件处理 |
|  okdownload|下载工具 |

### uikit
封装的都是与UI相关的基础控件和资源及自定义控件。
	
### imageloader
封装了图片请求框架（目前使用的是Glide）。

```
//ImageLoaderManager基本使用方式
ImageLoaderManager.getInstance()
	.loadImage(url, imageView);

ImageLoaderManager.getInstance()
	.loadImage(url, placeholder, imageView);
```

### businesslib
基础业务模块，所有功能组件直接依赖的基础模块，内部依赖了common和uikit模块。封装了与业务相关的mvp基类，并依赖业务的三方组件。

	
|  依赖  | 说明  |
|  ----  | ----  |
|  doorAccess  |门禁|
|  padsdk  |门禁sdk|
|  alicloud-push  |消息推送|
	
	
## 业务模块
### app
	包含首页功能，登录页面，广告页面，屏保页面，设置页面
	首页采用单Activity+多个Fragment的结构

### family
	智能家居
### market
	服务市场
### message
	消息模块
### neighbor
	邻里模块
### property
	物业服务
### safe
	可视安防
