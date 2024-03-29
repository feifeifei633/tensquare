package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    @RequestMapping(value = "/{labelid}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelid")String labelid){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelid));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result sava(@RequestBody Label label){
        labelService.sava(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/{labelId}")
    public Result update(@RequestBody Label label,@PathVariable String labelId){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/{labelid}")
    public Result deleteById(@PathVariable("labelid") String labelid){
        labelService.deleteById(labelid);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){

        Page<Label> pageDate = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageDate.getTotalElements(),pageDate.getContent()));
    }
}
