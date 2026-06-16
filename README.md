# Labyrinth Game

Java Swing製の迷路脱出ゲーム。プレイヤーはドラゴンを避けながら迷路の左下からスタートし、右上のゴールを目指す。全10ステージ、MySQL によるハイスコア保存機能付き。

---

## 技術スタック

| 用途 | 技術 |
|------|------|
| 言語 | Java 21 |
| GUI | Java Swing |
| データベース | MySQL 8.0 |
| DBドライバ | MySQL Connector/J 9.1.0（JDBC） |
| ビルド | Apache Ant |
| IDE | NetBeans |

---

## 動作環境

| 項目 | バージョン |
|------|-----------|
| Java | 21 以上 |
| MySQL | 8.0 以上 |
| MySQL Connector/J | 9.1.0 |
| IDE | NetBeans |

---

## セットアップ

### 1. リポジトリをクローンする

```bash
git clone https://github.com/joeokinaga/labyrinth-game.git
cd labyrinth-game
```

### 2. MySQL データベースを作成する

MySQL に接続し、以下を実行する。

```sql
CREATE DATABASE Protech3;
```

テーブルはゲーム初回起動時に自動作成される。

### 3. データベース接続情報を変更する

`src/labyrinthgame/Database.java` の以下の箇所を自分の環境に合わせて書き換える。

```java
// 変更前（例）
connection = DriverManager.getConnection(dbURL, "root", "Jookinaga0921");

// 変更後
connection = DriverManager.getConnection(dbURL, "root", "自分のMySQLパスワード");
```

### 4. MySQL Connector/J のパスを設定する

`nbproject/project.properties` の以下の行を、自分の環境の JAR の場所に変更する。

```
file.reference.mysql-connector-j-9.1.0.jar=/自分のパス/mysql-connector-j-9.1.0.jar
```

JAR は [MySQL公式サイト](https://dev.mysql.com/downloads/connector/j/) からダウンロードできる。

---

## 実行方法

1. NetBeans でプロジェクトを開く
2. メニューの「Run」→「Run Project」を選択する

---

## 操作方法

| キー | 動作 |
|------|------|
| `W`  | 上へ移動 |
| `A`  | 左へ移動 |
| `S`  | 下へ移動 |
| `D`  | 右へ移動 |

メニューバーの「Highscores」でランキング表示、「Restart」でレベル1から再スタート。

---

## ゲームルール

- プレイヤーは左下（タイル座標 1, 18）からスタートし、右上のゴール（タイル座標 18, 0）を目指す
- ドラゴンがプレイヤーの上下左右いずれかに隣接、または同じタイルに入るとゲームオーバー
- プレイヤーの視界は自分を中心とした 7×7 タイル（前後左右 3 タイル）のみ。それ以外は暗闇
- ドラゴンは壁にぶつかるまで直進し、壁に当たるとランダムに方向を変える
- ステージが進むにつれてドラゴンの移動速度が上がる（ステージ1: 900ms間隔 → ステージ10: 50ms間隔）
- 全10ステージをクリアするとゲームクリア

---

## ランキング

- ゲームオーバー時、またはゲームクリア時にプレイヤー名とクリアしたステージ数がデータベースに保存される
- クリアステージ数の多い順に上位10件が保存される
- 保存時点で上位10件に入らなかった場合はデータベースには記録されない

---

## プロジェクト構成

```
src/labyrinthgame/
├── LabyrinthGame.java   # エントリポイント（main メソッド）
├── LabyrinthGUI.java    # ウィンドウとメニューバーの管理
├── Game.java            # ゲームループ・キー入力・当たり判定
├── Board.java           # レベルファイルの読み込みと描画
├── Player.java          # プレイヤーの移動・死亡判定
├── Dragon.java          # ドラゴンの移動AI
├── PlayerVision.java    # 視界（暗闇）の描画
├── Database.java        # MySQL接続・ランキングの読み書き
├── Ranking.java         # ランキングデータクラス
├── Tile.java            # タイルの基底クラス
├── Position.java        # タイル座標のユーティリティ
└── Direction.java       # 方向（UP / DOWN / LEFT / RIGHT）

src/res/
├── player.png
├── dragon.png
├── wall.png
├── empty.png
└── levels/
    └── level1.txt 〜 level10.txt   # マップファイル（# = 壁、スペース = 通路）
```
