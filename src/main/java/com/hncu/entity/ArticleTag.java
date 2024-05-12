package com.hncu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.hncu.utils.Entity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章标签
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_article_tag")
@ApiModel(value="ArticleTag对象", description="文章标签")
public class ArticleTag extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer articleId;

    private Integer tagId;

    private String tagName;

    private Article article;
}
