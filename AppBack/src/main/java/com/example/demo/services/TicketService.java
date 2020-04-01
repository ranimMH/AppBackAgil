package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.AppTicket;

public interface TicketService {
	public AppTicket saveTicket(String username);
	public List<AppTicket> getAllTickets();
}
