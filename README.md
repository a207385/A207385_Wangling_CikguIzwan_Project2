# 🍃 Eco Fridge Tracker

**Course:** Mobile Application Programming (Project 2)
**Student Name:** WANGLING
**Matric Number:** A207385
**Instructor:** Cikgu Izwan

---

## 📖 Project Description

**Eco Fridge Tracker** is an Android mobile application built with Jetpack Compose. It is designed to help users efficiently manage their personal food inventory, track expiration dates, and share surplus food with the community to minimize food waste.

---

## 🌍 SDG Theme

**SDG 12: Responsible Consumption and Production**

By providing a smart way to track fridge items and a platform to donate surplus food to a community food bank, this app actively promotes responsible food consumption patterns and reduces global food waste.

---

## ✨ New Features in Project 2 (Technical Pillars)

This project successfully upgraded from Project 1 by implementing the following four advanced pillars:

1. **Hardware Sensor Integration (Camera)**
   Utilizes Android's CameraX and ML Kit to scan real-world product barcodes.

2. **Web API Integration**
   Connects to the public `OpenFoodFacts` REST API via network requests to dynamically fetch real product names based on scanned barcodes.

3. **Local Persistence (Room Database)**
   Persistently saves the user's scanned items offline into a local "My Fridge" database.

4. **Cloud Integration (Firebase Firestore)**
   Allows users to sync and back up their surplus items to a public "Community Food Bank" database on the cloud for sharing.

---

## 🛠️ Setup and Run Instructions

### 1️⃣ Clone the Repository

Open your terminal or command prompt and run:

```bash
git clone https://github.com/a207385/A207385_WANGLING_CikguIzwan_Project2.git
```

### 2️⃣ Open in Android Studio

1. Launch Android Studio.
2. Click **File > Open**.
3. Select the cloned project folder.
4. Wait for Gradle Sync to finish successfully.

### 3️⃣ Build and Run the Application

1. Connect a physical Android device or start an Android Emulator.
2. Ensure the device has:

   * Internet connection enabled
   * Camera permission granted
3. Click the green **Run** button or press **Shift + F10**.
4. Wait for the application to install and launch automatically.

---

## 📱 Main Functionalities

* Scan food products using CameraX and ML Kit barcode scanner.
* Retrieve product information from OpenFoodFacts API.
* Save scanned products into Room Database.
* View and manage fridge inventory.
* Donate surplus food items to the Community Food Bank.
* Synchronize community data using Firebase Firestore.
* Support SDG 12 by reducing unnecessary food waste.

---

## 🧰 Technologies Used

* Kotlin
* Jetpack Compose
* CameraX
* Google ML Kit Barcode Scanning
* Retrofit
* OpenFoodFacts API
* Room Database
* Firebase Firestore
* Material 3

---

