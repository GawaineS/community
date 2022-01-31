package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    // 注入Redis的Template来操作Redis数据库
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞的逻辑实现
     * @param userId 点赞的对象
     * @param entityType 被点赞的类型（帖子或者回复）
     * @param entityId 被点赞的ID
     * @param entityUserId 被点赞的实体的作者（我们需要在调用这个方法的时候传入作者，这样我们就不用去MySQL中找，提升性能）
     */
    public void like(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                // 获得key
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                // 确定是否已经点过赞
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                // 上面的查询步骤必须在Redis事务开始之前，因为事务中间是无法查询的
                // 开启事务
                operations.multi();

                // 已经点过赞就删除赞
                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                // 没有的话就添加数据
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                // 关闭事务
                return operations.exec();
            }
        });
    }

    /**
     * 查询某实体点赞的数量
     * @param entityType 该实体的类型
     * @param entityId 该实体的ID
     * @return 该实体收到的点赞的数量
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某实体的点赞状态
     * 1 表示点赞了
     * 0 表示尚未点赞
     * @param userId 点赞人的ID
     * @param entityType 点赞实体的类型
     * @param entityId 点赞实体的ID
     * @return 该ID用户对该ID的实体是否点赞
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    /**
     * 查询某个用户获得的赞
     * @param userId 用户的ID
     * @return 该ID用户获得赞的总数
     */
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }

}
