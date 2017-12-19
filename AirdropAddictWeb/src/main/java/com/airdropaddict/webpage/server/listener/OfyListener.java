package com.airdropaddict.webpage.server.listener;

import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.entity.EventEntity;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OfyListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        // This will be invoked as part of a warmup request, or the first user request if no warmup
        // request.
        ObjectifyService.register(CatalogEntity.class);
        ObjectifyService.register(EventEntity.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
