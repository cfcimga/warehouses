package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.User;
import com.managementsystem.service.UserService;
import com.managementsystem.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/findAllUser")
    public Result findAllUser()
    {
        List<User> result = userService.list();
        if (result.size() != 1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }

    }
    @GetMapping("/findUserById")
    public Result findUserById(Long uId)
    {
        User result = userService.findUserById(uId);
        if (result != null){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }

    }

    @RequestMapping("/findUserByIdAndPassword")
    public Result findUserByIdAndPassword(Integer uId,String uPassword){
        User result = userService.findUserByIdAndPassword(uId,uPassword);

        if (result != null){
            result.setToken(TokenUtil.sign(result));
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }


    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
//        return  Result.success(user);
        User user1 = userService.findUserById(user.getuId());
        System.out.println("user1:"+user1);
        if(user1!=null){
            return Result.fail("添加用户失败，该用户编号已经存在！");
        }
        int result = userService.addUser(user);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editUser")
    public Result editUser(@RequestBody User user){ //(required=false)
//        return Result.success(carrier);
        int result = userService.editUser(user);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",user);
        }
    }

    @DeleteMapping("/deleteUser/{uId}")
    public Result deleteUser(@PathVariable("uId") Integer uId){
        int result = userService.deleteUser(uId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",uId);
        }
    }

    @RequestMapping("/addImg/{uId}")
    @ResponseBody
    public String addImg(@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response,@PathVariable("uId") Integer uId)throws Exception{
        System.out.println("上传图片是否为空："+file.isEmpty());
        System.out.println("上传uId："+uId);
        //返回文件的存储路径
        if(file != null){
            String path =null;// 文件路径
            String imgType=null;//图片类型
            String trueFileName=null;//返回值
            String  fileName = file.getOriginalFilename();// 原文件名称
            // 判断图片类型
            imgType=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            System.out.println("imgType为"+imgType);
            if(imgType!=null){
                if("GIF".equals(imgType.toUpperCase()) || "PNG".equals(imgType.toUpperCase()) || "JPG".equals(imgType.toUpperCase()) || "JPEG".equals(imgType.toUpperCase()) || "JFIF".equals(imgType.toUpperCase())){
                    // 项目在容器中实际发布运行的根路径
//                     String realPaths = request.getSession().getServletContext().getRealPath("/");
                    String realPath = ResourceUtils.getURL("classpath:").getPath()+ "static/headPortrait/";  //部署在服务器时使用，获取resource资源位置，就是/G:/warehouse/backEndWarehouse/target/classes/
                    System.out.println("file为"+realPath);
//                    String realPath = "G:\\warehouse\\backEndWarehouse\\src\\main\\resources\\static\\headPortrait\\";
//                    String realPath = "G:\\warehouse\\backEndWarehouse\\target\\classes\\static\\headPortrait\\";
                    trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置图片存放的路径
                    path=realPath+trueFileName;
//                    System.out.println("图片的存放路径为"+path);
                    // 转存文件到指定路径
                    file.transferTo(new File(path)); // 转存而不是写出
                    System.out.println("文件成功上传到指定目录下");
                }else{
                    System.out.println("请上传GIF、PNG或者JPG格式的文件");
                }
            }else{
                System.out.println("文件类型为空");
            }
            return "headPortrait/"+trueFileName;
//            return realPaths
        }else{
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:"+file.getOriginalFilename());

        return "";
    }
    @RequestMapping("/deleteImg")
    public Result deleteImag(String path){
//        G:\warehouse\backEndWarehouse\src\main\resources\static\
        String src = "G:/warehouse/backEndWarehouse/target/classes/static/";
        java.io.File file = new java.io.File(src+path);
        boolean flag = file.delete();
        return Result.success(flag);
    }
}
