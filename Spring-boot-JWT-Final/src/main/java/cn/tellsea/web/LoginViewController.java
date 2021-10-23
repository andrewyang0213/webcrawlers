package cn.tellsea.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tellsea.bean.User;
import cn.tellsea.mapper.UserMapper;
import cn.tellsea.service.UserService;

@Controller
public class LoginViewController {

    @Autowired
    UserService userService;

    // 预先设置好的正确的用户名和密码，用于登录验证
    // private String rightUserName = "admin";
    // private String rightPassword = "admin";

    /**
     * 登录校验
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {

        String redirect = request.getParameter("redirect");
        if (redirect == "") {
            redirect = "/images";
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (null == username || null == password) {
            return "<script>alert('not null')</script>";
        }

        User match = userService.checkUserPass(username, password);

        if (match == null) {
            // System.out.println(match);
            return "<script>alert('password err')</script>";
        }

        // 前端传回的密码实际为用户输入的：用户名（小写）+ 密码（小写）组合的字符串生成的md5值
        // 此处先通过后台保存的正确的用户名和密码计算出正确的md5值，然后和前端传回来的作比较
        // String md5info = rightUserName + rightPassword;
        // String inputInfo = username + password;
        // String inputPassword = DigestUtils.md5DigestAsHex(inputInfo.getBytes());
        // String realPassword = DigestUtils.md5DigestAsHex(md5info.getBytes());
        // if (!inputPassword.equals(realPassword)) {
        // return "<script>alert('password err')</script>";
        // }

        // 校验通过时，在session里放入一个标识
        // 后续通过session里是否存在该标识来判断用户是否登录
        request.getSession().setAttribute("loginName", "admin");
        return "redirect:" + redirect;
    }

    /**
     * 注销登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

}