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
import static org.assertj.core.api.Assertions.assertThat;
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
                    1, "Chainsaw", 2, "JackHammer", 3, "Ladder");

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
        assertThat(result1.contains("PERCENT_OUT_OF_RANGE"));
    }

    /*
     * 2) Rent - LADW, 7/2/20, 3, 10
     */
    @Test
    public void test2() {
        String result2 = rentToolService.rentTool("LADW", "2020-07-20", "3", "10");
        System.out.println(result2);
        assertThat(result2.contains("Tool Code           : LADW"));
        assertThat(result2.contains("Tool Type           : Ladder"));
        assertThat(result2.contains("Tool Brand          : Werner"));
        assertThat(result2.contains("Rental Days         : 3"));
        assertThat(result2.contains("Check out date      : 07/20/20"));
        assertThat(result2.contains("Due date            : 07/23/20"));
        assertThat(result2.contains("Daily Rental Charge : $1.99"));
        assertThat(result2.contains("Charge Days         : 3"));
        assertThat(result2.contains("Pre-discount Charge : $5.97"));
        assertThat(result2.contains("Discount Percent    : 10.0%"));
        assertThat(result2.contains("Discount Amount     : $0.6"));
        assertThat(result2.contains("Final Charge        : $5.37"));
    }

    /*
     * 3) Rent - CHNS, 7/2/15, 5, 25
     */
    @Test
    public void test3() {
        String result3 = rentToolService.rentTool("CHNS", "2015-07-02", "5", "25");
        System.out.println(result3);
        assertThat(result3.contains("Tool Code           : CHNS"));
        assertThat(result3.contains("Tool Type           : Chainsaw"));
        assertThat(result3.contains("Tool Brand          : Stihl"));
        assertThat(result3.contains("Rental Days         : 5"));
        assertThat(result3.contains("Check out date      : 07/02/15"));
        assertThat(result3.contains("Due date            : 07/07/15"));
        assertThat(result3.contains("Daily Rental Charge : $1.49"));
        assertThat(result3.contains("Charge Days         : 3"));
        assertThat(result3.contains("Pre-discount Charge : $4.47"));
        assertThat(result3.contains("Discount Percent    : 25.0%"));
        assertThat(result3.contains("Discount Amount     : $1.12"));
        assertThat(result3.contains("Final Charge        : $3.35"));
    }

    /*
     * 4) Rent - JAKD, 9/3/15, 6, 0
     */
    @Test
    public void test4() {
        String result4 = rentToolService.rentTool("JAKD", "2015-09-03", "6", "0");
        System.out.println(result4);
        assertThat(result4.contains("Tool Code           : JAKD"));
        assertThat(result4.contains("Tool Type           : Jackhammer"));
        assertThat(result4.contains("Tool Brand          : DeWalt"));
        assertThat(result4.contains("Rental Days         : 6"));
        assertThat(result4.contains("Check out date      : 09/03/15"));
        assertThat(result4.contains("Due date            : 09/09/15"));
        assertThat(result4.contains("Daily Rental Charge : $2.99"));
        assertThat(result4.contains("Charge Days         : 3"));
        assertThat(result4.contains("Pre-discount Charge : $8.97"));
        assertThat(result4.contains("Discount Percent    : 0.0%"));
        assertThat(result4.contains("Discount Amount     : $0.0"));
        assertThat(result4.contains("Final Charge        : $8.97"));
    }

    /*
     * 5) Rent - JAKR, 7/2/15, 9, 0
     */
    @Test
    public void test5() {
        String result5 = rentToolService.rentTool("JAKR", "2015-07-02", "9", "0");
        System.out.println(result5);
        assertThat(result5.contains("Tool Code           : JAKR"));
        assertThat(result5.contains("Tool Type           : Jackhammer"));
        assertThat(result5.contains("Tool Brand          : Ridgid"));
        assertThat(result5.contains("Rental Days         : 9"));
        assertThat(result5.contains("Check out date      : 07/02/15"));
        assertThat(result5.contains("Due date            : 07/11/15"));
        assertThat(result5.contains("Daily Rental Charge : $2.99"));
        assertThat(result5.contains("Charge Days         : 5"));
        assertThat(result5.contains("Pre-discount Charge : $14.95"));
        assertThat(result5.contains("Discount Percent    : 0.0%"));
        assertThat(result5.contains("Discount Amount     : $0.0"));
        assertThat(result5.contains("Final Charge        : $14.95"));
    }

    /*
     * 6) Rent - JAKR, 7/2/20, 4, 50
     */
    @Test
    public void test6() {
        String result6 = rentToolService.rentTool("JAKR", "2015-07-02", "9", "0");
        System.out.println(result6);
        assertThat(result6.contains("Tool Code           : JAKR"));
        assertThat(result6.contains("Tool Type           : Jackhammer"));
        assertThat(result6.contains("Tool Brand          : Ridgid"));
        assertThat(result6.contains("Rental Days         : 4"));
        assertThat(result6.contains("Check out date      : 07/02/15"));
        assertThat(result6.contains("Due date            : 07/06/15"));
        assertThat(result6.contains("Daily Rental Charge : $2.99"));
        assertThat(result6.contains("Charge Days         : 1"));
        assertThat(result6.contains("Pre-discount Charge : $11.99"));
        assertThat(result6.contains("Discount Percent    : 0.0%"));
        assertThat(result6.contains("Discount Amount     : $1.5"));
        assertThat(result6.contains("Final Charge        : $1.49"));
    }

    /*
     * 7) Rent - JAKR, 7/2/20, 4, -1
     * Invalid Percent < 0 - out of range error
     */
    @Test
    public void test7() {
        String result7 = rentToolService.rentTool("JAKR", "2015-09-03", "5", "-1");
        System.out.println(result7);
        assertThat(result7.contains("PERCENT_OUT_OF_RANGE"));
    }

    /*
     * 8) Rent - YYYY, 7/2/20, 4, 0
     * Rent tool that doesn't exist
     */
    @Test
    public void test8() {
        String result7 = rentToolService.rentTool("JAKR", "2015-09-03", "5", "-1");
        System.out.println(result7);
        assertThat(result7.contains("ERROR_RENTING_TOOL"));


    }

    /*
     * 9) Rent - JAKR, 7/2/20, 0, 50
     * Invalid number of rental days < 1
     */
    @Test
    public void test9() {
        String result7 = rentToolService.rentTool("JAKR", "2020-07-02", "0", "50");
        System.out.println(result7);
        assertThat(result7.contains("RENTAL_DAY_COUNT_OUT_OF_RANGE"));
    }

    @AfterAll
    public void clearAfter() {
        toolChoicesService.deleteAllToolChoices();
        toolChargesService.deleteAllToolCharges();
        toolBrandService.deleteAllToolBrands();
        toolTypeService.deleteAllToolTypes();
    }
}
