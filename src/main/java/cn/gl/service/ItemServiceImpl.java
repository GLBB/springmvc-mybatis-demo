package cn.gl.service;

import cn.gl.mapper.ItemsMapper;
import cn.gl.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<Items> queryItemList() {
//        List<Items> items = itemsMapper.selectByExample(null);
        // 数据库中 detail 类型为 text 类型， 所以要加上 BLOBs
        List<Items> items = itemsMapper.selectByExampleWithBLOBs(null);
        return items;
    }

    @Override
    public Items queryItemById(Integer id) {
        Items item = itemsMapper.selectByPrimaryKey(id);
        return item;
    }

    @Override
    public void updateItemById(Items item) {
        itemsMapper.updateByPrimaryKeySelective(item);
    }
}
