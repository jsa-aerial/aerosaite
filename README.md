# aerosaite

[Saite](https://github.com/jsa-aerial/saite) delivered as self installing / running uberjar


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

wget http://bioinformatics.bc.edu/~jsa/aerial.aerosaite-1.4.3-standalone.jar

Or use curl or whatever else you like for fetching web located resources

## Installation

Now able to use JDK 8 or JDK 11+.  However, only tested with 8 and 11, while 16 is deprecated with binary incompatibilities with 17.  In general just stick with LTS versions.  **UPDATE** jdk-17 has been tested and it also works!

`java -jar aerial.aerosaite-1.4.3-standalone.jar --install`

take the defaults, in particular `~/.saite` for application home directory.  [MKL](https://www.intel.com/content/www/us/en/developer/tools/oneapi/onemkl.html#gs.gy8xm2) suitable for [Neanderthal](https://neanderthal.uncomplicate.org) will be downloaded  as part of the installation.  It will also set up the paths on Linux and MacOS.  Windows will get the libraries in the `.saite/Libs` directory, but auto setup is not yet available for Win as I haven't had access to be able to put together a working script for it.


## Updating

If you already have Saite, you can update it to the latest version with the `--update` switch.

`java -jar aerial.aerosaite-1.4.3-standalone.jar --update`


## Running

To run, the simplest and best way is to use the scripts available in the `.saite` home directory:

* `linux-runserver` : Linux JVM 8
* `jvm11+-linux-runserver` : Linux JVM 11+
* `mac-runserver` : MacOS JVM 8
* `jvm11+-mac-runserver` : MacOS JVM 11+
* `win-runserver.bat` : Windows 10 JVM 8
* `jvm11+-win-runserver.bat` : Windows 10 JVM 11+

As of 1.4.3 there are now 'base' scripts for Windows 10.  They do not include code able to setup the MKL libraries.  The reason for separate JVM 8 and 11+ versions is that JVM 9+ requires added switches to support [Neanderthal](https://neanderthal.uncomplicate.org) and [DeepDiamond](https://github.com/uncomplicate/deep-diamond).

For Windows, there has been some limited testing of the scripts and they *should* work.  But if not, and you are Windows user, you should be able to figure out the run command based on what you can see in the scripts.  Or better yet, if you really know how to do this, a PR would be *great*!!


## Take an overview stroll

Once up and running navigate to `localhost:3000` (unless you changed the port in your scripts/run command).  You will see this as the startup 'page/screen':


![Saite-start-page](resources/images/saite-start-page.png?raw=true)


Click on the `Upload Document` button ![upload-button](resources/images/upload-doc-button.png?raw=true).  Then select `Scicloj` from `Session` and then select `SaiteOverview`.  After a couple seconds or so, you should see this page. You are placed in the gallery tab, but go to the left and start with `Overview` and just go left to right for a general overview.


![Saite-start-page](resources/images/saite-overview-gallery-page.png?raw=true)




