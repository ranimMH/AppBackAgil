package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.entities.AppTicket;
@RepositoryRestResource
public interface AppTicketRepository extends JpaRepository<AppTicket, Long> {

}
