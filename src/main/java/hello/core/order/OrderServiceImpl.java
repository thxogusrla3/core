package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

/**
 * @RequiredArgsConstructor 이 어노테이션을 사용하게 되면 아래와 같이 final 키워드가 붙은 멤버변수들 파라미터로 갖는 생성자를 자동으로 만들어준다.
 * @Autowired
 * public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
 *     this.memberRepository = memberRepository;
 *     this.discountPolicy = discountPolicy;
 * }
 * */

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
