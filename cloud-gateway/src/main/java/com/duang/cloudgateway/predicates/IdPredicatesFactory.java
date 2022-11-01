//package com.duang.cloudgateway.predicates;
//
//import lombok.Data;
//import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Predicate;
//
//
//@Component
//public class IdPredicatesFactory extends AbstractRoutePredicateFactory<IdPredicatesFactory.Config> {
//
//    public IdPredicatesFactory(Class<IdPredicatesFactory.Config> configClass) {
//        super(configClass);
//    }
//
//    //用于从配置文件中获取参数值赋值到配置类中的属性上
//    @Override
//    public List<String> shortcutFieldOrder() {
////这里的顺序要跟配置文件中的参数顺序一致
//        return Arrays.asList("minId", "maxId");
//    }
//
//    //断言
//    @Override
//    public Predicate<ServerWebExchange> apply(IdPredicatesFactory.Config config) {
//        return new Predicate<ServerWebExchange>() {
//            @Override
//            public boolean test(ServerWebExchange serverWebExchange) {
//                //从serverWebExchange获取传入的参数
//                String id = serverWebExchange.getRequest().getQueryParams().getFirst("id");
//
//                if (Objects.nonNull(id)) {
//                    return id.equals(config.getMaxId()); //  && idconfig.getMaxAge();
//                }
//                return true;
//            }
//        };
//    }
//
//
//    @Data
//    class Config {
//        private String minId;
//        private String maxId;
//    }
//
//}
//
//
//
