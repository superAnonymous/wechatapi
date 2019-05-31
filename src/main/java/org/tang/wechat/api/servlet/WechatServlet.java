package org.tang.wechat.api.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.tang.wechat.api.configs.WechatApiConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = {"/wechat/wechatServlet"},
        loadOnStartup = 1,
        initParams = {@WebInitParam(name = "username", value = "Jack")}
)
public class WechatServlet extends HttpServlet {
    private static final long serialVersionUID = -5767625680763237936L;
    private WechatApiConfig wechatApiConfig;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        wechatApiConfig = (WechatApiConfig)context.getBean("wechatApiConfig");
    }
}
