package invoicing.dao.impl;

import invoicing.dao.KeyGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class LongKeyGenerator  implements KeyGenerator<Long> {
    private AtomicLong sequence = new AtomicLong();
    @Override
    public Long getNextId() {
        return sequence.incrementAndGet();
    }
}
