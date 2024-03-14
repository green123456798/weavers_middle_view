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

public class UnregisterAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원탈퇴 페이지로 이동하는 기능의 클래스
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 사용자의 정보를 담을 mDTO 객체와, 사용자 테이블에 기능을 수행할 mDAO 객체를 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// 유저의 정보를 담는 mDTO에 id와 pw, 검색조건을 설정
		mDTO.setMid((String)session.getAttribute("sessionMid"));
		mDTO.setMpw(request.getParameter("mpw"));
		mDTO.setSearchCondition("로그인");
		
		// mDAO를 통해서 해당유저가 존재하는지 
		mDTO = mDAO.selectOne(mDTO);
		
		if(mDTO == null) { // 해당 유저가 존재하지 않는다면
			forward.setPath("passwordCheck.do?type=unregister"); // 다시 비밀번호 확인 페이지로 전달
			forward.setRedirect(true);
		}
		else {
			forward.setPath("unregister.jsp");
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
