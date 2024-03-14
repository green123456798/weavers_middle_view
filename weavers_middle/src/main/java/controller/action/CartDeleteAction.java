package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.CartDAO;
import model.dto.CartDTO;

public class CartDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 장바구니 페이지로 이동하는 기능의 클래스
		ActionForward forward = new ActionForward();
		
		// CartDTO와 CartDAO 인스턴스 생성
     	// DB에서 장바구니 정보를 다루기 위해 필요한 객체들을 생성
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		
		// 현재 요청에 대한 세션 객체를 얻어옴 
        // 세션이 이미 존재하면 그 세션을 반환하고, 존재하지 않으면 새로운 세션을 생성함
		HttpSession session = request.getSession();
		
		
		// 장바구니에서 상품번호, 사용자 ID를 가져와 CartDTO에 설정
		cDTO.setPpk(Integer.parseInt(request.getParameter("ppk"))); // ppk를 받아야함
		cDTO.setMid((String)session.getAttribute("sessionMid"));
		
		// CartDTO에서 상품삭제 방식을 구분하기위해 작성
		cDTO.setSearchCondition("요소삭제");
		boolean flag = cDAO.delete(cDTO);
		
		// 받아온 flag의 값으로 구분하여 성공 여부를 판단
		if(flag) {
			//성공하였을 경우 장바구니로 이동
			forward.setPath("cart.do");
			forward.setRedirect(true);
		}
		else {
			//실패하였을 경우 에러페이지로 이동
			forward.setPath("error.jsp");
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
