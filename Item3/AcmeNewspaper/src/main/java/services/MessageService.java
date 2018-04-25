
package services;

import java.util.Collection;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;

import domain.Message;



@Service
@Transactional
public class MessageService {

	//Managed Repository ----
	@Autowired
	private MessageRepository messageRepository;

	//Constructors
	public MessageService() {
		super();
	}

	public Message create() {
		Message result;

		result = new Message();

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public void delete(final Message message) {

		this.messageRepository.delete(message);

	}

	public Message save(final Message message) {
		Message result;

		result = this.messageRepository.save(message);
		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

}
