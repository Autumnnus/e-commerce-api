package com.kadir.modules.paymentmethods.creditcard.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.paymentmethods.creditcard.controller.ICreditCardController;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardCreateDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardUpdateDto;
import com.kadir.modules.paymentmethods.creditcard.service.ICreditCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/credit-card")
public class CreditCardController extends RestBaseController implements ICreditCardController {

    @Autowired
    private ICreditCardService creditCardService;

    @PostMapping
    @Override
    public ApiResponse<CreditCardDto> addCreditCard(@RequestBody @Valid CreditCardCreateDto creditCardCreateDto) {
        return ok(creditCardService.addCreditCard(creditCardCreateDto));
    }

    @PutMapping("/{creditCardId}")
    @Override
    public ApiResponse<CreditCardDto> updateCreditCard
            (@PathVariable(name = "creditCardId") Long creditCardId, @RequestBody @Valid CreditCardUpdateDto creditCardUpdateDto) {
        return ok(creditCardService.updateCreditCard(creditCardId, creditCardUpdateDto));
    }

    @DeleteMapping("/{creditCardId}")
    @Override
    public ApiResponse<CreditCardDto> deleteCreditCard(@PathVariable(name = "creditCardId") Long creditCardId) {
        return ok(creditCardService.deleteCreditCard(creditCardId));
    }

    @GetMapping("/{creditCardId}")
    @Override
    public ApiResponse<CreditCardDto> getCreditCard(@PathVariable(name = "creditCardId") Long creditCardId) {
        return ok(creditCardService.getCreditCard(creditCardId));
    }

    @GetMapping
    @Override
    public ApiResponse<List<CreditCardDto>> getCreditCards() {
        return ok(creditCardService.getCreditCards());
    }
}
