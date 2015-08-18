# osiam-shell [![Build Status](https://travis-ci.org/osiam/shell.png?branch=master)](https://travis-ci.org/osiam/shell)

A Shell for [OSIAM](https://github.com/osiam). This project uses [JSimpleShell](https://github.com/rainu/jsimpleshell) as shell framework.

## Building

This project uses maven, so you can build it using

    mvn clean package

## Running

This guide assumes, that the [OSIAM docker](https://github.com/osiam/docker-image) image is running. 

To start the application, just execute the jar-file

    java -jar target/Osiam-Shell.jar

The application prints a welcome screen from the [JSimpleShell](https://github.com/rainu/jsimpleshell) shell framework.

After launching the application, you can connect to your OSIAM instance

    connect <endpoint> <redirectUri> <clientId> <clientSecret>

For example

    connect http://localhost:8080/ http://localhost:5000/oauth2 example-client secret

And log in

    login <username> <password>

For example

    login admin koala

