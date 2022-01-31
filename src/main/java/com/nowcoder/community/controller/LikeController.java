package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    // 注入点赞操作的业务内容
    @Autowired
    private LikeService likeService;

    // 注入存储用户信息的线程容器
    @Autowired
    private HostHolder hostHolder;


    /**
     * 实现用户的点赞的功能
     * 1 记录用户执行的点赞或者撤销点赞的操作
     * 2 更新被点赞实体收到的点赞数量
     * 3 更新页面显示的点赞状态
     * @param entityType 点赞的对象种类
     * @param entityId 点赞对象的ID（用来在数据库中查询）
     * @param entityUserId 操作点赞的用户
     * @return 这个map封装了点赞的数量和点赞的状态，返回给页面来显示
     */
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId) {
        // 获取当前登录的用户信息
        User user = hostHolder.getUser();

        // 记录用户点赞的操作
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 获得点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 更新点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        // 实例化一个map来封装点赞的状态和数量
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 返回上述的结果
        return CommunityUtil.getJSONString(0, null, map);
    }

}
