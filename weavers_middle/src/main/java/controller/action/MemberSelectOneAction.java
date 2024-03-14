package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class MemberSelectOneAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 로그인을 수행하는 기능의 클래스
		ActionForward forward = new ActionForward();
		
		// MemberDTO와 MemberDAO 인스턴스 생성
     	// DB에서 사용자정보를 다루기 위해 필요한 객체들을 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 로그인페이지에서 사용자 ID, 비밀번호 정보를 가져와 MemberDTO에 설정
		mDTO.setMid((String)request.getParameter("mid"));
		mDTO.setMpw((String)request.getParameter("mpw"));
		
		// 서버에서 입력 받은 사용자정보의 사용방식을 구분하기위해 작성
		mDTO.setSearchCondition("로그인");
		mDTO = mDAO.selectOne(mDTO);
		
		// 확인된 사용자 정보가 비어있거나 사용자의 등급이 5등급 인 경우
		if(mDTO == null || mDTO.getGpk() == 5) {
			request.setAttribute("msg", "잘못입력하셨거나 없는 회원입니다.");
			forward.setPath("login.jsp");
			forward.setRedirect(false);
		}
		// 확인된 사용자 정보가 있거나 사용자의 등급이 5등급 아닌 경우
		else {
			HttpSession session = request.getSession();
			session.setAttribute("sessionMid", mDTO.getMid());
			forward.setPath("main.do");
			forward.setRedirect(true);
		}
		
		return forward;
	}
}
