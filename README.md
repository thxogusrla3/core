# 좋은 객체지향 설계의 5가지 원칙(SOLID)

> * 좋은 소프트웨어란 변화에 대응을 잘하는 것이다. SOLID 객체지향을 적용하면 코드를 확장하고 유지보수가 쉬워진다.
> * 각 원칙들은 특정 문제를 해결하기 위한 지침일 뿐이며, 만일 코드에 해당 문제가 없으면 원칙을 적용할 이유가 없다.

1. SRP(단일책임 원칙)
   - 한 클래스는 하나의 책임만 가져야한다.
   - '책임' 이라는 의미는 '기능 담당' 으로 보면된다.
   - 예) 한 클래스 안에서 A를 고쳤더니 B를 수정해야하고 또 C를 수정해야하고, C를 수정했더니 다시 A로 돌아가서 수정해야하는 형태에 대해 SRP를 적용할 수 있다
   
   <br/>

2. OCP(개방/폐쇄 원칙)
    - 확장은 열려 있으나 변경에 닫혀야 함.
    - 추상화 사용을 통한 관계 구축을 권장을 의미하는 것이다.
    - 즉 다형성과 확장을 가능케 하는 객체지향의 장점을 극대화하는 것이다.

   <br/>

3. LSP(리스코프 치환 원칙)
    - 서브 타입은 언제나 기반(부모) 타입으로 교체할 수 있어야 한다는 원칙이다.
    - 부모의 규약에 맞게 개발되어야 한다.
    - 자동차 추상화로 비행기를 구현할 때 비행기는 자동차의 규약과는 맞지않기 때문에 LSP에 위반된다.

   <br/>

4. ISP(인터페이스 분리 원칙)
    - 인터페이스를 각각 사용에 맞게 끔 잘게 분리해야한다는 설계 원칙이다.
    - SRP는 클래스 분리를 통해 단일 책임을 강조한다면, ISP 는 인터페이스의 단일 책임을 강조하는 것이다.
    - 한번 인터페이스를 분리하여 구성하고 나중에 수정사항이 생겨 또 분리하는 행위는 하지 말아야한다.

   <br/>

5. DIP(의존 역전 원칙)
    - 어떤 Class를 참조해서 사용해야하는 상황이 생긴다면, 그 class를 직접 참조하는 것이 아니라 그 대상의 상위요소(추상 클래스 or 인터페이스)로 참조하라는 원칙
    - 구현체 의존x 인터페이스 의존o

   <br/>

# 회원 도메인 실행과 테스트
1. assertThat 
   - assertThat은 assertThat(T actual, Matcher<? super T> matcher)의 형태
   - 첫번째 파라미터에는 비교대상 값을, 두번째 파라미터로는 비교로직이 담긴 Matcher가 사용된다.
   - 검증시 assertThat 을 사용한다.
```java
class test{
    
   @Test
   @DisplayName("빈 이름 조회")
   void findBeanByName() {
      MemberService memberService = ac.getBean("memberService", MemberService.class);
      System.out.println("memberService = " + memberService);

      assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

   }
}
```