package com.example.electricity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElectricityBillCalculatorTest {
    @Test
    void calculatesProgressiveRatesAtSlabBoundaries() {
        assertEquals(new BigDecimal("0.00"), ElectricityBillCalculator.calculate(0).total());
        assertEquals(new BigDecimal("175.00"), ElectricityBillCalculator.calculate(50).total());
        assertEquals(new BigDecimal("179.00"), ElectricityBillCalculator.calculate(51).total());
        assertEquals(new BigDecimal("575.00"), ElectricityBillCalculator.calculate(150).total());
        assertEquals(new BigDecimal("580.20"), ElectricityBillCalculator.calculate(151).total());
        assertEquals(new BigDecimal("1095.00"), ElectricityBillCalculator.calculate(250).total());
        assertEquals(new BigDecimal("1101.50"), ElectricityBillCalculator.calculate(251).total());
    }

    @Test
    void rejectsNegativeUsage() {
        assertThrows(IllegalArgumentException.class, () -> ElectricityBillCalculator.calculate(-1));
    }
}
