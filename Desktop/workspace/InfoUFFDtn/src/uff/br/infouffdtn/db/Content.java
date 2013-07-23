package uff.br.infouffdtn.db;

import java.util.Date;

public class Content 
{
	private String name;
	private Date date;
	private String payload;
	
	
	public Content(String name,Date date, String payload)
	{
		this.setName(name);
		this.setDate(date);	
		this.setPayload(payload);

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
	@Override
	public String toString()
	{
		return name + "|" + date + "|" + payload;
		
	}

}
