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
import model.dao.ReviewDAO;
import model.dao.WishListDAO;
import model.dto.ProductDTO;
import model.dto.ReviewDTO;
import model.dto.WishListDTO;

public class ProductDetailAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 제품 상세페이지로 이동하는 기능의 클래스
		ActionForward forward = new ActionForward();
		
		// 제품 상세정보 보여주는 기능
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		HttpSession session = request.getSession();	
		
		// 제품번호를 받아와 ppk에 저장
		int ppk = Integer.parseInt(request.getParameter("ppk"));
		// 제품번호 와 사용자 ID를 ProductDTO에 저장
		// 로그인 상태, 로그아웃 상태 체크
		pDTO.setPpk(ppk);
		pDTO.setMid((String)session.getAttribute("sessionMid"));
		
		// 제품정보의 사용방식을 구분하기위해 작성
		pDTO.setSearchCondition("상세페이지");
		pDTO = pDAO.selectOne(pDTO);
		// 디버깅을 위한 출력
//		System.out.println(pDTO.getImg());
		
		// 확인한 제품정보가 없는 경우
		if(pDTO == null) {
			forward.setPath("error.jsp");
			forward.setRedirect(false);
			return forward;
		}
		// 확인한 제품정보가 있는 경우
		else {
			// 제품정보를 ProductDTO에서 받아와 저장
			request.setAttribute("ppk", pDTO.getPpk());
			request.setAttribute("pname", pDTO.getPname());
			request.setAttribute("price", pDTO.getPrice());
			request.setAttribute("detail", pDTO.getDetail());
			request.setAttribute("img", pDTO.getImg());
		}
		
		// 리뷰목록 보여주는 기능
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();
		rDTO.setPpk(ppk);
		rDTO.setSearchCondition("메인리뷰출력");
		ArrayList<ReviewDTO> rdatas =  rDAO.selectAll(rDTO);
		
		// 디버깅을 위한 출력
//		System.out.println(rDTO);
//		System.out.println(rdatas);
		
		// 리뷰 리스트의 저장된 리뷰가 없는 경우
		if(rdatas.size() <= 0) {
			forward.setPath("productDetail.jsp");
			forward.setRedirect(false);
		}
		// 리뷰 리스트의 저장된 리뷰가 있는 경우
		else {
			// 해당 리뷰를 전달
			request.setAttribute("rdatas", rdatas);
			forward.setPath("productDetail.jsp");
			forward.setRedirect(false);
		}
		
		// 찜상태 알려주는 기능
		WishListDTO wDTO = new WishListDTO();
		WishListDAO wDAO = new WishListDAO();
		wDTO.setMid((String)session.getAttribute("sessionMid"));
		wDTO.setPpk(ppk);
		wDTO = wDAO.selectOne(wDTO);
		
		// 해당 제품을 찜하지 않은 경우
		if(wDTO == null) {
			request.setAttribute("like", "0");
		}
		// 해당 제품을 찜한 경우
		else {
			request.setAttribute("like", "1");
		}
		return forward;
	}
}
