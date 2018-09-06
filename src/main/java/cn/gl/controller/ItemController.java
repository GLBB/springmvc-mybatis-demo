package cn.gl.controller;

import cn.gl.exception.MyException;
import cn.gl.pojo.Items;
import cn.gl.pojo.QueryVo;
import cn.gl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/itemList")
    public ModelAndView queryItemList(){

        List<Items> items = itemService.queryItemList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemList", items);
        modelAndView.setViewName("itemList");
        return modelAndView;
    }

    // 根据 HttpServletRequest 获得请求参数 第一种方法
//    @RequestMapping("/itemEdit")
//    public ModelAndView queryItemById(HttpServletRequest request){
//        String strId = request.getParameter("id");
//        Integer id = Integer.parseInt(strId);
//        Items item = itemService.queryItemById(id);
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("item", item);
//        modelAndView.setViewName("editItem");
//        return modelAndView;
//    }

    // 第二种方法
//    @RequestMapping("/itemEdit")
//    public String queryItemById(HttpServletRequest request, Model model){
//        String strId = request.getParameter("id");
//        Integer id = Integer.parseInt(strId);
//
//        Items item = itemService.queryItemById(id);
//        model.addAttribute("item", item);
//        return "editItem";
//    }

//     第三种方法
//    @RequestMapping("/itemEdit")
//    public String queryItemById(HttpServletRequest request, ModelMap modelMap){
//        String strId = request.getParameter("id");
//        Integer id = Integer.parseInt(strId);
//        Items item = itemService.queryItemById(id);
//        modelMap.addAttribute("item", item);
//        return "editItem";
//    }

    // 参数绑定
//    @RequestMapping("/itemEdit")
//    public String queryItemById(Integer id, ModelMap modelMap){
//        Items item = itemService.queryItemById(id);
//        modelMap.addAttribute("item", item);
//        return "editItem";
//    }

    // @RequestParam
    // 用 myId 来接受 id
//    value：参数名字，即入参的请求参数名字，如value=“itemId”表示请求的参数	   区中的名字为itemId的参数的值将传入
//
//    required：是否必须，默认是true，表示请求中一定要有相应的参数，否则将报错
//    TTP Status 400 - Required Integer parameter 'XXXX' is not present
//
//    defaultValue：默认值，表示如果请求中没有同名参数时的默认值
    @RequestMapping("/itemEdit")
    public String queryItemById(@RequestParam(value = "id") Integer myId, ModelMap modelMap){
        Items item = itemService.queryItemById(myId);
        modelMap.addAttribute("item", item);
        return "editItem";
    }

    // 绑定pojo类型 更新数据库中的值 为了上传图片演示而注释
//    @RequestMapping("/updateitem")
//    public String updateItem(Items item){
        //
//        System.out.println(item.getCreatetime());
//        itemService.updateItemById(item);
//        return "success";
//    }

    // 上传图片
    @RequestMapping("/updateitem")
    public String updateItem(Items item, MultipartFile pictureFile) throws IOException {
        String picName = UUID.randomUUID().toString();
        String oriName = pictureFile.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));

        pictureFile.transferTo(new File("F:\\pic\\"+picName+extName));
        item.setPic(picName + extName);
        itemService.updateItemById(item);

        return "redirect:/itemEdit.action?id=" + item.getId();
    }

    // 获得 QueryVo 中的值
    @RequestMapping("/item/queryitem.action")
    public String queryItem(QueryVo vo){
        System.out.println(vo.getItem().getId());
        System.out.println(vo.getItem().getName());
        return "success";
    }

    // 获得checkbox选中的ids
    @RequestMapping("/item/getIds.action")
    public String getIds(Integer[] ids, QueryVo vo){
        System.out.println("ids: "+Arrays.toString(ids));
        System.out.println("vo.ids: "+ Arrays.toString(vo.getIds()));
        System.out.println("vo.itemList: " + vo.getItemList());
        return "success";
    }

    // 重定向 或 转发

    // 返回值void 重定向
    @RequestMapping("/VoidRedirect.action")
    public void testVoidRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getContextPath());
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // void 转发
    @RequestMapping("/VoidForword.action")
    public void testVoidForword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("void forword");
        request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
    }

    // string 重定向
    @RequestMapping("/StringRedirect.action")
    public String testStringRedirect(){
        return "redirect:/index.jsp";
    }



    // string 转发
    @RequestMapping("/StringForword")
    public String testStringForword(){
//        return "forward:/itemEdit.action";
        return "success";
    }

    @RequestMapping("/StringForwordController")
    public String testStringForwordCotroller(){
        return "forward:/updateitem.action";
    }

    // 自定义异常
    @RequestMapping("/myexception")
    public String testMyExcetpion() throws MyException {
        throw new MyException("my exception");
    }

    // 运行时异常
    @RequestMapping("/runtimeException.action")
    public String testRuntimeException(){
        int a = 1 / 0;
        return "success";
    }

    @RequestMapping("/getJson")
    public void testGetJosn(@RequestBody Items item){
        System.out.println(item);
    }

    @RequestMapping("/returnJson")
    public
    @ResponseBody Items testSendJosn(){
        Items item = new Items();
        item.setId(1);
        item.setName("台式机");
        item.setPic("2.jpg");
        item.setCreatetime(new Date());
        item.setPrice(6000f);
        item.setDetail("质量好");
        return item;
    }

    // RESTful 风格
    // 根据id 查询返回 json 数据
    @RequestMapping("/item/{id}")
    @ResponseBody
    public Items testRESTful(@PathVariable Integer id){
        Items item = itemService.queryItemById(id);
        return item;
    }


}
