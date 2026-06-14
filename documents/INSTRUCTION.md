# INSTRUCTION - FTC開発の進め方

このドキュメントは `sample-pj/` のモーター順回転サンプルを例に、FTC開発の一連のワークフローを説明する。

---

## 1. 前提条件

- Android Studio (最新の安定版) がインストール済み
- FTC SDK 11.1.0 ベースの本プロジェクトがセットアップ済み
- REV Control Hub または Expansion Hub + Robot Controller Phone
- USB ケーブルまたは Wi-Fi Direct 接続

---

## 2. プロジェクトを開く

1. Android Studio を起動
2. **File → Open** でこの `ftcenv-quickstart/` フォルダを選択
3. Gradle Sync が自動で走る。初回はSDK依存のダウンロードに数分かかる
4. 左のProject Explorerで `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` を確認

---

## 3. サンプルコードを使って開発する (sample-pj を例に)

自分で新しいOpModeを作る場合も、同じ `teamcode/` ディレクトリ内にJavaファイルを作成する。

### 3.1 コードの構造を理解する

`SingleMotorForward.java` のポイント:

```java
@TeleOp(name = "Single Motor Forward", group = "Sample")  // ← Driver Stationに表示される名前
public class SingleMotorForward extends LinearOpMode {     // ← LinearOpModeを継承

    @Override
    public void runOpMode() {
        // 1. 初期化: ハードウェアマップからデバイスを取得
        DcMotor motor = hardwareMap.get(DcMotor.class, "testMotor");

        // 2. waitForStart(): STARTボタン待ち
        waitForStart();

        // 3. メインループ: opModeIsActive() が true の間ループ
        while (opModeIsActive()) {
            motor.setPower(gamepad1.right_trigger);
            telemetry.addData("Power", gamepad1.right_trigger);
            telemetry.update();
        }
    }
}
```

**OpMode の種類:**
- `@TeleOp` ... ドライバー操作モード (試合の操縦期間)
- `@Autonomous` ... 自律動作モード (試合の自律期間)

### 3.2 Robot Configuration を設定する

Driver Station 側で Hardware Configuration を作成する必要がある:

1. Driver Station アプリを開く
2. 右上のメニュー → **Configure Robot**
3. **New** で新しい構成を作成
4. Control Hub (または Expansion Hub) を選択
5. **Motors** → Port 0 に `testMotor` という名前で登録
6. モーターの種類を選択 (例: `Rev Robotics Core Hex Motor`, `goBILDA 5202/5203` 等)
7. **Save** で保存し、構成をアクティブにする

### 3.3 ビルドとデプロイ

1. Robot Controller とPCをUSBで接続 (または Wi-Fi Direct)
2. Android Studio 上部のデバイス選択で Robot Controller を選択
3. **Build → Make Project** (Ctrl+F9 / Cmd+F9) でコンパイル
4. **Run → Run 'TeamCode'** (Shift+F10 / Ctrl+R) でデプロイ
5. デプロイ完了後、Robot Controller のアプリが自動的に再起動する

### 3.4 実行と動作確認

1. Driver Station で **TeleOp** メニューから **"Single Motor Forward"** を選択
2. **INIT** ボタンを押す → テレメトリに "Initialized" が表示される
3. **START** ボタン (▶) を押す
4. ゲームパッド1の **右トリガー (RT)** を引く → モーターが回転する
5. テレメトリにモーターパワーがリアルタイム表示される
6. **STOP** ボタン (■) で停止

---

## 4. 新しいOpModeを作る手順

1. `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/` に新しいJavaファイルを作成
2. `LinearOpMode` を継承し `@TeleOp` または `@Autonomous` アノテーションを付ける
3. `runOpMode()` を実装する
4. ビルド → デプロイ → Driver Station で選択して実行

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "My Auto", group = "Competition")
public class MyAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() {
        // 初期化処理
        waitForStart();

        // 自律動作
        // motor.setPower(0.5);
        // sleep(1000);
        // motor.setPower(0);
    }
}
```

---

## 5. デバッグのコツ

- **telemetry** を積極的に使う。`telemetry.addData("key", value)` + `telemetry.update()` でリアルタイムに値を確認できる
- **Logcat** (Android Studio下部) でログを確認。`Log.d("TAG", "message")` でカスタムログを出力できる
- ハードウェアが見つからないエラーが出たら、Driver Station の Robot Configuration を確認する
- モーターの回転方向が逆の場合は `motor.setDirection(DcMotorSimple.Direction.REVERSE)` に変更する

---

## 6. よく使うパターン

### エンコーダーで指定距離を移動

```java
motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
motor.setTargetPosition(1000);  // エンコーダーティック数
motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
motor.setPower(0.5);
while (opModeIsActive() && motor.isBusy()) {
    telemetry.addData("Position", motor.getCurrentPosition());
    telemetry.update();
}
motor.setPower(0);
```

### サーボの制御

```java
Servo servo = hardwareMap.get(Servo.class, "myServo");
servo.setPosition(0.5);  // 0.0 ~ 1.0
```

### センサーの読み取り

```java
DistanceSensor distance = hardwareMap.get(DistanceSensor.class, "mySensor");
double cm = distance.getDistance(DistanceUnit.CM);
telemetry.addData("Distance", "%.1f cm", cm);
```
