#!/bin/bash

git clone https://github.com/20171119/project.git

cd project1

mvn install

mvn assembly:assembly

java -cp target/cse364-project1-1.0-SNAPSHOT-jar-with-dependencies.jar ReadData.java Adventure educator