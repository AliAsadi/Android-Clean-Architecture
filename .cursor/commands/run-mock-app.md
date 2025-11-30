# Run Mock App

To build and run the app on the Mock build variant:

1.  **Ensure a device is connected** (Emulator or Physical):
    ```bash
    adb devices
    ```

2.  **Build and Install MockDebug**:
    ```bash
    ./gradlew installMockDebug
    ```

3.  **Launch the App**:
    ```bash
    adb shell am start -n com.aliasadi.clean.mock/com.aliasadi.clean.ui.main.MainActivity
    ```

**Combined Command:**
```bash
./gradlew installMockDebug && adb shell am start -n com.aliasadi.clean.mock/com.aliasadi.clean.ui.main.MainActivity
```
