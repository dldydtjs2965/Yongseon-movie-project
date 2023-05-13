package com.dddsample.movieproject.domain.movie.model;

import com.dddsample.movieproject.common.model.BaseTimeEntity;
import com.dddsample.movieproject.common.model.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price", nullable = false))
    private Money price;

    @Column(nullable = false)
    private LocalTime runningTime;

    public Money getPayAmount(Integer count) {
        return this.price.multiply(count);
    }
}
