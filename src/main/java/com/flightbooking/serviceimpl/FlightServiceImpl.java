package com.flightbooking.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.model.Flight;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired
	private FlightRepository repository;
	
	
	public Flight addFlight(Flight flight) {
		return repository.save(flight);
	}

	
	public Flight updateFlight(Flight flight, long flightId ) throws FlightNotFoundException {
		
		if(repository.existsById(flightId)) {
			Flight u =repository.findById(flightId).get();
			u.setDepartureDate(flight.getDepartureDate());
			u.setArrivalDate(flight.getArrivalDate());
			u.setAvailableSeats(flight.getAvailableSeats());	
			return this.repository.save(u);
		}		
		throw new FlightNotFoundException();	
	}

	public List<Flight> getAllFlights() {
		return repository.findAll();
	}

	
	public Flight getFlightById(long flightId) throws FlightNotFoundException {
		Flight flt;
		if(repository.findById(flightId).isEmpty()) {
			throw new FlightNotFoundException();
		}
		else {
			flt=repository.findById(flightId).get();
		}
		return flt;
	}


	public List<Flight> getFlightByDestination(String destination) throws FlightNotFoundException {
		List<Flight> flights= repository.getFlightByDestination(destination);
		if(flights.isEmpty()) {
			throw new FlightNotFoundException();
		}
		return flights;
	}


	
	
}