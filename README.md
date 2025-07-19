# Artist Search Pro (Android Application)

This project is available in multiple languages:

- [English](README.md)
- [简体中文](README.zh-CN.md)

## Introduction

> Mobile version demo video: [artist-search-pro-android-presentation.mp4](https://drive.google.com/file/d/1xBWYpmpEkb--CG6Ag7SgFY3RbjRUEzDk/view?usp=sharing)

This project is the mobile version of [Artist Search Pro](https://github.com/zhichzhang/artist-search), built with Jetpack Compose and Kotlin, utilizing Retrofit for network communication. It fully implements all core features of the web version, [Artist Search Pro (Web Application)](https://github.com/zhichzhang/artist-search-pro), including artist search, detailed artist information display, user login and registration, artist favorites, artwork and style categorization, and similar artist recommendations. Additionally, it is optimized for mobile user experience, featuring a splash screen, artwork category carousel dialogs, session persistence, dark mode, and Snackbar notifications for smoother interaction.

## Core Features

- **Guest Features**  
  - Artist Name Search  
    - Debounced search recommendations: Automatically triggers card-style recommendations when input exceeds 3 characters, with built-in debounce to improve performance and responsiveness.  
  - Artist Detail View  
    - Basic Artist Information: Displays name, nationality, birth date, death date, biography, and other details.  
    - Artist Works Collection: Card-style display of artist’s works, supporting categorized carousel dialogs.

- **Registered User Features**  
  - Artist Name Search (includes all guest features)  
    - Favorites: Star icon on search result cards to add or remove artists from favorites.  
  - Artist Detail View (includes all guest features)  
    - Similar Artist Recommendations: Card-style display of artists with similar styles; users can favorite/unfavorite via star icon or tap the card to navigate to the artist’s detail page.  
    - Favorites: Star icon on the artist detail page for quick favorite/unfavorite.  
  - Favorites Management on Home  
    - Favorites Display: Shows cards of artists favorited by the user, including name, nationality, birth/death dates, and timestamp.  
    - Navigation: Tap a favorite card to open the corresponding artist detail page.  
  - Account Authentication and Session Management  
    - Logout: Adds the user’s cookies to a blacklist to immediately invalidate the current session.  
    - Account Deletion: Supports user account removal, clearing session and authentication data upon deletion.  
    - Session Persistence: Automatically restores previous login state if cookies are still valid.

- **General Features**  
  - Current Date Display on Home Page  
  - Light/Dark Theme Switching  
  - Splash Screen Display  
  - Authentication and Account Management  
    - Login  
      - Field validation  
      - Account and password verification; upon success, issues cookies and redirects to the logged-in home page  
      - Provides link to the registration page  
    - Registration  
      - Field validation  
      - Secure storage of user data  
      - Upon successful registration, issues cookies and redirects to the logged-in home page  
      - Provides link to the login page

## Technology Stack

- **Languages & Frameworks:** Kotlin, Jetpack Compose  
- **Networking:** Retrofit, OkHttp, Kotlinx Serialization  
- **Image Loading:** Coil  
- **Session Management:** PersistentCookieJar, SharedPreferences  
- **UI Components:** Material Design 3, LazyColumn, Dialog, Tabs, Snackbars  
- **Backend Services:** Express.js, MongoDB, Google Cloud Platform

## Disclaimer

This project is intended for research and discussion purposes only. Please do not plagiarize or submit this code as coursework.
