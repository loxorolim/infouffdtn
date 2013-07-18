package uff.br.infouffdtn.db;

import java.util.Date;

public class Content 
{
	private String name;
	private String payload;
	private Date date;
	
	public Content(String name, String payload,Date date)
	{
		this.setName(name);
		this.setPayload(payload);
		this.setDate(date);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
