package com.dddsample.movieproject.domain.discount.application;

import com.dddsample.movieproject.common.model.Money;
import com.dddsample.movieproject.domain.discount.infrastructure.DiscountRepository;
import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import com.dddsample.movieproject.domain.discount.model.vo.SequenceDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SequenceDiscountService {

    private final DiscountRepository<SequenceDiscount, Long> sequenceDiscountRepository;
    @Transactional
    public SequenceDiscount register(SequenceDiscount sequenceDiscount) {
        return sequenceDiscountRepository.save(sequenceDiscount);
    }

    @Transactional
    public Money calculateDiscount(SequenceDate sequenceDate, Money amount) {
        return sequenceDiscountRepository.findBySeqAndDiscountDate(sequenceDate)
                .map(sequenceDiscount -> sequenceDiscount.discountAmount(amount))
                .orElse(amount);
    }
}
