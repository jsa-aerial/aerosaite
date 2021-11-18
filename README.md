# aerosaite
Saite delivered as self installing / running uberjar

Table of Contents
=================

   * [Use](#use)
      * [Obtaining](#obtaining)
      * [Installation](#installation)
      * [Updating](#updating)
      * [Running](#running)
      * [Take an overview stroll](#take-an-overview-stroll)

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
* `jvm11+-linux-runserver` : Linux JVM 11+
* `mac-runserver` : MacOS JVM 8
* `jvm11+-mac-runserver` : MacOS JVM 11+

The reason for separate JVM 8 and 11+ versions is that JVM 9+ requires added switches to support [Neanderthal](https://neanderthal.uncomplicate.org) and [DeepDiamond](https://github.com/uncomplicate/deep-diamond).

For Windows, you should be able to figure out the run command based on what you can see in the Linux and Mac scripts.  Or better yet, if you know how to do this, a PR would be *great*!!


## Take an overview stroll

Once up and running navigate to `localhost:3000` (unless you changed the port in your scripts/run command).  You will see this as the startup 'page/screen':


![Saite-start-page](resources/images/saite-start-page.png?raw=true)


Click on the `Upload Document` button ![upload-button](resources/images/upload-doc-button.png?raw=true).  Then select `Scicloj` from `Session` and then select `SaiteOverview`.  After a couple seconds or so, you should see this page. You are placed in the gallery tab, but go to the left and start with `Overview` and just go left to right for a general overview.


![Saite-start-page](resources/images/saite-overview-gallery-page.png?raw=true)




