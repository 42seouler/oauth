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
