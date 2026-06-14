# hokage

## Pull request shortcut

`scripts/create-pr.sh` pushes the current working branch to `origin` and creates
a pull request against `upstream/main`.

```bash
git switch -c your-name/my-change
# Edit and commit files.
./scripts/create-pr.sh
```

Pass GitHub CLI options directly to the script when needed:

```bash
./scripts/create-pr.sh --title "Add feature" --body "Summary of changes"
```

The script requires GitHub CLI authentication, a clean working tree, and these
remotes:

```bash
git remote add origin https://github.com/YOUR_ACCOUNT/hokage.git
git remote add upstream https://github.com/9494hanabi/hokage.git
```

## Team development rules

1. Create a branch named with your own name and do all work on that branch.
2. Remember that this repository has reviewers. Submit changes in a form that
   can be reviewed clearly.
3. Split large changes into multiple focused pull requests.
4. Write clean, organized code that other people can understand easily.
