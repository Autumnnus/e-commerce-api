package com.kadir.modules.favoriteproduct.model;

import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favorite_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProduct extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "is_active")
    private boolean isActive = true;

}
