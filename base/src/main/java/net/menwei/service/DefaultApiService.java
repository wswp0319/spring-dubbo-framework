package net.menwei.service;

import net.menwei.ResultSet;

public interface DefaultApiService {

    ResultSet defaultMethod(String name, String address, Double longitude, Double latitude);
}
