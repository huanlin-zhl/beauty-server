package com.zhl.beauty.domain.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cloth_scheduled_log")
public class ClothScheduledLog {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 衣服Id
     */
    @Column(name = "cloth_id")
    private String clothId;

    /**
     * 预定使用时间
     */
    @Column(name = "scheduled_time")
    private Date scheduledTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}