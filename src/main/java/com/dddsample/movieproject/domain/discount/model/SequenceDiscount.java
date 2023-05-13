package com.dddsample.movieproject.domain.discount.model;


import com.dddsample.movieproject.domain.discount.model.vo.SequenceDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("SEQUENCE")
public class SequenceDiscount extends Discount{

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "sequence", column = @jakarta.persistence.Column(name = "sequence")),
            @AttributeOverride(name = "discountDate", column = @jakarta.persistence.Column(name = "discount_date"))
    })
    private SequenceDate sequenceDate;
}
