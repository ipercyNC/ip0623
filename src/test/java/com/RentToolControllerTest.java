/*
 * RentToolControllerTest.java
 * 7/5/2023
 * Ian Percy
 * 
 * Test for the RentToolController. Test renting tool functionality
 * 
 * 1) Rent - JAKR, 9/3/15, 5, 101
 * 2) Rent - LADW, 7/2/20, 3, 10
 * 3) Rent - CHNS, 7/2/15, 5, 25
 * 4) Rent - JAKD, 9/3/15, 6, 0
 * 5) Rent - JAKR, 7/2/15, 9, 0
 * 6) Rent - JAKR, 7/2/20, 4, 50
 */
package com;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Value;
import com.services.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class RentToolControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RentToolControllerTest.class);

    @Autowired
    RentToolService rentToolService;

    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    ToolChoicesService toolChoicesService;

    @Autowired
    ToolChargesService toolChargesService;

    @Autowired
    ToolBrandService toolBrandService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    @Value(value = "${local.server.port}")
    private int port;

    @BeforeAll
    public void clearBefore() {
        toolChoicesService.deleteAllToolChoices();
        toolChargesService.deleteAllToolCharges();
        toolBrandService.deleteAllToolBrands();
        toolTypeService.deleteAllToolTypes();

        try {
            // Insert default values into DB
            jdbcTemplate.update(
                    "INSERT into tool_type(id, name) " +
                            " VALUES(?, ?), (?, ?), (?, ?);",
                    1, "Chainsaw", 2, "Ladder", 3, "Jackhammer");

            jdbcTemplate.update(
                    "INSERT into tool_brand(id, name) " +
                            " VALUES(?, ?), (?, ?), (?, ?), (?, ?);",
                    1, "Stihl", 2, "Werner", 3, "DeWalt", 4, "Ridgid");
            jdbcTemplate.update(
                    "INSERT into tool_charges(id, type_id, daily_charge, weekday_charge, weekend_charge, holiday_charge) "
                            +
                            " VALUES(?, ?, ?, ?, ?, ?)," +
                            " (?, ?, ?, ?, ?, ?)," +
                            " (?, ?, ?, ?, ?, ?);",
                    1, 2, 1.99, 1, 1, 0,
                    2, 1, 1.49, 1, 0, 1,
                    3, 3, 2.99, 1, 0, 0);
            jdbcTemplate.update(
                    "INSERT into tool_choices(id, code, brand_id, type_id) " +
                            " VALUES(?, ?, ?, ?)," +
                            " (?, ?, ?, ?)," +
                            " (?, ?, ?, ?)," +
                            " (?, ?, ?, ?);",
                    1, "CHNS", 1, 1,
                    2, "LADW", 2, 2,
                    3, "JAKD", 3, 3,
                    4, "JAKR", 4, 3);

        } catch (Exception e) {
            logger.error("Error with default db setup " + e);
        }
    }

    /*
     * 1) Rent - JAKR, 9/3/15, 5, 101
     * Percent  > 100 - out of range error
     */
    @Test
    public void test1() {
        String result1 = rentToolService.rentTool("JAKR", "2015-09-03", "5", "101");
        assertTrue(result1.contains("PERCENT_OUT_OF_RANGE"));
    }

    /*
     * 2) Rent - LADW, 7/2/20, 3, 10
     * 7/3/20 (Friday) - Holiday - No Charge
     * 7/4/20 (Saturday) - Weekend - Charge
     * 7/5/20 (Sunday) - Weekend - Charge
     */
    @Test
    public void test2() {
        String result2 = rentToolService.rentTool("LADW", "2020-07-02", "3", "10");
        System.out.println(result2);
        assertTrue(result2.contains("Tool Code           : LADW"));
        assertTrue(result2.contains("Tool Type           : Ladder"));
        assertTrue(result2.contains("Tool Brand          : Werner"));
        assertTrue(result2.contains("Rental Days         : 3"));
        assertTrue(result2.contains("Checkout Date      : 07/02/20"));
        assertTrue(result2.contains("Due Date            : 07/05/20"));
        assertTrue(result2.contains("Daily Rental Charge : $1.99"));
        assertTrue(result2.contains("Charge Days         : 2"));
        assertTrue(result2.contains("Pre-discount Charge : $3.98"));
        assertTrue(result2.contains("Discount Percent    : 10%"));
        assertTrue(result2.contains("Discount Amount     : $0.40"));
        assertTrue(result2.contains("Final Charge        : $3.58"));
    }

    /*
     * 3) Rent - CHNS, 7/2/15, 5, 25
     * 7/3/15 (Friday) - Holiday - Charge
     * 7/4/15 (Saturday) - Weekend - No Charge
     * 7/5/15 (Sunday) - Weekend - No Charge
     * 7/6/15 (Monday) - Weekday- Charge
     * 7/7/15 (Tuesday) - Weekday - Charge
     */
    @Test
    public void test3() {
        String result3 = rentToolService.rentTool("CHNS", "2015-07-02", "5", "25");
        System.out.println(result3);
        assertTrue(result3.contains("Tool Code           : CHNS"));
        assertTrue(result3.contains("Tool Type           : Chainsaw"));
        assertTrue(result3.contains("Tool Brand          : Stihl"));
        assertTrue(result3.contains("Rental Days         : 5"));
        assertTrue(result3.contains("Checkout Date      : 07/02/15"));
        assertTrue(result3.contains("Due Date            : 07/07/15"));
        assertTrue(result3.contains("Daily Rental Charge : $1.49"));
        assertTrue(result3.contains("Charge Days         : 3"));
        assertTrue(result3.contains("Pre-discount Charge : $4.47"));
        assertTrue(result3.contains("Discount Percent    : 25%"));
        assertTrue(result3.contains("Discount Amount     : $1.12"));
        assertTrue(result3.contains("Final Charge        : $3.35"));
    }

    /*
     * 4) Rent - JAKD, 9/3/15, 6, 0
     * 9/4/15 (Friday) - Weekday - Charge
     * 9/5/15 (Saturday) - Weekend - No Charge
     * 9/6/15 (Sunday) - Weekend - No Charge
     * 9/7/15 (Monday) - Holiday Labor Day - No Charge
     * 9/8/15 (Tuesday) - Weekday - Charge
     * 9/9/15 (Wednesday) - Weekday - Charge
     */
    @Test
    public void test4() {
        String result4 = rentToolService.rentTool("JAKD", "2015-09-03", "6", "0");
        System.out.println(result4);
        assertTrue(result4.contains("Tool Code           : JAKD"));
        assertTrue(result4.contains("Tool Type           : Jackhammer"));
        assertTrue(result4.contains("Tool Brand          : DeWalt"));
        assertTrue(result4.contains("Rental Days         : 6"));
        assertTrue(result4.contains("Checkout Date      : 09/03/15"));
        assertTrue(result4.contains("Due Date            : 09/09/15"));
        assertTrue(result4.contains("Daily Rental Charge : $2.99"));
        assertTrue(result4.contains("Charge Days         : 3"));
        assertTrue(result4.contains("Pre-discount Charge : $8.97"));
        assertTrue(result4.contains("Discount Percent    : 0%"));
        assertTrue(result4.contains("Discount Amount     : $0.00"));
        assertTrue(result4.contains("Final Charge        : $8.97"));
    }

    /*
     * 5) Rent - JAKR, 7/2/15, 9, 0
     * 7/3/15 (Friday) - Holiday - No Charge
     * 7/4/15 (Saturday) - Weekend - No Charge
     * 7/5/15 (Sunday) - Weekend - No Charge
     * 7/6/15 (Monday) - Weekday- Charge
     * 7/7/15 (Tuesday) - Weekday - Charge
     * 7/8/15 (Wednesday) - Weekday - Charge
     * 7/9/15 (Thursday) - Weeekday - Charge
     * 7/10/15 (Friday) - Weekeday - Charge
     * 7/11/15 (Saturday) - Weekend - No Charge
     */
    @Test
    public void test5() {
        String result5 = rentToolService.rentTool("JAKR", "2015-07-02", "9", "0");
        System.out.println(result5);
        assertTrue(result5.contains("Tool Code           : JAKR"));
        assertTrue(result5.contains("Tool Type           : Jackhammer"));
        assertTrue(result5.contains("Tool Brand          : Ridgid"));
        assertTrue(result5.contains("Rental Days         : 9"));
        assertTrue(result5.contains("Checkout Date      : 07/02/15"));
        assertTrue(result5.contains("Due Date            : 07/11/15"));
        assertTrue(result5.contains("Daily Rental Charge : $2.99"));
        assertTrue(result5.contains("Charge Days         : 5"));
        assertTrue(result5.contains("Pre-discount Charge : $14.95"));
        assertTrue(result5.contains("Discount Percent    : 0%"));
        assertTrue(result5.contains("Discount Amount     : $0.00"));
        assertTrue(result5.contains("Final Charge        : $14.95"));
    }

    /*
     * 6) Rent - JAKR, 7/2/20, 4, 50
     * 7/3/20 (Friday) - Holiday July 4th - No Charge
     * 7/4/20 (Saturday) - Weekend - No Charge
     * 7/5/20 (Sunday) - Weekend - No Charge
     * 7/6/20 (Monday) - Weekday - Charge
     */
    @Test
    public void test6() {
        String result6 = rentToolService.rentTool("JAKR", "2020-07-02", "4", "50");
        System.out.println(result6);
        assertTrue(result6.contains("Tool Code           : JAKR"));
        assertTrue(result6.contains("Tool Type           : Jackhammer"));
        assertTrue(result6.contains("Tool Brand          : Ridgid"));
        assertTrue(result6.contains("Rental Days         : 4"));
        assertTrue(result6.contains("Checkout Date      : 07/02/20"));
        assertTrue(result6.contains("Due Date            : 07/06/20"));
        assertTrue(result6.contains("Daily Rental Charge : $2.99"));
        assertTrue(result6.contains("Charge Days         : 1"));
        assertTrue(result6.contains("Pre-discount Charge : $2.99"));
        assertTrue(result6.contains("Discount Percent    : 50%"));
        assertTrue(result6.contains("Discount Amount     : $1.50"));
        assertTrue(result6.contains("Final Charge        : $1.49"));
    }

    /*
     * 7) Rent - JAKR, 7/2/20, 4, -1
     * Invalid Percent < 0 - out of range error
     */
    @Test
    public void test7() {
        String result7 = rentToolService.rentTool("JAKR", "2015-09-03", "5", "-1");
        System.out.println(result7);
        assertTrue(result7.contains("ERROR_PERCENT_OUT_OF_RANGE"));
    }

    /*
     * 8) Rent - YYYY, 7/2/20, 4, 0
     * Rent tool that doesn't exist
     */
    @Test
    public void test8() {
        String result7 = rentToolService.rentTool("JAKR", "2015-09-03", "5", "-1");
        System.out.println(result7);
        assertTrue(result7.contains("ERROR_PERCENT_OUT_OF_RANGE"));


    }

    /*
     * 9) Rent - JAKR, 7/2/20, 0, 50
     * Invalid number of rental days < 1
     */
    @Test
    public void test9() {
        String result7 = rentToolService.rentTool("JAKR", "2020-07-02", "0", "50");
        System.out.println(result7);
        assertTrue(result7.contains("RENTAL_DAY_COUNT_OUT_OF_RANGE"));
    }

    /*
     * 10) Rent - JAKR, 7/2/21, 4, 0  (Test a July 4th on a Sunday)
     * 7/3/21 (Saturday) - Weekend - No Charge
     * 7/4/21 (Sunday) - Weekend - No Charge
     * 7/5/21 (Monday) - Holiday July 4th - No Charge
     * 7/6/21 (Tuesday) - Weekday - Charge
     */
    @Test
    public void test10() {
        String result6 = rentToolService.rentTool("JAKR", "2021-07-02", "4", "0");
        System.out.println(result6);
        assertTrue(result6.contains("Tool Code           : JAKR"));
        assertTrue(result6.contains("Tool Type           : Jackhammer"));
        assertTrue(result6.contains("Tool Brand          : Ridgid"));
        assertTrue(result6.contains("Rental Days         : 4"));
        assertTrue(result6.contains("Checkout Date      : 07/02/21"));
        assertTrue(result6.contains("Due Date            : 07/06/21"));
        assertTrue(result6.contains("Daily Rental Charge : $2.99"));
        assertTrue(result6.contains("Charge Days         : 1"));
        assertTrue(result6.contains("Pre-discount Charge : $2.99"));
        assertTrue(result6.contains("Discount Percent    : 0%"));
        assertTrue(result6.contains("Discount Amount     : $0.00"));
        assertTrue(result6.contains("Final Charge        : $2.99"));
    }
    @AfterAll
    public void clearAfter() {
        toolChoicesService.deleteAllToolChoices();
        toolChargesService.deleteAllToolCharges();
        toolBrandService.deleteAllToolBrands();
        toolTypeService.deleteAllToolTypes();
    }
}
