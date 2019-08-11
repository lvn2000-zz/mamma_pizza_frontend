Frontend application is application was developed using Scala program language and sbt - a build tool for Scala, Java.

For building application need to follow steps:

1. Download and install sbt. (see details on https://www.scala-sbt.org/)
2. Need to create jar file common-library (see details in readme.txt in root folder of common-library projetc)
3. After that can be possible working with code using following commands -
    sbt clean     - clean all generated, downnloaded, compiled, or otherwise derived artifacts 
    sbt compile   - compile all sources
    sbt run       - run application (local server) 
                    http://localhost:9000
    sbt test      - execute all tests
