# ๐ต ์์ ์ฌ์ ์ฑ
<img src="https://user-images.githubusercontent.com/79048895/222658184-f02e1c58-d424-499c-ba7b-37c4dffbb741.gif" width="324" height="702" />
<br>

- `MediaPlayer`๋ฅผ ์ด์ฉํ์ฌ ์์ ์ฌ์
- `Service`๋ฅผ ์ด์ฉํ์ฌ ์์ ์ฌ์
- `Notification`์ ์ด์ฉํ์ฌ ์์ ์ปจํธ๋กค๋ฌ ์ ๊ณต
- `BroadcastReceiver`๋ฅผ ์ด์ฉํ์ฌ `LOW_BATTERY` ์ด๋ฒคํธ ์์ 
<br>

## ๊ตฌํ ๊ธฐ๋ฅ
1. ๋ก์ปฌ์ ์ ์ฅ๋ ์์ ์ฌ์
    - `MediaPlayer`
    - `Serive`๋ฅผ ์ด์ฉํด ๋ค๋ฅธ ์ฑ์ด ์ผ์ ธ์์ด๋ ์ฌ์ ์ ์ง
    - `Notification`(์๋ฆผ์ฐฝ)์ผ๋ก ์์ ์ปจํธ๋กค โ `PendingIntent`
2. ๋ฐฐํฐ๋ฆฌ๊ฐ ๋ถ์กฑํด์ง๋ฉด ๋ฉ์ธ์ง ์ถ๋ ฅ
    - `BroadcastReceiver`๋ฅผ ์ด์ฉํด ์ด๋ฒคํธ ์์ 
<br> 
