package ptj;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExchangeRatesSeries")
public class ExchangeRatesSeries {
	private String table;
	private String currency;
	private String code;
	private List<Rate> rates;

	@XmlElement(name = "Table")
	public String getTable() {
		return table;
	}
	@XmlElement(name = "Currency")
	public String getCurrency() {
		return currency;
	}
	@XmlElement(name = "Code")
	public String getCode() {
		return code;
	}

	@XmlElementWrapper(name = "Rates")
	@XmlElement(name = "Rate")
	public List<Rate> getRates() {
		return rates;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
}
