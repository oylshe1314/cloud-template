package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityIdentity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ApplicationConfigRelation extends EntityIdentity {

  private Long applicationId;

  private Long configId;
}
