package net.menwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import net.menwei.ResultSet;
import net.menwei.doc.Store;
import net.menwei.repositories.StoreRepository;
import net.menwei.utils.StringUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Random;

@Service // dubbo注解
@Component
public class DefaultServiceImpl implements DefaultApiService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceImpl.class);

    @Autowired
    CacheService cacheService;
    @Autowired
    StoreRepository storeRepository;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")})
    public ResultSet defaultMethod(String name, String address, Double longitude, Double latitude) {
        /*
         * Hystrix超时配置的为2s,当实现类睡眠超过2s，服务调用者将执行服务降级函数
         */
        int nextInt = new Random().nextInt(1000);
        logger.info("name " + name);
        Object obj = cacheService.get(name);
        if (obj == null) {
            cacheService.set(name, name, 1);
        }
        logger.info("expire " + obj);

        Store store = new Store();
        store.setUuid(StringUtil.buildUUID());
        store.setAddress(address);
        store.setStoreName(name);
        GeoPoint coordinate = new GeoPoint(latitude, longitude);
        store.setCoordinate(coordinate);
        //storeRepository.save(store);

        logger.info("findStore:" + findStore("店", "汉", 0, 5));
        findNeo(114.340672, 30.552392, 0, 10);

//		try {
//			Thread.sleep(nextInt);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
        return ResultSet.createSuccess("create  " + name + " store from Dubbo Spring Boot");
    }

    public Page<Store> findStore(String storeName, String address, int page, int size) {
        Page<Store> store = storeRepository.findByStoreNameLikeOrAddressLike(storeName, address, PageRequest.of(page, size));
        Iterator<Store> it = store.iterator();
        while (it.hasNext()) {
            Store s = it.next();
            logger.info("store: " + s);
        }
        return store;
    }

    public void findNeo(Double longitude, Double latitude, int page, int size) {
        // 实现了SearchQuery接口，用于组装QueryBuilder和SortBuilder以及Pageable等
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page, size));

        // 间接实现了QueryBuilder接口
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("coordinate");
        distanceQueryBuilder.point(latitude, longitude);
        // 定义查询单位：公里
        distanceQueryBuilder.distance(50, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        // 按距离升序
        GeoDistanceSortBuilder distanceSortBuilder =
                new GeoDistanceSortBuilder("coordinate", latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.KILOMETERS);
        distanceSortBuilder.order(SortOrder.ASC);
        nativeSearchQueryBuilder.withSort(distanceSortBuilder);

        Page<Store> store = storeRepository.search(nativeSearchQueryBuilder.build());
        Iterator<Store> it = store.iterator();
        while (it.hasNext()) {
            Store s = it.next();
            double distance = GeoDistance.ARC.calculate(latitude, longitude, s.getCoordinate().getLat(), s.getCoordinate().getLon(), DistanceUnit.KILOMETERS);
            s.setDistance(distance);
            logger.info("store: " + s);
        }
    }

    public void findGeo(Double longitude, Double latitude, int page, int size) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("coordinate");
        distanceQueryBuilder.point(latitude, longitude);
        // 定义查询单位：公里
        distanceQueryBuilder.distance(50, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);

        Page<Store> store = storeRepository.search(boolQueryBuilder, PageRequest.of(page, size));
        Iterator<Store> it = store.iterator();

        while (it.hasNext()) {
            Store s = it.next();
            logger.info("store: " + s);
        }
    }
}