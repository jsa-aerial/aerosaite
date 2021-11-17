# aerosaite
Saite delivered as self installing / running uberjar

Table of Contents
=================

   * [Use](#use)
      * [Obtaining](#obtaining)
      * [Installation](#installation)
      * [Updating](#updating)
      * [Running](#running)

[toc](https://github.com/ekalinin/github-markdown-toc)
# Use

## Obtaining

wget http://bioinformatics.bc.edu/~jsa/aerial.aerosaite-1.0.0-standalone.jar

Or use curl or whatever else you like for fetching web located resources

## Installation

Now able to use JDK 8 or JDK 11+

`java -jar aerial.aerosaite-1.0.0-standalone.jar --install`

take the defaults, in particular `~/.saite` for application home directory.  MKL suitable for Neanderthal will be downloaded  as part of the installation.  It will also set up the paths on Linux and MacOS.  Windows will get the libraries in the `.saite/Libs` directory, but auto setup is not yet available for Win as I haven't had access to be able to put together a working script for it.


## Updating

If you already have Saite, you can update it to the latest version with the `--update` switch.

`java -jar aerial.aerosaite-1.0.0-standalone.jar --update`


## Running

To run, on Linux and MacOS, the simplest and best way is to use the scripts available in the `.saite` home directory:

* `linux-runserver` : Linux JVM 8
* `jvm11+-linux-runserver : Linux JVM 11+
* `mac-runserver` : MacOS JVM 8
* `jvm11+-mac-runserver` : MacOS JVM 11+

The reason for separate JVM 8 and 11+ versions is that JVM 9+ requires added switches to support [Neanderthal](https://neanderthal.uncomplicate.org) and [DeepDiamond](https://github.com/uncomplicate/deep-diamond).

For Windows, you should be able to figure out the run command based on what you can see in the Linux and Mac scripts.











