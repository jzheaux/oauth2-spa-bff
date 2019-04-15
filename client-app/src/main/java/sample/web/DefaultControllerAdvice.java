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

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joe Grandja
 */
@ControllerAdvice
public class DefaultControllerAdvice {

	@ModelAttribute("currentUser")
	User currentUser(@AuthenticationPrincipal OidcUser oidcUser) {
		User currentUser = new User();
		if (oidcUser != null) {
			currentUser.setFirstName(oidcUser.getGivenName());
			currentUser.setLastName(oidcUser.getFamilyName());
		}
		return currentUser;
	}

	@ExceptionHandler(WebClientResponseException.class)
	ModelAndView handleException(WebClientResponseException ex) {
		return errorView("An error occurred on the WebClient response -> [Status: " +
				ex.getStatusCode() + "] " + ex.getStatusText());
	}

	@ExceptionHandler(Exception.class)
	ModelAndView handleException(Exception ex) {
		return errorView("An error occurred: " + ex.getMessage());
	}

	private ModelAndView errorView(String errorMessage) {
		Map<String, Object> model = new HashMap<>();
		model.put("errorMessage", errorMessage);
		return new ModelAndView("error", model);
	}
}