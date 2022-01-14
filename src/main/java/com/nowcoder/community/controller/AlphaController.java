package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {return "Hello Spring Boot.";}

    @RequestMapping("/data")
    @ResponseBody
    public String find() {return alphaService.find();}

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // get requested data
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        // return data
        response.setContentType("text/html;charset=utf-8");
        try(
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>NowCoder</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET Request
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current", required = false, defaultValue = "1")int current,
                              @RequestParam(name = "limit", required = false, defaultValue = "20") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST Request
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // Response HTML Data
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "Carrie");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "Udub");
        model.addAttribute("age", 120);
        return "demo/view";
    }

    // Response JSON Data
    // Java object -> JSON string -> JS object

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Carrie");
        emp.put("age", 21);
        emp.put("salary", 12000);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Carrie");
        emp.put("age", 21);
        emp.put("salary", 12000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "Gavin");
        emp.put("age", 24);
        emp.put("salary", 8000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "Tom");
        emp.put("age", 23);
        emp.put("salary", 24000);
        list.add(emp);
        return list;
    }

}
