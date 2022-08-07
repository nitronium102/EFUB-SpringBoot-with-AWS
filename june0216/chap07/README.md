# Chap 07. AWS RDS

데이터베이스를 구축, 쿼리 튜닝에 대해서 기본적인 지식이 필요

# 7.1 RDS 인스턴스 생성하기

- RDS 대시보드 → 데이터 베이스 생성 버튼 클릭
- 엔진 옵션

  Maria DB 선택

  가격, Amazon Aurora 교체 용이성

  Amazon Aurora = AWS에서 MySQL과 postgreSQL을 클라우드 기반에 맞게 재구성한 데이터베이스

  AWS에서 직접 엔지니어링하고 있다. 다양한 기능 제공

  클라우드 서비스에 가장 적합한 데이터베이스이기 때문에 많은 회사가 Amazon Aurora를 선택

    - Maria DB는 MySQL대비 다양한 기능, 성능 향상 등의 장점으로 추천
- 템플릿

  프리티어

- 설정

  할당 스토리지 20

  본인만의 DB 인스턴스 이름과 사용자 정보 등록

- 연결

  퍼블릭 엑세스 가능 → yes (이후 보안 그룹에서 지정된 IP 만 접근 가능하도록 막을 예정)

- 데이터베이스 옵션
- 완료

# 7.2 RDS 운영 환경에 맞는 파라미터 설정하기

RDS를 처음 생성하면 몇 가지 설정을 필수로 해야 한다.

-타임존

-Character Set

-Max Connection

- 파라미터 그룹 → 파라미터 그룹 생성 → 파라미터 그룹 세부 정보
    - 방금 생성한 MariaDB와 같은 버전을 맞춰야 한다.
- 생성 완료 되면 파라미터 글부 목록 창에 새로 생성된 그룹을 볼 수 있다.
- 파라미터 편집→ 편집모드 → 설정값 변경시작

1) time_zone을 검색 → Asia/Seoul 선택

2) Character Set 변경

charactrer 항목들은 모두 utf8mb4로

collation 항목들은 모두 utf8mb4로  변경

→ utf8mb4와 utf8의 차이는 이모지 저장 가능 여부 차이

3) Max Connection

RDS의 Max Connection은 인스턴스 사양에 따라 자동으로 정해짐

- 변경 사항 저장

- 이렇게 생성된 파라미터 그룹을 데이터 베이스에 연결

(데이터베이스 옵션에서 DB 파라미터 그룹 “default” →  방금 생성한 신규 파라미터로 변경

- 저장 → 즉시 적용
- 재부팅

# 내 PC에서 RDS에 접속해 보기

로컬에서 RDS로 접근하기 위해 RDS의 보안 그룹에 본인 PC의 IP를 추가

- RDS의 세부 정보 페이지 → [보안 그룹] 클릭
- 브라우저를 새로 연다 → 새로운 창에서 보안 그룹 목록 중 EC2에 사용된 보안 그룹의 그룹 ID를 복사
- 복사된 보안 그룹 ID와 본인의 IP를 “RDS 보안 그룹 인바운드”로 추가

  보안 그룹 첫 번째 줄 : 현재 내 PC의 IP를 등록

  ⇒ 이렇게 하면 RDS와 개인 PC 간 연동 가능

  보안 그룹 두 번째 줄 : EC2의 보안 그룹을 추가

  ⇒ 이렇게 하면 EC2와 RDS 간에 접근이 가능하다.


## DataBase 플러그인 설치

로컬에서 원격 데이터베이스로 붙을 때 GUI 클라이언트를 많이 사용함

다양한 방법이 있지만 인텔리제이에 DataBase플러그인을 설치해서 진행

- database 플러그인 검색 → 설치 → 재시작 → Action 검색으로 Database Browser 실행
- +버튼 → MySQL 접속 정보를 열어본다 (MariaDB는 MySQL 기반이므로 MySQL 사용해도 된다)
- 본인이 생성한 RDS 정보를 차레대로 등록한다.
- 마스터 계정명과 비밀번호를 등록한 뒤, 화면 아래의 Test Connection을 클릭해 연결 테스트를 해본다.
- Connection Successful 메시지를 보았다면 Apply→ OK 버튼을 차례대로 눌러 최종 저장한다.
- 이후 쿼리문을 직접 작성하여 실행 가능

데이터베이스가 선택된 상태에서 현재의 character_set, collection 설정을 확인한다.

“show variables like ‘%c’; “

쿼리 결과를 보면 다른 필드들은 모두 utf8mb4가 잘 적용되었는데 character_set_database, collaction_connection 2가지 항목이 latin1로 되어 있다.

- 이 2개의 항목이 Maria DB에서만 RDS 파라미터 그룹으로는 변경이 안되기 때문에 직접 변경해야 한다.

다음의 쿼리를 실행하자

ALTER DATABASE 데이터베이스명

CHARACTER SET = ‘utf8mb4’

COLLATE = ‘utf8mb4_general_ci’ ;

쿼리 수행 후 다시 한 번 character set을 확인 → 성공적으로 모든 항목이 utf8mb4로 변경된 것을 확인

- 타임존 확인

select @@time_zone, now();

- 한글명이 잘 들어가는지 간단한 테이블 생성과 insert 쿼리 실행

  테이블 생성은 인코딩 설정 변경 전에 생성되면 안 된다. 만들어질 당시의 설정값을 그대로 유지하고 있어, 자동 변경이 되지 않고 강제로 변경해야만 한다.

  CREATE TABLE test(

  id bigint(20) NOT NULL AUTO_INCREMENT,

  content varchar(255) DEFAULT NULL,

  PRIMARY KEY(id)

  ) ENGINE=InnoDB;

  insert into test(content) values (’테스트');

  select * from test;


# 7.4 EC2에서 RDS에서 접근 확인

- EC2에 ssh 접속을 진행
- RDS 접근 명령어 입력
    - mysql -u [계정] -p -h [Host 주소]
    - 패스워드 입력
- 실제로 생성한 RDS가 맞는지 간단한 쿼리를 실행
    - show database;