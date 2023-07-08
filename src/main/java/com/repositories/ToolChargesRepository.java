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
import com.models.ToolType;

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
    int save(int typeId);

    /*
     * Saves ToolCharges to database
     * 
     * @param toolType ToolType for the ToolCharges object
     * 
     * @param dailyCharge double for the daily charge for the rental
     * 
     * @param weekdayCharge int for if there are charges on the weekday for the
     * ToolType
     * 
     * @param weekendCharge int for if there are charges on the weekend for the
     * ToolType
     * 
     * @param holidayCharge int for if there are charges on the holiday for the
     * ToolType
     * 
     * @return int number of rows saved
     */
    int save(ToolType toolType, double dailyCharge, int weekdayCharge, int weekendCharge, int holidayCharge);

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
