# NearBy-App
FoureSquare based application that displays realtime updates for places around your current location.

## Preview 

<img src="https://github.com/manartorky/NearBy-App/blob/master/art/device-2020-02-12-005726.png"  width="241" height="500" />  <img src="https://github.com/manartorky/NearBy-App/blob/master/art/device-2020-02-12-005955.png"  width="241" height="500" /> <img src="https://github.com/manartorky/NearBy-App/blob/master/art/device-2020-02-12-010104.png"  width="241" height="500" />


### Project Requirments.
- The App uses Foursquare API to display information about nearby places around user using user’s current location specified by Latitude and
Longitude
- The App has two operational modes, “Realtime" and “Single Update”. 
- Realtime allows app to always display to the user the current near by places based on his location, data should be seamlessly updated if 
  user moved by 500 m from the location of last retrieved places.
- Single update mode means the app is updated once in app is launched and doesn’t update again User should be able to switch between the two modes.
- Default mode is “Realtime”. 
- The App should remember user choices for next launches.
- Min SDK 19


## Built with
* MVVM as presentation layer.
* [NetworkBoundResource](https://github.com/android/architecture-components-samples/blob/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt) for caching mechanism.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) as a react component with lifecycle awareness benefit. 
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) for writing reactive components.
* [RxJava](https://github.com/ReactiveX/RxAndroid) for writing reactive components.
* [Retrofit](https://square.github.io/retrofit/) for consuming REST APIs.
* [Dagger2](https://github.com/google/dagger) for dependency injection.
* [Glide](https://github.com/bumptech/glide) for loading images from remote servers.
* [Room](https://developer.android.com/topic/libraries/architecture/room) as presistance library. 
* [Gson](https://github.com/google/gson) as data parser.
* [androidX libraries](https://developer.android.com/jetpack/androidx) as alternative for support library.
* [Android LifeCycle](https://developer.android.com/topic/libraries/architecture) to enable components to be lifecycle aware.


