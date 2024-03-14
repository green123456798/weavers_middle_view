package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AddressDAO;
import model.dto.AddressDTO;


@WebServlet("/addressInsert.asy")
public class AddressInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddressInsert() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 대한 응답을 보내기위해 printWriter 객체 생성
		PrintWriter out = response.getWriter();
		
		// 주소의 정보를 담을 aDTO 객체와, 주소테이블에 기능을 수행할 aDAO 객체를 생성
		AddressDTO aDTO = new AddressDTO();
		AddressDAO aDAO = new AddressDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// request로 배송지명을 받아와서 변수로 저장 (aname = 배송지명)
		String aname = request.getParameter("aname");
		if(aname == "") { // 만약 배송지명이 비어있다면
			aname = "이름없는배송지"; // 배송지명을 '이름없는배송지'로 초기화
		}
		// request와 session으로 파라미터와 attribute를 받아와서 변수로 저장
		// (sessionMid = 사용자의id, zondecode = '우편번호', jibunAddress = 지번주소)
		String mid = (String)session.getAttribute("sessionMid");
		String zonecode = request.getParameter("zondecode");
		String jibunAddress = request.getParameter("jibunAddress");
		if(jibunAddress == "") { // 만약 지번주소가 비어있다면 => 여러개의 지번주소가 하나의 도로명주소를 공유하기도 함 => 이때 도로명주소 선택시 지번주소가 미정
			jibunAddress = ""; // 지번주소를 빈칸으로 초기화
		}
		// request로 파라미터를 받아와서 변수로 저장 (roadAddress = 도로명주소, detail = 상세주소)
		String roadAddress = request.getParameter("roadAddress");
		String detail = request.getParameter("detail");
		if(detail == "null"){ // 만약 상세주소가 존재하지 않는다면
			detail = ""; // 상세주소를 빈칸으로 초기화
		}
		
		// 위에서 초기화한 변수값들을 aDTO에 저장
		aDTO.setAname(aname);
		aDTO.setMid(mid);
		aDTO.setZonecode(zonecode);
		aDTO.setJibunaddress(jibunAddress);
		aDTO.setRoadaddress(roadAddress);
		aDTO.setDetail(detail);
		
		System.out.println(aDTO);
		
		boolean flag = aDAO.insert(aDTO); // aDAO의 추가기능인 C(insert)기능을 통해서 주소 테이블에 주소를 추가
		if(flag) { // 주소테이블에 주소추가를 성공한경우
			System.out.println("추가성공"); // 콘솔에 성공문구 출력
			out.print("1");	// 1을 출력
		}
		else { // 주소테이블에 주소추가를 실패한 경우
			System.out.println("추가실패"); // 콘솔에 실패문구 출력
			out.print("0");	// 0을 출력
		}
	}
}
