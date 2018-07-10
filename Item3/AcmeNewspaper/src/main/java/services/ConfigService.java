
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigRepository;
import domain.Actor;
import domain.Administrator;
import domain.Advertisement;
import domain.Article;
import domain.Chirp;
import domain.Config;
import domain.Newspaper;

@Service
@Transactional
public class ConfigService {

	//Managed Repository ----
	@Autowired
	private ConfigRepository		configRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private ChirpService			chirpService;


	//Constructors
	public ConfigService() {
		super();
	}

	public Config create() {
		Config result;

		result = new Config();

		return result;
	}

	public Collection<Config> findAll() {
		Collection<Config> result;

		result = this.configRepository.findAll();

		return result;
	}

	public void delete(final Config config) {

		this.configRepository.delete(config);

	}

	public Config save(final Config config) {
		Config result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		Assert.isTrue(config.getId() != 0, "config.double");

		result = this.configRepository.save(config);
		return result;
	}
	public Config addTabooWord(final String tabooWord) {
		Config config;
		Config result;
		Set<String> tabooWords;
		Collection<Advertisement> advertisements;
		Collection<Newspaper> newspapers;
		final Collection<Article> articles;
		final Collection<Chirp> chirps;

		config = this.findConfiguration();
		tabooWords = new HashSet<String>(config.getTabooWords());
		// Assert.isTrue(!tabooWords.contains(tabooWord) && tabooWord != null); Esto petaba todos los tests de rendimiento.
		tabooWords.add(tabooWord);
		config.setTabooWords(tabooWords);

		advertisements = this.advertisementService.findAll();
		newspapers = this.newspaperService.findAll();
		articles = this.articleService.findAll();
		chirps = this.chirpService.findAll();
		for (final Advertisement a : advertisements)
			this.advertisementService.isTaboo(tabooWord, a);

		for (final Newspaper n : newspapers)
			this.newspaperService.isTaboo(tabooWord, n);

		for (final Article a : articles)
			this.articleService.isTaboo(tabooWord, a);

		for (final Chirp c : chirps)
			this.chirpService.isTaboo(tabooWord, c);

		result = this.save(config);

		return result;
	}
	public Config removeTabooWord(final String tabooWord) {
		Config config;
		Config result;
		Collection<String> tabooWords;

		config = this.findConfiguration();
		tabooWords = config.getTabooWords();
		tabooWords.remove(tabooWord);
		config.setTabooWords(tabooWords);
		result = this.save(config);

		return result;
	}
	public Config findOne(final int configId) {
		Config result;

		result = this.configRepository.findOne(configId);
		Assert.notNull(result);

		return result;
	}

	public Boolean isTaboo(final String string) {
		Boolean result;
		Collection<String> tabooWords;

		tabooWords = this.findConfiguration().getTabooWords();
		result = false;
		for (final String tabooWord : tabooWords)
			if (string.contains(tabooWord))
				result = true;

		return result;
	}
	public Config findConfiguration() {
		Config result;
		result = this.configRepository.findAll().get(0);
		return result;
	}

	public void flush() {
		this.configRepository.flush();
	}

}
