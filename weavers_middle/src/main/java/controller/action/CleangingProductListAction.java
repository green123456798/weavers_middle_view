package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.dao.ProductDAO;
import model.dto.ProductDTO;

public class CleangingProductListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 전체 상품목록 페이지로 이동하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		forward.setPath("cleangingProductList.jsp");
		forward.setRedirect(false);
		
		
		// 상품목록 불러오는 기능을 비동기로 분리해서 현재 사용XXX
		// ProductDTO와 ProductDAO 인스턴스 생성
     	// DB에서 상품정보를 다루기 위해 필요한 객체들을 생성
		/*
		 * ProductDTO pDTO = new ProductDTO(); ProductDAO pDAO = new ProductDAO();
		 * 
		 * // 서버에서 입력 받은 사용자정보의 사용방식을 구분하기위해 작성 pDTO.setSearchCondition("신상순");
		 * ArrayList<ProductDTO> datas = pDAO.selectAll(pDTO);
		 * 
		 * request.setAttribute("datas", datas);
		 */
		
		return forward;
	}

}
