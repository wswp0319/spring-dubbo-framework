package net.menwei.doc;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;

@Document(indexName = "company", type = "store", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Store implements Serializable {
    @Id
    private String uuid;
    @Field
    private String storeName;
    private String address;
    //@GeoPointField
    private GeoPoint coordinate;
    private Double distance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPoint getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoPoint coordinate) {
        this.coordinate = coordinate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Store{" +
                "uuid='" + uuid + '\'' +
                ", storeName='" + storeName + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", coordinate=" + coordinate.getLon() + "," + coordinate.getLat() +
                '}';
    }
}
