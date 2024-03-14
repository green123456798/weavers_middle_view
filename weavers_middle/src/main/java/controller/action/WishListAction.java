package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.WishListDAO;
import model.dto.WishListDTO;

public class WishListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 나의 찜목록 페이지로 이동하는 기능의 클래스
		
		// 찜목록으로 이동시 해당 사용자의 찜목록데이터를 전달해줘야함
		
		// forward객체를 생성하여 이동할 페이지를 설정
		ActionForward forward = new ActionForward();
		
		// 찜관련 정보를 담을 wDTO 객체와, 찜 테이블에 기능을 수행할 wDAO 객체를 생성
		WishListDTO wDTO = new WishListDTO();
		WishListDAO wDAO = new WishListDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// 세션객체에서 사용자의 id값을 받아와서 wDTO(찜DTO)에 값을 저장
		wDTO.setMid((String)session.getAttribute("sessionMid"));
		
		// wDTO를 사용하여 검색기능인 R(selectAll)기능을 수행하여 해당유저의 찜목록을 받아옴
		ArrayList<WishListDTO> wdatas =  wDAO.selectAll(wDTO);
		
		// request에 해당유저의 찜목록을 저장
		request.setAttribute("wdatas", wdatas);
		
		// wishList.jsp로 경로를 지정
		// 포워드 방식을 사용하여 request에 저장된 값을 전달가능
		forward.setPath("wishList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
