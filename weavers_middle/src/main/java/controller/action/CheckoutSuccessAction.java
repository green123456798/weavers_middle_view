package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Action;
import controller.ActionForward;
import model.dao.BuyProductDAO;
import model.dao.CartDAO;
import model.dao.ProductDAO;
import model.dao.SerialDAO;
import model.dto.AddressDTO;
import model.dto.BuyProductDTO;
import model.dto.CartDTO;
import model.dto.ProductDTO;
import model.dto.SerialDTO;

public class CheckoutSuccessAction implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 구매완료 페이지로 이동하는 기능의 클래스
      
      // 결제페이지 => 결제 완료 페이지로 넘어가면서 여러가지 기능이 존재 (트랜잭션)
      // 스프링에 가면 @트랜잭션에 대한 기능이 존재 ( 이전의 기능이 수행되었으나 이후의 기능이 정상 수행되지 않으면 이전의 기능을 다시 기능이 수행되기 이전의 상태로 되돌리는 기능 )
      // 지금은 트랜잭션기능이 존재하지 않아서 모든 기능이 정상적으로 작동한다고 가정
      // 트랜잭션과 비슷한 느낌을 유지하기 위해 try catch를 사용
      
      // 페이지이동기능을 담당하는 forward 객체를 생성 
      ActionForward forward = new ActionForward();
      
      // request로 세션을 받아와서 변수로 저장
      HttpSession session = request.getSession();
      
      // 세션에서 사용자의 id를 검색하여 변수로 저장
      String mid = (String)session.getAttribute("sessionMid");
      
      // 유효성검사를 위한 flag변수 선언 및 초기화;
      boolean flag = false;
      
      // 장바구니 기능을 사용하기 위해 cDTO, cDAO 객체 생성;
      CartDTO cDTO = new CartDTO();
      CartDAO cDAO = new CartDAO();
      
      // 장바구니DTO에 사용자 id를 저장하여 검색기능인 R(selectAll) 기능을 사용하여 해당 유저의 장바구니목록을 받아옴
      cDTO.setMid(mid);
      ArrayList<CartDTO> datas = cDAO.selectAll(cDTO);
      
      if(datas.isEmpty()) { // 만약 장바구니에 물품이 없을경우 장바구니페이지로 보냄 (유효성 검사)
         forward.setPath("cart.do");
         forward.setRedirect(true);
         return forward;
      }
      
      // 주소를 나타내는 객체인 aDTO 생성
      AddressDTO aDTO = new AddressDTO();
      
      String aname = request.getParameter("aname"); // aname(배송지명) 파라미터를 받아와서 변수로저장
      if(aname == null) { // 만약 aname(배송지명)이 null이라면
         aname = "임시작성배송지"; // 배송명을 초기화
      }
      // 파라미터들을 받아와서 변수로 저장 (zonecode = 우편번호, roadAddress = 도로명주소, detail = 상세주소)
      String zonecode = request.getParameter("checkout_zonecode");
      String roadAddress = request.getParameter("checkout_roadAddress");
      String detail = request.getParameter("checkout_detail");
      
      // aDTO에 파라미터로 받아온 상세주소, 도로명주소, 우편번호 변수들을 저장
      aDTO.setDetail(detail);
      aDTO.setRoadaddress(roadAddress);
      aDTO.setZonecode(zonecode);
         
      request.setAttribute("aDTO", aDTO); // 파라미터값을 저장한 aDTO를 request에 저장   
      
      // 시리얼테이블에 주문번호 추가하기 (INSERT)
      // 주문번호를 저장할 sDTO, sDAO 객체 생성
      SerialDTO sDTO = new SerialDTO();
      SerialDAO sDAO = new SerialDAO();
      
      // 주문번호에 저장할 배송지 ( 주소와는 별개로 지정해야함 ), 주소로 배송지를 저장한다면? => 이사해서 주소를 변경할 경우 과거의 배송지도 같이 변경되는 경우가 발생!
      // 배송지는 request로 값들을 받아와서 한줄의 문자열로 저장
      String address = "["+aname+"] "+roadAddress+" "+ detail;
      
      try {
         // 주문번호DTO에 사용자 id와 사용자의 배송지를 입력하여 주문번호DAO의 Insert기능을 수행 ( 주문번호를 테이블에 저장 ) 
         sDTO.setMid(mid);
         sDTO.setDeliveryAddress(address);
         flag = sDAO.insert(sDTO);
         if(flag) {    // 테이블에 데이터가 성공적으로 저장이 됐다면
            System.out.println("SERIAL테이블에 데이터 추가 성공!"); // 콘솔에 성공문구 출력
         }
         else {
            throw new Exception(); // 실패했다면 강제로 예외 발생
         }
      }catch(Exception e) { // 예외가 발생했다면 실패문구 출력
         System.out.println("SERIAL테이블에 데이터 추가 실패");
         e.printStackTrace();
      }
      
      // 구매상품내역에도 구매한 상품들을 추가하기 (INSERT)
      // 구매상품을 저장할 bDTO, bDAO 객체 생성
      BuyProductDTO bDTO = new BuyProductDTO();
      BuyProductDAO bDAO = new BuyProductDAO();
      try {
         for(int i=0; i<datas.size(); i++) {      // 장바구니 배열 사이즈만큼 반복문 진행
            bDTO.setPpk(datas.get(i).getPpk());   // bDTO에 배열의 i번째 pk를 저장
            bDTO.setCnt(datas.get(i).getCnt());   // bDTO에 배열의 i번째 cnt를 저장
            flag = bDAO.insert(bDTO);         // insert를 해준다
            if(!flag) { // 구매상품테이블에 데이터 저장을 실패했다면 
               break; // 반복문 정지
            }
         }
         if(!flag) { // 구매상품테이블에 데이터 저장을 실패했다면
            throw new Exception(); // 강제로 예외 발생
         }
         else { // 구매상품테이블에 데이터 저장을 성공했다면 
            System.out.println("BUYPRODUCT테이블에 데이터 추가 성공!"); // 구매성공문구 출력
         }
      }catch(Exception e) { // 예외가 발생했다면 실패문구 출력
         System.out.println("BUYPRODUCT테이블에 데이터 추가 실패");
         e.printStackTrace();
      }
      
      // 구매한 상품들만큼 제품의 판매량도 증가하기 (UPDATE)
      // 제품의 정보를 저장할 pDTO, pDAO 객체 생성
      ProductDTO pDTO = new ProductDTO();
      ProductDAO pDAO = new ProductDAO();
      try {
         for(int i=0; i<datas.size(); i++) {      // 장바구니 배열 사이즈만큼 반복문 진행
            pDTO.setSales(datas.get(i).getCnt());   // 구매한 수량만큼 상품의 판매량 증가해줘야하므로
            pDTO.setPpk(datas.get(i).getPpk());      // pDTO에 해당 상품의 pk번호와 구매한 수량을 저장
            flag = pDAO.update(pDTO);            // pDAO.update()를 통하여 팬매량을 수정
            if(!flag) { // 상품테이블에 판매량수정을 실패했다면
               break; // 반복문 정지
            }
         }
         if(!flag) { // 상품테이블에 판매량 수정을 실패했다면
            throw new Exception(); // 강제로 예외 발생
         }
         else { // 상품테이블에 판매량 수정을 성공했다면 
            System.out.println("PRODUCT테이블에 데이터 수정 성공!"); // 구매성공문구 출력
         }
      }catch(Exception e) { // 예외가 발생했다면 실패문구 출력
         System.out.println("PRODUCT테이블에 데이터 수정 실패");
         e.printStackTrace();
      }
      
      // 구매가 완료 되었다면 장바구니에서 구매한 물건들 전부삭제 => 해당 사용자의 장바구니 전부 비우기
      try {
         // 장바구니DTO에 사용자의 id와 '전체삭제'조건을 입력
         cDTO.setMid(mid);
         cDTO.setSearchCondition("전체삭제");
         flag = cDAO.delete(cDTO); // 장바구니DTO를 사용하여 삭제기능인 D(DELETE)기능을 사용하여 해당유저의 장바구니의 모든 상품 삭제
         if(flag) { // 장바구니 테이블에서 데이터 삭제가 성공했다면
            System.out.println("CART 테이블에 데이터 삭제 성공!"); // 삭제성공문구 출력
         }
         else { // 장바구니 테이블에서 데이터삭제가 실패했다면
            throw new Exception(); // 강제로 예외 발생
         }
      }
      
      catch (Exception e) { // 예외가 발생했다면
         System.out.println("CART테이블에 데이터 삭제 실패"); // 삭제실패문구 출력
         e.printStackTrace();
      }
      
      // 최근구매한 상품들을 출력해서 결제완료페이지에 보내줘야하므로
      // bDTO에 id와 검색조건을 설정하여 검색기능인 R(selectAll)기능을 사용하여 최근 구매한 상품목록을 받아옴 
      bDTO.setMid(mid);
      bDTO.setSearchCondition("최근구매내역");
      ArrayList<BuyProductDTO> bdatas = bDAO.selectAll(bDTO);
      request.setAttribute("bdatas", bdatas); // request에 최근 구매한 상품목록을 저장
      
      // 이메일 수신동의 했으면 이메일 보내기
      String checkMarketing = request.getParameter("marketing"); // marketing(이메일 수신 동의)파라미터를 받아와서 변수로 저장
      System.out.println(checkMarketing);
      if(checkMarketing == null) { // 만약 이메일수신동의를 하지 않았다면 
         forward.setPath("checkoutSuccess.jsp"); // 결제완료 페이지로 이동
         forward.setRedirect(false);
      }
      else {
         forward.setPath("mailSend.do"); // 만약 이메일 수신동의를 했다면
         forward.setRedirect(false); // 메일전송 페이지로 이동
      }

      return forward;
   }
}