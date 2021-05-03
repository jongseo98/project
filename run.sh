#!/bin/bash

git clone https://github.com/20171119/project.git

cd project2

mvn clean

mvn install

mvn assembly:assembly

mvn jacoco:prepare-agent test jacoco:report

java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project2.Recommend "F" "25" "Gradstudent" "Action|Comedy"
