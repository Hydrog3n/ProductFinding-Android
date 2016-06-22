package com.example.alehmann.productfinding.Classes;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by hydrog3n on 22/06/2016.
 */
public class OpenProduct {
    private long code;
    private Product product;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public class Product {
        private String product_name_fr;
        private List<String> brands_tags;

        public String getProduct_name_fr() {
            return product_name_fr;
        }

        public void setProduct_name_fr(String product_name_fr) {
            this.product_name_fr = product_name_fr;
        }

        public List<String> getBrands_tags() {
            return brands_tags;
        }

        public void setBrands_tags(List<String> brands_tags) {
            this.brands_tags = brands_tags;
        }
    }
}
