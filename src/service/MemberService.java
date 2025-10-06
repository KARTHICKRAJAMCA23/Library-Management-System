package service;

import dao.MemberDAO;
import model.Member;
import java.util.List;

public class MemberService {
    private MemberDAO memberDAO = new MemberDAO();

    public void addMember(Member member) { memberDAO.addMember(member); }
    public List<Member> getAllMembers() { return memberDAO.getAllMembers(); }
    public Member getMemberById(int id) { return memberDAO.getMemberById(id); }
}
