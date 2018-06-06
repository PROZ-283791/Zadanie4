package ptj;

import javax.xml.bind.annotation.XmlElement;

public class Rate {

	private String number;
	private String date;
	private float mid;
	private float bid;
	private float ask;

	@XmlElement(name = "No")
	public String getNo() {
		return number;
	}

	@XmlElement(name = "EffectiveDate")
	public String getDate() {
		return date;
	}

	@XmlElement(name = "Mid")
	public float getMid() {
		return mid;
	}
	
	@XmlElement(name = "Bid")
	public float getBid() {
		return bid;
	}
	
	@XmlElement(name = "Ask")
	public float getAsk() {
		return ask;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setMid(float mid) {
		this.mid = mid;
	}
}
