package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.AppTicket;
import com.example.demo.services.TicketService;

@RestController
public class TicketController {
	@Autowired
	TicketService ticketService;

	@PostMapping("/saveTicket")
	public AppTicket saveTicket(String username) {
		return ticketService.saveTicket(username);
	}
	@RequestMapping(value="/allTickets",method = RequestMethod.GET)
	public List<AppTicket> getAllTickets() {
		return ticketService.getAllTickets();
	}
}
//@Data
//class TicketForm {
//	private String ticketname;
//
//	public String getTicketname() {
//		return ticketname;
//	}
//
//	public void setTicketname(String ticketname) {
//		this.ticketname = ticketname;
//	}
//	
//}
