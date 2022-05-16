package com.github.harboat.logger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Logger {

    private LogProducer producer;

    public <T> void info(ServiceType service, T body) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        producer.sendLog(
                InfoLog.builder()
                        .className(className)
                        .methodName(methodName)
                        .service(service)
                        .content(body)
                        .build()
        );
    }

    public <T> void info(ServiceType service, String message, T body) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        producer.sendLog(
                InfoLog.builder()
                    .className(className)
                    .methodName(methodName)
                    .service(service)
                    .message(message)
                    .content(body)
                    .build()
        );
    }

}
