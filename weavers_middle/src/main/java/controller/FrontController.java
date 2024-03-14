package controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HandlerMapper handler;
	public FrontController() {
        super();
        handler = new HandlerMapper();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri=request.getRequestURI();
		String cp=request.getContextPath();
		System.out.println(uri);
		System.out.println(cp);
//		String[] uriList = uri.split("/");
//		int uriListLength = uriList.length;
//		String commend = "/"+uriList[uriListLength-1];
		String commend=uri.substring(cp.length());
		System.out.println("FC : "+commend);
		
		Action action = handler.getAction(commend); // 핸들러에 Action객체를 요청=> 팩토리 패턴(공장처럼 찍어내는 느낌)
		// Handler Mapping은 가장 대표적인 팩토리패턴을 활용하는 클래스이다.
		ActionForward forward = action.execute(request, response); // action.execute해서 주세요
		if(forward.isRedirect()) { // 리다이렉트 방식으로 보냄
			response.sendRedirect(forward.getPath());
		}
		else { // forward 방식으로 보냄
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);
			// pageContext.forward(forward.getPath());
			// RequsetDispatcher는 pageContext를 대체하는 객체
		}
	}
	

	
}
