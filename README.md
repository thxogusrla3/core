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

2. assertThrows
   - 예외처리 시 assertThrows를 사용한다.
   - 두번 째 인자를 실행하여 첫 번째 인자인 예외타입과 같은지 검사한다.
```java
class test{
   @Test
   @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다")
   void findBeanByParentTypeDuplicate() {
      assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
   }
    
}
```

# 원칙 위반 및 수정
1. DIP 위반 및 수정
```java
public class OrderServiceImpl implements OrderService {
    //추상화(DiscountPolicy)에만 의존해야지 구체화(FixDiscountPolicy) 에도 의존하기 때문에 DIP에 위반됨
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    
    //추상화만 의존하게 해줘야함.
    //단 이 행위는 외부 파일(AppConfig)에서 해줘야하는 것임
    private final DiscountPolicy discountPolicy;
    
    public OrderServiceImpl(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}

public class AppConfig {
    @Bean
   public DiscountPolicy discountPolicy() {
//  return new FixDiscountPolicy(); fix와 rete를 appconfig 이기 때문에 변경해가면서 사용하면 된다.
      return new RateDiscountPolicy();
   }
   
   @Bean
   public OrderService orderService() { //새로운 구조와 할인 정책 적용
      return new OrderServiceImpl(discountPolicy());
   }

}
```

2. OCP 위반 및 수정
```java
public class OrderServiceImpl implements OrderService{
    /**
     * OCP 위반 예제
     * fix -> rate로 변경을 예제로
     * 확장에는 열려있어야 하나 수정에는 닫혀있어야 하는 OCP 원칙은
     * fix -> rate 로 수정되기 때문에 OCP 원칙에 위반된다.
     */
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
                                             // = new RateDiscountPolicy();

    /**
     * dip 위반을 지키기 위해 위 코드를 아래와 같이
     * 생성자를 추가해 준다.
     */
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```

# IoC, DI 그리고 컨테이너
1. IoC(제어의 역전)
   - 프로그램의 제어 흐름을 직접 제어하는 것이 아닌 외부에서 실행 및 권한을 갖는 것
   - OrderServiceImpl은 어떤 구현 객체가 사용될지 모름
   - AppConfig 는 OrderServiceImpl이 어떤 구현객체를 실행할지 권한을 가지고 있음.

2. DI(의존성 주입)
   - 애플리케이션 "실행 시점(런타임)"에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제의 의존관계가 연결되는 것을 "의존관계 주입"이라 한다.
   - 객체 인스턴스를 생성하고 그 참조값을 전달해서 연결된다.

> AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을 IoC 컨테이너 또는 DI 컨테이너 라 한다.

# 스프링으로 전환하기
1. ApplicationContext 
   - 스프링 컨테이너라 한다.
   - @Configuration 어노테이션이 붙은 AppConfig의 @Bean이 붙은 메소드들을 다 스프링 컨테이너에 등록한다.
   - BeanFactory, ApplicationContext 둘 다 스프링 컨테이너라 부른다.

```java
class container{
    //스프링 컨테이너를 사용하기 전 소스
    AppConfig appConfig = new AppConfig();
    MemberService memberService = appConfig.memberService();
    OrderService orderService = appConfig.orderService();    

    //스프링 컨테이너를 사용 한 소스
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
}

```

> **좋은 객체지향이란 solid 원칙을 지키는 것이다. 하지만 다형성만으로는 OCP, DIP 만족시킬 수 없는데 이를 만족하기 위해 컨테이너라는 개념이 등장했고, 이는 스프링이 필요한 이유라는 것을 지금까지 학습한 것이다.**

# 빈 이름 조회하기
```java
class FindBean {
   AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    //1. bean 이름으로 조회
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    
    //2. bean 타입으로 조회
    MemberService memberService = ac.getBean(MemberService.class);

    //3. 구체 타입으로 조회,  유연성이 떨어짐 역할과 구현을 나눠야 하는데 그렇지 못하기 때문에
    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    
}
```

# BeanFactory 와 ApplicationContext
1. BeanFactory
   - 스프링 컨테이너의 최상위 인터페이스
   - 스프링 빈을 관리하고 조회하는 역할을 담당한다.
   - getBean() 외에도 대부분의 기능은 BeanFactory가 제공하는 기능이다.
   
   <br/>

2. ApplicationContext
   - BeanFactory를 상속받아 빈을 관리 및 검색하는 기능을 제공한다.
   - EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver 을 상속받는다.

   <br/>
3. ApplicationContext 사용하는 이유
   - 메세지소스를 활용한 국제화 기능(MessageSource)
   - 환경변수(EnvironmentCapable)
   - 애플리케이션 이벤트(ApplicationEventPublisher)
   - 편리한 리소스 조회(ResourcePatternResolver)

4. 스프링 컨테이너 형식
   - 자바 코드: new AnnotationConfigApplicationContext(AppConfig.class);
   - XML: 레거시
   - groovy

# 웹 애플리케이션과 싱글톤
1. 싱글톤이란
   - 웹 애플리케이션은 사용자가 100개의 요청을 보내면 100개의 객체를 생성해야하는데, 이렇게 되면 메모리 낭비가 심하다.
   - 이를 위해 클래스의 한개의 객체만 생성하고 공유하도록 하는게 싱글톤이다.

```java
public class SingletonService {
    //클래스 멤버변수로 생성을 하게 되면 클래스가 로드 될 때 클래스 멤버변수들도 함께 로드되고 모든 영역에 공유 된다.
    //이렇게 한개를 생성을 하고, 여러가지 방법으로 한개만 생성하게 막을 수 있다.
    private static final SingletonService instance = new SingletonService();
    public static SingletonService getInstance() {
        return instance;
    }
    
    //private 생성자를 사용하여 객체를 더 생성하지 못하게 막아주는 방법.
    private SingletonService() {}
}
```
2. 싱글톤 패턴 문제점
   - 구현이 어렵다.
   - 구체 클래스에 의존한다 -> DIP 위반한다. / OCP 원칙을 위반할 가능성이 높다.
   - 유연성이 떨어지며 안티패턴으로 불리기도 한다.

# 싱글톤 컨테이너
1. 싱글톤 컨테이너란?
   - 스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한다.
   - 지금까지 학습한 스프링 빈이 바로 싱글톤으로 관리되는 빈이다.

# 싱글톤 방식의 주의점
- 싱글톤 패턴은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
- 무상태(stateless)로 설계해야 한다.
- 즉 A 클라이언트의 부분을 B 클라이언트가 값을 변경할 수 있게 하면 안된다.

# 같은 빈 호출 시
- 아래 코드와 같이 MemoryMemberRepository를 세번 호출한다 해도 하나의 객체만 생성이되어 싱글톤을 지킬 수 있게된다.

```java
public class test { 
    MemberServiceImpl memberService   = ac.getBean("memberService",    MemberServiceImpl.class);
    OrderServiceImpl orderService     = ac.getBean("orderService",     OrderServiceImpl.class);
    MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

    //sout("memberService -> memberRepository = " + memberService.getMemberRepository());
    //sout("orderService  -> memberRepository = " + orderService.getMemberRepository());
    //sout("memberRepository = " + memberRepository);
}
```