package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    //final은 상수로써 값이 무조건 있어야 함.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * fix -> rate로 변경되면서 dip가 위반 됐다.
     * 이를 해결하기 위해 코드 수정이 필요하다.
     */
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); -> new RateDiscountPolicy();

    /**
     * dip를 지키기 위한 코드
     * 하지만 해당 아래 코드를 쓰게 되면 null 에러가 뜸. 아무것도 매핑된게 없기 때문에.
     */
//    private DiscountPolicy discountPolicy;

    /**
     * dip 위반을 지키기 위해 위 코드를 아래와 같이
     * 생성자를 추가해 준다.
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    @Autowired //생성자에서 여러의존관계들을 주입할 수 있음.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //단일책임원칙을 잘지킨 경우임
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); //회원을 조회
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
