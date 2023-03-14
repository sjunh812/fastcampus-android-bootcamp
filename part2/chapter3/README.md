# 📌 오늘의 공지 앱
<img src="https://user-images.githubusercontent.com/79048895/224906171-d5bfc543-ce3a-408b-b020-b00da6216940.gif" width="324" height="702" />

- `SocketServer`와 `Socket`으로 간단한 서버 및 클라이언트 구현
- `OkHttp`를 통해 데이터 가져오기
- `Gson`을 통해 **Json** 형식의 데이터 가져오기
<br>

## 구현 기능
1. 로컬 네트워크 상의 서버와 클라이언트 구현
    1. `Socket`을 이용하여 low level에서의 네트워크 통신 구현
    2. `OkHttp` 및 `Gson` 라이브러리를 사용해 간단하게 서버로부터 데이터 가져오기
2. 입력창에 연결할 서버 호스트를 입력하면, 서버로부터 가져온 데이터를 UI로 표현
<br>

## Socket
- 네트워크 상에서 데이터를 통신할 수 있도록 연결해주는 **End-point**
- 연결 요청, 연결 수락, connection, close 상태를 거침
- 실시간 데이터 통신, 양방향 데이터 통신
<br>

## HTTP / HTTPS 
- **Hyper Text Transfer Protocol (Secure)** 의 약자
- HTML 문서를 주고받기 위한 통신 규약
- 단방향 통신 (클라이언트의 요청, 서버의 응답 이후로 연결을 끊음)

### 요청 (Request)
**Request Line**, **Header**, **Body** 의 구성
- Request Line : 메소드 (GET / POST), version, URL 등의 정보
- Header : 기본 구조에 정의된 대로 서버에 전달하는 정보
- Body : 요청에 들어가는 데이터 (단순 데이터 요청 시, 사용하지 않음)

### 응답 (Response)
**Status Line**, **Header**, **Body** 의 구성
- Status Line : 상태 코드(200, 404..), version 등의 정보
- Header : 기본 구조에 정의된 대로 서버에 전달하는 정보
- Body : 실 데이터
<br>

## OkHttp
효율적으로 HTTP 통신을 할 수 있도록 도와주는 라이브러리
- 기본적으로 안드로이드 내부의 `URL` 클래스의 http 처리에도 `OkHttp`를 이용해서 구현되어 있음
- `enqueue()`, `execute()` 메소드를 이용해 각각 비동기, 동기적으로 구현이 가능
