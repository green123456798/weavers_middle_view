package controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.dao.BuyProductDAO;
import model.dao.ReviewDAO;
import model.dto.BuyProductDTO;
import model.dto.ReviewDTO;

public class ReviewAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 리뷰작성페이지로 이동하는 기능의 클래스
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 리뷰의 정보를 담을 rDTO 객체와, 리뷰테이블에 기능을 수행할 rDAO 객체를 생성
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();
		
		// request의 파라미터 값 bpk(구매한상품의 PK번호)를 변수 bpk에 저장
		int bpk = Integer.parseInt(request.getParameter("bpk"));
		rDTO.setBpk(bpk); // rDTO에 bpk값을 저장
		rDTO = rDAO.selectOne(rDTO); // rDTO를 사용하여 rDAO의 검색기능인 R(selectOne)기능을 사용하여 해당 상품이 존재하는 확인
		if(rDTO == null) { // 만약 해당 상품이 없다면 => 리뷰작성버튼으로 현재 action으로 들어왔다면
			BuyProductDTO bDTO = new BuyProductDTO(); // 구매한 상품의 정보를 담을 bDTO 객체와, 
			BuyProductDAO bDAO = new BuyProductDAO(); // 구매한 상품테이블에 기능을 수행할 bDAO 객체를 생성
			bDTO.setBpk(bpk); // bDTO에 bpk(구매한 상품의 PK번호)값을 저장
			bDTO = bDAO.selectOne(bDTO); // bDTO를 사용하여 bDAO의 검색기능인 R(selectOne)기능을 사용하여 해당 구매한 상품이 존재하는지 확인
			if(bDTO == null) { // 만약 해당 구매한 상품이 존재하지 않는다면
				forward.setPath("error.jsp"); // 에러페이지로 경로를 설정
				forward.setRedirect(true); 
				return forward; // 에러페이지로 경로를 설정하며 return으로 아래의 코드를 즉시 종료
			}
			// 구매한 상품이 존재한다면
			ReviewDTO data = new ReviewDTO(); // 임의의 리뷰정보를 담을 data객체 생성 => rDTO가 if문의 조건으로 사용되고 있어서 해당 scope에서 재활용 불가
			data.setBpk(bpk); // data객체에 bpk(구매한상품의 PK번호)를 저장
			data.setPpk(bDTO.getPpk()); // data객체에 bDTO의 ppk(제품의 PK번호)를 저장
			data.setPimg(bDTO.getImg()); // data객체에 bDTO의 img(제품의 이미지명)를 저장
			data.setPname(bDTO.getPname()); // data객체에 bDTO의 pname(제품명)을 저장
			System.out.println(data.getPpk());
			request.setAttribute("data", data);	//해당 data객체를 request에 저장
		}
		else { // 만약 rDAO의 selectOne으로 해당 리뷰가 존재한다면 => 리뷰수정버튼으로 현재 action으로 들어왔다면
			System.out.println(rDTO.getPpk());
			request.setAttribute("data", rDTO); // 해당 rDTO(리뷰관련DTO)를 request에 저장 
		}
		
		forward.setPath("review.jsp"); // review.jsp로 경로를 지정
		forward.setRedirect(false); // 포워드 방식을 사용하여 request에 저장된 값을 전달가능

		return forward;
	}
}
