package cn.gl.exception_handler;

import cn.gl.exception.MyException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomHandleException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof MyException){
            System.out.println("自定义异常");
        }else {
            ex.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", message);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
