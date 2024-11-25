package vn.hoanggiang.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoanggiang.jobhunter.domain.response.RestResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    
    //áp dụng format cho tất cả các response
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    //supports: true -> thay đổi nội dung dữ liệu trả về
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        //lấy mã trạng thái HTTP
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        
        //định nghĩa cấu trúc dữ liệu trả về
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(status);

        if (body instanceof String || body instanceof Resource) {
            return body;
        }

        if (status >= 400) {
            //đã custom đã globalexception
            return body;
        } else {
            res.setData(body);
            res.setMessage("CALL API SUCCESS");
        }
        return res;
    }

}