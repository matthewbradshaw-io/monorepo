#!/bin/bash
# Tools to improve directory interaction.

alias ls="ls -la"
alias nuke="rm -rf"
alias clr="clear"
alias up="cd .."


# Deletes a directory which cannot normally be removed with rm -rf.
supernuke() {
  chmod 777 -R $1
  nuke $1
}
