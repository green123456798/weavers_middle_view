package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CartDAO;
import model.dto.CartDTO;

@WebServlet("/cartInsert.asy")
public class CartInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CartInsert() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 대한 응답을 보내기위해 printWriter 객체 생성
		PrintWriter out = response.getWriter();
		
		// 장바구니의 정보를 담을 cDTO 객체와, 장바구니테이블에 기능을 수행할 cDAO 객체를 생성
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		// 유효성검사를 위한 flag변수 선언
		boolean flag;

		// cDTO에 request와 session으로 받아온 제품의 수량, 사용자의 id, ppk(제품의PK번호)를 저장
		cDTO.setCnt(Integer.parseInt(request.getParameter("cnt")));
		cDTO.setMid((String)session.getAttribute("sessionMid"));
		cDTO.setPpk(Integer.parseInt(request.getParameter("ppk")));
//		System.out.println(cDTO.getCnt());
//		System.out.println(cDTO.getMid());
//		System.out.println(cDTO.getPpk());
		// cDTO를 사용하여 cDAO의 검색기능인 R(selectOne)기능을 사용하여 해당 상품이 존재하는지 확인
		CartDTO data = cDAO.selectOne(cDTO);
//		System.out.println("없는 상품 1 : "+data);
		
		if(data == null) { // 만약 검색한 상품이 장바구니 테이블에 존재하지 않는다면
			flag = cDAO.insert(cDTO); // 장바구니에 상품을 추가 (cDAO의 insert기능을 사용)
			
			System.out.println("flag 1 : "+flag);
			if(flag) { // 장바구니테이블에 상품추가를 성공한다면 
				out.print("1");	// 1을 출력
			}
			else { // 장바구니테이블에 상품추가를 실패한다면
				out.print("0");	// 0을 출력
			}
		}
		else { // 만약 검색한 상품이 장바구니 테이블에 존재한다면
			cDTO.setSearchCondition("기존상품추가"); // cDTO에 검색조건을 저장
			flag = cDAO.update(cDTO); // cDTO를 사용한 cDAO의 수정기능인 U(update)기능을 사용하여 해당 제품의 수량을 장바구니테이블에서 변경
			System.out.println("flag 2 : "+flag);
			if(flag) { // 제품의 수량변경에 성공했다면
				out.print("1");	// 1을 출력
			}
			else { // 제품의 수량변경에 실패했다면 
				out.print("0");	// 0을 출력
			}
		}
	}
}
