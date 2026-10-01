# product-catalog-app-koinz
An Android product catalog application built with Kotlin and Jetpack Compose, following an MVVM-based architecture with feature-based organization.

## Project Setup

### Requirements

- Android Studio
- JDK 11
- Android SDK 37

### Run the project

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download dependencies.
4. Run the `app` configuration on an emulator or Android device.

The project uses **min SDK 24** and **target SDK 37**.

## Architecture

The app follows **MVVM** with a separation between UI, ViewModels, and data/networking layers.

```text
UI (Compose)
    ↓
ViewModel
    ↓
Repository
    ↓
Retrofit API
    ↓
DummyJSON
```

`StateFlow` is used to expose UI state from the ViewModel. The product list supports `Loading`, `Success`, `Empty`, and `Error` states. Pull-to-refresh is handled through the ViewModel and can be triggered while handling the different UI states.
Feature-specific code is organized under `features`, while reusable components and utilities are kept under `core`.

## Module Structure

The project contains two Gradle modules:

```text
ProductCatalog
├── app
└── data
```

### `app`

Contains the Android application and presentation layer.

```text
app
└── com.example.productcatalog
    ├── core
    │   ├── common
    │   └── components
    └── features
        ├── allproducts
        │   ├── di
        │   ├── presentation
        │   └── ui
        └── productdetails
            ├── di
            ├── presentation
            └── ui
```

The main product features are:

- Product list
- Product details
- Search UI
- Pull-to-refresh
- Loading, empty, and error states
- Favorites UI

The product details screen is launched from the product list using an `Intent` and a product ID.

### `data`

Responsible for data access and networking.

```text
data
├── di
├── dto
├── mapper
├── model
├── network
└── repository
```

## Networking

The project uses **Retrofit 3.0.0** with Gson for API communication.

Base URL:

```text
https://dummyjson.com/
```

Available API operations include:

- Get all products
- Get a product by ID
- Search products

Search is currently implemented in the data/repository layer but is **not connected to the UI**.

## Dependency Injection

**Koin 4.1.1** is used for dependency injection.

Dependencies are provided through separate modules:

- `dataModule` — Retrofit, API service, and repository
- `productListModule` — product list ViewModel
- `productDetailModule` — product detail ViewModel

Koin is started from `MyApplication`.

## Testing

The project currently includes dependencies for:

- JUnit
- AndroidX JUnit
- Espresso
- Compose UI testing

**Tests have not been implemented yet.**

## Development Assumptions

- No authentication is required.
- DummyJSON is the remote source of product data.
- The application does not use a local database or cache.
- Offline support is not implemented.
- Product data is read-only.
- Product images are provided by the remote API.
- The search endpoint exists but is not currently exposed through the UI.
