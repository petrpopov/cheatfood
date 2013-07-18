package com.petrpopov.cheatfood.service;

import com.petrpopov.cheatfood.model.Location;

/**
 * User: petrpopov
 * Date: 12.07.13
 * Time: 12:53
 */
public interface ILocationService extends IGenericService<Location> {

    public Location createOrSave(Location location);
    public void deleteLocation(String id);
    public void deleteLocation(Location location);
}
