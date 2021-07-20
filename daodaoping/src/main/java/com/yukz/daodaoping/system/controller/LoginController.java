package com.yukz.daodaoping.system.controller;

import com.yukz.daodaoping.common.annotation.Log;
import com.yukz.daodaoping.common.config.BootdoConfig;
import com.yukz.daodaoping.common.controller.BaseController;
import com.yukz.daodaoping.common.domain.FileDO;
import com.yukz.daodaoping.common.domain.Tree;
import com.yukz.daodaoping.common.service.FileService;
import com.yukz.daodaoping.common.utils.*;
import com.yukz.daodaoping.pddhelp.utils.PathUtil;
import com.yukz.daodaoping.pddhelp.utils.XmlParser;
import com.yukz.daodaoping.system.domain.MenuDO;
import com.yukz.daodaoping.system.service.MenuService;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    BootdoConfig bootdoConfig;

    @GetMapping({"/", ""})
    String welcome(Model model) {

        return "redirect:/login";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        model.addAttribute("name", getUser().getName());
        //获取登录人客户编码及客户名称
        //通过步骤配置文件获取客户编码对应的客户名称
        String segmentName = "";//客户名称
        String path = PathUtil.getClassResources()
                + "static/gotoo-zz/buzhou1.xml";
        Element element = XmlParser.getRootNode(path);
        //获取segment元素列表
        List<Element> segmentListE = XmlParser.getChildList(element);
        //遍历segment元素列表，获取到当前登录人的segment元素
        if (null != segmentListE && segmentListE.size()>0) {
            for (int n=0; n<segmentListE.size(); n++) {
                Attribute codeAttr = XmlParser.getAttribute(segmentListE.get(n),"code");
                if (null != codeAttr) {
                    if (org.apache.commons.lang.StringUtils.equals(codeAttr.getValue(), getSegmentCode())) {
                        Attribute nameAttr = XmlParser.getAttribute(segmentListE.get(n),"name");
                        segmentName = nameAttr.getValue();
                    }
                }
            }
        }
        model.addAttribute("segmentName", segmentName);
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", bootdoConfig.getUsername());
        model.addAttribute("password", bootdoConfig.getPassword());
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password,String verify,HttpServletRequest request) {

        try {
            //从session中获取随机数
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (StringUtils.isBlank(verify)) {
                return R.error("请输入验证码");
            }
            if (random.equals(verify)) {
            } else {
                return R.error("请输入正确的验证码");
            }
        } catch (Exception e) {
            logger.error("验证码校验失败", e);
            return R.error("验证码校验失败");
        }
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户名或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }

}
