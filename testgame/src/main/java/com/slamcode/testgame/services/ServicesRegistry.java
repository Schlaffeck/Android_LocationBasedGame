package com.slamcode.testgame.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smoriak on 04/08/2017.
 */

public final class ServicesRegistry {

    private Map<String, Object> servicesNameMap = new HashMap<>();

    private Map<String, ServiceFactoryConfig> servicesFactoriesNameMap = new HashMap<>();

    public void registerService(String name, Object service)
    {
        servicesNameMap.put(name, service);
    }

    public void registerServiceFactory(String name, ServiceFactory serviceFactory)
    {
        servicesFactoriesNameMap.put(name, new ServiceFactoryConfig(serviceFactory, false));
    }

    public void registerServiceFactory(String name, ServiceFactory serviceFactory, boolean isSingleton)
    {
        servicesFactoriesNameMap.put(name, new ServiceFactoryConfig(serviceFactory, isSingleton));
    }

    public Object provideService(String name)
    {
        Object result = servicesNameMap.get(name);
        if(result == null)
        {
            ServiceFactoryConfig factoryConfig = servicesFactoriesNameMap.get(name);
            if(factoryConfig != null && factoryConfig.factory != null) {
                result = factoryConfig.factory.createService(name);
                if(factoryConfig.isSingleton && result != null)
                    this.servicesNameMap.put(name, result);
            }
        }

        return result;
    }

    public interface ServiceFactory{

        Object createService(String serviceName);
    }

    private class ServiceFactoryConfig
    {
        boolean isSingleton;

        ServiceFactory factory;

        ServiceFactoryConfig(ServiceFactory serviceFactory, boolean isSingleton)
        {
            factory = serviceFactory;
            this.isSingleton = isSingleton;
        }
    }
}
