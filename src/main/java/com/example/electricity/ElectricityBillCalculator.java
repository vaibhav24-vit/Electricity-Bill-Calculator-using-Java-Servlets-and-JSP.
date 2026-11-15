package com.example.electricity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ElectricityBillCalculator {
    private static final BigDecimal FIRST_RATE = new BigDecimal("3.50");
    private static final BigDecimal SECOND_RATE = new BigDecimal("4.00");
    private static final BigDecimal THIRD_RATE = new BigDecimal("5.20");
    private static final BigDecimal FOURTH_RATE = new BigDecimal("6.50");

    private ElectricityBillCalculator() {
    }

    public static Bill calculate(int units) {
        if (units < 0) {
            throw new IllegalArgumentException("Units cannot be negative.");
        }

        List<Slab> slabs = new ArrayList<>();
        int remaining = units;
        remaining = addSlab(slabs, "Units 1–50", remaining, 50, FIRST_RATE);
        remaining = addSlab(slabs, "Units 51–150", remaining, 100, SECOND_RATE);
        remaining = addSlab(slabs, "Units 151–250", remaining, 100, THIRD_RATE);
        addSlab(slabs, "Above 250 units", remaining, Integer.MAX_VALUE, FOURTH_RATE);

        BigDecimal total = slabs.stream()
                .map(Slab::charge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Bill(units, List.copyOf(slabs), total.setScale(2));
    }

    private static int addSlab(List<Slab> slabs, String label, int remaining, int limit, BigDecimal rate) {
        int slabUnits = Math.min(remaining, limit);
        if (slabUnits > 0) {
            BigDecimal charge = rate.multiply(BigDecimal.valueOf(slabUnits)).setScale(2);
            slabs.add(new Slab(label, slabUnits, rate, charge));
        }
        return remaining - slabUnits;
    }

    public record Slab(String label, int units, BigDecimal rate, BigDecimal charge) {
        public String getLabel() {
            return label;
        }

        public int getUnits() {
            return units;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public BigDecimal getCharge() {
            return charge;
        }
    }

    public record Bill(int units, List<Slab> slabs, BigDecimal total) {
        public int getUnits() {
            return units;
        }

        public List<Slab> getSlabs() {
            return slabs;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }
}
