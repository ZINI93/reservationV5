# 病院予約システム

**概要**

Spring Bootに基づく病院予約システムです。会員登録、ログイン、予約管理などの機能を提供し、Spring Securityを使用してセキュリティを強化しています。また、Thymeleafを使用してサーバーサイドレンダリングを実現し、QueryDSLを利用して柔軟な検索機能を提供します。

**主な機能:**

* 会員管理（登録、ログイン、情報変更）
* 予約管理（作成、確認、変更、キャンセル）
* 管理者機能（会員管理、予約管理）
* Spring Securityに基づく認証と権限管理
* Thymeleafを利用したサーバーサイドレンダリング
* QueryDSLを利用した動的クエリ

***

## 技術スタック

* **言語とフレームワーク:** Java、Spring Boot、Spring Data JPA、Spring Security、Thymeleaf、QueryDSL
* **データベース:** H2 Database
* **その他:** Lombok、Validation、IntelliJ IDEA

***

## システムアーキテクチャ

* **Controller:** HTTPリクエストを処理し、サービス層に委任
* **Service:** ビジネスロジックを処理
* **Repository:** データベースとの相互作用
* **Entity:** ドメインモデル

***

## エンドポイント

**USER**
* **ダッシュボード:** GET/POST /reservations/new
* **予約作成:** GET/POST /reservations/new
* **予約確認:** GET /reservations/list

**ADMIN**
* **会員登録:** GET/POST /members/new
* **会員リストの確認:** GET /admin/members-list
* **会員情報の編集:** GET/POST /admin/members/{id}/edit
* **今日の予約確認:** GET/admin/today-reservations
* **全予約確認:** GET /admin/reservation-list
* **予約の編集:** GET /admin/reservation/{id}/edit

***

## サーバーサイドレンダリング (SSR)

サーバーサイドレンダリングは、Spring Frameworkで推奨されているViewテンプレートエンジンであるThymeleafを使用して、バックエンドサーバーでHTMLを動的にレンダリングします。

***

## 会員登録およびログイン機能

会員登録およびログイン機能は、Spring Securityを使用して実装されています。

> **パスワードの暗号化**
> > Spring Securityで提供されるクラスを使用して、パスワードを安全に暗号化します。
>
> > SHA-1とランダムに生成されたソルトをサポートしており、同じパスワードでも毎回異なる暗号化文字列を生成します。

> **主なメソッド**
> > `encode()`
> >> パスワードを暗号化するメソッドです。
>
> >> SHA-1およびランダムに生成されたソルトをサポートし、同じパスワードでも異なる暗号化文字列を生成します。

## 注意事項

**管理者アカウントは病院から発行され、直接登録することはできません。**

**QueryDSLを使用して動的クエリを通じて検索機能を実装します。**
