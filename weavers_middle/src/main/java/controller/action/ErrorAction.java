package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;

public class ErrorAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 에러페이지로 이동하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		forward.setPath("/weaveGlow_ver0.3.5/error.jsp");
		// 여러 /경로로 에러발생할수도있기때문에
		// 절대경로로 수정함
		forward.setRedirect(true);
		
		System.out.println("ErrorAction 클래스의 로그");
		System.out.println(forward);
		return forward;
	}
	
}
