# Dicoding Event Application

### Author: Agung Yuda Pratama  
### Institution: Universitas Teknologi Bandung

This project, **Dicoding Event Application**, is developed as the final submission for my course on Dicoding. It is a comprehensive Android app created with Kotlin and Android Studio, designed to provide users with easy access to a variety of event details via an intuitive and user-friendly interface. The app integrates multiple functionalities, including event listings, detailed event views, a favorites section, and theme switching, while leveraging the Android Architecture Components.

## Features

### 1. Event Lists via Bottom Navigation
- The app contains two main event lists, accessible through a bottom navigation bar:
  - **Active/Upcoming Events**
  - **Finished Events**
  
Each event list fetches data from an external API and displays:
  - Event image (`imageLogo`/`mediaCover`)
  - Event name (`name`)

### 2. Event Detail View
The event detail view provides comprehensive information for each event, displaying:
  - Event image (`imageLogo`/`mediaCover`)
  - Event name (`name`)
  - Organizer (`ownerName`)
  - Event date and time (`beginTime`)
  - Remaining quota (calculated as `quota - registrant`)
  - Event description (`description`)
  - A button to open the event link in a web browser

### 3. Home Screen
A dedicated home screen displays event highlights through a new Bottom Navigation fragment, featuring:
  - Up to 5 active/upcoming events in a **Horizontal RecyclerView**, **Carousel**, or **Banner** layout
  - Up to 5 finished events

### 4. Loading Indicator
Loading indicators appear whenever data is fetched from the API, ensuring a responsive user experience.

### 5. Android Architecture Component Implementation
The app leverages the Android Architecture Component (`ViewModel`) to persist data across orientation changes. Users can switch between portrait and landscape modes without data reloads, even without an active internet connection.

### 6. Error Handling
The app provides error messages when data fetching fails, such as during API errors or when there’s no internet connection.

### 7. Favorite Feature with Database Support
Users can add or remove events to/from their favorites list. The app provides:
  - A favorites list displaying all favorite events
  - A detailed view for each event in the favorites list

### 8. Theme Customization
A settings menu enables users to switch between **light** and **dark themes** using key-value storage. The app remembers the user's theme choice even after it is closed and reopened. The theme is managed through data observation to ensure all components are displayed clearly across themes.

### 9. Repository and Injection Pattern
The app follows best practices by implementing **Repository and Injection** for API data retrieval.

### 10. Coroutine Usage
**Coroutines** are utilized for managing asynchronous tasks in both **Retrofit** and **Room** implementations.

## Technologies Used
- **Kotlin**
- **Android Studio**
- **ViewModel** for data persistence
- **Retrofit** for networking
- **Room** for local database management
- **Coroutine** for efficient threading
- **Key-Value Storage** for theme persistence
