package com.wgmao.article.repository;

import com.wgmao.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/16 22:06
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
}
