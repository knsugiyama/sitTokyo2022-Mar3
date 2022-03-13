package my.after.domain.type.system;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Amount {

  private int value;

    public Amount(int value) {
        this.value = value;
    }

   public Amount(String value) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            Number number = decimalFormat.parse(value);
            this.value = number.intValue();
        } catch (ParseException e) {
            throw new NumberFormatException(value);
        }
    }

    public Amount add(Amount other) {
        return new Amount(value + other.value);
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0'円'").format(value);
    }

    public int value() {
        return value;
    }

    public Amount addAll(Amount... amounts) {
        int total = value;
        for (Amount amount : amounts) {
            total = total + amount.value;
        }
        return new Amount(total);
    }}
