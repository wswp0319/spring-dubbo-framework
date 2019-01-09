package net.menwei.repositories;

import net.menwei.doc.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreRepository extends ElasticsearchRepository<Store, String> {
    Page<Store> findByStoreNameLikeOrAddressLike(String storeName, String address, Pageable pageable);
}
