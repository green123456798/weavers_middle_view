package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;

public class LogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 로그아웃을 수행하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		forward.setPath("main.do");
		forward.setRedirect(true);

		// 현재 요청에 대한 세션 객체를 얻어옴
        // 세션이 이미 존재하면 그 세션을 반환하고, 존재하지 않으면 새로운 세션을 생성
		HttpSession session = request.getSession();
		
		//세션에 저장된 ID 와 NickName을 제거
		session.removeAttribute("sessionMid");
		session.removeAttribute("sessionNickname");

		return forward;
	}
}
