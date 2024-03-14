package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;

public class RegisterAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입 페이지로 이동하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		forward.setPath("register.jsp");
		forward.setRedirect(true);
		
		
		return forward;
	}

}
