package com.smart_justice.smart_justice.controller;

import com.smart_justice.smart_justice.model.Lawyer;
import com.smart_justice.smart_justice.model.LawyerTeam;
import com.smart_justice.smart_justice.model.User;
import com.smart_justice.smart_justice.model.bo.LawyerBO;
import com.smart_justice.smart_justice.service.LawyerService;
import com.smart_justice.smart_justice.service.LawyerTeamService;
import com.smart_justice.smart_justice.service.UserService;
import com.smart_justice.smart_justice.util.JsonResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 企业用户Controller类
 *
 * @author tudou
 * @version 1.0
 * @date 2020/7/14 15:20
 **/


@RestController
@RequestMapping("/lawyer")
public class LawyerController {

    @Autowired
    private LawyerService lawyerService;

    @Autowired
    private LawyerTeamService lawyerTeamService;

    @Autowired
    private UserService userService;

    /**
     * 企业用户个人中心信息
     */
    @RequestMapping("/info")
    public JsonResult lawyerInfo(HttpServletResponse response, HttpSession session){
        Integer userId= (Integer) session.getAttribute("user_id");
        Integer teamId= (Integer) session.getAttribute("team_id");
        String username=(String) session.getAttribute("username");
        if(userId==null||teamId==null|| username.equals("")){
            return JsonResult.errorMsg("请用户重新登陆");
        }

        User user = userService.getUserInfo(userId);
        if(user==null){
            return JsonResult.errorMsg("该用户不存在");
        }
        Lawyer lawyer=lawyerService.getLawyerInfoByUserId(userId);
        if(lawyer==null){
            return JsonResult.errorMsg("用户无权限");
        }
        LawyerTeam lawyerTeam=lawyerTeamService.getLawyerTeamInfo(teamId);
        if(lawyerTeam==null){
            return JsonResult.errorMsg("该企业不存在");
        }
        LawyerBO lawyerBO=new LawyerBO(userId,teamId,username,user.getRegisterTime(),lawyerTeam.getName(),lawyer.getWorkId(),user.getPhone(),user.getEmail());

        return JsonResult.ok(lawyerBO);
    }

    /**
     * 企业用户修改个人信息
     */
    @RequestMapping("/update")
    public JsonResult lawyerInfoUpdate(@RequestParam(value = "work_id",required = false)String workId,
                                       @RequestParam(value = "phone",required = false)String phone,
                                       @RequestParam(value = "email",required = false)String email,
                                       @RequestParam(value = "real_name",required = false)String realName,
                                       HttpServletResponse response,HttpSession session){
        Integer userId= (Integer) session.getAttribute("user_id");
        Integer teamId= (Integer) session.getAttribute("team_id");
        if(userId==null){
            return JsonResult.errorMsg("请用户重新登陆");
        }
        if(teamId==null||teamId==0){
            return JsonResult.errorMsg("请用户加入团队");
        }
        User user=new User();
        user.setId(userId);
        if(!Strings.isBlank(phone)) {
            user.setPhone(phone);
        }
        if(!Strings.isBlank(realName)){
            user.setRealName(realName);
        }
        if(!Strings.isBlank(email)){
            user.setEmail(email);
        }
        if(Strings.isBlank(workId)){
            workId=null;
        }
        boolean is=lawyerService.updateLawyerInfo(user,workId,teamId);
        if(!is){
            return JsonResult.errorMsg("修改失败");
        }

        return JsonResult.ok();
    }

}
