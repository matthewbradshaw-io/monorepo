#!/bin/bash
# Tools for working with git.

# Reverts a modified file.
# Arg 1: The file to revert, as a local or absolute path.
revert() {
  git checkout HEAD -- $1
}
