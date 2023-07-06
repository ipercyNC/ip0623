/*
 * ToolChargesRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Interface of the ToolChargesRepository.
 * Defines the general methods to be used with the ToolCharges table
 */
package com.respositories;

import java.util.List;
import com.models.ToolCharges;
public interface ToolChargesRepository {
    
    /*
     * Returns all ToolCharges objects from the database
     * @return List of ToolCharges objects
     */
    List<ToolCharges> findAll();

     /*
     * Saves ToolCharge to database
     * @param int of the new ToolCharge typeId
     * @return int number of rows saved
     */
    int save(int toolChargesName);

    /*
     * Delete all ToolCharges objects from the database
     * @return number of affected rows
     */
    int deleteAll();

    /*
     * Returns a matching ToolCharges from the database if it exists
     * @param id to search for in the database
     * @return ToolCharges object
     */
    ToolCharges findById(int id);

    /*
     * Returns a matching ToolCharges from the database if it exists
     * @param int to search for type_id in the database
     * @return ToolCharges object
     */
    ToolCharges findByTypeId(int toolTypeId);
}
