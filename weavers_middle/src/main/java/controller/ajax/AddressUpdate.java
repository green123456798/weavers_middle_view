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

@WebServlet("/addressUpdate.asy")
public class AddressUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddressUpdate() {
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
		aDTO.setApk(Integer.parseInt(request.getParameter("apk")));		
		AddressDAO aDAO = new AddressDAO();
		
		// request로 배송지명을 받아와서 변수로 저장 (aname = 배송지명)
		String aname = request.getParameter("aname");
		if(aname == null) { // 만약 배송지명이 없다면
			aname = "이름없는 배송지"; // 배송지명을 '이름없는 배송지'로 초기화
		}
		aDTO.setAname(aname); // aDTO에 배송지명 저장
		
		// request로 파라미터를 받아와서 저장 ( apk = 주소의 PK번호, zonecode = 우편번호, jibunAddress = 지번주소, roadAddress = 도로명주소)
		
		
		aDTO.setZonecode(request.getParameter("zondecode"));
		aDTO.setJibunaddress(request.getParameter("jibunAddress"));
		aDTO.setRoadaddress(request.getParameter("roadAddress"));
		
		// request로 상세주소 파라미터를 받아와서 변수로 저장 (detail = 상세주소)
		String detail = request.getParameter("detail");
		if(detail == null){ // 만약 상세주소가 null이라면
			detail = ""; // 빈칸으로 초기화
		}
		aDTO.setDetail(detail); // aDTO에 detail을 저장
		
		boolean flag = aDAO.update(aDTO); // aDAO의 수정기능인 U(update)기능으로 주소테이블의 정보를 수정
		if(flag) { // 주소테이블의 정보수정에 성공했다면
			out.print("1");	// 1을 출력
		}
		else { // 주소테이블의 정보수정에 실패했다면
			out.print("0");	 // 0을 출력
		}
	}
}
