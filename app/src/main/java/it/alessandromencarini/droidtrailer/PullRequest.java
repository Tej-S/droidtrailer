package it.alessandromencarini.droidtrailer;

import java.util.List;
import it.alessandromencarini.droidtrailer.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
// KEEP INCLUDES END
/**
 * Entity mapped to table PULL_REQUEST.
 */
public class PullRequest {

    private Long id;
    private String title;
    private String userLogin;
    private String state;
    private String url;
    private String userAvatarUrl;
    private Integer number;
    private Integer commentCount;
    private Boolean assignedToMe;
    private Boolean mergeable;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
    private java.util.Date closedAt;
    private java.util.Date mergedAt;
    private java.util.Date readAt;
    private Boolean participated;
    private long repositoryId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PullRequestDao myDao;

    private Repository repository;
    private Long repository__resolvedKey;

    private List<Comment> commentList;

    // KEEP FIELDS - put your custom fields here

    public final static SimpleDateFormat GITHUB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private Boolean mMarkedForDestruction = false;
    // KEEP FIELDS END

    public PullRequest() {
    }

    public PullRequest(Long id) {
        this.id = id;
    }

    public PullRequest(Long id, String title, String userLogin, String state, String url, String userAvatarUrl, Integer number, Integer commentCount, Boolean assignedToMe, Boolean mergeable, java.util.Date createdAt, java.util.Date updatedAt, java.util.Date closedAt, java.util.Date mergedAt, java.util.Date readAt, Boolean participated, long repositoryId) {
        this.id = id;
        this.title = title;
        this.userLogin = userLogin;
        this.state = state;
        this.url = url;
        this.userAvatarUrl = userAvatarUrl;
        this.number = number;
        this.commentCount = commentCount;
        this.assignedToMe = assignedToMe;
        this.mergeable = mergeable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.mergedAt = mergedAt;
        this.readAt = readAt;
        this.participated = participated;
        this.repositoryId = repositoryId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPullRequestDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getAssignedToMe() {
        return assignedToMe;
    }

    public void setAssignedToMe(Boolean assignedToMe) {
        this.assignedToMe = assignedToMe;
    }

    public Boolean getMergeable() {
        return mergeable;
    }

    public void setMergeable(Boolean mergeable) {
        this.mergeable = mergeable;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public java.util.Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(java.util.Date closedAt) {
        this.closedAt = closedAt;
    }

    public java.util.Date getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(java.util.Date mergedAt) {
        this.mergedAt = mergedAt;
    }

    public java.util.Date getReadAt() {
        return readAt;
    }

    public void setReadAt(java.util.Date readAt) {
        this.readAt = readAt;
    }

    public Boolean getParticipated() {
        return participated;
    }

    public void setParticipated(Boolean participated) {
        this.participated = participated;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId) {
        this.repositoryId = repositoryId;
    }

    /** To-one relationship, resolved on first access. */
    public Repository getRepository() {
        long __key = this.repositoryId;
        if (repository__resolvedKey == null || !repository__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RepositoryDao targetDao = daoSession.getRepositoryDao();
            Repository repositoryNew = targetDao.load(__key);
            synchronized (this) {
                repository = repositoryNew;
            	repository__resolvedKey = __key;
            }
        }
        return repository;
    }

    public void setRepository(Repository repository) {
        if (repository == null) {
            throw new DaoException("To-one property 'repositoryId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.repository = repository;
            repositoryId = repository.getId();
            repository__resolvedKey = repositoryId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Comment> getCommentList() {
        if (commentList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentDao targetDao = daoSession.getCommentDao();
            List<Comment> commentListNew = targetDao._queryPullRequest_CommentList(id);
            synchronized (this) {
                if(commentList == null) {
                    commentList = commentListNew;
                }
            }
        }
        return commentList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCommentList() {
        commentList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    public PullRequest(JSONObject json) throws JSONException {
        number = json.getInt("number");
        title = json.getString("title");
        userLogin = json.getJSONObject("user").getString("login");
        url = json.getString("html_url");
        state = json.getString("state");
        createdAt = parseDate(json.getString("created_at"));
        closedAt = parseDate(json.getString("closed_at"));
        mergedAt = parseDate(json.getString("merged_at"));
    }

    private Date parseDate(String string) {
        if (string.equals("null"))
            return null;

        try {
            return GITHUB_DATE_FORMAT.parse(string);
        } catch (ParseException e) {
            Log.e("PullRequest", "Could not parse date: ", e);
            return null;
        }
    }

    public Boolean getMarkedForDestruction() {
        return mMarkedForDestruction;
    }

    public void setMarkedForDestruction(Boolean markedForDestruction) {
        mMarkedForDestruction = markedForDestruction;
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass())
            return false;

        PullRequest otherPullRequest = (PullRequest)o;
        return id.equals(otherPullRequest.getId());
    }

    public String getCurrentState() {
        if (mergedAt != null) {
            return "merged";
        } else {
            return state;
        }
    }
    // KEEP METHODS END

}
