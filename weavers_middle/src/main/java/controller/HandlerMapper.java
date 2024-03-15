package controller;

import java.util.HashMap;
import java.util.Map;

import controller.action.CartAction;
import controller.action.CartDeleteAction;
import controller.action.CheckoutAction;
import controller.action.CheckoutListAction;
import controller.action.CheckoutSuccessAction;
import controller.action.CleangingProductListAction;
import controller.action.ContactAction;
import controller.action.ErrorAction;
import controller.action.LoginAction;
import controller.action.LogoutAction;
import controller.action.MailSendAction;
import controller.action.MainAction;
import controller.action.MemberSelectOneAction;
import controller.action.MemberUpdateAction;
import controller.action.MypageAction;
import controller.action.PasswordCheckAction;
import controller.action.ProductDetailAction;
import controller.action.ProductListAction;
import controller.action.ProfileChangeAction;
import controller.action.RegisterAction;
import controller.action.RegisterSuccessAction;
import controller.action.ReviewAction;
import controller.action.ReviewInsertAction;
import controller.action.ReviewListAction;
import controller.action.ReviewUpdateAction;
import controller.action.SearchProductNameAction;
import controller.action.SkincareProductListAction;
import controller.action.UnregisterAction;
import controller.action.UnregisterSuccessAction;
import controller.action.WishListAction;


//POJO
//사용자의 요청정보에 알맞는 Controller(==Action 객체)를 반환
public class HandlerMapper {
	private Map<String,Action> mappings;
	
	public HandlerMapper() {
		
		// 매핑을 위한 map을 생성
		mappings=new HashMap<String,Action>();
		
		// map에 매핑할 주소(key)와 그에 대한 수행할 기능(value)을 저장
		// 싱글톤을 유지하기 위해 매핑을 사용
		mappings.put("/main.do", new MainAction());
		mappings.put("/cart.do", new CartAction());
		mappings.put("/checkout.do", new CheckoutAction());
		mappings.put("/checkoutList.do", new CheckoutListAction());
		mappings.put("/checkoutSuccess.do", new CheckoutSuccessAction());
		mappings.put("/cleangingProductList.do", new CleangingProductListAction());
		mappings.put("/contact.do", new ContactAction());
		mappings.put("/login.do", new LoginAction());
		mappings.put("/logout.do", new LogoutAction());
		mappings.put("/mypage.do", new MypageAction());
		mappings.put("/passwordCheck.do", new PasswordCheckAction());
		mappings.put("/productDetail.do", new ProductDetailAction());
		mappings.put("/productList.do", new ProductListAction());
		mappings.put("/profileChange.do", new ProfileChangeAction());
		mappings.put("/register.do", new RegisterAction());
		mappings.put("/review.do", new ReviewAction());
		mappings.put("/reviewList.do", new ReviewListAction());
		mappings.put("/searchProductName.do", new SearchProductNameAction());
		mappings.put("/skincareProductList.do", new SkincareProductListAction());
		mappings.put("/registerSuccess.do", new RegisterSuccessAction());
		mappings.put("/unregister.do", new UnregisterAction());
		mappings.put("/unregisterSuccess.do", new UnregisterSuccessAction());
		mappings.put("/wishList.do", new WishListAction());
		mappings.put("/memberSelectOne.do", new MemberSelectOneAction());
		mappings.put("/reviewInsert.do", new ReviewInsertAction());
		mappings.put("/reviewUpdate.do", new ReviewUpdateAction());
		mappings.put("/memberUpdate.do", new MemberUpdateAction());
		mappings.put("/cartDelete.do", new CartDeleteAction());
		mappings.put("/mailSend.do", new MailSendAction());
		mappings.put("/error.do", new ErrorAction());
	}
	public Action getAction(String commend) {
		return mappings.get(commend); // get()을 통하여 인자에 key값을 받으면 해당 key값에 대한 value값을 가져온다.
	}
	
}
