
package services;

import java.util.Collection;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;

import domain.Folder;



@Service
@Transactional
public class FolderService {

	//Managed Repository ----
	@Autowired
	private FolderRepository folderRepository;

	//Constructors
	public FolderService() {
		super();
	}

	public Folder create() {
		Folder result;

		result = new Folder();

		return result;
	}

	public Collection<Folder> findAll() {
		Collection<Folder> result;

		result = this.folderRepository.findAll();

		return result;
	}

	public void delete(final Folder folder) {

		this.folderRepository.delete(folder);

	}

	public Folder save(final Folder folder) {
		Folder result;

		result = this.folderRepository.save(folder);
		return result;
	}

	public Folder findOne(final int folderId) {
		Folder result;

		result = this.folderRepository.findOne(folderId);
		Assert.notNull(result);

		return result;
	}

}
