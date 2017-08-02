package cn.cherish.springcloud.web.controller;

import cn.cherish.springcloud.service.FeignUserService;
import cn.cherish.springcloud.service.dto.UserDTO;
import cn.cherish.springcloud.service.req.UserReq;
import me.cherish.web.MResponse;
import me.cherish.web.controller.ABaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends ABaseController {

    private final FeignUserService feignUserService;

    @Value("${securityconfig.urlroles}")
    private String urlroles;

    @Autowired
    public UserController(FeignUserService feignUserService) {
        this.feignUserService = feignUserService;
    }

    @GetMapping("/index")
    public String index(ModelMap model, Principal user) {
        Authentication authentication = (Authentication) user;
        List<String> userroles = new ArrayList<>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            userroles.add(ga.getAuthority());
        }

        boolean newrole = false, editrole = false, deleterole = false;
        if (!StringUtils.isEmpty(urlroles)) {
            String[] resouces = urlroles.split(";");
            for (String resource : resouces) {
                String[] urls = resource.split("=");
                if (urls[0].indexOf("new") > 0) {
                    String[] newroles = urls[1].split(",");
                    for (String str : newroles) {
                        str = str.trim();
                        if (userroles.contains(str)) {
                            newrole = true;
                            break;
                        }
                    }
                } else if (urls[0].indexOf("edit") > 0) {
                    String[] editoles = urls[1].split(",");
                    for (String str : editoles) {
                        str = str.trim();
                        if (userroles.contains(str)) {
                            editrole = true;
                            break;
                        }
                    }
                } else if (urls[0].indexOf("delete") > 0) {
                    String[] deleteroles = urls[1].split(",");
                    for (String str : deleteroles) {
                        str = str.trim();
                        if (userroles.contains(str)) {
                            deleterole = true;
                            break;
                        }
                    }
                }
            }
        }

        model.addAttribute("newrole", newrole);
        model.addAttribute("editrole", editrole);
        model.addAttribute("deleterole", deleterole);

        model.addAttribute("user", user);
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        MResponse<UserDTO> response = feignUserService.findOne(id);
        model.addAttribute("user", response.getData());
        return "user/show";
    }

    @GetMapping("/list")
    @ResponseBody
    public MResponse getList() {
        MResponse response = feignUserService.getList();
        log.info("【获取到的数据】 {}", response);
        return response;
    }

    @GetMapping("/new")
    public String create(ModelMap model, UserReq userReq) {
        model.addAttribute("user", userReq);
        return "user/new";
    }

    @PostMapping("/save")
    @ResponseBody
    public MResponse save(UserReq userReq) {
        log.info("【保存新用户】 {}", userReq);
        MResponse<UserDTO> response = feignUserService.findByUsername(userReq.getUsername());
        if (response.getData() != null) {
            return buildResponse(Boolean.TRUE, "该用户名已存在，请更换再试", null);
        } else {
            BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
            userReq.setPassword(bpe.encode(userReq.getPassword()));
            feignUserService.save(userReq);
            log.info("新增->ID {}", userReq.getId());
            return buildResponse(Boolean.TRUE, "保存成功", null);
        }
    }

    @GetMapping("/edit/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        MResponse<UserDTO> response = feignUserService.findOne(id);
        model.addAttribute("user", response.getData());
        return "user/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public MResponse update(UserReq userReq) {
        MResponse response = feignUserService.update(userReq);
        log.info("【修改用户】 ID: {}", userReq.getId());
        return response;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        feignUserService.delete(id);
        log.info("【删除用户】 ID： {}", id);
        return "1";
    }

}
