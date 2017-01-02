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
2. Install `VoltDB` and Settings
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

### Start VoltDB

After installation, you need start the `VoltDB`

```terminal=
$> /home/usr/voltdb/bin/voltdb create --background
```

You may probably get some `ERROR` like:
```terminal=
ERROR: sudo bash -c "echo never > /sys/kernel/mm/transparent_hugepage/enabled"
ERROR: sudo bash -c "echo never > /sys/kernel/mm/transparent_hugepage/defrag"
```
Follow the instructions and **restart** the `VoltDB`

```terminal=
INFO: Deleting stale PID file "/home/usr/.voltdb_server/localhost_3021.pid"...
INFO: Starting VoltDB server in the background...
INFO:   Output files are in "/home/usr/.voltdb_server".
Background process started with process ID 2519.
```
Now, the `VoltDB` has been started successfully.

## VoltDB Introduction

> The world's fastest in memory operational database [VoltDB](https://www.voltdb.com)

* Stable release:  `6.0 / January 27, 2016`
* Repository: 	github.com/VoltDB/voltdb/
* Operating system:	`Linux` and `Mac OSX`
* Platform: `Java`

VoltDB is a  [scale-out](http://www.ems5.com/view.php?id=308) [NewSQL]("https://en.wikipedia.org/wiki/NewSQL") relational database. Each VoltDB database is optimized for a specific application by `partitioning` the `database tables` and the `stored procedures` that access those tables across multiple "sites" or partitions on one or more host machines to create the `distributed database`. Because both the data and the work is partitioned, `multiple queries` can be run `in parallel`. 

### Partioning 

By `analyzing` and `precompiling` the data access logic in the `stored procedures`, `VoltDB` can distribute both the `data` and the `processing` associated with it to the `individual partitions` on the cluster. In this way, each partition contains a unique "slice" of the data and the data processing. Each node in the cluster can support `multiple partitions`.

<img src="https://docs.voltdb.com/graphics/OverviewPictPartition.png" height="300" width="500"></img>

### Serialize (Single Thread Processing)

At run-time, calls to the `stored procedures` are passed to the appropriate partition. When procedures are `single-partitioned` (meaning they operate on data within a single partition) the `server process` executes the procedure by `itself`, freeing the rest of the cluster to `handle other requests in parallel`.

By using `serialized processing`, VoltDB ensures transactional consistency without the overhead of `locking`, `latching`, and `transaction logs`, while partitioning lets the database handle `multiple requests` at a time. As a general rule of thumb, the more processors (and therefore the more partitions) in the cluster, the more transactions VoltDB completes per second, providing an easy, almost `linear path` for `scaling` an application's `capacity` and `performance`.

<img src="https://docs.voltdb.com/graphics/OverviewPictSerialization.png" height="300" width="500"></img>

### Sample code for VoltDB connection

Use `Java` to send `SQL query` to `VoltDB`.
```java=
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;

// Create client
Client myApp = ClientFactory.createClient();

try{
    myApp.createConnection(ip); // Create connection to VoltDB
    myApp.callProcedure("@AdHoc", sql); // SQL query
}catch(Exception e){
    e.printStackTrace();
}
```

## Program Limitation

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
