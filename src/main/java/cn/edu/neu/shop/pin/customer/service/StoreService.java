package cn.edu.neu.shop.pin.customer.service;

import cn.edu.neu.shop.pin.mapper.PinStoreMapper;
import cn.edu.neu.shop.pin.model.PinStore;
import cn.edu.neu.shop.pin.util.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService extends AbstractService<PinStore> {

    @Autowired
    private PinStoreMapper pinStoreMapper;

    /**
     * 根据店铺ID 获取该店铺的详细信息
     * @param storeId
     * @return PinStore类
     */
    public PinStore getStoreById(Integer storeId) {
        PinStore pinStore = new PinStore();
        pinStore.setId(storeId);
        return pinStoreMapper.selectOne(pinStore);
    }

    /**
     * 根据店铺所有者的id返回店铺的list
     * @param id 店铺所有者的id
     * @return store list
     */
    public List<PinStore> getStoreListByOwnerId(Integer id){
        return pinStoreMapper.selectByOwnerId(id);
    }
}
