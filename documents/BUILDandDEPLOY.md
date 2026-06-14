# BUILD and DEPLOY

このドキュメントは、この `ftcenv-quickstart/` プロジェクトをビルドし、FTC Robot Controller へデプロイする方法をまとめたものです。

このリポジトリの現在の構成では、確実に成立する手順は **Android Studio からのビルド・デプロイ** です。
ルートには `gradlew` が存在せず、この作業環境では `gradle` コマンドも未導入だったため、CLI 手順は前提条件付きの補足として記載します。

---

## 1. 前提

- Android Studio がインストール済み
- Android SDK Platform 34 / Platform-Tools / Build-Tools が導入済み
- このプロジェクトを Android Studio で開ける状態になっている
- デプロイ先として以下のいずれかを使える
  - REV Control Hub
  - Robot Controller Phone
- 接続方法は以下のいずれか
  - USB
  - Wi-Fi ADB

現在のビルド設定:

| 項目 | 値 |
|---|---|
| FTC SDK | 11.1.0 |
| AGP | 8.7.2 |
| compileSdk | 34 |
| minSdk | 24 |
| targetSdk | 28 |
| Java | 1.8 |

---

## 2. どこがビルド対象か

- `TeamCode/` は自分たちの OpMode を置く場所です
- 実際に端末へ入るアプリは `com.qualcomm.ftcrobotcontroller` です
- そのため、デプロイは「TeamCode の Java ファイルを含んだ Robot Controller アプリを端末へ更新する」作業になります

---

## 3. 推奨手順: Android Studio からビルドする

### 3.1 プロジェクトを開く

1. Android Studio を起動
2. **File → Open** で `ftcenv-quickstart/` を開く
3. 初回の **Gradle Sync** が終わるまで待つ

Sync に失敗した場合は `documents/SOLUTION.md` を確認してください。

### 3.2 ビルド

1. メニューから **Build → Make Project**
2. 下部の Build ウィンドウで `BUILD SUCCESSFUL` を確認

この段階で確認すべき点:

- Java の構文エラーがない
- `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` 配下の OpMode が正しく認識されている
- 依存ライブラリ追加時に Gradle エラーが出ていない

### 3.3 デプロイ

1. Robot Controller を USB または Wi-Fi ADB で接続
2. Android Studio 上部のデバイス選択で接続先を選ぶ
3. **Run → Run 'TeamCode'** を実行
4. インストール完了後、Robot Controller アプリの更新を確認する

補足:

- 既存アプリが入っている場合も、同じ `applicationId` で上書きインストールされます
- 初回デプロイ後は Robot Controller 側が再起動することがあります

---

## 4. 接続方法

### 4.1 USB 接続

1. Control Hub または Robot Controller Phone を USB で PC に接続
2. Android Studio のデバイス一覧に端末が出ることを確認
3. その端末を選んで Run する

最も単純で、最初の動作確認に向いています。

### 4.2 Wi-Fi ADB 接続

Control Hub を使う場合は、PC を Control Hub 側ネットワークに接続してから ADB 接続します。

```bash
adb connect 192.168.43.1:5555
```

接続後に Android Studio のデバイス一覧へ表示されたら、そのまま Run でデプロイできます。

注意:

- `192.168.43.1:5555` は Control Hub の典型例です
- 実機設定によっては IP や接続条件が異なる場合があります

---

## 5. CLI でやりたい場合

このリポジトリには現時点で `./gradlew` がありません。
そのため、そのままでは一般的な以下の手順は使えません。

```bash
./gradlew build
./gradlew assembleDebug
```

CLI ビルドを使いたい場合は、少なくとも以下のどちらかが必要です。

1. Gradle Wrapper (`gradlew`, `gradlew.bat`) をこのリポジトリへ追加する
2. ローカル環境に Gradle 8.9 系を導入する

CLI 環境が整った後に使う想定コマンド例:

```bash
gradle build
gradle assembleDebug
```

ADB だけで端末へ入れたい場合の例:

```bash
adb connect 192.168.43.1:5555
adb install -r <生成されたapkのパス>
```

ただし、現在の作業環境では APK 生成までを未検証のため、CLI 運用を標準手順にはしていません。

---

## 6. デプロイ後の確認

1. Driver Station を起動
2. Robot Configuration が正しいことを確認
3. TeleOp または Autonomous の一覧に自作 OpMode が出ることを確認
4. `INIT` → `START` で動作確認する

表示されない場合の確認ポイント:

- クラスに `@TeleOp` または `@Autonomous` が付いているか
- `TeamCode` 配下に配置されているか
- ビルド成功後に再デプロイしたか
- Robot Controller 側に古い状態が残っていないか

---

## 7. 関連ドキュメント

- 環境構築: `documents/SETUP.md`
- 開発の進め方: `documents/INSTRUCTION.md`
- 技術仕様: `documents/ENVIRONMENT.md`
- 既知の問題: `documents/SOLUTION.md`
