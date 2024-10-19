package com.appsdeveloperblog.estore.ProductService.configuration;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

@Configuration
public class AxonConfiguration {


  @Bean
  public XStream xStream() {
      XStream xStream = new XStream();
      
      xStream.addPermission(NoTypePermission.NONE); // Forbid everything
      xStream.addPermission(NullPermission.NULL); // allow "null"
      xStream.addPermission(PrimitiveTypePermission.PRIMITIVES); // allow primitiv types  

      // Allow the ReserveProductCommand class
      xStream.allowTypes(new Class[] { com.appsdeveloperblog.estore.core.commands.ReserveProductCommand.class, 
        com.appsdeveloperblog.estore.ProductService.command.CreateProductCommand.class,
        java.lang.String.class 
      });
      // xStream.allowTypesByWildcard(new String[] {
      //   "com.appsdeveloperblog.estore.**"
      // });
      return xStream;
  }

  @Bean
  @Primary
  public Serializer serializer(XStream xStream) {
      return XStreamSerializer.builder()
              .xStream(xStream)
              .build();
  }
}
