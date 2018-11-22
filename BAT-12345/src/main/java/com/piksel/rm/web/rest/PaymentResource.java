package com.piksel.rm.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.piksel.rm.service.PaymentService;
import com.piksel.rm.service.dto.PaymentDTO;
import com.piksel.rm.service.dto.PaymentView;

import io.swagger.annotations.ApiOperation;

@RestController
public class PaymentResource {

	@Autowired
	PaymentService paymentService;

	@ApiOperation("Get list of all calculated payments")
	@JsonView(PaymentView.List.class)
	@GetMapping("/royaltymanager/payments")
	public ResponseEntity<List<PaymentDTO>> getPayments(Pageable page) {
		List<PaymentDTO> result = paymentService.findAll(page);
		return ResponseEntity.ok(result);
	}

	@ApiOperation("Get payment calculation for specific studio (rights owner)")
	@JsonView(PaymentView.Item.class)
	@GetMapping("/royaltymanager/payments/{studioId}")
	public ResponseEntity<PaymentDTO> getPayment(@PathVariable("studioId") String studioId) {
		Optional<PaymentDTO> payment = paymentService.find(studioId);
		if (payment.isPresent()) {
			return ResponseEntity.ok(payment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
