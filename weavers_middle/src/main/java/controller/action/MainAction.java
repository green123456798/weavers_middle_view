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

public class MainAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//[박수연]
		
		// 메인페이지로 이동하는 기능의  클래스
		ActionForward forward = new ActionForward();
		forward.setPath("main.jsp");
		forward.setRedirect(false);
		
		// ProductDTO와 ProductDAO 인스턴스 생성
     	// DB에서 상품정보를 다루기 위해 필요한 객체들을 생성
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		
		// 메인페이지에 호출되는 전체상품의 정렬방식을 구분하기위해 작성
		// 해당 방식은 찜의 개수로 순서를 정함
		pDTO.setSearchCondition("추천순");
		
		// 현재 요청에 대한 세션 객체를 얻어옴
        // 세션이 이미 존재하면 그 세션을 반환하고, 존재하지 않으면 새로운 세션을 생성
		HttpSession session = request.getSession();
		
		pDTO.setMid((String)session.getAttribute("sessionMid"));
		//찜 유무 확인을 위해 아이디 저장

		// 추천순으로 정렬된 상품정보를 리스트에 저장
		ArrayList<ProductDTO> wdatas = pDAO.selectAll(pDTO);
		request.setAttribute("wdatas", wdatas);
		
		// 메인페이지에 호출되는 전체상품의 정렬방식을 구분하기위해 작성
		// 해당 방식은 상품 판매량으로 순서를 정함
		pDTO.setSearchCondition("판매순");

		// 판매량순으로 정렬된 상품정보를 리스트에 저장
		ArrayList<ProductDTO> sdatas = pDAO.selectAll(pDTO);
		request.setAttribute("sdatas", sdatas);
		
		
		return forward;
	}
}
