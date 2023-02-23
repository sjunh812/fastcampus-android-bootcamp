# 🧮 계산기 앱
- `ConstraintLayout-Flow`를 이용하여 UI 구성
- 다크/라이트 모드에 따른 UI 그리기
- `BigDecimal`을 이용하여 매우 큰 연산에 대해 대응
- `DecimalFormat`을 이용하여 수를 표현
- `StringBuilder`를 이용하여 효율적인 문자열 처리
<br>

## 구현 기능
1. 자연수 범위에서 두 수간의 한 가지 연산을 수행
    - 연산 종류(`+`, `-`)
2. 다크/라이트 모드에 따라 UI 대응
3. `BigDecimal`을 사용하여 Primitive 타입보다 더 큰 범위까지 표현 가능
4. `DecimalFormat`을 이용해 3자리마다 `,` 표현 및 첫 자리 `0` 생략
5. 연산 시, 발생할 수 있는 여러 예외사항에 대한 처리 (`Toast` 이용)
<br>  

## 스크린샷 
<img src="https://user-images.githubusercontent.com/79048895/220875139-cbafa9a0-4985-48e6-9e37-fe4cb781c425.png" width="324" height="702" />  
<img src="https://user-images.githubusercontent.com/79048895/220875309-30db9c67-e908-47fb-83bb-792fa9ca47ee.png" width="324" height="702" />  
<img src="https://user-images.githubusercontent.com/79048895/220875499-6d970770-13d4-4010-a29e-a7cae77d9968.png" width="324" height="702" />
