package hello.core;


import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * AppConfig는 애플리케이션의 실제 동작에 필요한 '구현 객체'를 생성하는 역할을 한다.
 * 구성영역인 AppConfig는 무조건 변경이 된다. ex) fix -> rate
 * 사용영역에 있는 애들은 수정할 필요가 없음. ex) OrderServiceImpl
 * 이 어플의 구성정보가 어떻게 되어 있는지에 대한 정보
 * @Bean을 사용하게되면 스프링 컨테이너에 등록이 됨 이것들이 스프링 빈이 된 것임
 * 스프링 컨테이너에 등록될 때는 함수 이름이 빈에 등록된다.
 */
@Configuration //스프링 설정정보라는 뜻
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    
    @Bean
    public OrderService orderService() { //새로운 구조와 할인 정책 적용
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        //단축키 soutm
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//  return new FixDiscountPolicy(); fix와 rete를 appconfig 이기 때문에 변경해가면서 사용하면 된다.
        return new RateDiscountPolicy();
    }

 }
