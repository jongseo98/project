#!/bin/bash
git clone https://github.com/20171119/project.git
cd project1
mvn install
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project1.ReadData "adventure" "artist"