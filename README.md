
to use navigation, added
    implementation "androidx.navigation:navigation-compose:$nav_version"

to use topAppBar, added compose material
    implementation "androidx.compose.material:material:1.3.1"

changed compileSdk to 33 from 32

to use room database, added
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"

to use hilt dependency injection, added
    id 'com.google.dagger.hilt.android' version '2.44' apply false

