package com.ldblock.carid.service;

import java.util.List;

import com.ldblock.carid.data.bo.CarOwnerAddBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ldblock.carid.dao.CarOwnerMapper;
import com.ldblock.carid.data.bo.CarOwnerQuery;
import com.ldblock.carid.data.po.CarOwner;

@Service
public class CarOwnerService {

    @Autowired
    CarOwnerMapper carOwnerMapper;

    public CarOwner insert(CarOwnerAddBo carOwnerAddBo, Integer userId) {
        CarOwner existEntity = carOwnerMapper.selectByOwnerId(userId);
        if (existEntity != null) {
            existEntity.setCarLicenseNumber(carOwnerAddBo.getCarLicenseNumber());
            existEntity.setVinCode(carOwnerAddBo.getVinCode());
            carOwnerMapper.updateById(existEntity);
            return existEntity;
        } else {
            CarOwner entity = new CarOwner();
//		entity.setOwnerName(carOwnerAddBo.getOwnerName());
            entity.setOwnerId(userId);
            entity.setCarLicenseNumber(carOwnerAddBo.getCarLicenseNumber());
            entity.setVinCode(carOwnerAddBo.getVinCode());
            carOwnerMapper.insert(entity);
            return entity;
        }
    }

//	public CarOwner insert(String carNo, String ownerId) {
//		CarOwner entity = new CarOwner();
//		entity.setCarNo(carNo);
//		entity.setOwnerId(ownerId);
//		carOwnerMapper.insert(entity);
//		return entity;
//	}

    public List<CarOwner> query(CarOwnerQuery query) {
        return carOwnerMapper.query(query);
    }

//    //通过cache使用redis来进行缓存
//    @Cacheable(cacheNames = "carOwner", key = "#id")
//    public CarOwner getByid(Long id) {
//        return carOwnerMapper.selectById(id);
//    }

    @Cacheable(cacheNames = "carOwnerInfo", key = "#userId")
    public CarOwner getByOwnerId(Integer userId) {
    	return carOwnerMapper.selectByOwnerId(userId);
    }
}
