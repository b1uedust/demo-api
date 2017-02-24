package uk.co.bluedust;

import java.security.KeyPair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@SpringBootApplication
public class DemoApiApplication {

	public static final String USER_PROFILE_RESOURCE_ID = "user-profile";

	public static void main(String[] args) {
		SpringApplication.run(DemoApiApplication.class, args);
	}

	@Configuration
	@EnableResourceServer
	// [2]
	protected static class ResourceServer extends
			ResourceServerConfigurerAdapter {

		@Override
		// [3]
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
	           http
	           // Just for laughs, apply OAuth protection to only 2 resources
	           
	           .authorizeRequests()
	           .anyRequest().access("#oauth2.hasScope('openid')"); //[4]
	           // @formatter:on
		}
		
		

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			resources.tokenStore(tokenStore())
			.resourceId(USER_PROFILE_RESOURCE_ID);
			
		}

		@Bean
		public JwtTokenStore tokenStore() {
			// TODO Auto-generated method stub
			return new JwtTokenStore(jwtAccessTokenConverter());
		}



		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(
					"keystore.jks"), "foobar".toCharArray()).getKeyPair("test");
			converter.setKeyPair(keyPair);
			return converter;
		}

	}
}
