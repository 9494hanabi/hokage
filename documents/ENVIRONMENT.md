# ENVIRONMENT - FTC開発環境の説明書

---

## 概要

この `ftcenv-quickstart/` は FIRST Tech Challenge (FTC) のロボットプログラミング環境である。
FIRST-Tech-Challenge/FtcRobotController リポジトリ (SDK 11.1.0) の構造をベースに構築されている。

---

## SDK情報

| 項目 | 値 |
|---|---|
| FTC SDK バージョン | 11.1.0 |
| Gradle バージョン | 8.9 |
| Android Gradle Plugin | 8.7.2 |
| compileSdk | 34 |
| minSdk | 24 |
| targetSdk | 28 |
| Java バージョン | 1.8 |
| 対応ABI | armeabi-v7a, arm64-v8a |

---

## ディレクトリ構成

```
ftcenv-quickstart/
├── build.gradle                  # ルートビルドファイル (AGP・リポジトリ定義)
├── build.common.gradle           # 共通ビルド設定 (SDK提供、編集非推奨)
├── build.dependencies.gradle     # FTC SDK 依存ライブラリ定義
├── settings.gradle               # モジュール定義
├── gradle.properties             # Gradle設定 (JVMメモリ等)
├── .gitignore
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties   # Gradleラッパー設定
├── libs/                         # 署名用キーストア格納先
│   └── ftc.debug.keystore
├── FtcRobotController/           # SDK本体モジュール (編集不要)
│   ├── build.gradle
│   └── src/main/AndroidManifest.xml
├── TeamCode/                     # ★ 自分のコードはここに書く
│   ├── build.gradle              # チーム用ビルド設定 (依存追加可)
│   └── src/main/java/org/firstinspires/ftc/teamcode/
│       └── (OpModeファイルを配置)
├── sample-pj/                    # サンプルプロジェクト
│   ├── README.md
│   └── src/main/java/org/firstinspires/ftc/teamcode/
│       └── SingleMotorForward.java
└── documents/                    # ドキュメント
    ├── ENVIRONMENT.md            # ← このファイル
    ├── INSTRUCTION.md            # 開発の進め方
    ├── SETUP.md                  # 環境構築手順
    └── SOLUTION.md               # 既知の問題と解決策
```

---

## 各ファイルの役割

### ビルド関連

- **build.gradle** (ルート): Android Gradle Plugin のバージョンとリポジトリを定義。基本的に触らない。
- **build.common.gradle**: SDK提供の共通ビルド設定。compileSdk, minSdk, 署名設定、NDK設定などが含まれる。SDK更新時に差し替えるファイルなので編集しない。
- **build.dependencies.gradle**: FTC SDK の Maven 依存定義（現在は dependencies をルート `build.gradle` に統合済み）。
- **settings.gradle**: Gradleに認識させるモジュールを列挙。
- **gradle.properties**: JVMヒープサイズ等のGradle全般設定。

### モジュール

- **FtcRobotController/**: SDK本体。Robot Controller アプリの基盤コードが含まれる。通常は触らない。
- **TeamCode/**: 自分のチームのコードを書く場所。OpMode (TeleOp / Autonomous) をここに配置する。`build.gradle` に追加ライブラリを記述できる。

---

## Android Studio のセットアップ

### 必要なもの

1. **Android Studio** (Ladybug 2024.2 以降)
2. **Android SDK** (API Level 34)

### インストール手順

1. https://developer.android.com/studio からAndroid Studioをダウンロード・インストール
2. Android Studio 起動後 **SDK Manager** を開く (File → Settings → Appearance & Behavior → System Settings → Android SDK)
3. **SDK Platforms** タブ: Android 14 (API 34) にチェックを入れてインストール
4. **SDK Tools** タブ: 以下をインストール
   - Android SDK Build-Tools (最新)
   - Android SDK Platform-Tools
   - Android SDK Command-line Tools (latest)
5. **File → Open** で `ftcenv-quickstart/` を開く
6. Gradle Sync が実行される（初回は依存ライブラリのダウンロードに数分かかる）

### 接続方法

**USB接続:**
1. Robot Controller (Control Hub またはスマートフォン) をUSBでPCに接続
2. Android Studio のデバイスリストに表示される
3. Run ボタンでデプロイ

**Wi-Fi Direct (Control Hub):**
1. Control Hub の Wi-Fi ネットワークにPCを接続
2. Android Studio → Run → Edit Configurations → adb connect で Control Hub のIPに接続
3. `adb connect 192.168.43.1:5555` (デフォルトIP)

---

## 依存ライブラリ (SDK 11.1.0)

| ライブラリ | 用途 |
|---|---|
| `ftc:RobotCore` | ロボット制御の基盤API |
| `ftc:Hardware` | ハードウェアデバイス (モーター、サーボ、センサー) のAPI |
| `ftc:FtcCommon` | FTC共通ユーティリティ |
| `ftc:Vision` | カメラ・ビジョン処理 |
| `ftc:Blocks` | Blocks プログラミング対応 |
| `ftc:Inspection` | ロボット検査ツール |
| `ftc:RobotServer` | Robot Controller のWebサーバー |
| `ftc:OnBotJava` | OnBotJava (ブラウザIDEでのJavaコーディング) |
| `androidx.appcompat` | Android互換性ライブラリ |

---

## オプショナルライブラリ

`TeamCode/build.gradle` の dependencies に追加して使用する。

| ライブラリ | 用途 | 追加方法 |
|---|---|---|
| FTCLib | 便利なユーティリティ集 | `implementation 'org.ftclib.ftclib:core:2.1.1'` |
| Road Runner | モーションプランニング | `implementation 'com.acmerobotics.roadrunner:core:1.0.0'` |
| FTC Dashboard | リアルタイムテレメトリ可視化 | `implementation 'com.acmerobotics.dashboard:dashboard:0.4.16'` |

---

## SDK バージョンアップ手順

FIRSTから新しいSDKがリリースされた場合:

1. https://github.com/FIRST-Tech-Challenge/FtcRobotController/releases で最新リリースを確認
2. ルート `build.gradle` の FTC SDK バージョン番号を更新 (例: `11.1.0` → `11.2.0`)
3. 必要に応じて `build.common.gradle` を公式リポジトリのものに差し替え（AGP 8.x 構文に注意）
4. Gradle Sync を実行
5. ビルドエラーがないか確認

---

## 参考リンク

- [FtcRobotController 公式リポジトリ](https://github.com/FIRST-Tech-Challenge/FtcRobotController)
- [FTC SDK ドキュメント](https://ftc-docs.firstinspires.org/)
- [FTCLib ドキュメント](https://docs.ftclib.org/)
- [Road Runner ドキュメント](https://rr.brott.dev/)
