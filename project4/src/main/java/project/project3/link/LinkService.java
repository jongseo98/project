// package project.project4.link;

// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.stereotype.Service;
// import org.springframework.beans.factory.annotation.Autowired;
// import project.project4.link.Link;
// import java.util.List;

// @Service
// public class LinkService {
	
// 	@Autowired
// 	private MongoTemplate mongoTemplate;
	
// 	public Link getLink(String _id) {
// 		Link Link = mongoTemplate.findById(_id, Link.class);
// 		return Optional.ofNullable(Link).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Not found Link"));
// 	}
	
// 	public List<Link> getLinkList(String title) {
// 		Query query = new Query().addCriteria(Criteria.where("id").is(id));
// 		return mongoTemplate.find(query, Link.class);
// 	}
	
// 	public Link insertLink(Link body) { 
// 		return mongoTemplate.insert(body);
// 	}

// }