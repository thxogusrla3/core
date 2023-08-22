package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        //아래 코드로 인해 appConfig.memberService() 이렇게 가져오지 않아도 됨.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);//모든 객체(Bean)를 관리해줌.
        //@Bean이 붙은 메소드의 이름 -> memberService
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("new Memeber = " + member.getName());
        System.out.println("find Memeber = " + findMember.getName());
    }
}
