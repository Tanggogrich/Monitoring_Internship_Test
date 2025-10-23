#!/bin/bash

# This script runs the k6 test script in a loop with a random delay.
# It will continue to run until the user manually stops the process (e.g., with Ctrl+C).

# Check if a k6 script path is provided as an argument.
if [ -z "$1" ]; then
  echo "Usage: ./run_http_requests.sh <path_to_test_script>"
  exit 1
fi

TEST_SCRIPT="$1"

while true
do
  echo "Running test script: $TEST_SCRIPT"
  k6 run "$TEST_SCRIPT"

  #Generate a random delay between 30 and 35 seconds.
  DELAY_DURATION=$((RANDOM % 6 + 60))

  sleep $DELAY_DURATION
done