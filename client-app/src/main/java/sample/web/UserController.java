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

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

/**
 * @author Joe Grandja
 */
@RestController
@CrossOrigin(value="http://localhost:3000", allowCredentials = "true")
public class UserController {
	private final WebClient webClient;
	private final String contactsBaseUri;

	public UserController(WebClient webClient,
						  @Value("${oauth2.resource.contacts-base-uri}") String contactsBaseUri) {
		this.webClient = webClient;
		this.contactsBaseUri = contactsBaseUri;
	}

	@GetMapping("/users")
	public List<User> getUsers(@RegisteredOAuth2AuthorizedClient("messaging") OAuth2AuthorizedClient messagingClient) {
		ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
		return this.webClient
				.get()
				.uri(this.contactsBaseUri)
				.attributes(oauth2AuthorizedClient(messagingClient))
				.retrieve()
				.bodyToMono(typeRef)
				.block();
	}

	@GetMapping("/user")
	User currentUser(@AuthenticationPrincipal OidcUser oidcUser) {
		User currentUser = new User();
		if (oidcUser != null) {
			currentUser.setFirstName(oidcUser.getGivenName());
			currentUser.setLastName(oidcUser.getFamilyName());
		}
		return currentUser;
	}
}