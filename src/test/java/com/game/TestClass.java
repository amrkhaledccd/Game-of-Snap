package com.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestClass {

    @Test
    public void test() {
        int x = 5;
        int y = 6;
        int z = x * y;

        Assertions.assertEquals(30, z);
    }

}
