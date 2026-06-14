# 🍃 Eco Fridge Tracker

**Course:** Mobile Application Programming (Project 2)  
**Student Name:** WANGLING  
**Matric Number:** A207385  
**Instructor:** Cikgu Izwan  

---

## 📖 Project Description
**Eco Fridge Tracker** is an Android mobile application built with Jetpack Compose. It is designed to help users efficiently manage their personal food inventory, track expiration dates, and share surplus food with the community to minimize food waste.

## 🌍 SDG Theme
**SDG 12: Responsible Consumption and Production**  
By providing a smart way to track fridge items and a platform to donate surplus food to a community food bank, this app actively promotes responsible food consumption patterns and reduces global food waste.

## ✨ New Features in Project 2 (Technical Pillars)
This project successfully upgraded from Project 1 by implementing the following four advanced pillars:
1. **Hardware Sensor Integration (Camera):** Utilizes Android's CameraX and ML Kit to scan real-world product barcodes.
2. **Web API Integration:** Connects to the public `OpenFoodFacts` REST API via network requests to dynamically fetch real product names based on scanned barcodes.
3. **Local Persistence (Room Database):** Persistently saves the user's scanned items offline into a local "My Fridge" database.
4. **Cloud Integration (Firebase Firestore):** Allows users to sync and back up their surplus items to a public "Community Food Bank" database on the cloud for sharing.

## 🛠️ Setup and Run Instructions
To successfully build and run this application, please follow these steps:

1. **Clone the Repository:**
   Open your terminal or command prompt and run the following command to clone the project to your local machine:
```bash
   git clone [https://github.com/a207385/A207385_WANGLING_CikguIzwan_Project2.git](https://github.com/a207385/A207385_WANGLING_CikguIzwan_Project2.git)

2. **Open in Android Studio:** Launch Android Studio, click **File > Open**, select the cloned project directory, and wait a few minutes for the Gradle files to fully sync.

3. **Build and Run the Application:** Connect your Android device or start an emulator (make sure it has an active internet connection and camera working to test CameraX, Retrofit, and Firebase), then click the green **Run** button (or press `Shift + F10`) to deploy.
