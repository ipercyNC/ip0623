/*
 * ToolChoicesRepository.java
 * 7/5/2023
 * Ian Percy
 * 
 * Interface of the ToolChoicesRepository.
 * Defines the general methods to be used with the ToolChoices table
 */
package com.repositories;

import java.util.List;
import com.models.ToolChoices;

public interface ToolChoicesRepository {
    /*
     * Returns all ToolChoices objects from the database
     * @return List of ToolChoices objects
     */
    List<ToolChoices> findAll();

    /*
     * Saves ToolChoices to database
     * @param String of the new ToolChoice code
     * @return int number of rows saved
     */
    int save(String toolChoicesCode, int brandId, int typeId);

    /*
     * Delete all ToolChoices objects from the database if it exists
     * @return number of affected rows 
     */
    int deleteAll();

    /*
     * Returns a matching ToolChoices from the database if it exists
     * @param id to search for in the database
     * @return ToolChoices object
     */
    ToolChoices findById(int id);

    /*
     * Returns a matching ToolChoices from the database if it exists
     * @param id to search for in the database
     * @return ToolChoices object
     */
    ToolChoices findByCode(String code);

     //TODO: Possibly add looking by ToolBrand id or ToolType id, however you could do findALL() and filter
     // selectiviy might be the same there - minimal performance gain initially
}
