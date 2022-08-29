Chap.08 EC2 서버에 프로젝트를 배포해보자

# 8.1 EC2에 프로젝트 clone 받기

깃허브에서 코드를 받아올 수 있게 EC2에 깃을 설치한다.

EC2로 접속해서 다음과 같이 깃 설치 명령어를 입력한다.

```java
sudo yum install git 
```

깃이 성공적으로 설치되면 git clone으로 프로젝트를 저장할 디렉토리를 생성한다.

```java
mkdir ~/app && midir ~/app/step1
```

생성된 디렉토리로 이동한다.

```java
cd ~/app/step1
```

깃허브 웹페이지에서 http 주소를 복사한다.

```java
git clone [복사한 주소]
```

git clone이 끝났으면 클론된 프로젝트로 이동해서 파일들이 잘 복사되었는지 확인

```java
cd [프로젝트명]
ll
```

코드들이 잘 수행되는지 테스트로 검증

```java
./gradlew test
```

→ 정상적 테스트 통과

만약 gradlew 실행 권한이 없다는 메시지가 뜨면 다음과 같이 추가한 뒤 테스트를 수행하면 된다.

```java
chmod +x ./gradlew
```

지금까지는 깃을 통해 프로젝트를 클론하고 풀한 과정이다.

참고로 현재 EC2엔 gradle이 설치되어 있지 않지만 프로젝트 내부에 gradlew 파일이 있기 때문에 Gradle Task(test)를 진행할 수 있었다.

# 8.2 배포 스크립트 만들기

작성한 코드를 실제 서버에 반영하는 것을 “배포”라고 한다.

이 책에서 배포라는 것은 다음과 같은 과정을 모두 포괄하는 것이다.

- git clone 혹은 git pull을 통해 새 버전의 프로젝트를 받음
- Gradle 이나 Maven을 통해 프로젝트 테스트 빌드
- EC2 서버에서 해당 프로젝트 실행 및 재실행

앞선 과정을 배포할 때마다 개발자가 하나하나 명령어를 실행하는 것은 불편함이 많다.

→ 그래서 이를 쉘 스크립트로 작성해 스크립트만 실행하면 앞의 과정이 차례로 진행되도록 할 수 있다.

(참고로 쉘 스크립트은 .sh 라는 파일 확장자를 가진 파일이다. 리눅스에서 기본적으로 사용할 수 있는 스크립트 파일의 한 종류이다. VIM은 GUI 환경이 아닌 환경에서 사용할 수 있는 편집 도구이다. )

- [deploy.sh](http://deploy.sh) 파일 생성

```java
vim ~/app/step1/deploy.sh
```

```bash
#!/bin/bash 

REPOSITORY=/home/ec2-user/app/step1 #프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하기 때문에 변수로 저장
PROJECT_NAME=june-springboot2-webservice

cd $REPOSITORY/$PROJECT_NAME/ #제일 처음 git clone 받았던 디렉토리로 이동

echo $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull" # 디렉토리 이동 후 master 브랜치의 최신 내용을 받는다. 

git pull

echo "> 프로젝트 Build 시작"

./gradlew build # 프로젝트 내부의 gradlew로 build를 수행한다. 

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사 "

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY
#build의 결과물인 jar파일을 복사해 jar 파일을 모아둔 위치로 복사한다. 

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=${pgrep -f ${PROJECT_NAME}.*jar) # 기존에 수행중이던 스프링부트 애플리케이션을 종료 

echo "현재 구동 중인 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동 중인 애플리케이션이 없으므로 종료되지 않았습니다."

else
	echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다"

else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
#새로 실행할 jar 파일명을 찾습니다. 
#여러 jar 파일이 생기기 때문에 tail -n로 가장 나중의 jar 파일을 변수에 저장한다. 

nohup java -jar $REPOSITORY/$JAR_NAME 2>& 1 &
#찾은 jar 파일명으로 해당 jar 파일을 nohup로 실행한다.
#스프링 부트의 장점으로 특별히 외장 톰캣을 설치할 필요가 없다. 
#내장 톰캣을 이용해서 jar 파일만 있으면 바로 웹 애플리케이션 서버를 실행할 수 있다. 
#java -jar 은 사용자가 터미널 접속을 끊을 때 애플리케이션도 같이 종료되므로 nohup 명령어를 사용

```

이렇게 생성한 스크립트에 다음과 같이 권한을 추가한다.

```bash
chmod +x ./deploy.sh
```

스크립트를 실행

```bash
./deploy.sh
```

로그가 출력되며 애플리케이션이 실행된다.

nohup.out 파일을 열어 로그를 보기

```bash
vim nohup.out
```

만약 ClientRegistrationRepository를 찾을 수 없다고 에러가 발생한다면 애플리케이션 실행에 실패한 것이다.

지금까지 과정만 실행했다면 저렇게 되어 있을 것이다. 그 이유는 아래에 소개할 것이다.

# 8.3 외부 Security 파일 등록하기

이유는 ClientRegistrationRepository를 생성하려면 clientId와 clientSecret가 필수이다. 로컬 PC에서 실행할 때는 [application-oauth.properties](http://application-oauth.properties) 가 있어서 문제가 없었는데 이 파일은 .gitignore로 git에서 제외 대상이라 깃허브에는 올라가있지 않은 상태이기 때문이다.

서버에 이 설정들을 가지고 있게 하면 된다.

- app 디렉토리에 properties 파일을 생성한다

```bash
vim /home/ec2-user/app/application-oauth/properties
```

이 파일에 로컬에 있는 [application-oauth.properties](http://application-oauth.properties) 파일 내용을 그대로 붙여넣기를 하고 저장한다.

- 방금 생성한 파일을 쓸 수 있도록 [deploy.sh](http://deploy.sh) 파일을 수정한다.

```bash
...

nohup java -jar \
	-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app
/application-oauth.properties \ 
	$REPOSITORY/$JAR_NAME 2>&1 &
#Dspring.config.location = 스프링 설정 파일 위치 지정 
#classpath = jar안에 있는 resources 디렉토리를 기준으로 경로가 생성된다. 
```

# 8.4 스프링 부트 프로젝트로 RDS 접근

MariaDB에서 스프링부트 프로젝트를 실행하기 위해서 몇 가지 작업이 필요하다.

- 테이블 생성 = 자동이 아닌 직접 쿼리를 이용하여 생성해야 한다.
- 프로젝트 설정 = 자바 프로젝트가 MariaDB에 접근하려면 데이터베이스 드라이버가 필요하다. 이를 프로젝트에 추가한다.
- EC2 설정 = 데이터베이스 접속 정보는 보호해야하므로 EC2 서버 내부에서 접속 정보를 관리하도록 설정한다.

### RDS 테이블 생성

엔티티 테이블과 스프링 세션이 사용될 테이블 2가지 종류를 생성한다.

1. RDS 테이블 생성

아래의 내용을 RDS 에 반영한다.

```xml
create table posts (id bigint not null auto_increment, created_date datetime,
modified_date datetime, author varchar(255), content TEXT not null, title varchar(500)
not null, primary key (id)) engine=InnoDB

Hibernate: create table user (id bigint not null auto_increment, created_date datetime,
modified_date datetime, email varchar(255) not null, name varchar(255) not null,
picture varchar(255), role varchar(255) not null, primary key (id)) engine=InnoDB
```

1. 스프링 세션 테이블은 schema-mysql.sql 파일 이용

(파일 검색)ctrl+shift+n -> schema-mysql.sql 검색

여기서 나온 테이블 역시 복사해 RDS에 반영

### 프로젝트 설정

먼저 MariaDB 드라이버를 build.gradle에 등록한다.

`testImplementation(”org.mariadb.jdbc:mariadb-java-client”)`

그리고 서버에서 구동될 환경을 하나 구성한다.

src/main/resources/에 [application-real.properties](http://application-real.properties) 파일을 추가한다.

앞에서 이야기한 대로 application-real.properties로 파일을 만들면 profile=real인 환경이 구성된다.

실제 운영될 환경이기 때문에 보안, 로그 상 이슈가 될 만한 설정들을 모두 제거하며 RDS 환경 profile 설정이 추가된다.

```bash
spring.profiles.include=oauth,real-db
spring.jpa.properties.hibernate.dialect=org.hibernate.spring.session.store-type=jdbc
```

- 모든 설정이 되었다면 깃허브로 푸쉬한다.

### EC2 설정

OAuth와 마찬가지로 RDS 접속 정보도 보호해야 할 정보이니 EC2 서버에 직접 설정 파일을 만들어둔다.

app 디렉토리에 [application-real-db.properties](http://application-real-db.properties) 파일을 생성한다.

```bash
vim ~/app/application-real-db.properties
```

```bash
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://rds주소:포트명(기본은 3306)/database 이름
spring.datasource.username=db계정
spring.datasource.password=db계정 비밀번호 
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

```

[deploy.sh](http://deploy.sh) 가 real profile을 쓸 수 있도록 다음과 같이 개선

```bash
...

nohup java -jar \
	-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app
/application-oauth.properties, home/ec2-user/app
/application-real-db.properties, home/ec2-user/app
/application-real.properties \ 
	-Dspring.profiles.active=real \
	$REPOSITORY/$JAR_NAME 2>&1 &
```
