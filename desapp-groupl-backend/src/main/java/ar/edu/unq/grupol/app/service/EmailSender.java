package ar.edu.unq.grupol.app.service;

import java.util.List;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Emailv31;
import ar.edu.unq.grupol.app.model.MailJetUser;

@Component
public class EmailSender {

	private String apiKey;
	private String apiSecret;
	private JSONObject mailFrom;

	public EmailSender() throws JSONException {
		apiKey = "16a6b8c2894541dacd5d82bee3a35f46";
		apiSecret = "ebbe9b9a09a9076144a8802abdd16019";
		mailFrom = createMail(new MailJetUser("Eventeando", "marinoalanunq@gmail.com"));
	}

	public void send(List<MailJetUser> toUsers) {
		MailjetClient client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));
		MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, createMail(toUsers));
		Executors.newSingleThreadExecutor().submit(() -> client.post(request));
	}
	
	private JSONArray createMail(List<MailJetUser> toUsers) {
		JSONArray jsonArray = new JSONArray();
		toUsers.forEach(user -> {
			try {
				jsonArray.put(
						new JSONObject().put(Emailv31.Message.FROM, mailFrom)
						.put(Emailv31.Message.TO, new JSONArray().put(createMail(user)))
						.put(Emailv31.Message.SUBJECT, "Eventeando: Invitación")
						.put(Emailv31.Message.TEXTPART, "Estimado " + user.getName() + " has sido invitado al evento." )
						);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
		return jsonArray;
	}
	
	private JSONObject createMail(MailJetUser user) throws JSONException {
		return new JSONObject().put("Email", user.getMailAddress()).put("Name", user.getName());
	}

}