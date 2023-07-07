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
     * 
     * @return List of ToolChoices objects
     */
    List<ToolChoices> findAll();

    /*
     * Saves ToolChoices to database
     * 
     * @param toolChoicesCode String of the new ToolChoices code
     * 
     * @param brandId int of the ToolBrand
     * 
     * @param typeId int of the ToolType
     * 
     * @return int number of rows saved
     */
    int save(String toolChoicesCode, int brandId, int typeId);

    /*
     * Delete all ToolChoices objects from the database
     * 
     * @return number of affected rows
     */
    int deleteAll();

    /*
     * Returns a matching ToolChoices from the database if it exists
     * 
     * @param id int for the id to search for in the database
     * 
     * @return ToolChoices object
     */
    ToolChoices findById(int id);

    /*
     * Returns a matching ToolChoices from the database if it exists
     * 
     * @param code String for tool code to search for in the database
     * 
     * @return ToolChoices object
     */
    ToolChoices findByCode(String code);

     //TODO: Possibly add looking by ToolBrand id or ToolType id, however you could do findALL() and filter
     // selectiviy might be the same there - minimal performance gain initially
}
