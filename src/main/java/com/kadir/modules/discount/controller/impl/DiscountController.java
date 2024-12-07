package com.kadir.modules.discount.controller.impl;

import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.discount.controller.IDiscountController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/discount")
public class DiscountController extends RestBaseController implements IDiscountController {
}
