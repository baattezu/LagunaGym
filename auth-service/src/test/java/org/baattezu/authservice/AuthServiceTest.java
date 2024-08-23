package org.baattezu.authservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {
    @Test
    void testRegister(){
        int sum = 1+3;
        Assertions.assertEquals(4, sum);
    }
}
