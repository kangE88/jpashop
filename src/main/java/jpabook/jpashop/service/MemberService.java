package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //@RequiredArgsConstructor final 필드만 갖고 생성자 만들어줌

    //@Autowired
    private final MemberRepository memberRepository;

    //생성자 주입
    //@Autowired
    //public MemberService(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository;
    //}

    //Setter 주입
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //@Transactional(readOnly = true) //읽기전용엔 readOnly 사용 -> 클래스 자체가 readOnly 로 설정하여 주석
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //특정 회원 조회
    //@Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
