package org.samjingwen.snsexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Configuration
public class AwsConfig {

    @Bean
    public SnsAsyncClient snsAsyncClient(StsAssumeRoleCredentialsProvider credentialsProvider) {
        return SnsAsyncClient.builder()
            .region(Region.AP_SOUTHEAST_1)
            .credentialsProvider(credentialsProvider)
            .build();
    }

    @Bean
    public StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider() {
        return StsAssumeRoleCredentialsProvider.builder()
            .refreshRequest(assumeRoleRequest())
            .build();
    }

    private AssumeRoleRequest assumeRoleRequest(){
        return AssumeRoleRequest.builder()
            .durationSeconds(3600)
            .roleArn()
            .build();
    }

}
