package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;

public class PasswordCheckAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 비밀번호를 확인하는 페이지로 이동하는 기능의 클래스
		ActionForward forward = new ActionForward();
		
		request.setAttribute("type", (String)request.getParameter("type"));

		forward.setPath("passwordCheck.jsp");
		forward.setRedirect(false);
		
		
		return forward;
	}

}
