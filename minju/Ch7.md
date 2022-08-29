# 07. AWS에 데이터베이스 환경을 만들어보자 - AWS RDS
## RDS
> - AWS에서 제공하는 모니터링, 알람, 백업, HA 구성 등을 지원하는 관리형 서비스
> - AWS에서 지원하는 클라우드 기반 관계형 데이터베이스
> - 조정 가능한 용량 지원

### 데이터베이스 비교
- MySQL
    - 단순 쿼리 처리 성능이 뛰어남
    - 이미 오래 사용되어 성능과 신뢰성 우수
- MariaDB
    - MySQL 기반
    - 동일 하드웨어 사양으로 보다 향상된 성능
    - 좀 더 활성화된 커뮤니티
    - 다양한 기능
    - 다양한 스토리지 엔진
### 데이터베이스 세부 사항
- Character Set
    - utf8 : 이모지 저장 불가
    - utf8mb4 : 이모지 저장 가능 -> 보편적 사용
    - MariaDB에서 character_set_database, collation_connection에서 적용하는 방법
        ```
      ALTER DATABASE 데이터베이스명
      CHARACTER SET = 'utf8mb4'
      COLLATE = 'utf8mb4_general_ci';
      ```