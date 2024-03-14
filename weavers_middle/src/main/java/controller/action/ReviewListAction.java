package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.ReviewDAO;
import model.dto.ReviewDTO;

public class ReviewListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 내 리뷰목록 페이지로 이동하는 기능의 클래스
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 리뷰의 정보를 담을 rDTO 객체와, 리뷰테이블에 기능을 수행할 rDAO 객체를 생성
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// rDTO 객체에 사용자의 id, 검색조건을 저장
		rDTO.setMid((String)session.getAttribute("sessionMid"));
		rDTO.setSearchCondition("개인리뷰출력");
		System.out.println(rDTO);
		
		// 리뷰관련 정보를 담고있는 rDTO 객체를 사용하여 rDAO의 검색기능인 R(selectAll)기능을 사용하여 개인의 리뷰목록을 출력
		ArrayList<ReviewDTO> rdatas = rDAO.selectAll(rDTO);
		System.out.println(rdatas.size());
		
		// selectAll으로 받아온 개인리뷰목록을 request에 저장
		System.out.println("로그456");
		request.setAttribute("rdatas", rdatas);
		System.out.println("로그123");
		
		// reviewList.jsp로 경로를 지정
		// 포워드 방식을 사용하여 request에 저장된 값을 전달가능
		forward.setPath("reviewList.jsp");
		forward.setRedirect(false);
		System.out.println("로그789");

		return forward;
	}

}
