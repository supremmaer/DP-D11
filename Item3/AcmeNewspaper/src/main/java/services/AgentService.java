
package services;

import java.util.Collection;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AgentRepository;

import domain.Agent;



@Service
@Transactional
public class AgentService {

	//Managed Repository ----
	@Autowired
	private AgentRepository agentRepository;

	//Constructors
	public AgentService() {
		super();
	}

	public Agent create() {
		Agent result;

		result = new Agent();

		return result;
	}

	public Collection<Agent> findAll() {
		Collection<Agent> result;

		result = this.agentRepository.findAll();

		return result;
	}

	public void delete(final Agent agent) {

		this.agentRepository.delete(agent);

	}

	public Agent save(final Agent agent) {
		Agent result;

		result = this.agentRepository.save(agent);
		return result;
	}

	public Agent findOne(final int agentId) {
		Agent result;

		result = this.agentRepository.findOne(agentId);
		Assert.notNull(result);

		return result;
	}

}
