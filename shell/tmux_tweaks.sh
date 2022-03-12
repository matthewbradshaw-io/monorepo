#!/bin/bash

# Tweaks to make tmux more convenient.

tmux_purge() {
  THIS_ID=$(tmux display-message -p '#S')
  tmux kill-session -at $THIS_ID
}
