#!/bin/bash

# Push pom.xml file on github

# Configure git
git config --global user.email "travis@travis-ci.com"
git config --global user.name "Travis CI"
git checkout -b master

# Commit pom.xml
git add pom.xml
git commit -m "Prepare next release"

# Push pom.xml
git remote add origin https://${GITHUB_TOKEN}@github.com/madoci/jelly.git
git push --quiet --set-upstream origin master
