package ru.geekbrains.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLineItemDto {

    @JsonProperty
    private Long productId;

    @JsonProperty
    private Integer qty;

    @JsonProperty
    private String color;

    @JsonProperty
    private String material;

    public AddLineItemDto() {
    }



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
