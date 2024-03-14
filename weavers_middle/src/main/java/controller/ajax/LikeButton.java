package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.WishListDAO;
import model.dto.WishListDTO;

@WebServlet("/likeButton.asy")
public class LikeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LikeButton() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();					// HttpSession 객체를 통해 세션 관리
		PrintWriter out = response.getWriter();						// PrintWriter 선언 및 초기화 (응답 전송)
		
		WishListDTO wDTO = new WishListDTO();						// WishListDTO 객체 생성
		WishListDAO wDAO = new WishListDAO();						// WishListDAO 객체 생성
		
		int pk = Integer.parseInt(request.getParameter("pk"));		// 파라미터값을 pk에 저장
		
		wDTO.setPpk(pk);											// wDTO에 pk저장
		wDTO.setMid((String)session.getAttribute("sessionMid"));	// wDTO에 세션ID 저장 (로그인 상태, 로그아웃 상태 체크)
		
		WishListDTO data = wDAO.selectOne(wDTO);					// selectOne()을 통해 리턴값(객체) 저장
		
		if(data == null) {								// 찜이 안된 상태라면
			boolean flag = wDAO.insert(wDTO);			// 찜 하기
			if(flag) {
				out.print("1");							// 찜 성공 1		
			}
			else {
				out.print("0");							// 찜 실패 0
			}
		}
		else {
			boolean flag = wDAO.delete(wDTO);			// 찜이 된 상태라면
			if(flag) {
				out.print("2");							// 찜 취소 2
			}
			else {
				out.print("0");							// 찜 실패 0
			}
		}
	}

}
