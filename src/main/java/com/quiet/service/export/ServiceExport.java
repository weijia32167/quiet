package com.quiet.service.export;


import com.quiet.service.IService;
import com.quiet.service.ServiceAPI;
import com.quiet.service.ServiceException;
import com.quiet.service.ServiceState;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/3/8
 * Desc   :
 */
public class ServiceExport implements IExport {

    public static  final Logger logger = LoggerFactory.getLogger(ServiceExport.class);
    /**
     * Cached IService Instance Proxy Object,make sure every IService Class Object only have one proxy.
     */
    public static final Map<Class<IService>,ProxyObject> serviceProxyMap = new ConcurrentHashMap<>();

    /*Service 导出的服务API以一个接口形式输出，这个玩意当然需要全部方法都过滤*/
    public static final MethodFilter ALL_METHOD_FILTER = new MethodFilter() {
        @Override
        public boolean isHandled(Method method) {
            return true;
        }
    };

    private IService service;
    Class clazz;

    private Class<?> api ;
    private Class<?> impl ;

    public ServiceExport(IService service) {
        this.service = service;
        clazz = service.getClass();
        ServiceAPI serviceAPI = (ServiceAPI) clazz.getAnnotation(ServiceAPI.class);
        api = serviceAPI.api();
        impl = serviceAPI.impl();
    }

    @Override
    public IService getService () {
        return service;
    }

    @Override
    public <T> T exportAPI () throws Throwable{
        ProxyObject result = serviceProxyMap.get(api);
        if(result!=null){
            return (T)result;
        }else{
            ProxyFactory factory=new ProxyFactory();
            factory.setInterfaces(new Class[]{api});
            factory.setFilter(ALL_METHOD_FILTER);
            Class<ProxyObject> proxyClass = factory.createClass();
            result = proxyClass.newInstance();
            Constructor<?> constructor = impl.getConstructor(clazz);
            Object target = constructor.newInstance(service);
            result.setHandler(new ServiceMethodHandler(service,target));
            serviceProxyMap.put(clazz,result);
            return (T)result;
        }
    }

    public  void register(Class<?> clazz){
        logger.debug(clazz.getName());
    }

    /**
     * 判断状态，统计暂停时间内阻止的调用次数(理论上应该可以扩展成限流)
     */
    public class ServiceMethodHandler implements MethodHandler{

        private IService service;

        private Object target;

        public ServiceMethodHandler (IService service,Object target) {
            this.service = service;
            this.target = target;
        }

        @Override
        public Object invoke (Object o, Method thisMethod, Method process, Object[] args) throws Throwable {
            ServiceState state = service.serviceState();
            Object result = null;
            try{
                switch(state){
                    case NEW:
                        throw new ServiceException("Service State is not SERVICE，current State is "+ ServiceState.NEW);
                    case SERVICE:
                        result = thisMethod.invoke(target, args);
                        break;
                    case PAUSE:
                        service.pauseRequestCount().getAndIncrement();
                        throw new ServiceException("Service State is not SERVICE，current State is "+ ServiceState.PAUSE);
                    case STOP:
                        throw new ServiceException("Service State is not SERVICE，current State is "+ ServiceState.STOP);
                }
            }catch (Throwable e){
                if(e instanceof ServiceException){
                    throw (ServiceException)e;
                }else{
                    throw new ServiceException(e);
                }
            }
            return result;
        }
    }
}
