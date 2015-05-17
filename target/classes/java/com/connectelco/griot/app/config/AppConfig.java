package com.connectelco.griot.app.config;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.connectelco.griot.app.rs.JaxRsApiApplication;
import com.connectelco.griot.app.rs.PeopleRestService;
import com.connectelco.griot.app.rs.SendSMSRestService;

@Configuration
@Import( InMemorySecurityConfig.class )
public class AppConfig {	
	@Bean( destroyMethod = "shutdown" )
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean @DependsOn ( "cxf" )
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		factory.setServiceBeans( Arrays.< Object >asList( peopleRestService(), sendSMSRestService() ) );
		factory.setAddress( factory.getAddress() );
		factory.setProviders( Arrays.< Object >asList( new JsrJsonpProvider() ) );
		return factory.create();
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
	
	@Bean 
	public PeopleRestService peopleRestService() {
		return new PeopleRestService();
	}
	
	@Bean 
	public SendSMSRestService sendSMSRestService() {
		return new SendSMSRestService();
	}		
}
