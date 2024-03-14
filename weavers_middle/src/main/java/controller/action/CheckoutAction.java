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
import model.dao.MemberDAO;
import model.dto.CartDTO;
import model.dto.MemberDTO;

public class CheckoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 결제하기 페이지로 이동하는 기능의 클래스
		
		// 페이지이동기능을 담당하는 forward 객체를 생성 
		ActionForward forward = new ActionForward();
		
		// 장바구니에 담긴 물건 출력
		
		// 장바구니 cDAO cDTO, session 객체 생성
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		HttpSession session = request.getSession();
		
		// 세션에 저장된 사용자의 id값을 변수로 저장
		String mid = (String)session.getAttribute("sessionMid");
		
		// 장바구니 DTO에 사용자의 id값을 이용하여 R(selectAll) 기능을 통한 장바구니 물건들 검색
		cDTO.setMid(mid);
		ArrayList<CartDTO> cdatas = cDAO.selectAll(cDTO);
		if(cdatas.isEmpty()) { // 장바구니에 물품이 없을경우 유효성검사	
			forward.setPath("cart.jsp");
			forward.setRedirect(false);
			return forward;
		}
		else { // 장바구니에 물건이 존재할경우 해당 목록을 보냄
			request.setAttribute("cdatas", cdatas);
		}
		
		// 결제 페이지에 구매할 물품들을 출력하기위해 전달
		request.setAttribute("cdatas", cdatas);
		
		// 사용자 DTO, DAO 객체 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// 사용자 DTO에 'id'와 검색조건'회원정보'설정
		mDTO.setMid(mid);
		mDTO.setSearchCondition("회원정보");
		// selectOne => R 기능을 통해 해당 사용자가 존재하는지 확인
		mDTO = mDAO.selectOne(mDTO);
		if(mDTO == null) { // 해당 사용자가 존재하지 않는다면
			forward.setPath("error.jsp"); // 메인화면으로 이동하기(controller의 유효성검사)
			forward.setRedirect(true);
		} else { // 사용자가 존재한다면 사용자의 이름, 이메일, 전화번호, 마케팅(이메일수신) 정보를 request에 저장해서 checkout.jsp로 전송
			request.setAttribute("name", mDTO.getName());
			request.setAttribute("email", mDTO.getEmail());
			request.setAttribute("phone", mDTO.getPhone());
			request.setAttribute("marketing", mDTO.getMarketing());
			
			//  request에 저장된 값이 존재하기 때문에 forward를 false로 설정
			// 결제페이지로 이동
			forward.setPath("checkout.jsp");
			forward.setRedirect(false);
		}
		
		return forward;
	}
}
