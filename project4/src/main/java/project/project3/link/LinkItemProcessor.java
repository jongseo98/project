package project.project4.link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import project.project4.link.Link;
import project.project4.link.LinkDetail;


public class LinkItemProcessor implements ItemProcessor<LinkDetail, Link> {
    private static final Logger log = LoggerFactory.getLogger(LinkItemProcessor.class);

    @Override
    public Link process(LinkDetail item) throws Exception {

        log.info("processing Link data.....{}", item);

        Link transformedLink = new Link();
        transformedLink.setLinkId(item.getLinkId());
        transformedLink.setImdbId(item.getImdbId());
        return transformedLink;
    }
}