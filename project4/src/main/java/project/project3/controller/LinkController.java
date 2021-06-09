package project.project4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import project.project4.link.LinkRepository;
import project.project4.link.Link;

@RestController
public class LinkController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LinkRepository linkRepository;
	
	public LinkController(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	@RequestMapping(value = "/links", method = RequestMethod.GET)
	public List<Link> getAllLinks() {
		LOG.info("Getting all links.");
		return linkRepository.findAll();
	}

	
	@RequestMapping(value = "/links/{linkId}", method = RequestMethod.GET)
	public Link getLink(@PathVariable String linkId) {
		LOG.info("Getting link with ID: {}.", linkId);
		return linkRepository.findById(linkId).orElse(null);
	}

	
	@RequestMapping(value = "/links/create", method = RequestMethod.POST)
	public Link addNewLinks(@RequestBody Link link) {
		LOG.info("Saving link.");
		return linkRepository.save(link);
	}
}