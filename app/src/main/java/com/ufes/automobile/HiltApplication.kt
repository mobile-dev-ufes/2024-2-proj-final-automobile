package com.ufes.automobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [AutoMobileApp] is the main application class for the AutoMobile application.
 *
 * It extends the [Application] class and is annotated with [HiltAndroidApp].
 * This annotation triggers Hilt's code generation and sets up the application-level
 * dependency injection container.
 *
 * **Key Responsibilities:**
 *
 * 1. **Hilt Setup:** Initializes the Hilt dependency injection framework for the application.
 *    This allows for easy dependency injection throughout the app using Hilt annotations.
 *
 * 2. **Application Lifecycle:**  As the primary application component, it handles
 *    application-level lifecycle events.
 *
 * 3. **Global Context:** Provides access to a global application context that can be used
 *    by other parts of the application when needed.
 *
 * **Usage:**
 *
 * - This class is declared in the `AndroidManifest.xml` file as the application's name.
 *   ```xml
 *   <application
 *       android:name=".AutoMobileApp"
 *       ...>
 *       ...
 *   </application>
 *   ```
 * - No manual instantiation is required. Android instantiates this class at application startup.
 *
 * **Note:**
 *  - Replace `your.package.name` with your actual package name.
 *  - If you add logic to onCreate() method make sure to add it to the documentation.
 */
@HiltAndroidApp
class AutoMobileApp : Application()