// 一个简单的静态文件用来管理redis的key

package com.nowcoder.community.util;

public class RedisKeyUtil {

    // 设定我们拼接Key需要的关键词
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 生成记录某个用户关注的实体的key
     * followee:userId:entityType -> zset(entityId,now)
     * now用来按先后顺讯显示关注对象
     * @param userId 用户ID
     * @param entityType 实体的类型
     * @return 返回拼接而成的Key
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    /**
     * 生成记录某个实体拥有的粉丝的key
     * follower:entityType:entityId -> zset(userId,now)
     * now用来按先后顺讯显示粉丝，方便按时间统计
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return 返回拼接的key
     */
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 登录验证码
     * @param owner 用户临时凭证来链接用户和验证码
     * @return
     */
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * 登录的凭证
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 拼接储存用户的key
     * @param userId 用户的ID
     * @return 用户key
     */
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

}
