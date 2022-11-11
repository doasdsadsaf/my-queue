package com.duang.support.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duang.support.support.domain.MessageTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 消息模板Dao
 * @author 3y
 */
@Mapper
public interface MessageTemplateMapper extends BaseMapper<MessageTemplate> {


    /**
     * 查询 列表（分页)
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEquals(Integer deleted, Pageable pageable);


    /**
     * 统计未删除的条数
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);

}
