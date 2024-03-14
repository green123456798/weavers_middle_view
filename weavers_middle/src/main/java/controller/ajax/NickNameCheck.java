package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

//----- 닉네임 중복체크 -----
@WebServlet("/nickNameCheck.asy")
public class NickNameCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NickNameCheck() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();			// HttpSession 객체를 통해 세션 관리
		PrintWriter out = response.getWriter();				// PrintWriter 선언 및 초기화 (응답 전송)
		
		MemberDTO mDTO = new MemberDTO();					// MemberDTO 객체 생성
		MemberDAO mDAO = new MemberDAO();					// MemberDAO 객체 생성

		String nickname = request.getParameter("param");	// 파라미터값을 변수에 저장
		
		mDTO.setNickname(nickname);							// mDTO에 닉네임 저장
		mDTO.setSearchCondition("닉네임중복체크");				// mDTO에 검색조건 저장
		
		mDTO = mDAO.selectOne(mDTO);						// selectOne()을 통해 리턴값(객체) 저장
		
		if(mDTO == null || mDTO.getMid().equals(session.getAttribute("sessionMid"))) {	// mDTO가 null인 경우(중복x), 개인정보수정에서 기존 닉네임을 사용하고 싶은 경우
			out.print("1");									// 1 응답
		}
		else {												// mDTO가 null이 아닌 경우(중복o),
			out.print("0");									// 0 응답
		}
	}

}
