#!/bin/bash

# List of branches to clean
BRANCHES=("mainline" "mvc")

# Remote name
REMOTE="origin"

# Loop through each branch
for BRANCH in "${BRANCHES[@]}"; do
  echo "Cleaning branch: $BRANCH"

  # Fetch and check out remote branch
  git fetch $REMOTE $BRANCH
  git checkout -b $BRANCH $REMOTE/$BRANCH

  # Create orphan branch (no history)
  git checkout --orphan temp-clean-$BRANCH

  # Add all files
  git add -A

  # Commit as fresh start
  git commit -m "Initial commit"

  # Delete old branch locally
  git branch -D $BRANCH

  # Rename clean branch to original name
  git branch -m $BRANCH

  # Force push to overwrite remote history
  #git push -f $REMOTE $BRANCH

  echo "✔ Cleaned and pushed branch: $BRANCH"
done

echo "🎉 All specified branches have been cleaned."
