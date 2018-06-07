package ptj;

import java.io.StringReader;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Path("rates")
public class HttpController {
	@GET
	@Path("{table}/{code}/{number}")
	@Produces(MediaType.TEXT_PLAIN)
	public String averageText(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("number") int number) {
		return "" + getResponse(table, code, number);
	}

	@GET
	@Path("{table}/{code}/{number}")
	@Produces(MediaType.TEXT_XML)
	public String averageXml(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("number") int number) {
		return "<?xml version=\"1.0\"?><avg>" + getResponse(table, code, number) + "</avg>";
	}

	@GET
	@Path("{table}/{code}/{number}")
	@Produces(MediaType.TEXT_HTML)
	public String averageHtml(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("number") int number) {
		return "<html><body><h1>" + getResponse(table, code, number) + "</h1></body></html>";
	}

	@GET
	@Path("{table}/{code}/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public String averageJson(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("number") int number) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("avg", getResponse(table, code, number));
		JsonObject ob = builder.build();
		return ob.toString();
	}

	public float getResponse(String table, String code, int number) {
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder
				.fromUri("http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/last/" + number + "/")
				.build();
		WebTarget webTarget = client.target(uri);
		String xmlAnswer = webTarget.request().accept(MediaType.TEXT_XML).get(String.class);
		ExchangeRatesSeries rates = new ExchangeRatesSeries();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRatesSeries.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rates = (ExchangeRatesSeries) jaxbUnmarshaller.unmarshal(new StringReader(xmlAnswer));
			float sum = 0;
			if (table.equals("c") || table.equals("C"))
				for (Rate r : rates.getRates())
					sum += (r.getBid() + r.getAsk()) / 2;
			else
				for (Rate r : rates.getRates())
					sum += r.getMid();
			return sum / rates.getRates().size();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
