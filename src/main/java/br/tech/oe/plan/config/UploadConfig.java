package br.tech.oe.plan.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {

    @Value("${br.tech.oe.plan.application.cloudinary-url}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinaryConfig() {
        return new Cloudinary(cloudinaryUrl);
    }
}
