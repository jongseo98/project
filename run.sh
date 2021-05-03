#!/bin/bash

git clone https://github.com/20171119/project.git

cd project/project2

mvn install

mvn assembly:assembly

java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project2.Recommend "M" "25" "Artist"

java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project2.Recommend "F" "25" "Gradstudent" "Action|Comedy"
