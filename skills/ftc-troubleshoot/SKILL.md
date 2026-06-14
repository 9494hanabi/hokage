---
name: ftc-troubleshoot
description: |
  FTCプロジェクトのビルドエラーや環境問題を体系的にトラブルシュートし、SOLUTION.mdに記録する。
  以下のトリガーで使用する:
  - Gradle Sync エラーが発生したとき
  - ビルドが失敗したとき
  - 「エラー」「ビルドできない」「Sync失敗」「動かない」などFTC開発中の問題報告
  - スタックトレースやエラーメッセージが貼られたとき
  - 「SOLUTION.mdに追記して」と言われたとき
  FTC/Android Studio/Gradle/AGP に関するビルドエラー全般に対応する。
  問題が解決したら必ずSOLUTION.mdに記録する。
---

# FTC Troubleshoot Skill

FTC開発環境で発生した問題を発見→診断→解決→記録する一連のワークフロー。

---

## ワークフロー

### Phase 1: 問題の特定

1. ユーザーからエラーメッセージまたはスタックトレースを受け取る
2. エラーの核心部分を抽出する (Caused by, キーとなるメッセージ)
3. 以下を判断する:
   - どのファイルが関係しているか
   - どのバージョン不整合が起きているか
   - どのGradle phaseで失敗しているか (configuration, execution, sync)

### Phase 2: 原因の診断

原因カテゴリを特定する:

| カテゴリ | 典型的なエラーパターン |
|---|---|
| バージョン非互換 | AGP vs Gradle, SDK vs compileSdk |
| スコープ誤り | `implementation()` がルートで呼ばれている等 |
| 構文変更 | deprecated API (packagingOptions → packaging) |
| 依存解決失敗 | Could not resolve, artifact not found |
| 設定不足 | namespace未指定, SDK未インストール |

診断の手順:
1. エラーメッセージから直接的な原因を読み取る
2. 関連ファイルを `Read` で確認する
3. バージョン互換性テーブルと照合する
4. 必要に応じて `WebSearch` で最新情報を確認する

### Phase 3: 解決策の実施

1. 修正が必要なファイルを特定する
2. `Edit` または `Write` で修正を適用する
3. ユーザーに再度 Gradle Sync / Build を依頼する
4. エラーが解消するまで Phase 1 に戻る

### Phase 4: SOLUTION.md への記録

問題が解決したら、プロジェクトルートの `SOLUTION.md` に以下のフォーマットで追記する:

```markdown
## Issue #N: [エラーの簡潔な説明]

**エラーメッセージ:**
\```
[エラー全文またはキーとなる部分]
\```

**原因:**
[根本原因の説明]

**解決策:**
[具体的な修正手順とコード差分]
```

SOLUTION.md が存在しない場合は新規作成する。Issue番号は既存の最大番号 + 1 とする。

---

## よくある問題と解決パターン

### AGP / Gradle バージョン非互換

AGP と Gradle にはバージョン対応関係がある。Android Studio が Gradle Wrapper を自動更新した場合、AGP が追従していないとビルドが壊れる。

対応表:
| AGP | 必要な Gradle |
|---|---|
| 7.0.x | 7.0+ |
| 7.2.x | 7.3.3+ |
| 8.0.x | 8.0+ |
| 8.5.x | 8.7+ |
| 8.7.x | 8.9+ |

解決: `build.gradle` の AGP バージョンを Gradle に合わせてアップグレードする。

### deprecated API の移行

AGP 8.x で変更された主要API:

| 旧 (AGP 7.x) | 新 (AGP 8.x) |
|---|---|
| `compileSdkVersion N` | `compileSdk N` |
| `minSdkVersion N` | `minSdk N` |
| `targetSdkVersion N` | `targetSdk N` |
| `packagingOptions { pickFirst }` | `packaging { jniLibs { pickFirsts += [...] } }` |
| `android.ndkVersion` (固定) | 削除可 (AGP自動解決) |

### スコープエラー (implementation not found)

`dependencies { implementation ... }` は Android/Java プラグインが適用されたモジュールでのみ有効。ルートプロジェクトの `allprojects` ブロック内で直接呼ぶとエラーになる。

解決: `subprojects` + `afterEvaluate` + `hasProperty('android')` ガードで囲む。

---

## 注意事項

- 修正は最小限にとどめる。無関係なファイルを変更しない
- エラー1つにつき1回の修正サイクルで対応する。複数の問題がある場合は順番に解決する
- SOLUTION.md の記録は解決確認後に行う (未解決の問題は記録しない)
- ユーザーが別のエラーで戻ってきた場合は Phase 1 から再開する
