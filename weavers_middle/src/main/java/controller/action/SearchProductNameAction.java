package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.ProductDAO;
import model.dto.ProductDTO;

public class SearchProductNameAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 상품의 정보를 담을 pDTO 객체와, 상품테이블에 기능을 수행할 pDAO 객체를 생성
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();	
		
		// pDTO객체에 id와 검색조건, 상품명을 파라미터로 받아와서 저장
		pDTO.setMid((String)session.getAttribute("sessionMid"));	
		pDTO.setSearchCondition("이름으로찾기");
		String content = request.getParameter("content");
		pDTO.setPname(content);
		
		// pDTO를 사용하여 검색기능인 R(selectAll)기능을 수행하여 해당하는 상품들의 리스트를 배열객체에 저장
		ArrayList<ProductDTO> datas = pDAO.selectAll(pDTO);
		
		// 받아온 리스트를 request에 저장함
		request.setAttribute("datas", datas);
		
		//view 에게 keyword 로 content 값 전달 
		request.setAttribute("keyword",  content);
		
		// searchProductName.jsp로 경로를 지정
		// 포워드 방식을 사용하여 request에 저장된 값을 전달가능
		forward.setPath("searchProductName.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
