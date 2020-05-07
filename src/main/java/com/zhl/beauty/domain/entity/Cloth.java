package com.zhl.beauty.domain.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cloth")
public class Cloth {
    /**
     * Id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 衣服唯一编号
     */
    @Column(name = "cloth_id")
    private String clothId;

    /**
     * 图片存放地址
     */
    private String picture;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}