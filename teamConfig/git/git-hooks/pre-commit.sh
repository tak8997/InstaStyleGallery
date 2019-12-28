#!/bin/bash

echo "Running static analysis..."

export PATH=/usr/local/bin:$PATH

# Validate Kotlin code with detekt and KtLint before committing
./gradlew ktlint ktlintFormat

status=$?

if [ "$status" = 0 ] ; then
    echo "Static analysis found no issues. Proceeding with push."
    exit 0
else
    echo 1>&2 "Static analysis found issues you need to fix before pushing."
    exit 1
fi