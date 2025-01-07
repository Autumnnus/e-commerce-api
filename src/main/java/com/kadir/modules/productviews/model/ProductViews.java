package com.kadir.modules.productviews.model;

import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_views")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductViews extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "view_date", nullable = false)
    private LocalDateTime viewDate;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "view_count")
    private int viewCount;
}
