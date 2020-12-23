//package bit.group.ourchat.Interceptor;
//
//import bit.group.ourchat.entity.exampleEntity;
//import bit.group.ourchat.entity.user;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Component
//public class SecurityInterceptor extends HandlerInterceptorAdapter {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        HttpSession session = request.getSession();
//
////            判断是否已有该用户登录的session
//        if(session.getAttribute("user") != null){
//            user user1= (user) session.getAttribute("user");
////            System.out.println(customer.getName()+" "+customer.getPassword());
//            return true;
//        }
//
////            跳转到登录页
//        String url = "/login";
//        response.sendRedirect(url);
//        return false;
//    }
//}
