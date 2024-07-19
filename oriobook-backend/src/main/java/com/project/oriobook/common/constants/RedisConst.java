package com.project.oriobook.common.constants;

public class RedisConst {
    /* cart:userId:productId */
    public static final String CART_SET_CACHE = "cart:%s:%s";
    public static final String CART_GET_CACHE = "cart:%s";
}
