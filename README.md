# 書籍管理API

# 概要

書籍とそのカテゴリーに関するCRUD機能をもった REST API です。

## 主な使用技術

- Java
- Spring Boot
- MyBatis
- MySQL
- Docker
- 自動テスト
- CI（GitHub Actions、自動テストを実装）
- AWSデプロイ（これから実施予定）

## アプリケーション概略図

![book-application drawio (3)](https://github.com/kawara777/book-application/assets/138858245/38025fce-3645-4530-89b5-745a8644794b)

# 機能

書籍情報に対して以下の処理を実行できます。（カテゴリーに対してはこれから実装予定）

[API仕様書](https://kawara777.github.io/book-application/docs/dist/index.html)参照

# 設計書

## E-R図

![book-application drawio (1)](https://github.com/kawara777/book-application/assets/138858245/746a8845-4e1f-4958-a43c-6e88baaa6226)

## データベース定義

**books**

| カラム名(論理名) | カラム名（物理名） | 型（桁） | Nullable | その他 | 
| :---: | :---: | :---: | :---: | :---: | 
| ID | book_id | int | NO | PRIMARY KEY<br>AUTO INCREMENT | 
| 書籍名 | name | VARCHAR(1000) | NO |
| 発売日  | release_date | DATE | NO | yyyy-MM-dd 形式 | 
| 購入状況 | is_purchased | TINYINT(1) | NO | DEFAULT 0 | 
| カテゴリーID | category_id | int | NO | FOREIGN KEY<br>DELETE NO ACTION<br>UPDATE CASCADE | 

**categories**

| カラム名（論理名） | カラム名（物理名） | 型（桁）    | Nullable | その他 | 
| :----------------: | :----------------: | :---------: | :------: | :----: | 
| ID                 | category_id        | int         | NO       | PRIMARY KEY       | 
| カテゴリー         | category           | VARCHAR(20) | NO       |        | 

## API仕様書

[swaggerによるAPI仕様書](https://kawara777.github.io/book-application/docs/dist/index.html)

# ローカルでのアプリケーション起動方法

1. git、Java、Dockerをインストールする（OSによってインストール方法が異なります）
2. ターミナルを開く
3. 下記コマンドでこのリポジトリをgit clone（コピー）する
```text
git clone https://github.com/kawara777/book-application.git
``` 
4. クローンしたディレクトリに移動する
5. 下記コマンドでdockerを起動
```text
docker compose up
```
もしくは
```text
docker compose up -d
```
6. 下記コマンドでSpring Bootを起動
```text
./gradlew bootRun
```
7. ブラウザやcurlなどでリクエストを送る（API仕様書参考）

# 自動テスト

以下のテストコードを実施
- DBテスト
  - BookMapper
- 単体テスト
  - BookService
  - BookCreateRequest
  - BookUpdateRequest
- 結合テスト
  - BookController

<img width="576" alt="スクリーンショット 2024-04-21 17 49 28" src="https://github.com/kawara777/book-application/assets/138858245/0dd1fcb1-a116-43f6-9e16-524195fc9502">

# 今度の展望

- 追加機能の実装 ([issues](https://github.com/kawara777/book-application/issues))
- デプロイ（AWS）
- Spring Securityの導入
- CDの導入
- フロントエンドの導入
