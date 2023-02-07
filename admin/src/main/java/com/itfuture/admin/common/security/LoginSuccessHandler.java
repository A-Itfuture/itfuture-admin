package com.itfuture.admin.common.security;

import cn.hutool.json.JSONUtil;
import com.itfuture.admin.entity.R;
import com.itfuture.admin.util.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**登陆成功处理器
 * @author： wxh
 * @version：v1.0
 * @date： 2023/01/11 12:27
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String username = "user";
        String token = JwtUtils.genJwtToken(username);

        outputStream.write(JSONUtil.toJsonStr(R.ok("登陆成功").put("authorization",token)).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
