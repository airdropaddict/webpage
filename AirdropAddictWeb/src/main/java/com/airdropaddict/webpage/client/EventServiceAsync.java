package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface EventServiceAsync {

    /**
     * Initializes default catalogs.
     * @param callback contains true if any catalog value was added
     */
    void initializeCatalogs(AsyncCallback<Boolean> callback);

    /**
     * Loads catalog of given type
     * Return empty list if no such catalog can be found
     * @param catalogType type of catalog filter
     * @param callback contains catalog list
     */
    void loadCatalogByType(CatalogType catalogType, AsyncCallback<List<CatalogData>> callback);

    /**
     * Returns single catalogData object that matches catalogType with given code.
     * Returns null if no such data can be found.
     * @param catalogType type of catalog
     * @param code catalog code
     * @param callback contains catalogData or null
     */
    void getCatalogByTypeAndCode(CatalogType catalogType, String code, AsyncCallback<CatalogData> callback);

    /**
     * Returns task templates
     * @param callback contains list of task templates
     */
    void getTaskTemplates(AsyncCallback<List<TaskTemplateData>> callback);

    /**
     * Saves a single task tamplate. If template already exists (id != 0) the entity is updated, otherwise it is created.
     * @param taskTemplate object to save (update)
     * @param callback null
     */
    void saveTaskTemplate(TaskTemplateData taskTemplate, AsyncCallback<Long> callback);

    /**
     * Deletes task template
     * @param taskTemplateId id of template to delete
     * @param callback empty
     */
    void deleteTaskTemplate(long taskTemplateId, AsyncCallback<Void> callback);

    /**
     * Returns user by email address
     * @param email email address to search for
     * @param callback contains user object
     */
    void getUserByEmail(String email, AsyncCallback<UserData> callback);

    /**
     * Saves user object. If user already exists (id != 0) the entity is updated, otherwise it is created.
     * @param user object to save
     * @param callback contains id of saved object
     */
    void saveUser(UserData user, AsyncCallback<Long> callback);

    /**
     * Reads complete event info by given id and returns it.
     * @param id event id
     * @param access invoke client specs
     * @param callback contains event
     */
    void getEventById(long id, AccessData access, AsyncCallback<EventData> callback);

    /**
     * Saves given event
     * @param event
     * @param callback contains event id
     */
    void saveEvent(EventData event, AsyncCallback<Long> callback);

    /**
     * Delete event by given id
     * @param id event id
     * @param callback empty
     */
    void deleteEvent(long id, AsyncCallback<Void> callback);

    /**
     * @deprecated - use getSimplePageableEvents and getEventById combination
     * Returns all active events
     * @param eventTypeCode event type
     * @param access invoke client specs
     * @param callback contains event list
     */
    @Deprecated
    void getActiveEvents(String eventTypeCode, AccessData access, AsyncCallback<List<EventData>> callback);

    /**
     * Returns page of simple event results filtered by input parameters.
     * Event data of this type is designed for fast loading and brief event representation
     * @param eventTypeCode event type
     * @param resultType result type enum
     * @param scam true for scam only false for no scam
     * @param resultsPerPage number of results per page
     * @param page page number (starts with 0)
     * @param access invoke client specs
     * @param callback contains pageData
     */
    void getSimplePageableEvents(String eventTypeCode, EventResultType resultType, boolean scam, int resultsPerPage, int page, AccessData access, AsyncCallback<PageData<SimpleEventData>> callback);

    /**
     * Rate given event
     * @param eventId id of event to rate
     * @param rating rating of this event
     * @param access invoke client specs
     * @param callback contains detailed event data with refreshed rating information
     */
    void rateEvent(long eventId, int rating, AccessData access, AsyncCallback<EventData> callback);
}