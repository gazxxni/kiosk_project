# Kiosk 프로젝트

이 프로젝트는 Kiosk 애플리케이션입니다. 이 README 파일은 데이터베이스 설정 및 PHP 서버 설정에 대한 지침을 제공합니다.

## 요구 사항

- MySQL
- PHP
- XAMPP (또는 다른 PHP 및 MySQL을 지원하는 서버 환경)
- Git

## 데이터베이스 설정

1. MySQL 서버를 시작합니다.
2. `kiosk_db_backup.sql` 파일을 사용하여 데이터베이스를 복원합니다. 다음 명령어를 사용하세요:
    ```bash
    mysql -u root -p kiosk_db < /path/to/kiosk_db_backup.sql
    ```
   - 위 명령어에서 `/path/to/kiosk_db_backup.sql`를 `kiosk_db_backup.sql` 파일의 실제 경로로 변경하세요.

## PHP 서버 설정

1. XAMPP를 설치하고 실행합니다.
2. XAMPP의 `htdocs` 디렉토리에 프로젝트의 PHP 파일을 복사합니다. 예를 들어, 다음과 같이 복사합니다:
    ```bash
    cp -r /path/to/project/php /path/to/xampp/htdocs/kiosk
    ```
   - 위 명령어에서 `/path/to/project/php`를 PHP 파일이 있는 실제 경로로, `/path/to/xampp`를 XAMPP가 설치된 실제 경로로 변경하세요.
3. XAMPP 제어판에서 Apache 서버를 시작합니다.

## 환경 변수 설정

1. 프로젝트 루트 디렉토리에 `.env` 파일을 생성하고 다음 내용을 추가합니다:
    ```env
    DB_SERVER=localhost
    DB_USERNAME=root
    DB_PASSWORD=your_password
    DB_NAME=kiosk_db
2. DB_PASSWORD를 실제 MySQL root 비밀번호로 변경하세요.

## PHP Dotenv 설치

1. PHP Dotenv 패키지를 설치하려면, Composer를 사용하여 설치합니다:
    cd /path/to/xampp/htdocs/kiosk
    composer require vlucas/phpdotenv
2. 위 명령어에서 /path/to/xampp/htdocs/kiosk를 PHP 파일이 있는 실제 경로로 변경하세요.

## PHP 파일 구성
- `get_items.php`: 아이템 데이터를 데이터베이스에서 가져오는 PHP 파일입니다.
- `get_promotions.php`: 프로모션 데이터를 데이터베이스에서 가져오는 PHP 파일입니다.
- `get_sales.php`: 매출 데이터를 데이터베이스에서 가져오는 PHP 파일입니다.
- `delete_item.php`: 아이템 데이터를 데이터베이스에서 삭제하는 PHP 파일입니다.
- `update_item.php`: 아이템 데이터를 데이터베이스에서 수정하는 PHP 파일입니다.
- `config.php`: 데이터베이스 연결 설정을 포함한 PHP 파일입니다.

## 프로젝트 구조
    project-root/
    ├── app/
    │   ├── src/
    │   ├── res/
    │   └── ... (Android 프로젝트 파일)
    ├── php/
    │   ├── img/
    │   │   ├── americano.jpg
    │   │   ├── vanilla_latte.jpg
    │   │   └── ...
    │   ├── get_items.php
    │   ├── get_promotions.php
    │   ├── get_sales.php
    │   ├── delete_item.php
    │   ├── update_item.php
    │   ├── config.php
    │   ├── .env
    │   └── ... (기타 PHP 파일)
    ├── backup/
    │   ├── kiosk_db_backup.sql
    ├── README.md
    ├── .gitignore
    └── ...
