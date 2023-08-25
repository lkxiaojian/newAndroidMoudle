
#### APP壳工程：
1. Application类
2. 打包环境，签名，混淆规则，业务模块集成，APP 主题等配置等
#### mod_main：
1. 包含首页，分类，体系，我的四个tab，Navigation实现管理
2. 首页tab包含Banner轮播图，视频列表，项目文章列表
3. 分类tab包含各网站文章的分类内容
4. 体系tab包含知识体系的文章分类，二级为文章列表
5. 我的tab包含个人信息，服务栏，文章推荐列表
#### mod_user：
1. 包含个人设置页面，账户安全
2. 个人信息页设置头像、姓名、手机号码等个人信息
3. 隐私政策条款功能
4. 查看版本信息以及更新App功能
5. 清除手机缓存功能
6. 用户退出登录功能
#### mod_login：
1. 登录页面登录功能，以及隐私政策
2. 其他登录方式选择
3. 用户注册页面
#### mod_search：
1. 搜索页面
2. 用户搜索历史数据
3. 搜索推荐数据
#### mod_video：
1. RecyclerView 实现防抖音短视频列表，保证全局只有一个播放器
2. ExoPlayer 播放器实现视频播放功能
3. RotateNoteView 实现旋转音乐盒
#### lib_framework：
1. Base基类相关
2. Ext拓展函数
3. Loading加载框
4. LogUtil日志打印工具类
5. Manager相关管理类
6. TipsToast吐司工具类
7. Utils相关工具类
8. 带删除按钮的EditText
#### lib_common：
1. 二次封装的Banner组件
2. 常量类
3. 实体Bean
4. 组件化通信的provider和IService
5. 通用View
#### lib_network：
1. Api接口类
2. 错误相关类
3. Flow扩展类，网络请求封装
4. Http相关拦截器
5. 相关管理类
6. BaseViewModel&BaseRepository协程网络请求封装
7. OkHttp和Retrofit封装
#### lib_stater：
1. 异步任务，延迟任务启动器
2. 任务优先级，线程池，依赖关系，是否需要等待
#### lib_banner：
1. 功能全面的Banner组件，lifecycle关联Activity/Fragment生命周期
#### lib_glide：
1. 对Glide使用的二次封装
2. 圆角，圆形，缓存，高斯模糊图片加载
#### lib_room：
1. Room数据库相关
2. 视频列表缓存

