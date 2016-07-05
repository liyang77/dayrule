/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuseinfo.dayrule;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HolidayCalendarTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HolidayCalendarTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( HolidayCalendarTest.class );
    }

    /**
     * AdjDays Test :-)
     */
    public void testAdjDays() {
    	int[] ymd = DateUtils.adjDays(2016, 3, -140);
    	assertEquals(ymd[0], 2015);
    	assertEquals(ymd[1], 10);
    	assertEquals(ymd[2], 12);
    }
    
    /**
     * FixedHolidayRule Test :-)
     */
    public void testFixedHolidayRule() {
    	FixedHolidayRule xmas = new FixedHolidayRule(new String[]{"12","25","2","6","0"});
    	assertTrue(xmas.apply(2015, 12, 25));
    	assertTrue(xmas.apply(2015, 12, 28));
    	assertTrue(!xmas.apply(2015, 12, 29));
    }

    /**
     * MonthDayRule Test :-)
     */
    public void testMonthDayRule() {
    	MonthDayRule victoria = new MonthDayRule(new String[]{"5","19","1"});
    	assertTrue(victoria.apply(2016, 5, 23));
    	assertTrue(!victoria.apply(2016, 5, 16));
    }
    
    /**
     * EasterDateRule Test :-)
     */
    public void testEasterDateRule() {
    	EasterDateRule goodFriday = new EasterDateRule(new String[]{"-2"});
    	assertTrue(goodFriday.apply(2015, 4, 3));
    	assertTrue(goodFriday.apply(2016, 3, 25));
    }
    
    /**
     * HolidayCalendar Test :-)
     */
    public void testHolidayCalendar() {
    	HolidayCalendar holidayCalendar = new HolidayCalendar("CA_ON.properties");
    	assertEquals(holidayCalendar.rules.length, 10);
    }
    
}
