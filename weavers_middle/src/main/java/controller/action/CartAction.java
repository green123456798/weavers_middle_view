package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.CartDAO;
import model.dto.CartDTO;

public class CartAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 장바구니 페이지로 이동하는 기능의 클래스
		ActionForward forward = new ActionForward();

		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		HttpSession session = request.getSession();
		
		// 장바구니에 담긴 물건 출력
		cDTO.setMid((String)session.getAttribute("sessionMid"));;
		ArrayList<CartDTO> cdatas = cDAO.selectAll(cDTO);
		if(cdatas.isEmpty()) { // 장바구니에 물품이 없을경우 메세지를 보냄
			request.setAttribute("msg", "장바구니가 비었습니다.");
			forward.setPath("cart.jsp");
			forward.setRedirect(false);
		}
		else { // 장바구니에 물건이 존재할경우 해당 목록을 보냄
			request.setAttribute("cdatas", cdatas);
			forward.setPath("cart.jsp");
			forward.setRedirect(false);
		}
		return forward;
	}

}
