# osiam-shell [![Build Status](https://travis-ci.org/osiam/shell.png?branch=master)](https://travis-ci.org/osiam/shell)

A Shell for [OSIAM](https://github.com/osiam/osiam). This project uses
[JSimpleShell](https://github.com/rainu/jsimpleshell) as shell framework.

## Building

This project uses gradle, so you can build it using

    ./gradlew

## Running

This guide assumes that the [OSIAM docker](https://github.com/osiam/docker-image)
image is running.

To start the application, just execute the jar-file

    java -jar build/libs/shell-1.1-SNAPSHOT-all.jar

The application prints a welcome screen from the
[JSimpleShell](https://github.com/rainu/jsimpleshell) shell framework.

After launching the application, you can connect to your OSIAM instance

    connect <endpoint> <redirectUri> <clientId> <clientSecret>

For example

    connect http://localhost:8080/osiam http://localhost:5000/oauth2 example-client secret

And log in

    login <username> <password>

For example

    login admin koala

You can also make use of the command line arguments by starting the shell like

    java -jar build/libs/shell-1.1-SNAPSHOT-all.jar
        --endpoint http://localhost:8080/osiam
        --redirectUri http://localhost:5000/oauth2
        --clientId example-client
        --clientSecret secret

## Uploading

For both, release and snapshot, be sure to set the environment variables that
are necessary for the upload:

    export BINTRAY_USER=<USER>
    export BINTRAY_KEY=<KEY>

### Snapshot

To upload snapshots to [oss.jfrog.org](https://oss.jfrog.org/), use

    ./gradlew publish

### Release

For information about the bintray gradle plugin, please refer to its
[README](https://github.com/bintray/gradle-bintray-plugin).

To upload releases to [bintray](https://bintray.com/), use

    ./gradlew bintrayUpload
