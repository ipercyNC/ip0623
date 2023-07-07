/*
 * ToolChargesRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Interface of the ToolChargesRepository.
 * Defines the general methods to be used with the ToolCharges table
 */
package com.repositories;

import java.util.List;
import com.models.ToolCharges;

public interface ToolChargesRepository {
    
    /*
     * Returns all ToolCharges objects from the database
     * 
     * @return List of ToolCharges objects
     */
    List<ToolCharges> findAll();

    /*
     * Saves ToolCharge to database
     * 
     * @param typeId int of the new ToolCharge typeId
     * 
     * @return int number of rows saved
     */
    int save(int toolChargesInt);

    /*
     * Delete all ToolCharges objects from the database
     * 
     * @return number of affected rows
     */
    int deleteAll();

    /*
     * Returns a matching ToolCharges from the database if it exists
     * 
     * @param id int of id to search for in the database
     * 
     * @return ToolCharges object
     */
    ToolCharges findById(int id);

    /*
     * Returns a matching ToolCharges from the database if it exists
     * 
     * @param typeId int to search for type_id in the database
     * 
     * @return ToolCharges object
     */
    ToolCharges findByTypeId(int toolTypeId);
}
