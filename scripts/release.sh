#!/bin/bash

# ---------------------------------------------------------------
# Script for release with Maven
#   - Prepare the release (update POM version and commit)
#   - Perform the release (tag the commit)
#   - Prepare next development version (update POM and commit)
# ---------------------------------------------------------------

# Retrieving arguments
while [ -n "$1" ]
do
    case "$1" in
        --auto | -a)
            auto=true
            ;;
        --release | -r | --version | -v)
            shift
            DEFAULT_VERSION="$1"
            ;;
        --tagPrefix | -p)
            shift
            DEFAULT_PREFIX="$1"
            ;;
        --tag | -t)
            shift
            DEFAULT_TAG="$1"
            ;;
        --snapshot | -s)
            shift
            DEFAULT_SNAPSHOT="$1"
            ;;
        --help | -h)
            echo "Options:"
            echo "  --auto | -a : enable auto mode (no user input, default values will be used)"
            echo "  --release | -r | --version | -v [version] : set the default release version to [version]"
            echo "  --tagPrefix | -p [prefix] : set the default tag prefix to [prefix]"
            echo "  --tag | -t [tag] : set the default release tag to [tag]"
            echo "  --snapshot | -s [version] : set the default snapshot version to [version]"
            exit 0
            ;;
        *)
            echo "$1: Unknown option"
            echo "Use --help or -h to show all available options"
            exit 1
            ;;
    esac
    shift
done

# ------------------------------
# Release Version
# ------------------------------

if [ -z "$DEFAULT_VERSION" ]
then
    # Extracting current version from POM and deducing release version
    POM_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
    DEFAULT_VERSION=${POM_VERSION//-SNAPSHOT/}
fi

# Skip input if auto is set
if [ -z "$auto" ]
then
    # Ask for release version
    echo -n "Release version ? [$DEFAULT_VERSION] : "; read version
fi
if [ -z "$version" ]
then
    version=$DEFAULT_VERSION
fi

# ------------------------------
# Release Tag
# ------------------------------

if [ -z "$DEFAULT_PREFIX" ]
then
    # Default tag prefix is 'v'
    DEFAULT_PREFIX="v"
fi

if [ -z "$DEFAULT_TAG" ]
then
    DEFAULT_TAG="$DEFAULT_PREFIX$version"
fi

# Skip input if auto is set
if [ -z "$auto" ]
then
    # Ask for release tag
    echo -n "Release tag ? [$DEFAULT_TAG] : "; read tag
fi
if [ -z "$tag" ]
then
    tag=$DEFAULT_TAG
fi

# ------------------------------
# Snapshot Version
# ------------------------------

if [ -z "$DEFAULT_SNAPSHOT" ]
then
    # Deduce next development version
    SPLIT_VERSION=${version//./ }
    OLD_IFS=$IFS
    IFS=' '
    read -ra SPLIT_VERSION <<< "$SPLIT_VERSION"
    IFS=$OLD_IFS
    NEW_MINOR=$( expr ${SPLIT_VERSION[1]} + 1 )
    DEFAULT_SNAPSHOT="${SPLIT_VERSION[0]}.$NEW_MINOR-SNAPSHOT"
fi

# Skip input if auto is set
if [ -z "$auto" ]
then
    echo -n "Next development version ? [$DEFAULT_SNAPSHOT] : "; read snapshot
fi
if [ -z "$snapshot" ]
then
    snapshot=$DEFAULT_SNAPSHOT
fi

# ------------------------------
# Perform the release
# ------------------------------

echo "--------------------------------------------------"
echo "- Preparing release $version (tagged $tag)"
echo "--------------------------------------------------"

# Make sure we are on master and everything is up-to-date
git checkout master
git pull

# Set the release version in POM file
mvn versions:set -DnewVersion=$version

# Commit new POM
git add .
git commit -m "Prepare release $version"
git push

# Tag release
git tag $tag
git push --tags

echo "--------------------------------------------------"
echo "- Preparing next development version $snapshot"
echo "--------------------------------------------------"

# Set the next development version in POM file
mvn versions:set -DnewVersion=$snapshot

# Commit new POM
git add .
git commit -m "Prepare for next development version $snapshot"
git push

# ------------------------------
# Perform the release
# ------------------------------
