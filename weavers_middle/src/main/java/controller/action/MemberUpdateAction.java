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

public class MemberUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//해당하는 페이지로 이동하는 기능의  클래스
		ActionForward forward = new ActionForward();
		
		// MemberDTO와 MemberDAO 인스턴스 생성
     	// DB에서 사용자정보를 다루기 위해 필요한 객체들을 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 현재 요청에 대한 세션 객체를 얻어옴
        // 세션이 이미 존재하면 그 세션을 반환하고, 존재하지 않으면 새로운 세션을 생성
		HttpSession session = request.getSession();
		
		
		// 개인정보수정페이지에서 사용자 ID, 비밀번호, email, 닉네임, 전화번호를 가져와 MemberDTO에 설정
		mDTO.setMid((String)session.getAttribute("sessionMid"));
		mDTO.setMpw(request.getParameter("mpw"));
		mDTO.setEmail(request.getParameter("email"));
		mDTO.setNickname(request.getParameter("nickname"));
		mDTO.setPhone(request.getParameter("phone"));
		
		// 서버에서 입력 받은 사용자정보의 사용방식을 구분하기위해 작성
		mDTO.setSearchCondition("정보수정");
		
		// 디버깅을 위한 출력
		System.out.println(mDTO);
		
		// 업데이트 성공 여부를 나타내는 플래그
		// 코드의 가독성을 높이고 유지보수를 용이하게 해줌
		boolean flag = mDAO.update(mDTO);
		
		if(flag) { // 성공했으면 mypage로 이동
			forward.setPath("mypage.do");
			forward.setRedirect(true);
		}
		else { // 실패했으면 다시 에러페이지로 이동
			forward.setPath("error.jsp"); 
			forward.setRedirect(true);
		}
		
		
		return forward;
	}

}
