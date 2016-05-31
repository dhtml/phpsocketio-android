# PHPSocket.IO-android

PHPSocket.IO-android is a library project to enable android developers integrate PHPSocket.IO into their applications faster and easier.

## Dependencies
* http://www.koushikdutta.com/AndroidAsync (at least version 2.0+)

## Set up Instructions
Set up the project dependencies. To use this library in your project, you have two options:

(1) Add this the most [recent jar](http://dhtml.github.io/phpsocket.io/bin/phpsocketio.jar)  to your project's lib and modify your gradle dependency as shown below:
```shell
 dependencies {
    ...
    compile 'com.koushikdutta.async:androidasync:2.+'
    compile files('libs/phpsocketio.jar')
 }
```

(2) Use the GitHub source and include that as a module dependency by following these steps:
 * Clone this library into a project named phpsocketio, parallel to your own application project:
```shell
git clone https://github.com/dhtml/phpsocketio-android.git PHPSocketIOLibrary
```
 * In the root of your application's project edit the file "settings.gradle" and add the following lines:
```shell
include ':PHPSocketIOLibrary'
project(':PHPSocketIOLibrary').projectDir = new File('../PHPSocketIOLibrary/')
```
 * In your application's main module (usually called "app"), edit your build.gradle to add a new dependency:
```shell
 dependencies {
    ...
    compile project(':PHPSocketIOLibrary')
 }
```
Now your project is ready to use this library

## Documentation and Demo
[Visit the project page](http://dhtml.github.io/phpsocket.io/) for documentation and usage. 
You can download a demo  by clicking [chat-sample](http://dhtml.github.io/phpsocket.io/bin/chat-sample.apk).

Note: this demo apk uses the default server of the library that was demonstrated on the [main project page](https://github.com/dhtml/phpsocket.io/).
This means that this demo can chat and receive from anyone using the [basic example](http://dhtml.github.io/phpsocket.io/examples/basic/index.html) described on the main project page.

## How To Use This Library
You can checkout the repository of the demo here - https://github.com/dhtml/phpsocketio-android-demo
This gives a very good example of implementing the basic example (done in html) inside android.

### Author

**Anthony Ogundipe** a.k.a dhtml

Special thanks to <a href="https://www.facebook.com/wasconet">Adewale Wilson</a> (wasconet) for his contributions to this library.

## Community
You can chat with us on facebook http://facebook.com/dhtml5 


## License

`phpsocket.io`'s code in this repo uses the MIT license, see our `LICENSE` file.