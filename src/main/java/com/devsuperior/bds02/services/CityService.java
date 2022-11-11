package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		List<City> list = cityRepository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		
		entity.getEvents().clear();
		for(EventDTO evDto : dto.getEvents()) {
			Event event = eventRepository.getOne(evDto.getId());
			entity.getEvents().add(event);
		}
		
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	}
}
