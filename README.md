# Parse Twitter Utils for Android
[![Build Status][build-status-svg]][build-status-link]
[![Coverage Status][coverage-status-svg]][coverage-status-link]
[![License][license-svg]][license-link]

A utility library to authenticate `ParseUser`s with Twitter.

## Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Then, add the library to your project `build.gradle`
```gradle
dependencies {
    implementation 'com.github.parse-community:ParseTwitterUtils-Android:latest.version.here'
}
```

## Usage
Extensive docs can be found in the [guide][guide]. The basic steps are:
```java
ParseTwitterUtils.initialize("YOUR CONSUMER KEY", "YOUR CONSUMER SECRET");
```
Then later, when your user taps the login button:
```java
ParseTwitterUtils.logIn(this, new LogInCallback() {
  @Override
  public void done(ParseUser user, ParseException err) {
    if (user == null) {
      Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
    } else if (user.isNew()) {
      Log.d("MyApp", "User signed up and logged in through Twitter!");
    } else {
      Log.d("MyApp", "User logged in through Twitter!");
    }
  }
});
```

## How Do I Contribute?
We want to make contributing to this project as easy and transparent as possible. Please refer to the [Contribution Guidelines](CONTRIBUTING.md).

## License
    Copyright (c) 2015-present, Parse, LLC.
    All rights reserved.

    This source code is licensed under the BSD-style license found in the
    LICENSE file in the root directory of this source tree. An additional grant
    of patent rights can be found in the PATENTS file in the same directory.

As of April 5, 2017, Parse, LLC has transferred this code to the parse-community organization, and will no longer be contributing to or distributing this code.

 [guide]: https://docs.parseplatform.org/android/guide/#twitter-users

 [latest]: https://search.maven.org/remote_content?g=com.parse&a=parsetwitterutils&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/

 [build-status-svg]: https://travis-ci.org/ParsePlatform/ParseTwitterUtils-Android.svg?branch=master
 [build-status-link]: https://travis-ci.org/ParsePlatform/ParseTwitterUtils-Android
 [coverage-status-svg]: https://coveralls.io/repos/ParsePlatform/ParseTwitterUtils-Android/badge.svg?branch=master&service=github
 [coverage-status-link]: https://coveralls.io/github/ParsePlatform/ParseTwitterUtils-Android?branch=master
 [maven-svg]: https://maven-badges.herokuapp.com/maven-central/com.parse/parsetwitterutils/badge.svg?style=flat
 [maven-link]: https://maven-badges.herokuapp.com/maven-central/com.parse/parsetwitterutils
 [license-svg]: https://img.shields.io/badge/license-BSD-lightgrey.svg
 [license-link]: https://github.com/ParsePlatform/ParseTwitterUtils-Android/blob/master/LICENSE
