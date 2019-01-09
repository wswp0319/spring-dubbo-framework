package net.menwei.domain;

import java.io.Serializable;

/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ModeDto implements Serializable {

	/**主键，不要改动**/
	private Integer id;

	/**1:本周 2:全部**/
	private Integer mode;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setMode(Integer mode){
		this.mode = mode;
	}

	public Integer getMode(){
		return this.mode;
	}

}
