package cn.edu.neu.shop.pin.service;

import cn.edu.neu.shop.pin.mapper.PinStoreGroupCloseBatchMapper;
import cn.edu.neu.shop.pin.model.PinStoreGroupCloseBatch;
import cn.edu.neu.shop.pin.util.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StoreCloseBatchService extends AbstractService<PinStoreGroupCloseBatch> {

    private final PinStoreGroupCloseBatchMapper pinStoreGroupCloseBatchMapper;

    public StoreCloseBatchService(PinStoreGroupCloseBatchMapper pinStoreGroupCloseBatchMapper) {
        this.pinStoreGroupCloseBatchMapper = pinStoreGroupCloseBatchMapper;
    }

    public List<PinStoreGroupCloseBatch> getGroupCloseBatchTime(Integer storeId) {
        return pinStoreGroupCloseBatchMapper.getStoreGroupCloseBatchByStoreIdAndTimeAsc(storeId);
    }

    PinStoreGroupCloseBatch getRecentGroupCloseBatchTime(Integer storeId) {
        List<PinStoreGroupCloseBatch> list = getGroupCloseBatchTime(storeId);
        if (list == null || list.size() == 0) return null;
        for (PinStoreGroupCloseBatch batch : list) {
            Date nowPlusTenMinutes = new Date(new Date().getTime() + 600000);
            if (nowPlusTenMinutes.before(batch.getTime())) {
                return batch;
            }
        }
        return list.get(0);
    }

    @Transactional
    public void deleteGroupCloseBatch(Integer storeId, Integer id) {
        pinStoreGroupCloseBatchMapper.deleteStoreGroupCloseBatch(storeId, id);
    }

    @Transactional
    public void addGroupCloseBatch(Integer storeId, Date time) {
        pinStoreGroupCloseBatchMapper.addStoreGroupCloseBatch(storeId, time);
    }
}
