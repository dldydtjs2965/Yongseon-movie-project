package com.dddsample.movieproject.domain.discount.application;

import com.dddsample.movieproject.domain.discount.infrastructure.DiscountRepository;
import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SequenceDiscountService {

    private final DiscountRepository<SequenceDiscount, Long> sequenceDiscountRepository;

    @Transactional
    public SequenceDiscount register(SequenceDiscount sequenceDiscount) {
        return sequenceDiscountRepository.save(sequenceDiscount);
    }
}
