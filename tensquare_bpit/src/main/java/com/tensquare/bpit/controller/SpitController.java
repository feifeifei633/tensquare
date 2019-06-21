package com.tensquare.bpit.controller;

import com.tensquare.bpit.pojo.Spit;
import com.tensquare.bpit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findOne(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public  Result findByParentid(@PathVariable String parentId,@PathVariable Integer page,@PathVariable Integer size){
        Page<Spit> pageList = spitService.findByParentid(parentId,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }

    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        String userid ="2023";

        if(redisTemplate.opsForValue().get("thumbup_"+userid+"_"+id)!=null){
            return new Result(false,StatusCode.REPERROR,"你已经点赞了");
        }

        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_"+userid+"_"+id,1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}

