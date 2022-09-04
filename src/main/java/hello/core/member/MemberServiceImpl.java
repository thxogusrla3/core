package hello.core.member;

//MemberServiceImpl 구현체라고 보면 됨.
//구현체가 하나 일경우엔 impl 이라고 많이 씀.
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository ;

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
}
