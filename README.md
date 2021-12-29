# 모의 면접 참패!!!

4월9일 금요일 오늘은 모의면접을 봤습니다. 결과는 참패!! 흑흑

# 클러스터를 놀러다녔더니..

![](https://images.velog.io/images/42seouler/post/792a51e2-ba78-43ef-810e-2eb692adc2c4/image.png)

하라는 공부는 안하고 맨날 즐겁게 클러스터를 뛰어다니니... 당연한 결과였습니다.
조금만 놀고 공부 할걸.... 뒤 늦은 후회!!!! 그럼 뭐해 다 끝났는데~~~~~~!!
42s_HoTechCourse로 항상 지도편달 해주시는 이호준멘토님께 석고대죄라도 올려야 할 판.....

# 면접에서 탈탈 털린 부분 채우기!! 

![](https://images.velog.io/images/42seouler/post/e511b19a-b2bf-4550-929e-ecb87871de69/32ebf89a259c8cfe5d729a00708c5fd8_a..gif)

목표 : JWT, Index, HTTP 1.0과 1.1의 차이 확인하기
방법 : Document, RFC과 서적을 통해서 자료 정리하기

# 1. JWT? 존맛탱? 이해하기 

## 그냥 JWT는 JWT라 생각 것이 온데....

![](https://images.velog.io/images/42seouler/post/0e54470e-ef42-4874-834e-a57e64612815/image.png)

알아보기 이전 저의 생각은 주변에서 주워 들은대로 JWT는 로그인하면 주는 토큰 같은 것이었습니다.
아마도 자세히 알아보지 않으면 저와 비슷하게 생각하고 있지 않을까 생각하며 정리해보기로 했습니다.

**JWT의 정의란?**

JWT(JSON WEB TOKEN)는 당사자간에 정보를 JSON객체로 안전하게 전송하기 위한 개방형 표준이라고 합니다.
> JWT 공식 사이트에서 나오는 문장이지 간과하기 쉬운 그러나 사실 몰라도 상관없지만.....
**Although JWTs can be encrypted to also provide secrecy between parties, we will focus on signed tokens.**

<span style="color:red">이 부분을 놓치기 쉬운데 RFC 문서보면 JWT는 권한, claims을 JWS, JWE를 통해 인코딩 한 JSON 객체라는 것 입니다.</span>
JWS(JSON Web Signature)와 JWE(JSON Web Encryption)로 서명과 암호화 한 것이 JWT라는 것이다.

## 그래도 잘 모르시겠다구요?

![](https://images.velog.io/images/42seouler/post/6677ae8d-43e5-4ea7-8920-c6a734dd2f55/image.png)

이해하기 쉽게 설명하자면 JWT는 추상화 클래스라 할 수 있고, JWS와 JWE는 추상화 클래스를 마저 구현한 콘크리트 클래스라고 할 수 있습니다. 내가 사용 하는 JWT는 콘트리트 타입의 인스턴트라고 생각하시면 됩니다!
결론적으로 Header.Payload.Signature은 우리가 사용하는 JWS Compact Serialization 직렬화한 문자열이다.

## 그래도 어렵죠? 좀더 자세히 들어가보죠!

![](https://images.velog.io/images/42seouler/post/b03e8acf-b9c1-4572-a33b-16ea4589af2d/image.png)

JWS Compact 직렬화에 대한 자세한 정리

1. JOSE Header(JSON Object Signing and Encryption) Header
- 토큰 발급자는 토큰 유형과 서명 알고리즘을 작성해야 합니다.
- 꼭 UTF-8로 인코딩되어야 합니다. JSON 기본 인코딩이 UTF-8이기 때문입니다.
2. JWS Payload
- 페이로드는 JSON이지 않아도 됩니다 하지만 통상적으로 JSON을 사용 합니다.
3. JWS Signature
- JOSE 헤더에서 작성 된 alg에 따라 이전 단계에서 구성된 메시지로 서명 합니다.

# [JWT의 구조와 소개 - 벨로퍼트](https://velopert.com/2389)

JWT의 구조와 소개는 벨로퍼트님의 글로 대체하도록 하겠습니다.
구조와 소개는 벨로퍼트님의 블로그 글에 자세히 나와있기도 하고 공식 사이트글을 보더라도 자세히
알 수 있습니다. 전 그 이외에 RFC를 봐야 알 수 있는것들로 슥슥 채우겠습니다!!

## JWT공식 사이트에서 이상한 점 발견!?

![](https://images.velog.io/images/42seouler/post/792ad104-db47-4f21-bf03-358c441b3d61/image.png)

지금까지 열심히 JWT를 알아보고 적용하려고 했는데 갑자기 나오는 다른 이름의 토큰!!!!
Bearer 스키마를 사용하는 Authorization 헤더에 JWT를 전송해야 한다.
이 부분은 아래에서 Oauth 2.0을 파트에서 이야기 하도록 하겠습니다.

## 일반적인 JWT 사용자 인증 과정

![](https://images.velog.io/images/42seouler/post/708376ce-0b59-420c-8100-b0eba921a173/image.png)

일반적인 순서는 위와 같고 2번, 5번의 과정은 사용하는 프레임워크에 따라 다르게 구현 됩니다. 
<span style="color:red">토큰의 서명을 확인 하는것은 CPU 싸이클을 사용하며 IO 또는 네트워크 액세스가 필요하지 않습니다.</span>
이외에 JWT 토큰을 사용 했을 때 얻을 수 있는 이점과 단점은 다른 인터넷 자료들에 잘 나와있으므로 생략 하도록 하겠습니다.

💡 <span style="color:yellow; background-color:black">**토큰 자체에 사용자 인증에 필요한 모든 정보를 포함하고 있어 별도의 인증 저장소가 필요 없다.
헤더 + 페이로드 + 시크릿키를 조합해서 해시해서 검증하기 때문에 위변조시 토큰이 무효화 됩니다.
  다만 시크릿키가 노출 된다면 외부에서 토큰을 생성 할 수도 있습니다.
**</span>

> JWT를 이해하기 위한 꼭 읽어봐야 할 것들
[JWT 공식 사이트](https://jwt.io/introduction)
[JWT RFC7519](https://tools.ietf.org/html/rfc7519)
[JWS RFC7515](https://tools.ietf.org/html/rfc7515)
[NHN Cloud - JWT를 소개합니다](https://meetup.toast.com/posts/239)
[JWT 자바 가이드](https://medium.com/@OutOfBedlam/jwt-%EC%9E%90%EB%B0%94-%EA%B0%80%EC%9D%B4%EB%93%9C-53ccd7b2ba10)
[bearer Token RFC6750](https://tools.ietf.org/html/rfc6750)

## Oauth 2.0 

## Oauth란

![](https://images.velog.io/images/42seouler/post/903ee524-a482-4351-aa60-5b2d8b525140/image.png)

OAuth는 클라이언트가 리소스 소유자를 대신하여 보호 된 리소스에 액세스 할 수있는 방법을 제공합니다.
일반적으로 클라이언트가 보호 된 리소스에 액세스하려면 먼저 리소스 소유자로부터 권한 부여를 받은 다음 권한 부여를 액세스 토큰으로 교환해야합니다.
그리고 클라이언트는 리소스 서버에 액세스 토큰을 제공하여 보호 된 리소스에 액세스합니다.

💡 <span style="color:yellow; background-color:black">**자원의 소유자인 이용자를 대신하여 서비스를 요청할 수 있도록 자원 접근 권한을 위임하는 방법입니다**.</span>

**Bearer Token RFC를 보면!**

![](https://images.velog.io/images/42seouler/post/1ca7bd15-838b-4dfe-98f3-41f2c9bd937a/image.png)

결론적으로 우리는 OAuth 2.0 프레임워크를 사용하고 토큰의 유형으로 Bearer Token을 사용하는 것입니다.
그리고 Bearer Token이 우리가 사용하는 JWT이기도 합니다.

![](https://images.velog.io/images/42seouler/post/ba420f6f-663d-4804-8031-b17ca9412af2/image.png)

💡 <span style="color:yellow; background-color:black">**JWS를 쓰는 이유는 TLS를 통해 전송 간 암호화를 사용해 JWE를 사용하지 않아도 되는 이유이다.
즉 비밀을 준수 해야 하는 경우 내용을 암호화 하는게 아니라 전송 계층을 암호화 된다는 것이다.**</span>

## OAuth 2.0의 역할과 설명

![](https://images.velog.io/images/42seouler/post/b45dcc9d-0719-4b01-a75b-95309a457ecf/image.png)

💡 <span style="color:yellow; background-color:black">**각각의 역할을 이해하지 못하고 토큰으로 인증과 권한을 부여 받는다고 생각하면 헷갈릴 수 있으니 꼭 숙지하고 아래의 흐름을 봐주세요!**.</span>

![](https://images.velog.io/images/42seouler/post/ceeaa1b9-4ea4-421b-98ac-f61d1b7e6978/image.png)

OAuth 2.0의 권한 승인 과정은 6단계로 간략하게 요략 할 수 있습니다.

1. 자원에 대한 권한 요청
- 자원 소유자에게 직접 요청
- 권한 서버를 중개자로 간접 요청 (권장하는 방식 아래에서 설명 하겠음)

2. 클라이언트에게 자원에 대한 접근 권한을 승인(4가지 권한 승인 방법이 있음)

3. 클라이언트가 권한 서버에게 접근 토큰을 요청

4. 권한 서버는 클라이언트를 인증 및 부여 받은 권한을 검증하고, 검증 된 경우 클라이언트에게 접근 토큰 발행

5. 클라이언트가 자원 서버에게 접근 토큰으로 인증 및 요청

6. 자원 서버는 접근 토큰을 검증하고, 검증 된 경우 서비스 제공

## 네가지 방법 중 추천하는 방법

권한 코드 승인으로 진행하는 방법에 대해 알아보겠습니다.
왜냐하면 저희가 사용하는 OAuth방식이 보통 권한 코드 승인 방법을 따르기 때문입니다.
기타 권한 승인 방식의 세부 단계는 RFC-6749 - The OAuth 2.0 Authorization Framework 참조하세요!

![](https://images.velog.io/images/42seouler/post/1be5597c-9acf-4404-b1a7-7e41f822a1d4/image.png)

1. 클라이언트가 웹 브라우저에게 리다이렉트 URI를 전달하여 권한 서버의 인증 및 권한 확인 웹 페이지로 리다이렉션 시킴
- 권한 서버가 클라이언트를 식별할 수 있도록 client_id를 함께 전달

![](https://images.velog.io/images/42seouler/post/b68e5b0a-55de-4974-b630-bbdd76e27736/image.png)

2. 권한 서버의 자원 소유자에 대한 인증 방식을 통해 인증 및 자원에 대한 접근 권한 승인 요청
- 일반적인 웹 서비스에서는 ID/PASSWORD 방식을 통해 자원 소유자 인증

![](https://images.velog.io/images/42seouler/post/d48ee834-ec9f-4abd-af41-a5c2bbbcc279/image.png)

3. 권한 서버는 웹 브라우저를 다시 클라이언트에게로 리다이렉션 시키며, 이때 자원 소유자로부터 자원에 대한 접근 권한을 승인 받았음을 나타내는 권한 코드를 클라이언트에게 전달
- 클라이언트 등록 시, 권한 서버에 제출한 URI, 또는 1 단계에서 전달한 리다이렉트 URI 사용

4. 클라이언트는 접근 토큰을 요청하기 위해, 권한 코드와 함께 클라이언트 인증을 위한 client_id 및 client_secret을 권한 서버에 전달

5. 권한 서버는 접근 토큰을 클라이언트에게 반환
- 접근 토큰에 유효 기간이 존재하는 경우, 기간 만료까지 남은 시간 (expires_in)과 재발급 토큰(refresh_token) 정보도 함께 반환

**클라이언트 유형**
- 서버 웹 애플리케이션
- 네이티브 애플리케이션(백엔드 서버 사용)
**장기적 접근**
- 재발급 토큰을 통해 지원
**보안성**
- 이용자 웹 브라우저를 통해 접근 토큰이 노출되지 않도록 권한 코드 사용
- 클라이언트는 권한 코드를 사용하여 권한 서버에게 직접 접근 토큰을 요청
- 중요 자격증명 정보가 서버에 저장되기 때문에 다른 권한 승인 방법에 비해 안전함

## 나머지 정보는 인터넷을 통해!

![](https://images.velog.io/images/42seouler/post/48013bcd-9606-4e2b-a9a5-25a6fa3f9a8f/image.png)

OAuth 1.0 과 2.0의 큰 차이는 https가 기본 이라는 것 입니다.
그리고 그 외 정보들은 인터넷에 잘 정리되어 있어서 궁금하시면 찾아 보시면 될 것 같습니다.


## 로그인은 무엇인가?

![](https://images.velog.io/images/42seouler/post/4adb8b6b-bd27-4c2f-ba2c-c2d3a9b56b60/image.png)

로그인은 클라이언트가 접근허가 증명을 위해 자기 자신을 증명하는 것입니다.

## 로그인은 어떻게 할 수 있을까?

** HTTP는 Connectionless, Stateless인데 **
- 비연결성이란 클라이언트가 요청을 하고 서버가 해당 요청에 응답을 하게 되면 연결이 끊어집니다.
이 특징으로 상태가 없게 됩니다. 그래서 클라이언트가 동일한 요청을 보내더라도 인증을 계속 해야 합니다.

![](https://images.velog.io/images/42seouler/post/5d82ee45-a4c8-4d4e-be17-0c70cbdbcc08/Untitled%20Diagram.png)

평소에 생각하던 로그인과 정보 요청 방법이 아닌데 어떻게 가능 한지 궁금했습니다.

## 서버 기반(Session) / Token 인증이있다!

Session, Token인증에 관해 자세한 설명은 인터넷에 워낙 자료 정리가 잘되어있으니까요!

![](https://images.velog.io/images/42seouler/post/d80a62b8-0875-4c78-8e98-a17f07f60904/image.png)

** 내가 Token 인증을 선택한 이유 **
- 세션은 데이터를 저장하지 않습니다. 그래서 정보를 꺼내기 위해 매번 데이터베이스를 조회해야한다.
- Oauth로의 확장을 위해서입니다 (예를 들어 구글로그인, 페이스북 로그인 등)
- 쿠키는 발행한 서버에서만 유용하지만 토큰은 html body의 형태로 전달하기 때문에 다른 도메인에서도 사용 할 수 있다.

**JWT 인증 흐름**

![](https://images.velog.io/images/42seouler/post/b4f31990-6660-457d-b7a1-46fb79c81d20/Untitled%20Diagram%20(1).png)
- 엑세스 토큰을 얻기 위해 클라이언트는 요청 본문에 아이디와 패스워드로 요청 합니다.
- 서버는 사용자의 아이디와 패스워드의 유효성을 검사 한 다음에 엑세스 토큰을 반환 합니다.
- 요청마다 헤더에 JWT 토큰을 함께 보냅니다.
- 유효성 검사 후 응답 합니다.

## 스프링에서 JWT를 사용하려면 Spring security 필수!

[스프링 시큐리티 Docs](https://docs.spring.io/spring-security/site/docs/5.4.5/reference/html5/#features) 블로그 많은 예제들이 있지만 제대로 사용하기 위해서는 공식문서를 정독하고 사용해야한다.

![](https://images.velog.io/images/42seouler/post/31a49a3f-4847-448d-9238-5446acdf834a/ezgif.com-gif-maker.gif)

Spring Security의 OAuth 로그인을 사용하면 Google로그인을 통해 타사 로그인을 수행 할 수 있습니다. OAuth의 로그인 흐름은 백엔드가 OAuth 서버에 인증코드를 요청을 보내는 것입니다. 그리고 OAuth서버가 프론트엔드 로그인화면으로 리디렉션합니다. 프론트엔드가 로그인 한 후 OAuth서버는 인증코드를 백엔드로 보냅니다. 마지막으로 백엔드는 OAuth 서버에 인증코드, 클라이언트 ID 및 클라이언트를 확인하는 요청을 보냅니다 OAuth서버가 성공적으로 인증되면 ID토큰과 엑세스 토큰을 반환합니다. 휴대폰 앱에서는 리디렉션은 적합하지 않다고 생각했습니다.
그래서 인증코드를 프론트엔드로 전송하는게 아니라 백엔드에서 전송 받아서 OAuth서버로 보내고
유저에게는 자체 발행한 JWT를 주도록 했습니다.

## 로그인을 위한 JSP 활용

![](https://images.velog.io/images/42seouler/post/9b7f1eff-bed4-4576-809f-b4df96192f83/image.png)

## 로그인 직후 JWT 토큰을 유저에게 발행

![](https://images.velog.io/images/42seouler/post/8f6e8321-409f-4e96-b0e4-0bba4181c867/image.png)

토큰 정보가 굉장히 긴 것을 알 수 있다.
이 토큰의 내용을 볼 수 있도록 별도로 버튼을 준비하였다.

## JWT 토큰에 포함 된 access, refresh 토큰

![](https://images.velog.io/images/42seouler/post/d8cbff61-60b5-4872-82a9-ca43f3d08326/image.png)

[OAuth access 토큰 만료 예시](https://cloud.google.com/apigee/docs/api-platform/antipatterns/oauth-long-expiration?hl=ko)
다음 OAuthV2 정책 예시는 200일이라는 긴 만료 시간이 지정된다고 한다.
그렇다면 내가 발행한 토큰도 이보다 짧은 만료시간을 두어 설정하게 된다면 access token
만료 이전에 나의 JWT토큰을 만료 시킬 수 있지 않을까란 생각으로 JWT로 랩핑하게 되었습니다.
