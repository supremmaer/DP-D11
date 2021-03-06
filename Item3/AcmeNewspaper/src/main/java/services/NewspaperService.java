
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import domain.Actor;
import domain.Administrator;
import domain.Advertisement;
import domain.Article;
import domain.CreditCard;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class NewspaperService {

	//Managed Repository ----
	@Autowired
	private NewspaperRepository		newspaperRepository;

	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserService				userService;
	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private ArticleService			articleService;
	@Autowired
	private VolumeService			volumeService;
	@Autowired
	private ConfigService			configService;


	//Constructors
	public NewspaperService() {
		super();
	}

	public Newspaper create() {
		Newspaper result;
		final Collection<Article> articles = new ArrayList<>();

		result = new Newspaper();
		result.setTaboo(false);
		result.setArticles(articles);
		return result;
	}

	public Collection<Newspaper> findAll() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findAll();

		return result;
	}

	public void delete(final Newspaper newspaper) {
		Actor principal;
		User user;
		Collection<Newspaper> newspapers;
		Collection<Advertisement> advertisements;
		Collection<CreditCard> creditCards;
		Collection<Volume> volumes;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		user = this.userService.findByNewspaperId(newspaper.getId());
		newspapers = user.getNewspapers();
		newspapers.remove(newspaper);
		user.setNewspapers(newspapers);
		advertisements = this.advertisementService.findByNewspaper(newspaper.getId());
		creditCards = this.creditCardService.findByNewspaper(newspaper.getId());
		volumes = this.volumeService.findByNewspaper(newspaper.getId());
		this.userService.save(user);
		for (final Article a : newspaper.getArticles())
			this.articleService.deleteByNewspaper(a);
		for (final CreditCard c : creditCards) {
			c.getNewspapers().remove(newspaper);
			this.creditCardService.saveByAdmin(c);
		}
		for (final Volume v : volumes)
			this.volumeService.removeNewspaperByAdmin(v.getId(), newspaper.getId());
		for (final Advertisement a : advertisements)
			this.advertisementService.delete(a);

		this.newspaperRepository.delete(newspaper);

	}
	public Newspaper save(final Newspaper newspaper) {
		Newspaper result;

		if (this.configService.isTaboo(newspaper.getTitle()) || this.configService.isTaboo(newspaper.getDescription()) || (newspaper.getPicture() != null && this.configService.isTaboo(newspaper.getPicture())))
			newspaper.setTaboo(true);

		if (newspaper.getId() != 0)
			result = this.newspaperRepository.save(newspaper);
		else {
			result = this.newspaperRepository.save(newspaper);
			final User u = (User) this.actorService.findByPrincipal();
			final Collection<Newspaper> newspapers = u.getNewspapers();
			newspapers.add(result);
			u.setNewspapers(newspapers);
			this.userService.save(u);
		}
		return result;
	}

	public Newspaper publish(final int newspaperId) {
		Newspaper newspaper;
		newspaper = this.newspaperRepository.findOne(newspaperId);
		final User u = (User) this.actorService.findByPrincipal();
		Assert.isTrue(u.getNewspapers().contains(newspaper));
		Assert.isTrue(newspaper.getPublicationDate() == null);
		Assert.isTrue(!newspaper.getArticles().isEmpty());
		Assert.notNull(newspaper);
		for (final Article a : newspaper.getArticles()) {
			Assert.isTrue(!a.isDraftMode());
			a.setPublishMoment(new Date(System.currentTimeMillis()));
			this.articleService.updateDate(a);
		}
		newspaper.setPublicationDate(new Date(System.currentTimeMillis()));
		newspaper = this.newspaperRepository.save(newspaper);
		return newspaper;
	}

	public Newspaper findOne(final int newspaperId) {
		Newspaper result;

		result = this.newspaperRepository.findOne(newspaperId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.newspaperRepository.flush();
	}
	public Collection<Newspaper> findByCriteria(final String criteria) {
		final Collection<Newspaper> newspapers;
		newspapers = this.newspaperRepository.findByCriteria(criteria);
		return newspapers;
	}

	public Newspaper findByArticleId(final int id) {
		Newspaper result;

		result = this.newspaperRepository.findByArticleId(id);

		return result;
	}
	public Collection<Newspaper> findTaboo() {
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperRepository.findTaboo();

		return newspapers;
	}

	public Boolean isTaboo(final String string, final Newspaper newspaper) {
		Boolean result = false;
		String lower;
		if (string != null) {
			lower = string.toLowerCase();

			if (newspaper.getTitle().contains(lower) || newspaper.getDescription().contains(lower)) {
				newspaper.setTaboo(true);
				result = true;
				this.newspaperRepository.save(newspaper);
			}
		}
		return result;
	}

	public Collection<Newspaper> findByCreditCardID(final int cardID) {
		return this.newspaperRepository.findByCreditCardID(cardID);
	}

	public Collection<Newspaper> findByCustomerID(final int customerID) {
		return this.newspaperRepository.findByCustomerID(customerID);
	}
	public Collection<Newspaper> findByCustomerIDAndVolumes(final int customerID) {
		return this.newspaperRepository.findByCustomerIDAndVolumes(customerID);
	}

	public Collection<Newspaper> findAllPublished() {
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperRepository.findAllPublished();

		return newspapers;
	}

	public Collection<Newspaper> findByAgentWithAdvertisements(final int id) {
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperRepository.findByAgentWithAdvertisements(id);
		return newspapers;
	}

	public Collection<Newspaper> findByAgentWithoutAdvertisements(final int id) {
		Collection<Newspaper> newspapers;
		Collection<Newspaper> restar;
		newspapers = this.newspaperRepository.findAll();
		restar = this.newspaperRepository.findByAgentWithAdvertisements(id);
		newspapers.removeAll(restar);
		return newspapers;
	}
	//DASHBOARD

	public Double averageArticlesPerNewspaper() {
		return this.newspaperRepository.averageArticlesPerNewspaper();
	}

	public Double standardArticlesPerNewspaper() {
		return this.newspaperRepository.standardArticlesPerNewspaper();
	}

	public Collection<Newspaper> findNewspapersWithMoreArticlesThanAverage() {
		final Collection<Newspaper> result = this.newspaperRepository.findNewspapersWithMoreArticlesThanAverage();
		return result;
	}

	public Collection<Newspaper> findNewspapersWithLessArticlesThanAverage() {
		final Collection<Newspaper> result = this.newspaperRepository.findNewspapersWithLessArticlesThanAverage();
		return result;
	}

	public Double ratioPublicVsPrivate() {
		return this.newspaperRepository.ratioPublicVsPrivate();
	}

	public Double averageArticlesPerPrivateNewspaper() {
		return this.newspaperRepository.averageArticlesPerPrivateNewspaper();
	}

	public Double averageArticlesPerPublicNewspaper() {
		return this.newspaperRepository.averageArticlesPerPublicNewspaper();
	}

	public Collection<Newspaper> findNewPrivateByUser(final int id) {
		final Collection<Newspaper> result = this.newspaperRepository.findNewPrivateByUser(id);
		return result;
	}

	public Collection<Newspaper> findNewPublicByUser(final int id) {
		final Collection<Newspaper> result = this.newspaperRepository.findNewPublicByUser(id);
		return result;
	}

	public Double avgRatioPrivateVsPublicPerUser() {
		final Collection<User> users = this.userService.findAll();
		Collection<Newspaper> priv = new ArrayList<Newspaper>();
		Collection<Newspaper> pub = new ArrayList<Newspaper>();
		Double aux = 0.0;
		Double d = 1.0;
		for (final User u : users) {
			priv = this.findNewPrivateByUser(u.getId());
			pub = this.findNewPublicByUser(u.getId());

			d = 1.0 * pub.size();

			if (d == 0)
				d = 1.0;

			aux = aux + (100 * priv.size() / d);
		}
		return aux / users.size();

	}

	public Collection<Newspaper> findNewspaperWithAdvertisement() {
		Collection<Newspaper> result;
		result = this.newspaperRepository.findNewspaperWithAdvertisement();

		return result;
	}

	public Double ratioNewsWithVsWithoutAdvertisements() {
		Double result;
		final Collection<Newspaper> with = this.findNewspaperWithAdvertisement();
		final Collection<Newspaper> without = this.findAll();
		without.removeAll(with);
		result = 100.0 * with.size() / without.size() * 1.0;
		return result;
	}

	//dashboard
}
