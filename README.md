# Facebook Crawler

## Introduction
Using `Java` to crawl the numbers of user `talks` and ` likes` from facebook fanpage.
The results stored into `VoltDB` for data storage.


## Environmental Settings

### Execution requirement

 `Java-1.8` and `Voltdb-6.0.1` 
 
 
### Installation

1. Install `Java 1.8`
```terminal=
$> sudo add-apt-repository ppa:webupd8team/java
$> sudo apt-get update
$> sudo apt-get install oracle-java8-installer
```
After Java installation, you could check by
```terminal=
$> java -version

$> java version "1.8.0_111"
   Java(TM) SE Runtime Environment (build 1.8.0_111-b14)
   Java HotSpot(TM) 64-Bit Server VM (build 25.111-b14, mixed mode)
```
2. Install `Voltdb` and Settings
``` terminal=
$> sudo apt-get -y install ant build-essential ant-optional default-jdk
   python cmake valgrind ntp ccache git-arch git-completion git-core
   git-svn git-doc git-email python-httplib2 python-setuptools python-dev
   apt-show-versions
       
$> wget https://github.com/VoltDB/voltdb/archive/voltdb-6.0.1.tar.gz
    
$> tar -zxf voltdb-6.0.1 voltdb
$> mv voltdb-voltdb-6.0.1 voltdb
$> cd voltdb
    
$> ant clean; ant -Djmemcheck=NO_MEMCHECK
    
$> export CLASSPATH="$CLASSPATH:$HOME/voltdb/voltdb/*:$HOME/voltdb/lib/*:../"
$> alias voltdb='/home/usr/voltdb/bin/sqlcmd'
```

### Start Voltdb

After installation, you need start the `Voltdb`

```terminal=
$> /home/usr/voltdb/bin/voltdb create --background
```

You may probably get some `ERROR` like:
```terminal=
ERROR: sudo bash -c "echo never > /sys/kernel/mm/transparent_hugepage/enabled"
ERROR: sudo bash -c "echo never > /sys/kernel/mm/transparent_hugepage/defrag"
```
Follow the instructions and **restart** the `Voltdb`

```terminal=
INFO: Deleting stale PID file "/home/usr/.voltdb_server/localhost_3021.pid"...
INFO: Starting VoltDB server in the background...
INFO:   Output files are in "/home/usr/.voltdb_server".
Background process started with process ID 2519.
```
Now, the `voltdb` has been started successfully.


## Program limitation

### Execution speed limitation
The program runs on single thread, costing about `1 second` for each `crawling and storing`.

### Facebook permission
There are `access restrictions` for some facebook fanpages . For example, [this page](https://www.facebook.com/pg/172159439521671/likes/?ref=page_internal) could not be accessed by user without `sign-in`. They would be skipped by crawler, because the program doesn't have `sign-in permission` to access these fanpages.

### Some exceptions for Facebook
Some facebook fanpages do not present their number of user talks on pages like [this](https://www.facebook.com/profile.php?id=257722857678218). 
They would also be skipped by crawler.


## Source code Information 

* Java source code:  ` F74022133.java `
* Jar file: `F74022133.jar `
* Input file: ` IdList.txt `
* Readme file: `README.md`
* Execution method : ` java -jar F74022133.jar `
 (with `Voltdb` on and `input file` in same folder)
