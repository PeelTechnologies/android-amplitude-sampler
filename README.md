# android-amplitude-sampler
A library to use Amplitude with sampling of users to stay under event quota. If you had a large user base, you can reduce your event volume 100x by using a samplingRatio of 1:100. We have successfully used this project even with 1:1000 sampling ratio.

It is as easy as using `AmplitudeAnalytics` instance instead of `Amplitude.getInstance()`!

```
AmplitudeAnalytics amplitude = new AmplitudeAnalytics(app, "my-amplitude-project-id", 100);
amplitude.logEvent("MY_EVENT_NAME");
```

​* Use with Gradle
* add to your repositories
    ```
    repositories {
        maven { url "https://jitpack.io" }
    }
```
* In your app build.gradle, add:  ```implementation "com.github.PeelTechnologies:android-amplitude-sampler:1.0.0"```
