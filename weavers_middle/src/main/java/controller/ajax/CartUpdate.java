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

@WebServlet("/cartUpdate.asy")
public class CartUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CartUpdate() {
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
        
        // cDTO객체에 사용자의 id와 ppk(상품의PK번호)를 저장
        cDTO.setMid((String)session.getAttribute("sessionMid"));
        cDTO.setPpk(Integer.parseInt(request.getParameter("ppk")));
        // request로 파라미터값을 받아와서 변수명 'updown'에 저장
        String updown = request.getParameter("updown");
        // 만약 'updown'이 1이면 cDTO 객체에 검색조건을 '수량증가'로 설정 => 1인경우 장바구니 상품개수 증가
        if ("1".equals(updown)) {
            cDTO.setSearchCondition("수량증가");
        } else { // 'updown'이 1이 아니면 cDTO 객체에 검색조건을 '수량감소'로 설정한다. => 0인경우 장바구니 상품개수 감소
            cDTO.setSearchCondition("수량감소");
        }
        
        // cDTO를 사용하여 cDAO의 수정기능인 U(update)기능을 사용하여 장바구니 테이블의 수량을 수정
        boolean flag = cDAO.update(cDTO);

        if(flag) { // 만약 장바구니 테이블에 수량이 정상적으로 수정되었다면
			out.print("1");	// 1을 출력
		}
		else { // 장바구니 테이블에 수량을 수정하는데 실패했다면
			out.print("0");	 // 0을 출력
		}
		
	}

}
