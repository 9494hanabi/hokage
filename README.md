# hokage

## Pull Request 作成ショートカット

`scripts/create-pr.sh` は、現在作業中のブランチを `origin` にプッシュし、
`upstream/main` に対する Pull Request を作成します。

```bash
git switch -c 自分の名前/変更内容
# ファイルを編集してコミットします。
./scripts/create-pr.sh
```

タイトルや本文などの GitHub CLI オプションも指定できます。

```bash
./scripts/create-pr.sh --title "機能を追加" --body "変更内容の概要"
```

実行には、GitHub CLI の認証、コミット済みの作業ツリー、および以下の
remote 設定が必要です。

```bash
git remote add origin https://github.com/YOUR_ACCOUNT/hokage.git
git remote add upstream https://github.com/9494hanabi/hokage.git
```

## チーム開発ルール

1. 自分の名前を含むブランチを作成し、そのブランチ上で作業してください。
2. このリポジトリにはレビュワーがいます。レビューしやすい状態で変更を提出してください。
3. 大きな変更は、焦点を絞った複数の Pull Request に分けてください。
4. 他の人が理解しやすい、整理されたコードを書いてください。
5. `README.md`、`CLAUDE.md`、`AGENTS.md` は編集しないでください。

## English

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
5. Do not edit `README.md`, `CLAUDE.md`, or `AGENTS.md`.
