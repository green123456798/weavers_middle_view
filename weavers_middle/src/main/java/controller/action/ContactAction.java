package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;

public class ContactAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Contact(회사정보) 페이지로 이동하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		forward.setPath("contact.jsp");
		forward.setRedirect(true);

		
		return forward;
	}
}
