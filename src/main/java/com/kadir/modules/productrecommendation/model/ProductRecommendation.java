package com.kadir.modules.productrecommendation.model;

import com.kadir.common.enums.RecommendationType;
import com.kadir.common.model.BaseEntity;
import com.kadir.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_recommendation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "recommended_product_id", referencedColumnName = "id")
    private Product recommendedProduct;

    @Enumerated(EnumType.STRING)
    private RecommendationType recommendationType;

}
