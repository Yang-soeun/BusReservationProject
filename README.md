# BusReservationProject🚍
> 장애인들을 위한 저상버스 예약 시스템
> 사용자 페이지, 버스전용 페이지 두가지로 구성

#### 사용자 페이지
- 버스 번호별로 시간표 조회 기능
- 정류장 검색기능
- 예약기능

#### 버스전용 페이지
- 예약 현황
- 예약 완료처리 기능

### 기술 스택
MySQL, JPA, Spring, CSS

### DB 설계
![image](https://user-images.githubusercontent.com/87464750/210979733-092f71aa-c090-4b35-a082-2a3ccb29ac8a.png)

## 📑 사용자 페이지
### 💡 시간표 조회
![image](https://user-images.githubusercontent.com/87464750/211001367-140814d4-3f53-46e3-b007-e3e4e2abd1d0.png)

### 💡 정류장 검색 & 예약
- 가장 빨리 오는 버스만 예약 가능
- 탑승지와 하차지를 선택하면 탑승지 ~ 하차지까지의 정류장 예약 처리
- 종점은 탑승지로 예약 불가
<br>

![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/87464750/210999590-67620c42-780f-4ffe-a0ad-78aca6cb8e01.gif)

### 💡 예약이 있는 경우
- 예약이 있는 경우 예약 불가능
![image](https://user-images.githubusercontent.com/87464750/211000732-c401e57c-3ce6-4a8c-b96b-f078568c3992.png)


## 📑 버스전용 페이지
- 로그인된 버스 번호에 따라 해당 버스번호로 예약된 정보만 나타냄
- 예약된 탑승 정류장과 하자 정류장 정보가 나타남
- 하차 후 완료 버튼을 누르면 예약 완료 처리

![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/87464750/211000528-c6fa0833-41ad-4f1c-99de-178a22f1538e.gif)
