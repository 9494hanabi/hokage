# CONTROL

このドキュメントは、**デプロイ済みの FTC コードを Robot Controller / Driver Station 上で実行する方法** をまとめたものです。

ビルドとデプロイがまだの場合は、先に `documents/BUILDandDEPLOY.md` を参照してください。

---

## 1. 前提

- コードが Robot Controller にデプロイ済み
- Robot Controller と Driver Station が起動できる
- Robot Configuration が作成済み
- 実行したい OpMode が `TeamCode` にあり、デプロイ後の一覧に反映されている

---

## 2. 実行の全体像

FTC のコードは、一般的な Android アプリのように端末上で直接起動するのではなく、**Driver Station から OpMode を選んで実行** します。

流れは以下です。

1. Robot Controller を起動する
2. Driver Station を Robot Controller に接続する
3. 実行したい OpMode を選ぶ
4. `INIT`
5. `START`
6. 必要ならゲームパッドで操作する
7. 終了時に `STOP`

---

## 3. 実行手順

### 3.1 Robot Controller を起動する

1. Control Hub または Robot Controller Phone の電源を入れる
2. Robot Controller アプリが起動していることを確認する
3. 必要ならアプリ再起動後、初期化完了まで待つ

デプロイ直後は Robot Controller 側が再起動することがあります。

### 3.2 Driver Station を接続する

1. Driver Station アプリを起動する
2. Robot Controller と通信できる状態にする
3. 接続済み表示になることを確認する

接続できない場合は以下を確認します。

- Control Hub / Robot Controller が起動しているか
- Wi-Fi 接続先が正しいか
- Robot Controller アプリが停止していないか

### 3.3 OpMode を選択する

1. Driver Station の OpMode 一覧を開く
2. 実行したいプログラムを選ぶ

例:

- `Single Motor Forward`

`@TeleOp(name = "...")` または `@Autonomous(name = "...")` に書かれた名前で表示されます。

### 3.4 INIT する

1. `INIT` ボタンを押す
2. 初期化が完了するのを待つ
3. テレメトリや状態表示にエラーがないことを確認する

`INIT` の段階では、ハードウェア取得や初期姿勢の確認などが行われます。

### 3.5 START する

1. `START` ボタンを押す
2. OpMode の処理が開始される

TeleOp の場合は、その後ゲームパッド操作に応じてロボットが動きます。
Autonomous の場合は、`START` 後に登録した自律処理がそのまま進行します。

### 3.6 停止する

1. 終了時は `STOP` ボタンを押す
2. ロボットが停止したことを確認する

異常動作時も、まず `STOP` を使って停止します。

---

## 4. サンプル `Single Motor Forward` の動かし方

`TeamCode/src/main/java/org/firstinspires/ftc/teamcode/SingleMotorForward.java` を使う場合の手順です。

### 4.1 事前設定

Driver Station 側で Robot Configuration を作成し、モーターを以下の名前で登録します。

- Motor Port 0
- 名前: `testMotor`

### 4.2 実行

1. Driver Station で **TeleOp**
2. **`Single Motor Forward`** を選択
3. `INIT`
4. `START`
5. ゲームパッド1の **右トリガー (RT)** を引く

結果:

- `testMotor` が順回転する
- テレメトリにパワー値が表示される

### 4.3 終了

- トリガーを離すと出力が下がる
- 完全に停止したい場合は `STOP` を押す

---

## 5. OpMode が表示されない場合

以下を順に確認してください。

1. Java ファイルが `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` 配下にあるか
2. クラスに `@TeleOp` または `@Autonomous` が付いているか
3. ビルド成功後に再デプロイしたか
4. Robot Controller の再起動後に一覧を見直したか
5. Java のコンパイルエラーが残っていないか

---

## 6. INIT で止まる / 実行できない場合

主な確認ポイント:

- Hardware Configuration の名前と `hardwareMap.get()` の名前が一致しているか
- 必要なモーター、サーボ、センサーが正しいポートに設定されているか
- Robot Controller と Driver Station の接続が安定しているか
- テレメトリやログに例外が出ていないか

典型例:

- コードが `testMotor` を要求しているのに、Robot Configuration 側の名前が別になっている
- TeleOp のつもりで探しているが、実際には `@Autonomous` が付いている

---

## 7. 運用上の注意

- 実行前にロボットが安全な位置にあることを確認する
- モーターやアームが急に動いても危険がない状態で `START` する
- デバッグ中は低出力から確認する
- 異常時はすぐ `STOP` を押せる状態にしておく

---

## 8. 関連ドキュメント

- ビルドとデプロイ: `documents/BUILDandDEPLOY.md`
- 開発の進め方: `documents/INSTRUCTION.md`
- 初期セットアップ: `documents/SETUP.md`
- 技術仕様: `documents/ENVIRONMENT.md`
- 既知の問題: `documents/SOLUTION.md`
