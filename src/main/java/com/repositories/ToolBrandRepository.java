/*
 * ToolBrandRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Interface of the ToolBrand.
 * Defines the general methods to be used with the ToolBrand table
 */
package com.repositories;

import java.util.List;

import com.models.ToolBrand;

public interface  ToolBrandRepository {
    /*
     * Returns all ToolBrand objects from the database
     * @return List of ToolBrands
     */
    List<ToolBrand> findAll();

    /*
     * Saves ToolBrand to the database
     * @param String of the new ToolBrand name
     * @return int number of rows saved
     */
    int save(String toolBrandName);

    /*
     * Delete all ToolBrand objects from the database
     * @return int number of affected rows
     */
    int deleteAll();

    /*
     * Returns a matching ToolBrand from the database if it exists
     * @param id to serach for in the database
     * @return ToolBrand object
     */
    ToolBrand findById(int id);

    /*
     * Returns a matching ToolBrand from the database if it exists
     * @param String to search for in the database
     * @return ToolBrand object
     */
    ToolBrand findByName(String toolBrandName);
}
