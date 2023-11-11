#!/bin/bash

# Get a list of all tracked files
tracked_files=$(git ls-files)

# Read each line from the .gitignore
while IFS= read -r pattern; do
    # Skip comment lines
    if [[ "$pattern" =~ ^# ]]; then
        continue
    fi
    # Expand the pattern to match tracked files
    for file in $tracked_files; do
        if [[ "$file" == $pattern ]]; then
            git update-index --assume-unchanged "$file"
        fi
    done
done < .gitignore