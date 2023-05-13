package com.dddsample.movieproject.domain.discount.model.vo;

import jakarta.persistence.Embeddable;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
public class SequenceDate {
    private final Integer sequence;
    private final LocalDate discountDate;

    public SequenceDate() {
        this.sequence = 0;
        this.discountDate = null;
    }

    public static SequenceDate of(Integer sequence, LocalDate discountBaseDate) {
        return new SequenceDate(sequence, discountBaseDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequenceDate that = (SequenceDate) o;
        return Objects.equals(sequence, that.sequence) && Objects.equals(discountDate, that.discountDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, discountDate);
    }
}
