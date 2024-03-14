package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

//----- 아이디 중복체크 -----
@WebServlet("/idCheck.asy")
public class IdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdCheck() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		PrintWriter out = response.getWriter();			// PrintWriter 선언 및 초기화 (응답 전송)
		
		MemberDTO mDTO = new MemberDTO();				// MemberDTO 객체 생성
		MemberDAO mDAO = new MemberDAO();				// MemberDAO 객체 생성
		
		String mid = request.getParameter("param");		// 파라미터값을 변수에 저장
		
		mDTO.setMid(mid);								// mDTO에 아이디 저장
		mDTO.setSearchCondition("ID중복검사");				// mDTO에 검색조건 저장
		
		mDTO = mDAO.selectOne(mDTO);					// selectOne()을 통해 리턴값(객체) 저장
		
		if(mDTO == null) {								// mDTO가 null인 경우(중복x)
			out.print("1");								// 1 응답
		}
		else {											// mDTO가 null이 아닌 경우(중복o),
			out.print("0");								// 0 응답
		}
	}
}
