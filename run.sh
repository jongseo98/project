#!/bin/bash

git clone https://github.com/20171119/project.git

cd project1

mvn install

java -cp target/project1-1.0-SNAPSHOT.jar project1.App
