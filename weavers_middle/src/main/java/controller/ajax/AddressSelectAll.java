package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.dao.AddressDAO;
import model.dto.AddressDTO;

@WebServlet("/addressSelectAll.asy")
public class AddressSelectAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddressSelectAll() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터를 전달할 때 한글이 깨지지 않도록하기 위해 인코딩 설정
		response.setCharacterEncoding("UTF-8");
		
		// 요청에 대한 응답을 보내기위해 printWriter 객체 생성
		PrintWriter out = response.getWriter();
		
		// 주소의 정보를 담을 aDTO 객체와, 주소테이블에 기능을 수행할 aDAO 객체를 생성
		AddressDTO aDTO = new AddressDTO();
		AddressDAO aDAO = new AddressDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		// aDTO에 사용자의 id를 저장
		aDTO.setMid((String)session.getAttribute("sessionMid"));
		// aDAO의 검색기능인 R(selectAll)기능을 사용하여 주소테이블에서 해당 사용자의 주소목록을 출력
		ArrayList<AddressDTO> adatas = aDAO.selectAll(aDTO);
		
		if (adatas == null) { // 만약 주소목록이 비어있다면
			out.print("[]"); // 빈 배열을 반환
		} else { // 만약 주소목록에 주소가 존재한다면
			// Gson 라이브러리를 사용하여 객체를 JSON 문자열로 변환 => 데이터를 json형식으로 바꿔주는 기능
			Gson gson = new Gson(); // Gson 객체 생성
			String json = gson.toJson(adatas); // 배열을 json타입으로 변환시킨뒤 문자열로 저장
			out.print(json); // 문자열로 변환된 배열을 출력
			System.out.println(json); 
		}
	}
}
