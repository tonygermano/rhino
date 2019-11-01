# Rhino: JavaScript in Java

![Rhino](https://developer.mozilla.org/@api/deki/files/832/=Rhino.jpg)

Rhino is an implementation of JavaScript in Java.

## License

Rhino is licensed under the [MPL 2.0](./LICENSE.txt).

## Releases

<table>
<tr><td><a href="https://github.com/mozilla/rhino/releases/tag/Rhino1_7R5_RELEASE">Rhino 1.7R5</a></td><td>January 29, 2015</td></tr>
<tr><td><a href="https://github.com/mozilla/rhino/releases/tag/Rhino1_7_6_RELEASE">Rhino 1.7.6</a></td><td>April 15, 2015</td></tr>

</table>

[Release Notes](https://github.com/mozilla/rhino/blob/master/RELEASE-NOTES.md) for recent releases.

[Compatability table](http://mozilla.github.io/rhino/compat/engines.html) which shows which advanced JavaScript
features from ES5, 6, and 7 are implemented in Rhino.

## Documentation

Information for script builders and embedders:

[https://developer.mozilla.org/en-US/docs/Rhino_documentation](https://developer.mozilla.org/en-US/docs/Rhino_documentation)

JavaDoc for all the APIs:

[http://mozilla.github.io/rhino/javadoc/index.html](http://mozilla.github.io/rhino/javadoc/index.html)

More resources if you get stuck:

[https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Community](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Community)

## Building

### Status of "master" branch

<table>
<tr><td><b>Java 6</b></td><td>
  <a href="http://ci.apigee.io/job/Mozilla%20Rhino%20Java%206">
    <img src="http://ci.apigee.io/buildStatus/icon?job=Mozilla%20Rhino%20Java%206"/>
  </a></td></tr>
<tr><td><b>Java 7</b></td><td>
  <a href="http://ci.apigee.io/job/Mozilla%20Rhino">
    <img src="http://ci.apigee.io/buildStatus/icon?job=Mozilla%20Rhino"/>
  </a></td></tr>
<tr><td><b>Java 8</b></td><td>
  <a href="http://ci.apigee.io/job/Mozilla%20Rhino%20Java%208">
    <img src="http://ci.apigee.io/buildStatus/icon?job=Mozilla%20Rhino%20Java%208"/>
  </a></td></tr>
</table>

### How to Build

Rhino builds with Ant. Here are some useful tasks:

    ant jar

Build and create "js.jar" in the build/VERSION directory.

    ant junit-all

Build and run all the tests.

    ant help

to find out about the rest.

## Running

Rhino can run as a stand-alone interpreter from the command line:

    java -jar build/rhino1.7.6/js.jar
    Rhino 1.7.6 2015 04 15
    js> print('Hello, World!');
    Hello, World!
    js>

You can also embed it, as most people do. See below for more docs.

## Issues

Most issues are managed on GitHub:

[https://github.com/mozilla/rhino/issues](https://github.com/mozilla/rhino/issues)

## More Help

The Google group is the best place to go with questions:

[https://groups.google.com/forum/#!forum/mozilla-rhino](https://groups.google.com/forum/#!forum/mozilla-rhino)


