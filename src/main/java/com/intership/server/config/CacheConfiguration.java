package com.intership.server.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.intership.server.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.intership.server.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.intership.server.domain.User.class.getName());
            createCache(cm, com.intership.server.domain.Authority.class.getName());
            createCache(cm, com.intership.server.domain.User.class.getName() + ".authorities");
            createCache(cm, com.intership.server.domain.Student.class.getName());
            createCache(cm, com.intership.server.domain.Teacher.class.getName());
            createCache(cm, com.intership.server.domain.StuTea.class.getName());
            createCache(cm, com.intership.server.domain.Intership.class.getName());
            createCache(cm, com.intership.server.domain.AttendanceManagement.class.getName());
            createCache(cm, com.intership.server.domain.Leave.class.getName());
            createCache(cm, com.intership.server.domain.AbsenceRecord.class.getName());
            createCache(cm, com.intership.server.domain.JobChangeRecords.class.getName());
            createCache(cm, com.intership.server.domain.InternshipReport.class.getName());
            createCache(cm, com.intership.server.domain.InternshipTask.class.getName());
            createCache(cm, com.intership.server.domain.GraduationProject.class.getName());
            createCache(cm, com.intership.server.domain.SharingCenter.class.getName());
            createCache(cm, com.intership.server.domain.AttendanceRecord.class.getName());
            createCache(cm, com.intership.server.domain.AlarmEvent.class.getName());
            createCache(cm, com.intership.server.domain.Certificate.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
