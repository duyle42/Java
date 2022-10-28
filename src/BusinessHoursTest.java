import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class BusinessHoursTest {

    @Test
    public void test1() {
        Assert.assertEquals(BusinessHours.getBusinessHours(LocalDateTime.parse("2022-09-19T11:00:00.000"),
                LocalDateTime.parse("2022-09-19T15:00:00.000")), 5.0);
    }
}
