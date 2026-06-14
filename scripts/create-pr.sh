#!/usr/bin/env bash

set -euo pipefail

base_branch="${BASE_BRANCH:-main}"
branch="$(git branch --show-current)"

if ! command -v gh >/dev/null 2>&1; then
  echo "Error: GitHub CLI (gh) is required." >&2
  exit 1
fi

if [[ -z "$branch" ]]; then
  echo "Error: HEAD is detached. Switch to a working branch first." >&2
  exit 1
fi

if [[ "$branch" == "$base_branch" ]]; then
  echo "Error: Create and switch to a working branch before creating a PR." >&2
  echo "Example: git switch -c feature/my-change" >&2
  exit 1
fi

if [[ -n "$(git status --porcelain)" ]]; then
  echo "Error: Commit or stash your changes before creating a PR." >&2
  exit 1
fi

if ! git remote get-url origin >/dev/null 2>&1 ||
   ! git remote get-url upstream >/dev/null 2>&1; then
  echo "Error: Both origin (your fork) and upstream remotes are required." >&2
  exit 1
fi

normalize_github_repo() {
  local url="${1%.git}"
  url="${url#git@github.com:}"
  url="${url#https://github.com/}"
  printf '%s\n' "$url"
}

origin_repo="$(normalize_github_repo "$(git remote get-url origin)")"
upstream_repo="$(normalize_github_repo "$(git remote get-url upstream)")"
origin_owner="${origin_repo%%/*}"

if [[ "$origin_repo" == "$upstream_repo" ]]; then
  echo "Error: origin must point to your fork, not the upstream repository." >&2
  exit 1
fi

git push -u origin "$branch"
gh pr create --repo "$upstream_repo" --head "$origin_owner:$branch" --base "$base_branch" "$@"
