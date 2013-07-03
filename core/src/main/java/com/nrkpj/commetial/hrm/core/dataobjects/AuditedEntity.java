package com.nrkpj.commetial.hrm.core.dataobjects;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditedEntity {

    @Column(name = "last_modified_on", nullable = false)
    private Date lastModifiedOn;

    @Column(name = "created_on", nullable = false)
    private Date createdOn;

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
