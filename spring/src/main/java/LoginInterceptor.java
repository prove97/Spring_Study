import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();	
		UserDTO u = (UserDTO)session.getAttribute("user");
		
		if(u == null) {
			return true;
		} else {
			response.sendRedirect("/"); //메인페이지 이동
			return false;
		}
	
	}

}