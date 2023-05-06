package com.dddsample.movieproject.presentation.discount;

import com.dddsample.movieproject.domain.discount.application.SequenceDiscountService;
import com.dddsample.movieproject.domain.discount.model.Discount;
import com.dddsample.movieproject.domain.discount.model.SequenceDiscount;
import com.dddsample.movieproject.presentation.discount.request.SequenceDiscountRequestDto;
import com.dddsample.movieproject.presentation.discount.response.SequenceDiscountResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    private final SequenceDiscountService sequenceDiscountService;
    @PostMapping("/sequence")
    public ResponseEntity createSequenceDiscount(@RequestBody @Valid SequenceDiscountRequestDto sequenceDiscountRequest) {
        SequenceDiscount savedSeqDiscount = sequenceDiscountService.register(sequenceDiscountRequest.toEntity());

        return ResponseEntity.status(201).body(new SequenceDiscountResponseDto(savedSeqDiscount));
    }
}
