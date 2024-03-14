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

@WebServlet("/addressDelete.asy")
public class AddressDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddressDelete() {
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
		
		// aDTO에 파라미터로 받아온 apk(주소의 PK번호)값을 저장
		aDTO.setApk(Integer.parseInt(request.getParameter("apk")));
		boolean flag = aDAO.delete(aDTO); // aDAO의 삭제기능인 D(delete)기능을 사용하여 주소 테이블에서 해당 주소를 삭제
		if(flag) { // 주소테이블에서 주소삭제에 성공했다면
			out.print("1");	// 1을 출력
		}
		else { // 주소테이블에서 주소삭제에 실패했다면 
			out.print("0");	// 0을 출력
		}
		
	}

}
