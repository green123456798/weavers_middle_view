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

public class UnregisterSuccessAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원탈퇴 완료페이지로 이동하는 기능의 클래스
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 사용자의 정보를 담을 mDTO 객체와, 사용자 테이블에 기능을 수행할 mDAO 객체를 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// 유저의 정보를 담는 mDTO에 id와 검색조건을 설정
		mDTO.setMid((String)session.getAttribute("sessionMid"));
		mDTO.setSearchCondition("회원탈퇴");
		
		// mDAO를 통해서 유저의 탈퇴를 진행 => DELETE(삭제)가 아닌 UPDATE(수정)을 통하여 유저의 상태변경 (회원 => 탈퇴한사용자)
		boolean flag = mDAO.update(mDTO);
		System.out.println(flag);
		
		if(flag) { // 탈퇴에 성공했다면 세션에서 사용자의 id를 삭제
			session.removeAttribute("sessionMid");
			forward.setPath("unregisterSuccess.jsp"); // 삭제완료 페이지로 이동
			forward.setRedirect(true);
		} else { // 탈퇴에 실패했다면 에러페이지로 이동
			forward.setPath("error.jsp");
			forward.setRedirect(true);
		}
		return forward;
	}

}
