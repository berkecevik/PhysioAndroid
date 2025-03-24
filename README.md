# 🏃‍♂️ PhysioBuddy 

**PhysioBuddy** is an AI-powered physiotherapy assistant that helps users track their body posture and movement in real-time using **MediaPipe Pose Landmarker Lite**. It’s designed for use in physical rehabilitation, fitness tracking, and movement analysis.

---

## 📱 Features

- ✅ Real-time **pose estimation** using live camera feed  
- 🖼️ Analyze **images or videos** from your gallery  
- 🎯 Detect and **visualize joint angles**  
- ⚙️ Adjustable **confidence thresholds** and **delegate (CPU/GPU)** options  
- 📊 Displays **inference time** and pose accuracy  
- 🌙 Works **offline** – no internet required  
- 💡 Clean, intuitive UI using Jetpack Compose & Material Components  

---

## 🧠 Tech Stack

| Layer            | Technology Used                           |
|------------------|--------------------------------------------|
| Language         | Kotlin                                     |
| Architecture     | MVVM (Model-View-ViewModel)                |
| UI               | Fragments, Material Components, XML Layouts|
| ML Framework     | MediaPipe Pose Landmarker Lite and Full    |
| Camera API       | CameraX                                    |
| Async Processing | Kotlin Coroutines                          |
| Navigation       | Jetpack Navigation                         |
| Testing          | JUnit, Espresso                            |

---

## 📷 Supported Media Types

- Live camera stream  
- Images  
- Videos  

All media is analyzed with **MediaPipe Pose Landmarker**, which detects 33 body landmarks and calculates angles between joints.

---

## 🚀 Getting Started

### 1. Clone the Repo

Using Git:
```bash
git clone https://github.com/your-username/PhysioBuddy.git
```
Or via Android Studio:
- Open Android Studio > Get from VCS
- Paste the repo URL above
- Choose a local directory and click Clone

### 2. Run the App
- Launch Android Studio and open the project.
- Wait for Gradle sync to complete.
- Run the app on a real device or emulator with Camera support.
- Grant camera permission on first launch.
  
---

## 📂 Project Structure

📦 PhysioBuddy/
├── MainActivity.kt                # Hosts fragments and bottom navigation
├── MainViewModel.kt              # Stores and manages pose settings
├── CameraFragment.kt             # Camera input + live pose tracking
├── GalleryFragment.kt            # Image/video input + analysis
├── PermissionsFragment.kt        # Handles camera permission request
├── OverlayView.kt                # Draws pose landmarks and joint angles
├── PoseLandmarkerHelper.kt       # ML logic using MediaPipe
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── fragment_camera.xml
│   │   ├── fragment_gallery.xml
│   │   ├── info_bottom_sheet.xml
│   ├── values/
│   │   ├── strings.xml
│   │   ├── colors.xml
│   │   ├── dimens.xml
│   │   ├── styles.xml
│   │   ├── themes.xml
├── AndroidManifest.xml           # App metadata and permissions
├── build.gradle.kts              # Dependency and plugin configurations
