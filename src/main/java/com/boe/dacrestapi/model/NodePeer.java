package com.boe.dacrestapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 节点信息表
 * @author CAD40
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_nodepeer")
public class NodePeer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable=false,unique=false)
	private String nodeName;
	@Column(nullable=false,unique=true)
	private String nodeIp;
	@Column(nullable=true,unique=false)
	private String nodePosition;
	@Column(nullable=true,unique=false)
	private String status;
	@Column(nullable=true,unique=false)
	private long blockHeight;
}
