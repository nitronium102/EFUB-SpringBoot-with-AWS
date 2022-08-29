# 08. EC2 서버에 프로젝트를 배포해 보자
## Step1. EC2에 프로젝트 Clone 받기
1. EC2에 깃 설치
```
sudo yum install git
```
```
git --version
```
2. git clone으로 프로젝트를 저장할 디렉토리 생성
```
mkdir ~/app && mkdir ~/app/step1
```
3. git clone 진행
```
cd ~/app/step1
git clone 본인의 깃헙 레포에서 복사한 주소
```
```
cd 프로젝트명
ll
```
4. 테스트 수행
```
./gradlew test
```
- gradlew 실행 권한 없다면
```
chmod +x ./gradlew
```
## Step2. 배포 스크립트 만들기
1. 배포 스크립트 생성
```
vim ~/app/step1/deploy.sh
```
2. 배포 스크립트에 코드 추가
3. 스크립트에 실행 권한 추가
```
chmod +x ./deploy.sh
```
4. 스크립트 실행
```
./deploy.sh
```
5. nohup으로 파일 열어 로그 확인
```
vim nohup.out
```
6. security 파일 등록하기
   (gitignore 파일 등록)
```
vim /home/ec2-user/app/application-oauth.properties
```
후 안에 내용 붙여넣기. 다시 deploy 파일 수행
## EC2에서 소셜로그인하기
구글 웹 콘솔과 네이버 개발자 센터에 접속해서 EC2 도메인을 등록해야 한다.
