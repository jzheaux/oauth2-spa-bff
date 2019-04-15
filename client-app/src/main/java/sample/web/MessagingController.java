/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

/**
 * @author Joe Grandja
 */
@RestController
@CrossOrigin(value="http://localhost:3000", allowCredentials = "true")
@RequestMapping("/messages")
public class MessagingController {
	private static final String MESSAGE_ATTRIBUTE_NAME = "message";
	private static final String MESSAGES_ATTRIBUTE_NAME = "messages";
	private static final String MESSAGE_TYPE_ATTRIBUTE_NAME = "messageType";
	private static final String USERS_ATTRIBUTE_NAME = "users";
	private static final String MESSAGE_TYPE_INBOX = "Inbox";
	private static final String MESSAGE_TYPE_SENT = "Sent";
	private final WebClient webClient;
	private final String messagesBaseUri;
	private final String contactsBaseUri;

	public MessagingController(WebClient webClient,
								@Value("${oauth2.resource.messages-base-uri}") String messagesBaseUri,
								@Value("${oauth2.resource.contacts-base-uri}") String contactsBaseUri) {
		this.webClient = webClient;
		this.messagesBaseUri = messagesBaseUri;
		this.contactsBaseUri = contactsBaseUri;
	}

	@GetMapping("/inbox")
	public List<Message> inbox(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient,
						Map<String, Object> model) {
		return getMessages(messagingClient, this.messagesBaseUri + "/inbox");
	}

	@GetMapping("/sent")
	public List<Message> sent(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient,
						Map<String, Object> model) {
		return getMessages(messagingClient, this.messagesBaseUri + "/sent");
	}

	private List<Message> getMessages(OAuth2AuthorizedClient messagingClient, String messagesUri) {
		ParameterizedTypeReference<List<Message>> typeRef = new ParameterizedTypeReference<List<Message>>() {};
		return this.webClient
				.get()
				.uri(messagesUri)
				.attributes(oauth2AuthorizedClient(messagingClient))
				.retrieve()
				.bodyToMono(typeRef)
				.block();
	}

	@GetMapping("/{id}")
	public Message get(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient,
						Map<String, Object> model,
						@PathVariable Long id) {
		return this.webClient
				.get()
				.uri(this.messagesBaseUri + "/" + id)
				.attributes(oauth2AuthorizedClient(messagingClient))
				.retrieve()
				.bodyToMono(Message.class)
				.block();
	}

	@PostMapping
	public Message save(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient,
						@Valid @RequestBody Message message,
						@AuthenticationPrincipal OidcUser oidcUser) {
		message.setFromId(oidcUser.getClaimAsString("user_name"));
		return this.webClient
				.post()
				.uri(this.messagesBaseUri)
				.contentType(MediaType.APPLICATION_JSON)
				.syncBody(message)
				.attributes(oauth2AuthorizedClient(messagingClient))
				.retrieve()
				.bodyToMono(Message.class)
				.block();
	}

	@DeleteMapping("/{id}")
	public Long delete(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient,
							@PathVariable Long id,
							@RequestHeader("Referer") String referrer) {
		this.webClient
			.delete()
			.uri(this.messagesBaseUri + "/" + id)
			.attributes(oauth2AuthorizedClient(messagingClient))
			.retrieve()
			.bodyToMono(Void.class)
			.block();
		return id;
	}
}