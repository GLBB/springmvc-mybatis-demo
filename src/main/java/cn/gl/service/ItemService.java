package cn.gl.service;

import cn.gl.pojo.Items;

import java.util.List;

public interface ItemService {

    List<Items> queryItemList();

    Items queryItemById(Integer id);

    void updateItemById(Items item);
}
