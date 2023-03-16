# 🐈‍⬛ Github Repository 조회 앱
<img src="https://user-images.githubusercontent.com/79048895/225524486-08e22094-0195-41b1-a7da-f9b7ef8112d2.gif" width="324" height="702" />

- `Retrofit`를 통해 네트워크 통신 구현
- **Github Open API** 사용
- `RecyclerView`와 `ListAdapter`를 통해 리스트 형식의 UI 구현
- `Handler`를 이용해 **debounce** 구현 (+ `Coroutine-Flow`)
<br>

## 구현 기능
1. Github 아이디 검색
    - 입력마다 API 호출 (일정 시간마다 최신 작업을 수행하는 **debounce** 이용)
2. 검색한 아이디에 해당하는 Repository 리스트 가져오기
3. 특정 Repository 선택 시, 브라우저로 해당 페이지 띄워주기
<br>

## Retrofit
- `OKHttp`를 네트워크 Layer로 활용하는 상위호환 라이브러리
- 네트워크 통신을 편하게 할 수 있도록 도와주는 기능이 많이 있음.
<br>

## Github Open REST API
Github에서 제공하는 API를 자유롭게 활용할 수 있다.
```
API : 애플리케이션 프로그래밍 인터페이스
REST : Representational State Transfer, 일종의 네트워크에서 통신을 관리하기 위한 아키텍쳐
```
<br>

## Retrofit에서 API에 공통되는 Header 설정
- `Retrofit`은 기본적으로 `OkHttp`를 네트워크 Layer로 이용하고 있으므로, `OkHttpClient`를 이용함.
- `OkHttpClient`에 `Interceptor`를 정의하여 Header를 설정한 새로운 request를 보냄.
```Kotlin
object ApiClient {

    private const val BASE_URL = "https://api.github.com/"

    private fun getOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AppInterceptor())
        .build()

    fun getRetrofit() = Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
```
```Kotlin
class AppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_API_ACCESS_TOKEN}")
            .build()
        proceed(newRequest)
    }
}
```
<br>

## RecyclerView에서의 Paging 처리
- `Paging3`와 같은 좋은 Paging 라이브러리가 있지만, 라이브러리 없이 `RecyclerView`의 스크롤 이벤트를 통해 구현할 수 있음.
- `LinearLayoutManager`는 `RecyclerView`에서 현재 보이고 있는 마지막 아이템의 위치를 알고 있음.  
  → `findLastCompletelyVisibleItemPosition()`
- 위의 위치값과 전체 아이템의 개수를 이용한 마지막 위치의 값을 비교하여 마지막 아이템까지 스크롤이 이뤄졌는지 알 수 있음.
```Kotlin
private fun initViews() {
    with(binding) {
        //.. 

        rvRepo.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(this@RepoActivity)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition = (layoutManager as LinearLayoutManager).itemCount - 1
                    val lastVisiblePosition =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (lastPosition <= lastVisiblePosition) {
                        callRepoList(userName, ++reposPage) // page 값을 증가시킨 새로운 API 호출
                    }
                }
            })
        }
    }
}
```
