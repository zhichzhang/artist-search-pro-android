# Artist Search Pro (Android Application)

## 介绍

> 移动端版本演示视频链接为 [artist-search-pro-android-presentation.mp4](https://drive.google.com/file/d/1xBWYpmpEkb--CG6Ag7SgFY3RbjRUEzDk/view?usp=sharing)
 
本项目是 [Artist Search Pro](https://github.com/zhichzhang/artist-search) 的移动端版本，基于 Jetpack Compose 和 Kotlin 构建，并使用 Retrofit 进行网络通信。该版本完整实现了网页版，即[Artist Search Pro (Web Application)](https://github.com/zhichzhang/artist-search-pro)，的所有核心功能，包括艺术家搜索、详细信息展示、用户登录与注册、艺术家收藏、作品与风格分类展示以及相似艺术家推荐等。同时针对移动端用户体验进行了优化，新增了启动画面、作品分类弹窗轮播、会话持久化、深色模式与 Snackbar 提示机制，带来更顺畅的交互体验。  

## 核心功能  

- 游客可用功能
  - 艺术家名称检索
    - 防抖检索推荐：输入字符超过 3 个时自动触发卡片式推荐，内置防抖机制以提升性能与响应效率
  - 艺术家详情页浏览
    - 艺术家基础信息：展示姓名、国籍、出生日期、逝世日期及生平简介等内容
    - 艺术家作品合集：卡片化展示艺术家作品，支持作品类别分类弹窗轮播展示

- 注册用户专属功能
  - 艺术家名称检索功能（包含游客功能全部内容）
    - 收藏：检索卡片右上角显示星号，支持收藏与取消收藏操作
  - 艺术家详情页浏览（包含游客功能全部内容）
    - 类似艺术家推荐：卡片化显示与该艺术家风格类似的艺术家，点击星号可收藏或取消收藏，点击卡片区域跳转至对应艺术家详情页
    - 收藏：艺术家详情页右上角显示星号，支持收藏与取消收藏操作
  - 首页收藏夹管理
    - 收藏：首页展示用户已收藏艺术家的卡片，包含姓名、国籍、出生/逝世日期及收藏时间
    - 跳转：点击卡片可跳转至对应艺术家详情页
  - 账户认证与状态管理
    - 登出：将用户 Cookie 加入黑名单，立即使当前会话失效
    - 注销：支持账户删除，注销后同步清除会话及身份凭据
    - 登录状态持久化：如 Cookie 未过期，自动恢复上次登录状态

- 通用功能
  - 首页当前日期展示
  - 浅色/深色主题切换
  - 启动页展示
  - 账户认证与状态管理
    - 登录
      - 字段合法性校验
      - 账户密码验证，验证成功后下发 Cookie，并自动跳转至登录状态主页
      - 提供跳转注册页入口
    - 注册
      - 字段合法性校验
      - 信息加密存储
      - 注册成功后下发 Cookie，并自动跳转至登录状态主页
      - 提供跳转登录页入口


## 技术栈

- **语言与框架**：Kotlin、Jetpack Compose  
- **网络通信**：Retrofit、OkHttp、Kotlinx Serialization  
- **图像加载**：Coil  
- **会话管理**：PersistentCookieJar、SharedPreferences  
- **用户界面**：Material Design 3、LazyColumn、Dialog、Tabs、Snackbars  
- **后端服务**：Express.js、MongoDB、Google Cloud Platform

## 注意事项

本项目仅供研究与讨论使用。请勿抄袭或将本代码作为课程作业提交。
