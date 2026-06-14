# SETUP - FTC開発環境構築手順

このドキュメントは、FTC (FIRST Tech Challenge) のロボットプログラミング環境をゼロから構築する手順を示す。

---

## 前提

- OS: macOS / Windows / Linux
- インターネット接続

---

## Step 1: Android Studio のインストール

1. https://developer.android.com/studio にアクセス
2. 最新の安定版 (Ladybug 2024.2 以降) をダウンロード
3. インストーラーの指示に従いインストール
4. 初回起動時のセットアップウィザードで **Standard** を選択

---

## Step 2: Android SDK の設定

Android Studio を起動し、SDK Manager を開く。

**開き方:** File → Settings → Appearance & Behavior → System Settings → Android SDK
(macOS: Android Studio → Settings → ...)

### SDK Platforms タブ

以下にチェックを入れてインストール:

- **Android 14 (API 34)** — compileSdk として使用

### SDK Tools タブ

以下にチェックを入れてインストール:

- Android SDK Build-Tools (最新)
- Android SDK Platform-Tools
- Android SDK Command-line Tools (latest)

**Apply** を押してインストールを完了する。

---

## Step 3: プロジェクトを開く

1. Android Studio で **File → Open**
2. この `ftcenv-quickstart/` フォルダを選択
3. **Gradle Sync** が自動で開始される
4. 初回は FTC SDK の依存ライブラリダウンロードに 3〜5 分かかる
5. Sync 完了後、左パネルの Project Explorer に以下が表示される:
   - `FtcRobotController` モジュール
   - `TeamCode` モジュール
   - `Gradle Scripts`

### Sync が失敗する場合

SOLUTION.md に既知の問題と解決策を記録している。まずそちらを確認すること。

---

## Step 4: プロジェクト構成の確認

Gradle Sync 成功後の状態:

```
ftcenv-quickstart/
├── FtcRobotController/    ... SDK本体 (触らない)
├── TeamCode/              ... ★ 自分のコードを書く場所
│   └── src/main/java/org/firstinspires/ftc/teamcode/
├── sample-pj/             ... サンプルOpMode
├── documents/             ... ドキュメント一式
├── build.gradle           ... AGP 8.7.2 / SDK 11.1.0
├── build.common.gradle    ... 共通ビルド設定 (compileSdk 34)
├── gradle.properties      ... Gradle設定
└── gradle/wrapper/
    └── gradle-wrapper.properties  ... Gradle 8.9
```

### 現在のバージョン構成

| コンポーネント | バージョン |
|---|---|
| FTC SDK | 11.1.0 |
| Android Gradle Plugin (AGP) | 8.7.2 |
| Gradle | 8.9 |
| compileSdk | 34 |
| minSdk | 24 |
| targetSdk | 28 |
| Java | 1.8 |

---

## Step 5: サンプルコードを配置する

`sample-pj/` にモーター1つを順回転させるサンプルが入っている。これを TeamCode にコピーする:

```bash
cp sample-pj/src/main/java/org/firstinspires/ftc/teamcode/SingleMotorForward.java \
   TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
```

または Android Studio 上でファイルをドラッグ&ドロップしてコピーする。

コピー後、Project Explorer の `TeamCode > java > org.firstinspires.ftc.teamcode` に `SingleMotorForward` が表示されることを確認する。

---

## Step 6: ビルド確認

1. メニューバーから **Build → Make Project** (Ctrl+F9 / Cmd+F9)
2. 画面下部の Build ウィンドウに `BUILD SUCCESSFUL` が表示されれば完了

ビルドエラーが出た場合は SOLUTION.md を参照。

---

## Step 7: Robot Controller への接続とデプロイ

### USB接続の場合

1. REV Control Hub (または Robot Controller Phone) をUSBケーブルでPCに接続
2. Android Studio 上部のデバイス選択ドロップダウンに端末が表示される
3. **Run → Run 'TeamCode'** (Shift+F10 / Ctrl+R) でデプロイ

### Wi-Fi Direct 接続の場合 (Control Hub)

1. PCの Wi-Fi 設定で Control Hub のネットワークに接続
   - SSID: `FTC-XXXX` (XXXXはチーム番号)
   - パスワード: Control Hub に設定されたもの
2. ターミナルで adb 接続:
   ```bash
   adb connect 192.168.43.1:5555
   ```
3. Android Studio のデバイスリストに Control Hub が表示される
4. Run でデプロイ

---

## Step 8: 動作確認

1. Driver Station アプリを開く
2. Robot Configuration で `testMotor` を Motor Port 0 に登録 (まだの場合)
3. TeleOp メニューから **"Single Motor Forward"** を選択
4. **INIT** → **START**
5. ゲームパッド1の右トリガー (RT) を引くとモーターが回転する
6. テレメトリにパワー値がリアルタイム表示される

ここまで確認できれば環境構築完了。

---

## 次のステップ

- 開発の進め方の詳細 → [INSTRUCTION.md](./INSTRUCTION.md)
- 環境の技術仕様 → [ENVIRONMENT.md](./ENVIRONMENT.md)
- 既知の問題と解決策 → [SOLUTION.md](./SOLUTION.md)
