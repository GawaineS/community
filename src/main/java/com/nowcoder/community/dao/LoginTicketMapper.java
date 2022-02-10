// 这个文件配置的是如何通过接口来操作MySQL里面的login ticket这个表
// Login ticket存在cookies中可以让我们通过ID定位到User，从而不用在cookie里面储存用户这么敏感的消息

package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) ",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id") //让MySQL自动生成ID的value
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status = #{status} where ticket = #{ticket}",
            "<if test=\" ticket != null\">",
            "and 1 = 1",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
