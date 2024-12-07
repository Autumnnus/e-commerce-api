package com.kadir.modules.coupon.controller.impl;

import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.coupon.controller.ICouponController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/coupon")
public class CouponController extends RestBaseController implements ICouponController {
}
