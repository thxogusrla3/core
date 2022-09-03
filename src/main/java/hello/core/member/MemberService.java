package hello.core.member;

public interface MemberService {  // 역할을 만든거임.
    void join(Member member);

    Member findMember(Long memberId);
}
