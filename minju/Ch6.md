# 06. AWS 서버 환경을 만들어보자 - AWS EC2
## 클라우드
> 인터넷(클라우드)을 통해 서버, 스토리지, 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것
> - AWS EC2의 경우, 서버 장비 내부의 로그 관리, 모니터링, 하드웨어 교체, 네트웨크 관리 등을 기본적으로 제공
### 형태
- Infrastructure as a Service(IaaS, 아이아스, 이에스)
    - 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스
    - 가상 머신, 스토리지, 네트워크, 운영체제 등의 **IT 인프라** 대여
    - ex) AWS EC2, S3 등
- Platform as a Service(PaaS, 파스)
    - IaaS를 한번 더 추상화한 서비스 -> 많은 기능이 자동화
    - ex) AWS Beanstalk, Heroku 등
- Software as a Service(SaaS, 사스)
    - 소프트웨어 서비스
    - ex) 구글 드라이브, 드랍박스 등
## EC2 인스턴스 생성
### AMI(아마존 머신 이미지)
> EC2 인스턴스를 시작하는 데 필요한 정보를 **이미지로 만들어 둔 것**
> 인스턴스라는 **가상 머신에 운영체제 등을 설치할 수 있게** 구워 넣은 이미지

ex) Amazon Linux AMI
### T 시리즈
- 범용 시리즈 -> 다양한 사양을 사용할 수 있음
- 인스턴스 크기에 따라 정해진 비율로 CPU 크레딧을 계속 받게 됨
- 사용하지 않을 때는 크레딧을 축적하고 사용할 때 축적해둔 크레딧까지 사용.
### 스토리지
- 서버의 용량
### 보안그룹
- 방화벽
- 22 : 지정된 IP에서만 ssh가 접속 가능하도록 하는 것이 바람직.
## 아마존 리눅스1 서버 생성 시 해야할 설정
- Java 설치 : 프로젝트에 사용한 자바의 버전에 맞게 설치
  - Java설치
    ```
    sudo yum install -y java-1.9.0-openjdk-devel.x86_64
    ```
  - 버전 8으로 변경
      ```
      sudo /usr/sbin/alternatives --config java
      ```
  - 사용하지 않는 java7 삭제
      ```
      sudo yum remove java-1.7.0-openjdk
      ```
  - 설치된 자바 버전 확인
    ```
    java -version
    ```
- 타임존 변경 : 기본 미국 시간대이기 때문에 한국 시간대로 변경
  ```
  sudo rm /etc/localtime
  sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
  ```
- 호스트네임 변경 : 현재 접속한 서버의 별명을 등록
    ```
    sudo vim /etc/sysconfig/network
    ```
  - 서버 리부팅
    ```
    sudo reboot
    ```
  - hostname 등록
    ```
    sudo vim /etc/hosts
    ```
    ```
    127.0.0.1 등록한 HOSTNAME
    ```
    - 확인
    ```
    curl 등록한 HOSTNAME
    ```