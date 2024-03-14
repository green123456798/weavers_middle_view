package controller;

public class ActionForward { // 클래스명이 스프링에서 사용하는단어
	
	private String path; // 어디로 가야하는지 == 경로
	private boolean redirect; // 어떻게 갈지 == 리다이렉트 or 포워드
	
	public ActionForward() {
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	@Override
	public String toString() {
		return "ActionForward [path=" + path + ", redirect=" + redirect + "]";
	}
}
