/*
 * ToolTypeRepository.java
 * 7/4/2023
 * Ian Percy
 * 
 * Interface of the ToolTypeRepository.
 * Defines the general methods to be used with the ToolType table
 */
package com.repositories;

import java.util.List;
import com.models.ToolType;

public interface ToolTypeRepository {
    /*
     * Returns all ToolType objects from the database
     * 
     * @return List of ToolType objects
     */
    List<ToolType> findAll();

    /*
     * Saves ToolType to database
     * 
     * @param toolTypeName String of the new ToolType name
     * 
     * @return int number of rows saved
     */
    int save(String toolTypeName);

    /*
     * Delete all ToolType objects from the database
     * 
     * @return number of affected rows
     */
    int deleteAll();

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param id int for the id to search for in the database
     * 
     * @return ToolType object
     */
    ToolType findById(int id);

    /*
     * Returns a matching ToolType from the database if it exists
     * 
     * @param toolTypeName String to search for in the database
     * 
     * @return ToolType object
     */
    ToolType findByName(String toolTypeName);
}
