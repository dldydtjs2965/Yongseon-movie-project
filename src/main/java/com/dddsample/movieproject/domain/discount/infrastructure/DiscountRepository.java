package com.dddsample.movieproject.domain.discount.infrastructure;

import com.dddsample.movieproject.domain.discount.model.Discount;
import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import com.dddsample.movieproject.domain.discount.model.vo.SequenceDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiscountRepository<T extends Discount, Long> extends Repository<T, Long> {
    T save(T discount);

    @Query("select seqDiscount " +
            "from SequenceDiscount seqDiscount " +
            "join fetch seqDiscount.discountPolicy " +
            "where seqDiscount.sequenceDate = :seqDiscountDate")
    Optional<SequenceDiscount> findBySeqAndDiscountDate(@Param("seqDiscountDate") SequenceDate seqDiscountDate);
}
