package hello.core.member;

import java.util.HashMap;
import java.util.Map;

//MemberRepository의 구현체
public class MemoryMemberRepository implements MemberRepository {

    // 동시성 이슈로 hashmap 보단 ConcurrentHashMap 을 더 사용하긴한다.
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
