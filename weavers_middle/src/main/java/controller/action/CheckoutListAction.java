package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.BuyProductDAO;
import model.dto.BuyProductDTO;
import model.dto.SerialDTO;


public class CheckoutListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 내 구매내역 페이지로 이동하는 기능의 클래스
		
		// 페이지이동기능을 담당하는 forward 객체를 생성 
		ActionForward forward = new ActionForward();
		
		// request로 세션을 받아와서 세션 변수에 값을 저장
		HttpSession session = request.getSession();
		
		// 구매한상품에 대한 bDTO, bDAO 생성
		BuyProductDTO bDTO = new BuyProductDTO();
		BuyProductDAO bDAO = new BuyProductDAO();
		
		// bDTO에 사용자의 id와 검색조건을 저장
		bDTO.setMid((String)session.getAttribute("sessionMid"));
		bDTO.setSearchCondition("전체구매내역");
		
		// 값이 저장된 bDTO를 검색기능인 R(selectAll)기능을 통해서 해당 사용자의 구매내역을 받아옴
		ArrayList<BuyProductDTO> datas = bDAO.selectAll(bDTO);
		// 받아온 구매내역을 주문번호별로 묶기 위해 맵형식으로 저장
		TreeMap<Integer, ArrayList<BuyProductDTO>> mapData = new TreeMap<>(Collections.reverseOrder());
		
		for (BuyProductDTO data : datas) { // 반복문을 사용하여 모든 구매내역을 맵으로 저장
            int spk = data.getSpk();
            // 이미 해당 spk(주문번호)로 묶인 리스트가 있는지 확인
            if (mapData.containsKey(spk)) { // 해당 구매내역의 주문번호가 이미 key로 존재한다면
                mapData.get(spk).add(data); // 해당 key(주문번호)에 값을 추가
            } else {
                // 해당 spk(주문번호)로 묶인 리스트가 없다면 새로 생성하여 추가하여 값(해당 구매내역)을 저장
            	ArrayList<BuyProductDTO> newList = new ArrayList<>();
                newList.add(data);
                mapData.put(spk, newList);
            }
        }
		
		if(datas.size() == 0) { // 구매내역이 존재하지 않을경우 '상품없음'메세지를 보냄
			request.setAttribute("msg", "구매한 내역이 없습니다.");
			forward.setPath("checkoutList.jsp");
			forward.setRedirect(false);
		}
		else { // 구매내역이 존재할 경우 selectAll로 받아온 목록 출력해서 보냄
			request.setAttribute("mapData", mapData);
			forward.setPath("checkoutList.jsp");
			forward.setRedirect(false);
		}
		return forward;
	}
}
