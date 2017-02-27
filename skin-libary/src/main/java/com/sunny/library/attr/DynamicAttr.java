package com.sunny.library.attr;

/**
 * 用于Java代码生成的View属性的Model
 */
public class DynamicAttr {

    //属性名称
    public String attrName;

    //属性资源ID
    public int attrValueId;


    public DynamicAttr(String attrName, int attrValueId) {
        this.attrName = attrName;
        this.attrValueId = attrValueId;
    }
}
