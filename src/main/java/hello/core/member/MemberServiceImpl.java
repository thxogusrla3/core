package hello.core.member;

//MemberServiceImpl 구현체라고 보면 됨.
//구현체가 하나 일경우엔 impl 이라고 많이 씀.

/**
 * DIP 원칙을 지킴, 추상화(interface)는 의존하지만 구현체(class)는 의존하지 않음.
 * MemberRepository는 외부 파일(AppConfig)에서 실행할 때 정해진다.
 * 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중한다.
 * */
public class MemberServiceImpl implements MemberService{
    
    // 추상화만 의존한 상태
    private final MemberRepository memberRepository ;

    // 구체화도 의존한 상태, DIP 위반
    private final MemberRepository memberRepository1 = new MemoryMemberRepository();

    //추상화만 의존하고 있는 코드임.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
