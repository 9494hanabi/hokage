# SOLUTION - 既知の問題と解決策

---

## Issue #1: `implementation()` メソッドが見つからない

**エラーメッセージ:**
```
Could not find method implementation() for arguments [org.firstinspires.ftc:Inspection:11.1.0]
on object of type org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler.
```

**原因:**
`build.dependencies.gradle` を `allprojects { apply from: ... }` でルートプロジェクトにも適用していた。ルートプロジェクトには Android プラグインが適用されていないため、`implementation` configuration が存在しない。

**解決策:**
ルート `build.gradle` で `allprojects` → repositories のみに使い、dependencies は `subprojects { afterEvaluate { ... } }` で Android モジュールにのみ適用する。

```groovy
// OK: repositories は全プロジェクトに適用
allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

// OK: dependencies は Android モジュールにのみ適用
subprojects {
    afterEvaluate {
        if (it.hasProperty('android')) {
            dependencies {
                implementation 'org.firstinspires.ftc:RobotCore:11.1.0'
                // ...
            }
        }
    }
}
```

---

## Issue #2: IncrementalTaskInputs 非互換エラー

**エラーメッセージ:**
```
Cannot use @TaskAction annotation on method IncrementalTask.taskAction$gradle_core()
because interface org.gradle.api.tasks.incremental.IncrementalTaskInputs
is not a valid parameter to an action method.
```

**原因:**
AGP (Android Gradle Plugin) 7.2.0 は Gradle 7.3.3〜7.4.2 にのみ対応。Gradle 8.0 以降で `IncrementalTaskInputs` インターフェースが削除されたため、Gradle 8.9 と AGP 7.2.0 の組み合わせでクラッシュする。

**解決策:**
Gradle 8.9 に対応する AGP 8.7.2 にアップグレードし、ビルド設定を AGP 8.x 構文に更新。

| 項目 | 変更前 | 変更後 |
|---|---|---|
| AGP | 7.2.0 | 8.7.2 |
| `compileSdkVersion 30` | deprecated | `compileSdk 34` |
| `minSdkVersion 24` | deprecated | `minSdk 24` |
| `targetSdkVersion 28` | deprecated | `targetSdk 28` |
| `packagingOptions { pickFirst }` | deprecated | `packaging { jniLibs { pickFirsts += [...] } }` |
| `ndkVersion '21.3.6528147'` | 固定値 | 削除 (AGP自動解決) |

**AGP / Gradle バージョン対応表 (参考):**

| AGP | 必要な Gradle |
|---|---|
| 7.2.x | 7.3.3 – 7.4.x |
| 8.0.x | 8.0+ |
| 8.5.x | 8.7+ |
| 8.7.x | 8.9+ |

---

## 問題追加テンプレート

```markdown
## Issue #N: [エラーの簡潔な説明]

**エラーメッセージ:**
\```
[エラー全文またはキーとなる部分]
\```

**原因:**
[根本原因の説明]

**解決策:**
[具体的な修正手順]
```
