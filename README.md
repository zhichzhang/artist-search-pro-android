# Artist Search Pro (Android Application)

## 介绍 / Intro

> 移动端版本演示视频链接为 [artist-search-pro-android-presentation.mp4](https://drive.google.com/file/d/1xBWYpmpEkb--CG6Ag7SgFY3RbjRUEzDk/view?usp=sharing)  
> The presentation video of the Android version is available here: [artist-search-pro-android-presentation.mp4](https://drive.google.com/file/d/1xBWYpmpEkb--CG6Ag7SgFY3RbjRUEzDk/view?usp=sharing)

本项目是 [Artist Search Pro](https://github.com/zhichzhang/artist-search) 的移动端版本，基于 Jetpack Compose 和 Kotlin 构建，并使用 Retrofit 进行网络通信。该版本完整实现了网页版，即[Artist Search Pro (Web Application)](https://github.com/zhichzhang/artist-search-pro)，的所有核心功能，包括艺术家搜索、详细信息展示、用户登录与注册、艺术家收藏、作品与风格分类展示以及相似艺术家推荐等。同时针对移动端用户体验进行了优化，新增了启动画面、作品分类弹窗轮播、会话持久化、深色模式与 Snackbar 提示机制，带来更顺畅的交互体验。

This project is the Android version of [Artist Search Pro](https://github.com/zhichzhang/artist-search), built using Jetpack Compose and Kotlin, with Retrofit for backend communication. It fully implements the core features of the web version, namely [Artist Search Pro (Web Application)](https://github.com/zhichzhang/artist-search-pro), including artist search, detailed artist information, user authentication and registration, artist favoriting, artwork and style category display, and similar artist recommendations. Additionally, the Android version is optimized for mobile UX with enhancements like a splash screen, category carousel dialogs, persistent login, dark mode, and Snackbar feedback for key interactions.

## 核心功能 / Key Features

- 艺术家搜索（输入超过 3 个字符后自动触发，含防抖处理）  
  Artist search triggered automatically when more than 3 characters are entered, with debounce logic to reduce unnecessary API calls  
- 艺术家详情页（基础信息与简介）  
  Artist detail screen with biography and metadata  
- 收藏管理（添加 / 移除收藏，后端同步）  
  Favorite management with backend synchronization  
- 作品展示（支持分类查看）  
  Artwork display with category viewing support  
- 相似艺术家推荐（登录后可见）  
  Similar artist recommendation (visible after login)  
- 用户认证与持久化登录（基于 CookieJar）  
  User authentication and persistent login via `PersistentCookieJar`  
- 分类弹窗轮播展示  
  Artwork categories shown in a carousel within a dialog  
- 深色模式支持  
  Support for dark theme  
- 全局 Snackbar 提示机制  
  Global snackbar-based feedback system  
- 启动画面（Splash Screen）  
  Splash screen for app startup

## 技术栈 / Tech Stack

- **语言与框架 / Language & Framework**：Kotlin, Jetpack Compose  
- **网络通信 / Networking**：Retrofit, OkHttp, Kotlinx Serialization  
- **图像加载 / Image Loading**：Coil  
- **会话管理 / Session Management**：PersistentCookieJar, SharedPreferences  
- **用户界面 / UI Components**：Material Design 3, LazyColumn, Dialog, Tabs, Snackbars  
- **后端服务 / Backend**：Express.js, MongoDB, Google Cloud Platform

## 注意事项 / Caution

本项目仅供研究与讨论使用。请勿抄袭或将本代码作为课程作业提交。  

This project is intended solely for research and discussion purposes. Please refrain from copying or submitting this code as part of any academic assignments.
