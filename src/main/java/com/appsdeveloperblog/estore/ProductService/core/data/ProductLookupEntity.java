package com.appsdeveloperblog.estore.ProductService.core.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productlookup")
public class ProductLookupEntity implements Serializable {

  private static final long serialVersionUID = 182912898129182890L;

  @Id
  private String productId;
  @Column(unique = true)
  private String title;

}
