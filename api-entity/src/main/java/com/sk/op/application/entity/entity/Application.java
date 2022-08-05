package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Application extends EntityOperation {

  private String name;

  private String profile;

  private String label;

  private Integer state;
}
