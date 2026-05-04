# Dimension Scout 🚀

Dimension Scout is a high-quality Android application that allows users to search for and explore characters from the Rick and Morty universe. This project was built as part of a technical interview challenge, demonstrating modern Android development best practices, Clean Architecture, and polished UI/UX.

## 📱 Features

- **Real-time Search**: Search for characters with a 500ms debounce to optimize network usage.
- **Infinite Pagination**: Smoothly load more characters as you scroll.
- **Shared Element Transitions**: Fluid animations for character images between the list and detail screens.
- **Detailed Information**: View comprehensive character data, including status, species, origin, and last known location.
- **Error Handling**: Graceful handling of network errors, rate limits (HTTP 429), and empty states.
- **Accessibility Optimized**: 
    - Full TalkBack support with optimized grouping.
    - Dynamic font size support via Material3 typography.
    - High-contrast colors and clear content descriptions.

## 🏗️ Architecture

The project follows **Clean Architecture** principles and the **MVVM** (Model-View-ViewModel) pattern:

- **Domain Layer**: Contains business logic, entities (`Character`), and Use Cases.
- **Data Layer**: Handles data retrieval from the Rick and Morty API using Retrofit. Includes DTOs, Mappers, and Repository implementations.
- **Presentation Layer**: Built with Jetpack Compose. ViewModels manage state using `StateFlow` and handle UI logic.

## 🛠️ Tech Stack

- **UI**: Jetpack Compose (Material3)
- **Navigation**: Type-safe Navigation Compose (`kotlinx-serialization`)
- **DI**: Koin
- **Networking**: Retrofit & Gson
- **Concurrency**: Kotlin Coroutines & Flow
- **Image Loading**: Coil
- **Testing**: MockK & JUnit 4
- **Animations**: `SharedTransitionLayout` (Experimental Compose API)

## 🚀 Getting Started

### Prerequisites

- Android Studio Ladybug (or newer)
- JDK 17
- Android SDK 34+

### Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the `app` configuration on an emulator or physical device.

### Running Tests

To run the unit tests, use the following command in the terminal or use the Android Studio test runner:

```bash
./gradlew test
```

## 🧪 Key Highlights for Reviewers

- **State Management**: Uses a robust `CharacterSearchState` to handle loading, data, and error states in a single stream.
- **Experimental APIs**: Implementation of `SharedTransitionLayout` for premium-feel navigation.
- **Clean Code**: Strict separation of concerns and high testability.
- **Resilience**: Custom error mapping for common API failures to provide user-friendly feedback.
