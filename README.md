<!DOCTYPE html>
<html>

<body>
    <h1>TMDB Application</h1>
    <p>A simple Android app to explore and display movie details using the TMDB (The Movie Database) API.</p>
    <h2>Features</h2>
    <ul>
        <li>keyBoard Navigating .</li>
        <li>Browse popular, top-rated, and upcoming movies.</li>
        <li>View detailed movie information including posters, overviews, and ratings.</li>
        <li>Mark movies as favorites for quick access.</li>
        <li>Modern UI built with Jetpack Compose.</li>
    </ul>
    <h2>Technologies Used</h2>
    <ul>
        <li><strong>Language:</strong> Kotlin</li>
        <li><strong>UI Framework:</strong> Jetpack Compose</li>
        <li><strong>Networking:</strong> Retrofit with the TMDB API</li>
        <li><strong>Image Loading:</strong> Coil</li>
        <li><strong>Build System:</strong> Gradle</li>
    </ul>
    <h2>Setup Instructions</h2>
    <ol>
        <li>Clone the repository:
            <pre><code>git clone https://github.com/guy4213/TMBD-Application.git</code></pre>
        </li>
        <li>Open the project in Android Studio.</li>
        <li>Sync Gradle to download dependencies.</li>
        <li>Create a <code>props.properties</code> file in the root directory and add your TMDB API key:
            <pre><code>TMDB_API_KEY=your_api_key_here</code></pre>
        </li>
        <li>Build and run the app on an emulator or physical device.</li>
    </ol>
    <h2>Project Structure</h2>
    <ul>
        <li><strong>app/</strong>: Main application module</li>
        <li><strong>gradle/</strong>: Gradle wrapper files</li>
        <li><strong>build.gradle</strong>: Project-level Gradle configuration</li>
        <li><strong>settings.gradle.kts</strong>: Settings for Gradle modules</li>
        <li><strong>props.properties</strong>: Contains sensitive API keys (not tracked by Git)</li>
    </ul>
    <h2>Screenshots</h2>
    <p>Include screenshots or GIFs here to showcase the app's functionality and UI.</p>
   <h3>HomeScreen</h3>
  <img src="![image] (https://github.com/user-attachments/assets/d8f606ce-792d-4753-bd26-3575dbd7e13a)"
" alt="HomeScreen" width="300"/>
  <h3>dropdown</h3>
  <img src=" ![image](https://github.com/user-attachments/assets/2849c545-d3ce-4843-9adc-9922f61cb1d2)"
" alt="dropdown" width="300"/>
  <h3>Favourites</h3>
      <img src="![image](https://github.com/user-attachments/assets/63f41e13-7c5c-483e-873a-8f59085f89f5)
" alt="Favourites display" width="300">
    <h3>currently broadcasted</h3>
      <img src="![image](https://github.com/user-attachments/assets/d7947340-352b-40fb-b35e-d41682e5c50a)
" alt="currently broadcast display" width="300">
   <h3>movie Details</h3>
   <img src="![image](https://github.com/user-attachments/assets/b3d0e264-4904-4214-8e57-23c84b3fbbfb)
" alt="movie Details" width="300">
      <h2>Contributing</h2>
    <p>Contributions are welcome! Please follow the steps below:</p>
    <ol>
        <li>Fork the repository.</li>
        <li>Create a feature branch: <code>git checkout -b feature/your-feature</code></li>
        <li>Commit your changes: <code>git commit -m "Add your feature"</code></li>
        <li>Push to the branch: <code>git push origin feature/your-feature</code></li>
        <li>Open a Pull Request.</li>
    </ol>
    <h2>License</h2>
    <p>This project is licensed under the <a href="LICENSE">MIT License</a>.</p>
    <h2>Contact</h2>
    <p>For any questions or feedback, please contact <a href="mailto:guy4213@gmail.com">guy4213@gmail.com</a>.</p>
</body>
</html>
