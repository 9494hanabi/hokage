package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * SingleMotorForward - モーター1つを順回転させるサンプルOpMode
 *
 * ■ 動作概要:
 *   ゲームパッド1の右トリガーを引くとモーターが順回転する。
 *   トリガーを離すと停止する。
 *
 * ■ ハードウェア構成:
 *   - DC Motor x1 (Control Hub / Expansion Hub の Motor Port 0)
 *   - Robot Configuration で "testMotor" という名前で登録すること
 *
 * ■ 使い方:
 *   1. このファイルを TeamCode/src/main/java/org/firstinspires/ftc/teamcode/ にコピー
 *   2. Android Studio で Build → Make Project
 *   3. Robot Controller にデプロイ
 *   4. Driver Station で TeleOp → "Single Motor Forward" を選択して INIT → START
 */
@TeleOp(name = "Single Motor Forward", group = "Sample")
public class SingleMotorForward extends LinearOpMode {

    // ハードウェアマップに登録するモーター名
    private static final String MOTOR_NAME = "testMotor";

    @Override
    public void runOpMode() {
        // ---- 初期化フェーズ ----
        // ハードウェアマップからモーターを取得
        DcMotor motor = hardwareMap.get(DcMotor.class, MOTOR_NAME);

        // モーターの回転方向を設定 (FORWARD = 順回転)
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        // エンコーダーなしで動作 (パワー制御のみ)
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // パワーを0にしたときの挙動: ブレーキ
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // テレメトリに初期化完了を表示
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Motor", MOTOR_NAME);
        telemetry.update();

        // ---- START ボタン待ち ----
        waitForStart();

        // ---- メインループ ----
        while (opModeIsActive()) {
            // 右トリガーの値 (0.0 ~ 1.0) をモーターパワーに直接使用
            double power = gamepad1.right_trigger;

            // モーターにパワーを設定
            motor.setPower(power);

            // テレメトリ表示
            telemetry.addData("Status", "Running");
            telemetry.addData("Motor Power", "%.2f", power);
            telemetry.addData("Trigger (RT)", "%.2f", gamepad1.right_trigger);
            telemetry.update();
        }

        // ---- 停止処理 ----
        motor.setPower(0);
    }
}
