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

public class ProfileChangeAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원정보 수정 페이지로 이동하는 기능의 클래스
		ActionForward forward = new ActionForward();
		
		// MemberDTO와 MemberDAO 인스턴스 생성
     	// DB에서 사용자정보를 다루기 위해 필요한 객체들을 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 현재 요청에 대한 세션 객체를 얻어옴 
        // 세션이 이미 존재하면 그 세션을 반환하고, 존재하지 않으면 새로운 세션을 생성
		HttpSession session = request.getSession();
		
		// 서버로부터 사용자정보를 받아와 MemberDTO에 저장
		mDTO.setMid((String)session.getAttribute("sessionMid"));
		mDTO.setMpw(request.getParameter("mpw"));
		
		// 서버에서 입력 받은 사용자정보의 사용방식을 구분하기위해 작성
		mDTO.setSearchCondition("로그인");
		mDTO = mDAO.selectOne(mDTO);
		
		// 확인된 사용자 정보가 비어있는 경우
		if(mDTO == null) {
			forward.setPath("passwordCheck.do?type=profileChange");
			forward.setRedirect(true);
		}
		else {
			//확인한 사용자 정보가 있는 경우
			request.setAttribute("mid", mDTO.getMid()); 
			request.setAttribute("mpw", mDTO.getMpw());
			request.setAttribute("name", mDTO.getName());
			request.setAttribute("birth", mDTO.getBirth());
			request.setAttribute("phone", mDTO.getPhone());
			request.setAttribute("nickname", mDTO.getNickname());
			request.setAttribute("email", mDTO.getEmail());
			request.setAttribute("marketing", mDTO.getMarketing());
			forward.setPath("profileChange.jsp");
			forward.setRedirect(false);
		}
		
		return forward;
	}

}